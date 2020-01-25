<?php

/*

pemohon                             petugas
100                              menunggu pembayaran                 -

200 (upload bayar)               menunggu verifikasi pembayaran      [BAYAR TERIMA]  | [BAYAR TOLAK]

210 (verifikasi bayar berhasil)  menunggu verifikasi berkas          [BERKAS TERIMA] | [BERKAS TOLAK]

310 (verifikasi berkas berhasil) silahkan mengambil dokumen          [DOKUMEN DIAMBIL]

399 (verifikasi berkas gagal)    verifikasi berkas gagal             verifikasi berkas gagal
[UPLOAD ULANG]                      MENUNGGU UPLOAD ULANG
=> 210

299 (verifikasi bayar gagal)     verifikasi bayar gagal              verifikasi bayar gagal
[UPLOAD ULANG]                      MENUNGGU UPLOAD ULANG
=> 200

400                              selesai (diambil pada ...)          [SELESAI]

 */

defined('BASEPATH') or exit('No direct script access allowed');

class Manage extends MX_Controller
{
    private $user_id;
    private $user_level;

    public function __construct()
    {
        parent::__construct();
        date_default_timezone_set('Asia/Jakarta');

        $this->load->database();
        $this->load->helper(array('url', 'libs'));
        $this->load->library(array('form_validation', 'session', 'breadcrumbs'));

        $this->user_level = $this->session->userdata('user_level');
        $this->breadcrumbs->load_config('default');

        $level = array('admin', 'petugas', 'kepala');

        if (!in_array($this->user_level, $level)) {
            redirect(site_url('signin'), 'reload');
        }

        $this->output->set_header('Last-Modified: ' . gmdate('D, d M Y H:i:s') . ' GMT');
        $this->output->set_header('Cache-Control: no-store, no-cache, must-revalidate, no-transform, max-age=0, post-check=0, pre-check=0');
        $this->output->set_header('Pragma: no-cache');
        $this->output->set_header('Expires: Mon, 26 Jul 1997 05:00:00 GMT');
    }

    public function _page_output($output = null)
    {
        $this->load->view('master_view.php', (array) $output);
    }

    private function _count_tab_permohonan($table, $filter_status)
    {

        $ex = explode('.', $filter_status);

        $this->db->where_in('status', $ex);
        $ret = $this->db->get($table);
        return $ret->num_rows();
    }

    public function notifikasi()
    {

        header("content-type: application/json");

        $param_a = $this->uri->segment(3, '-');

        if ($param_a === "tab_permohonan") {

            $param_b = str_replace('-', '_', $this->uri->segment(4, '-')); //table

            /*
            $('#tab_proses').html(data.tab_proses);
            $('#tab_tungguambil').html(data.tab_tungguambil);
            $('#tab_selesai').html(data.tab_selesai);
            $('#tab_revisi').html(data.tab_revisi);
             */

            echo json_encode(
                array(
                    'tab_proses'      => $this->_count_tab_permohonan($param_b, '200.210'),
                    'tab_tungguambil' => $this->_count_tab_permohonan($param_b, '310'),
                    'tab_selesai'     => $this->_count_tab_permohonan($param_b, '400'),
                    'tab_revisi'      => $this->_count_tab_permohonan($param_b, '299.399'),
                )
            );

        } elseif ($param_a === 'upper_alert') {
            /*
            $( $('#dd_alert_total').html(data.alert_total);
            $('#dd_alert_sertifikat_keselamatan').html(data.alert_sertifikat);
            $('#dd_alert_bongkar_muat').html(data.alert_bongkar_muat);
            $('#dd_alert_masa_layar').html(data.alert_masa_layar);
             */

            $dd_alert_sertifikat_keselamatan = $this->_count_tab_permohonan('sertifikat_keselamatan', '200.210');
            $dd_alert_bongkar_muat           = $this->_count_tab_permohonan('bongkar_muat', '200.210');
            $dd_alert_masa_layar             = $this->_count_tab_permohonan('masa_layar', '200.210');

            echo json_encode(
                array(
                    'dd_alert_total'                  => ($dd_alert_sertifikat_keselamatan + $dd_alert_bongkar_muat + $dd_alert_masa_layar),
                    'dd_alert_sertifikat_keselamatan' => $dd_alert_sertifikat_keselamatan,
                    'dd_alert_bongkar_muat'           => $dd_alert_bongkar_muat,
                    'dd_alert_masa_layar'             => $dd_alert_masa_layar,
                )
            );

        } elseif ($param_a === 'riwayat_permohonan.200') {

            $this->db->limit(1);
            $rps = $this->db->get_where('riwayat_permohonan', array('status' => '200', 'notified' => 'N'));

            if ($rps->num_rows() > 0) {
                $rp = $rps->row_array();

                $this->db->where('id', $rp['id']);
                $this->db->update('riwayat_permohonan', array('notified' => 'Y'));

                echo json_encode(
                    array(
                        'jml_notif' => $rps->num_rows(),
                        'pesan'     => "Ada permohonan " . str_replace("_", " ", $rp['jenis']) . " baru!",
                        'url'       => site_url('manage/' . str_replace("_", "-", $rp['jenis']) . '/200.210'),
                    )
                );
            } else {
                echo json_encode(
                    array(
                        'jml_notif' => 0,
                        'pesan'     => '-',
                        'url'       => '-',
                    )
                );
            }

        }
    }

    public function download_sertifikat($id, $download = "false")
    {

        require APPPATH . '/third_party/phpword/PHPWord.php';

        $template = FCPATH . "uploads/dok_sertifikat_keselamatan.docx";
        $PHPWord  = new PHPWord();
        $document = $PHPWord->loadTemplate($template);

        $kapal = $this->db->get_where('kapal', array('id' => $id));

        if ($kapal->num_rows() > 0) {
            $k = $kapal->row_array();

            $document->setValue('NAMA_KAPAL', $k['nama_kapal']);
            $document->setValue('KODE_PENGENAL', $k['kode_pengenal']);
            $document->setValue('PELABUHAN_DAFTAR', $k['pelabuhan_daftar']);
            $document->setValue('GRT', $k['grt']);
            $document->setValue('IMO_NUMBER', $k['imo_number']);
            $document->setValue('TGL_KONTRAK', $k['tgl_kontrak']);
            $document->setValue('TGL_PELETAKAN_LUNAS', $k['tgl_peletakan_lunas']);
            $document->setValue('TGL_SERAH_TERIMA', $k['tgl_serah_terima']);
            $document->setValue('TGL_PERUBAHAN', $k['tgl_perubahan']);
            $document->setValue('LAMBUNG_TIMBUL', $k['lambung_timbul']);

        } else {

            $document->setValue('NAMA_KAPAL', '');
            $document->setValue('KODE_PENGENAL', '');
            $document->setValue('PELABUHAN_DAFTAR', '');
            $document->setValue('GRT', '');
            $document->setValue('IMO_NUMBER', '');
            $document->setValue('TGL_KONTRAK', '');
            $document->setValue('TGL_PELETAKAN_LUNAS', '');
            $document->setValue('TGL_SERAH_TERIMA', '');
            $document->setValue('TGL_PERUBAHAN', '');
            $document->setValue('LAMBUNG_TIMBUL', '');

        }

        $file_save_path = FCPATH . "uploads/" . $id . '-sertifikat_keselamatan.docx';
        $document->save($file_save_path);

        if ($download === "true") {
            $this->load->helper('download');
            $data = file_get_contents($file_save_path); // Read the file's contents
            $name = $id . '-sertifikat_keselamatan.docx';

            force_download($name, $data);

        } else {
            return site_url("uploads/" . $id . '-sertifikat_keselamatan.docx');
        }

    }

    public function download_bongkarmuat($id, $download = "false")
    {
        require APPPATH . '/third_party/phpword/PHPWord.php';

        $template = FCPATH . "uploads/dok_bongkar_muat.docx";
        $PHPWord  = new PHPWord();
        $document = $PHPWord->loadTemplate($template);

        $this->db->select('b.nama AS nama_perusahaan,
                           a.tgl_mohon,
                           a.nama_kapal,
                           a.agen_kapal,
                           a.jenis_angkutan,
                           a.angkutan_nopol AS nopol,
                           a.angkutan_supir AS nama_supir,
                           a.tgl_pelaksanaan,
                           a.jenis_muatan AS jenis_barang,
                           a.bobot AS berat,
                           a.jenis_kapal,
                           a.gt_kapal AS gt,
                           a.tgl_update AS tgl_dikeluarkan');
        $this->db->join('pemohon b', 'a.pemohon_id = b.id', 'left');
        $bongkar_muat = $this->db->get_where('bongkar_muat a', array('a.id' => $id));

        if ($bongkar_muat->num_rows() > 0) {

            $bm = $bongkar_muat->row_array();

            $document->setValue('NAMA_PERUSAHAAN', $bm['nama_perusahaan']);
            $document->setValue('TGL_MOHON', convert_sql_date_to_date($bm['tgl_mohon']));
            $document->setValue('NAMA_KAPAL', $bm['nama_kapal']);
            $document->setValue('AGEN_KAPAL', $bm['agen_kapal']);
            $document->setValue('JENIS_ANGKUTAN', $bm['jenis_angkutan']);
            $document->setValue('NOPOL', $bm['nopol']);
            $document->setValue('NAMA_SUPIR', $bm['nama_supir']);
            $document->setValue('TGL_PELAKSANAAN', convert_sql_date_to_date($bm['tgl_pelaksanaan']));
            $document->setValue('JENIS_BARANG', $bm['jenis_barang']);
            $document->setValue('BERAT', $bm['berat'] . ' Ton');
            $document->setValue('JENIS_KAPAL', $bm['jenis_kapal']);
            $document->setValue('GT', $bm['gt']);
            $document->setValue('TGL_DIKELUARKAN', $bm['tgl_dikeluarkan']);

        } else {

            $document->setValue('NAMA_PERUSAHAAN', '-');
            $document->setValue('TGL_MOHON', '-');
            $document->setValue('NAMA_KAPAL', '-');
            $document->setValue('AGEN_KAPAL', '-');
            $document->setValue('JENIS_ANGKUTAN', '-');
            $document->setValue('NOPOL', '-');
            $document->setValue('NAMA_SUPIR', '-');
            $document->setValue('TGL_PELAKSANAAN', '-');
            $document->setValue('JENIS_BARANG', '-');
            $document->setValue('BERAT', '-');
            $document->setValue('JENIS_KAPAL', '-');
            $document->setValue('GT', '-');
            $document->setValue('TGL_DIKELUARKAN', '-');

        }

        $file_save_path = FCPATH . "uploads/" . $id . '-bongkar_muat.docx';
        $document->save($file_save_path);

        if ($download === "true") {
            $this->load->helper('download');
            $data = file_get_contents($file_save_path); // Read the file's contents
            $name = $id . '-bongkar_muat.docx';

            force_download($name, $data);
        } else {
            return site_url("uploads/" . $id . '-bongkar_muat.docx');
        }

    }

    public function download_masalayar($id, $download = "false")
    {
        require APPPATH . '/third_party/phpword/PHPWord.php';

        $template = FCPATH . "uploads/dok_masa_layar.docx";
        $PHPWord  = new PHPWord();
        $document = $PHPWord->loadTemplate($template);

        $this->db->select('a.pemohon_id,
                           b.nama,
                           CONCAT(b.tempat_lahir,"/",b.tanggal_lahir) AS ttl,
                           c.nomor_buku AS buku_pelaut,
                           CONCAT(d.nama_sertifikat," TAHUN ", YEAR(d.tgl_terbit)) AS ijazah,
                           a.tgl_update AS tgl_dikeluarkan');
        $this->db->join('pemohon b', 'a.pemohon_id = b.id', 'left');
        $this->db->join('buku_pelaut c', 'b.id = c.pemohon_id', 'left');
        $this->db->join('(SELECT pemohon_id,
                                 nama_sertifikat,
                                 tgl_terbit,
                                 MAX(id)
                          FROM sertifikat_pelaut
                          GROUP BY pemohon_id) d', 'c.pemohon_id = d.pemohon_id', 'left');

        $query = $this->db->get_where('masa_layar a', array('a.pemohon_id' => $id));

        if ($query->num_rows() > 0) {

            $q = $query->row_array();

            $document->setValue('NAMA_PEMOHON', $q['nama']);
            $document->setValue('TTL', $q['ttl']);
            $document->setValue('BUKU_PELAUT', $q['buku_pelaut']);
            $document->setValue('IJAZAH', $q['ijazah']);
            $document->setValue('TGL_DIKELUARKAN', $q['tgl_dikeluarkan']);

            $this->db->select('nama_kapal,
                               tenaga_mesin,
                               jabatan,
                               tgl_naik,
                               tgl_turun,
                               timestampdiff(YEAR,tgl_naik,tgl_turun) AS diff_year,
                               timestampdiff(MONTH,tgl_naik,tgl_turun) AS diff_month');
            $this->db->order_by('tgl_naik', 'ASC');
            $riwayat_pelayaran = $this->db->get_where('riwayat_pelayaran', array('pemohon_id' => $q['pemohon_id']));

            $loop = 0;
            $ttot = 0;
            $btot = 0;
            foreach ($riwayat_pelayaran->result_array() as $rp) {
                $loop += 1;
                $document->setValue('KAPAL_' . $loop, $rp['nama_kapal']);
                $document->setValue('TMESIN_' . $loop, $rp['tenaga_mesin']);
                $document->setValue('JABATAN_' . $loop, $rp['jabatan']);
                $document->setValue('NAIK_' . $loop, convert_sql_date_to_date($rp['tgl_naik']));
                $document->setValue('TURUN_' . $loop, convert_sql_date_to_date($rp['tgl_turun']));
                $document->setValue('THN_' . $loop, $rp['diff_year']);
                $document->setValue('BLN_' . $loop, $rp['diff_month'] - ($rp['diff_year'] * 12));

                $ttot += $rp['diff_year'];
                $btot += $rp['diff_month'] - ($rp['diff_year'] * 12);
            }

            $document->setValue('TTOT', $ttot);
            $document->setValue('BTOT', $btot);

            if ($riwayat_pelayaran->num_rows() < 5) {
                for ($i = $loop; $i <= 5; $i++) {
                    $document->setValue('KAPAL_' . $i, "");
                    $document->setValue('TMESIN_' . $i, "");
                    $document->setValue('JABATAN_' . $i, "");
                    $document->setValue('NAIK_' . $i, "");
                    $document->setValue('TURUN_' . $i, "");
                    $document->setValue('THN_' . $i, "");
                    $document->setValue('BLN_' . $i, "");
                }
            }

            $btot       = ($ttot * 12) + $btot;
            $btot_year  = floor($btot / 12);
            $btot_month = $btot - ($btot_year * 12);

            $document->setValue('BTTOT', $btot_year . " TAHUN " . $btot_month . " BULAN");

            // $this->db->select('timestampdiff(YEAR,MIN(tgl_turun),MAX(tgl_naik))');
            // $this->db->where('pemohon_id',$q['pemohon_id']);
            // $riwayat_pelayaran = $this->db->get('riwayat_pelayaran');

            // if($riwayat_pelayaran->num_rows() > 0){

            // }else{

            // }

        } else {
            $document->setValue('NAMA_PEMOHON', '-');
            $document->setValue('TTL', '-');
            $document->setValue('BUKU_PELAUT', '-');
            $document->setValue('IJAZAH', '-');
            $document->setValue('TGL_DIKELUARKAN', '-');

        }

        $file_save_path = FCPATH . "uploads/" . $id . '-masa_layar.docx';
        $document->save($file_save_path);

        if ($download === "true") {
            $this->load->helper('download');
            $data = file_get_contents($file_save_path); // Read the file's contents
            $name = $id . '-masa_layar.docx';

            force_download($name, $data);
        } else {
            return site_url("uploads/" . $id . '-masa_layar.docx');
        }

    }

    public function sendNotification($token, $pesan)
    {
        //$token   = 'dDgIPQCmgr4:APA91bFg7rsPZZgR1sVr0bDo-7DG94QlBgTEFaywJSKtwBy3-BvQaF_tAwbcI-am1otnVzB_ifQH_6vc7JisD8Lh6acfuTaCjoXH3xdxJyWGaRxGnncJjrxLJSV7bUqhrMDRgR8U3kTe'; // push token
        //$message = "Test notification message";

        define('API_ACCESS_KEY', 'AAAApjBSNG8:APA91bEPf0di1IgVRo_xmmoNz_KF_tmZPMbJZTFG2SQhK-bnwuDdFsizFvuSCCdBocdIEwCZDBKAnL5SW_vHniRgtTkZDmcKt9YPOwf6_IyiDj494mbD-oKltvgP9jYOaijWmpuMkjK6');
        $fcmUrl = 'https://fcm.googleapis.com/fcm/send';

        $notification = [
            'title' => $pesan['title'],
            'body'  => $pesan['body_of_message'],
            'icon'  => 'myIcon',
            'sound' => 'mySound',
        ];

        $extraNotificationData = ["message" => $notification, "moredata" => 'dd'];

        $fcmNotification = [
            'to'           => $token, //single token
            'notification' => $notification,
            'data'         => $extraNotificationData,
        ];

        $headers = [
            'Authorization: key=' . API_ACCESS_KEY,
            'Content-Type: application/json',
        ];

        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $fcmUrl);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fcmNotification));
        $result = curl_exec($ch);
        curl_close($ch);
    }

    public function laporan($page = "default")
    {

        $data = array();

        $level = array('admin', 'kepala');

        if (!in_array($this->user_level, $level)) {
            redirect(site_url('web'), 'reload');
        }

        $this->breadcrumbs->push('Dashboard', '/manage');

        if ($page === "default") {

            $this->breadcrumbs->push('Laporan', '/manage/laporan/');

            $data['page_name']  = 'laporan';
            $data['page_title'] = 'Laporan';

        } elseif ($page === "jumlah_permohonan") {

            $this->breadcrumbs->push('Laporan', '/manage/laporan/');
            $this->breadcrumbs->push('Jumlah Permohonan', '/manage/laporan/jumlah-permohonan');

            $data['page_name']  = 'laporan/jumlah_permohonan/menu';
            $data['page_title'] = 'Laporan';

        } elseif ($page === "jumlah_permohonan_masa_layar") {

            $this->breadcrumbs->push('Laporan', '/manage/laporan/');
            $this->breadcrumbs->push('Jumlah Permohonan', '/manage/laporan/jumlah-permohonan');
            $this->breadcrumbs->push('Masa Layar', '/manage/laporan/jumlah-permohonan-masa-layar');

            $this->db->distinct();
            $this->db->select('YEAR(tgl_mohon) AS tahun');
            $data['tahun'] = $this->db->get('masa_layar');

            $post_tahun             = $this->input->post('tahun');
            $data['selected_tahun'] = $post_tahun;

            $data['page_name']  = 'laporan/jumlah_permohonan/masa_layar';
            $data['page_title'] = 'Laporan';

        } elseif ($page === "jumlah_permohonan_sertifikat_keselamatan") {

            $this->breadcrumbs->push('Laporan', '/manage/laporan/');
            $this->breadcrumbs->push('Jumlah Permohonan', '/manage/laporan/jumlah-permohonan');
            $this->breadcrumbs->push('Sertifikat Keselamatan', '/manage/laporan/jumlah-permohonan-sertifikat-keselamatan');

            $this->db->distinct();
            $this->db->select('YEAR(tgl_mohon) AS tahun');
            $data['tahun'] = $this->db->get('sertifikat_keselamatan');

            $post_tahun             = $this->input->post('tahun');
            $data['selected_tahun'] = $post_tahun;

            $data['page_name']  = 'laporan/jumlah_permohonan/sertifikat_keselamatan';
            $data['page_title'] = 'Laporan';

        } elseif ($page === "jumlah_permohonan_bongkar_muat") {

            $this->breadcrumbs->push('Laporan', '/manage/laporan/');
            $this->breadcrumbs->push('Jumlah Permohonan', '/manage/laporan/jumlah-permohonan');
            $this->breadcrumbs->push('Bongkar Muat', '/manage/laporan/jumlah-permohonan-bongkar-muat');

            $this->db->distinct();
            $this->db->select('YEAR(tgl_mohon) AS tahun');
            $data['tahun'] = $this->db->get('bongkar_muat');

            $post_tahun             = $this->input->post('tahun');
            $data['selected_tahun'] = $post_tahun;

            $data['page_name']  = 'laporan/jumlah_permohonan/bongkar_muat';
            $data['page_title'] = 'Laporan';

        } elseif ($page === "waktu_proses") {

            $this->breadcrumbs->push('Laporan', '/manage/laporan/');
            $this->breadcrumbs->push('Waktu Proses', '/manage/laporan/waktu-proses');

            $data['page_name']  = 'laporan/waktu_proses/menu';
            $data['page_title'] = 'Laporan';

        } elseif ($page === "waktu_proses_masa_layar") {

            $this->breadcrumbs->push('Laporan', '/manage/laporan/');
            $this->breadcrumbs->push('Waktu Proses', '/manage/laporan/waktu-proses');
            $this->breadcrumbs->push('Masa Layar', '/manage/laporan/waktu-proses-masa-layar');

            $this->db->distinct();
            $this->db->select('YEAR(tgl_mohon) AS tahun');
            $data['tahun'] = $this->db->get('masa_layar');

            $post_tahun             = $this->input->post('tahun');
            $data['selected_tahun'] = $post_tahun;

            $data['page_name']  = 'laporan/waktu_proses/masa_layar';
            $data['page_title'] = 'Laporan';

        } elseif ($page === "waktu_proses_sertifikat_keselamatan") {

            $this->breadcrumbs->push('Laporan', '/manage/laporan/');
            $this->breadcrumbs->push('Waktu Proses', '/manage/laporan/waktu-proses');
            $this->breadcrumbs->push('Sertifikat Keselamatan', '/manage/laporan/waktu-proses-sertifikat-keselamatan');

            $this->db->distinct();
            $this->db->select('YEAR(tgl_mohon) AS tahun');
            $data['tahun'] = $this->db->get('sertifikat_keselamatan');

            $post_tahun             = $this->input->post('tahun');
            $data['selected_tahun'] = $post_tahun;

            $data['page_name']  = 'laporan/waktu_proses/sertifikat_keselamatan';
            $data['page_title'] = 'Laporan';

        } elseif ($page === "waktu_proses_bongkar_muat") {

            $this->breadcrumbs->push('Laporan', '/manage/laporan/');
            $this->breadcrumbs->push('Waktu Proses', '/manage/laporan/waktu-proses');
            $this->breadcrumbs->push('Bongkar Muat', '/manage/laporan/waktu-proses-bongkar-muat');

            $this->db->distinct();
            $this->db->select('YEAR(tgl_mohon) AS tahun');
            $data['tahun'] = $this->db->get('bongkar_muat');

            $post_tahun             = $this->input->post('tahun');
            $data['selected_tahun'] = $post_tahun;

            $data['page_name']  = 'laporan/waktu_proses/bongkar_muat';
            $data['page_title'] = 'Laporan';

        } elseif ($page === "rating") {

            $this->breadcrumbs->push('Laporan', '/manage/laporan/');
            $this->breadcrumbs->push('Rating', '/manage/laporan/rating');

            $data['page_name']  = 'laporan/rating/menu';
            $data['page_title'] = 'Laporan';

        } elseif ($page === "rating_masa_layar") {

            $this->breadcrumbs->push('Laporan', '/manage/laporan/');
            $this->breadcrumbs->push('Rating', '/manage/laporan/rating');
            $this->breadcrumbs->push('Masa Layar', '/manage/laporan/rating-masa-layar');

            $this->db->distinct();
            $this->db->select('YEAR(tgl_mohon) AS tahun');
            $data['tahun'] = $this->db->get('masa_layar');

            $post_tahun             = $this->input->post('tahun');
            $data['selected_tahun'] = $post_tahun;

            $data['page_name']  = 'laporan/rating/masa_layar';
            $data['page_title'] = 'Laporan';

        } elseif ($page === "rating_sertifikat_keselamatan") {

            $this->breadcrumbs->push('Laporan', '/manage/laporan/');
            $this->breadcrumbs->push('Rating', '/manage/laporan/rating');
            $this->breadcrumbs->push('Sertifikat Keselamatan', '/manage/laporan/rating-sertifikat-keselamatan');

            $this->db->distinct();
            $this->db->select('YEAR(tgl_mohon) AS tahun');
            $data['tahun'] = $this->db->get('sertifikat_keselamatan');

            $post_tahun             = $this->input->post('tahun');
            $data['selected_tahun'] = $post_tahun;

            $data['page_name']  = 'laporan/rating/sertifikat_keselamatan';
            $data['page_title'] = 'Laporan';

        } elseif ($page === "rating_bongkar_muat") {

            $this->breadcrumbs->push('Laporan', '/manage/laporan/');
            $this->breadcrumbs->push('Rating', '/manage/laporan/rating');
            $this->breadcrumbs->push('Bongkar Muat', '/manage/laporan/rating-bongkar-muat');

            $this->db->distinct();
            $this->db->select('YEAR(tgl_mohon) AS tahun');
            $data['tahun'] = $this->db->get('bongkar_muat');

            $post_tahun             = $this->input->post('tahun');
            $data['selected_tahun'] = $post_tahun;

            $data['page_name']  = 'laporan/rating/bongkar_muat';
            $data['page_title'] = 'Laporan';

        } else {

            redirect(site_url('manage'), 'reload');

        }

        $this->_page_output($data);

    }

    public function index()
    {

        $data['page_name']  = 'dashboard';
        $data['page_title'] = 'Beranda';
        $this->_page_output($data);
    }

    public function permohonan()
    {

        $data['page_name']  = 'permohonan';
        $data['page_title'] = 'Permohonan';
        $this->_page_output($data);
    }

    public function settings($act = null, $param = null)
    {
        // $this->load->model(array('Basecrud_m'));
        $this->breadcrumbs->push('Setting', '/settings');

        $data['breadcrumbs'] = $this->breadcrumbs->show();

        if ($act === 'upload') {
            if (!empty($_FILES['img']['name'])) {
                $upload                  = array();
                $upload['upload_path']   = './uploads';
                $upload['allowed_types'] = 'jpeg|jpg|png';
                $upload['encrypt_name']  = true;

                $this->load->library('upload', $upload);

                if (!$this->upload->do_upload('img')) {
                    $data['msg'] = $this->upload->display_errors();
                } else {
                    $success  = $this->upload->data();
                    $value    = $success['file_name'];
                    $file_ext = $success['file_ext'];

                    $this->db->where('title', $param);
                    $this->db->update('settings', array('value' => $value, 'tipe' => 'image'));

                    redirect('manage/settings');
                }
            }
        } elseif ($act === 'edt') {
            $value = $this->input->post('value');

            $this->db->where('title', $param);
            $this->db->update('settings', array('value' => $value));

            exit(0);
        }

        $data['setting']    = $this->db->get('settings');
        $data['page_name']  = 'settings';
        $data['page_title'] = 'Data Settings';

        $this->_page_output($data);
    }

    public function ajax_status_permohonan()
    {
        header('content-type: application/json');

        if (in_array(date('w'), array('7'))) {

            echo json_encode(
                array(
                    'error'   => 'true',
                    'message' => 'Perubahan status dan atau pengambilan berkas hanya dapat dilakukan pada hari kerja!',
                )
            );

        } else {

            $jenis         = $this->input->post('jenis');
            $permohonan_id = $this->input->post('permohonan_id');
            $status        = $this->input->post('status');

            if ($status === '399' || $status == '299') {

                $alasan = $this->input->post('alasan');

                $this->db->where('id', $permohonan_id);
                $this->db->update($jenis,
                    array(
                        'status'        => $status,
                        'alasan_status' => $alasan,
                        'tgl_update'    => date('Y-m-d H:i:s'),
                    )
                );

                echo json_encode(
                    array(
                        'error'   => 'false',
                        'message' => 'Status berhasil diubah',
                    )
                );

            } else {

                if ($status === "310") {

                    //jika hari sabtu atau minggu, berikan notifikasi kalau bukan hari kerja

                    $res = $this->db->query("SELECT MakeDateList(tgl_upload_bukti_bayar,'" . date('Y-m-d') . "','1,2,3,4,5') + 1 AS hari_proses
                                             FROM $jenis WHERE id = $permohonan_id")->row_array();

                    $this->db->where('id', $permohonan_id);
                    $this->db->update($jenis,
                        array(
                            'status'                 => $status,
                            'tgl_update'             => date('Y-m-d H:i:s'),
                            'total_harikerja_proses' => $res['hari_proses'],
                        )
                    );

                    echo json_encode(
                        array(
                            'error'   => 'false',
                            'message' => 'Status berhasil diubah',
                        )
                    );

                } elseif ($status === "400") {

                    $file_path = "";
                    if ($jenis === "masa_layar") {
                        $file_path = $this->download_masalayar($permohonan_id, "false");
                        //echo $file_path;
                    } elseif ($jenis === "bongkar_muat") {
                        $file_path = $this->download_bongkarmuat($permohonan_id, "false");
                    } else {
                        $file_path = $this->download_sertifikat($permohonan_id, "false");
                    }

                    $this->db->where('id', $permohonan_id);
                    $this->db->update($jenis,
                        array(
                            'status'     => $status,
                            'tgl_update' => date('Y-m-d H:i:s'),
                        )
                    );

                    echo json_encode(
                        array(
                            'error'     => 'false',
                            'message'   => 'Status berhasil diubah',
                            'file_path' => $file_path,
                        )
                    );

                } else {
                    $this->db->where('id', $permohonan_id);
                    $this->db->update($jenis,
                        array(
                            'status'     => $status,
                            'tgl_update' => date('Y-m-d H:i:s'),
                        )
                    );

                    echo json_encode(
                        array(
                            'error'   => 'false',
                            'message' => 'Status berhasil diubah',
                        )
                    );
                }

            }

            $this->db->select("b.token_id,a.isi_notifikasi");
            $this->db->join('pemohon b', 'a.pemohon_id = b.id', 'left');
            $this->db->order_by('a.id', 'DESC');
            $this->db->limit(1);
            $pesan = $this->db->get_where('notifikasi a', array('jenis_permohonan' => $jenis, 'a.permohonan_id' => $permohonan_id))->row_array();

            // var_dump($pesan);
            $this->sendNotification($pesan['token_id'], array('title' => 'Notifikasi', 'body_of_message' => $pesan['isi_notifikasi']));

        }

    }

    public function ajax_riwayat_permohonan()
    {
        header('content-type: application/json');

        $permohonan_id = $this->input->get('permohonan_id');
        $jenis         = $this->input->get('jenis');

        $this->db->select('a.status,a.keterangan,a.tgl,b.arti');
        $this->db->order_by('tgl', 'ASC');
        $this->db->join('kode_status b', 'a.status = b.kode_angka', 'left');
        $riwayat = $this->db->get_where('riwayat_permohonan a', array('a.permohonan_id' => $permohonan_id, 'jenis' => $jenis));

        echo json_encode(
            $this->load->view('riwayat_permohonan', array('riwayat' => $riwayat), true)
        );
    }

    public function ajax_biodata()
    {
        header('content-type: application/json');
        $pemohon_id = $this->input->get('pemohon_id');

        $pemohon = $this->db->get_where('pemohon', array('id' => $pemohon_id))->row_array();

        if ($pemohon['jenis'] === 'perorangan') {
            echo json_encode(
                array(
                    'biodata' => $this->load->view('profile/profile_perorangan', array('profile' => $pemohon), true),
                )
            );
        } else {
            echo json_encode(
                array(
                    'biodata' => $this->load->view('profile/profile_perusahaan', array('profile' => $pemohon), true),
                )
            );
        }
    }

    public function ajax_detail_pemohon()
    {
        header('content-type: application/json');
        $pemohon_id = $this->input->get('pemohon_id');

        $pemohon = $this->db->get_where('pemohon', array('id' => $pemohon_id))->row_array();

        if ($pemohon['jenis'] === 'perorangan') {

            $buku_pelaut       = $this->db->get_where('buku_pelaut', array('pemohon_id' => $pemohon_id))->row_array();
            $sertifikat_pelaut = $this->db->get_where('sertifikat_pelaut', array('pemohon_id' => $pemohon_id));
            $masa_layar        = $this->db->get_where('masa_layar', array('pemohon_id' => $pemohon_id));
            $riwayat_pelayaran = $this->db->get_where('riwayat_pelayaran', array('pemohon_id' => $pemohon_id));

            echo json_encode(
                array(
                    'detail' => $this->load->view('profile/tab_content_perorangan',
                        array(
                            'profile'           => $this->load->view('profile/profile_perorangan', array('profile' => $pemohon), true),
                            'buku_pelaut'       => $this->load->view('profile/buku_pelaut', array('buku_pelaut' => $buku_pelaut, 'riwayat_pelayaran' => $riwayat_pelayaran), true),
                            'sertifikat_pelaut' => $this->load->view('profile/sertifikat_pelaut', array('sertifikat_pelaut' => $sertifikat_pelaut), true),
                            'masa_layar'        => $this->load->view('profile/permohonan_masa_layar', array('masa_layar' => $masa_layar), true),
                        ),
                        true),
                )
            );
        } else {

            $kapal = $this->db->get_where('kapal', array('pemohon_id' => $pemohon_id));

            $this->db->select('a.*');
            $this->db->join('kapal b', 'a.kapal_id = b.id', 'left');
            $this->db->join('pemohon c', 'b.pemohon_id = c.id', 'left');
            $sertifikat_keselamatan = $this->db->get_where('sertifikat_keselamatan a', array('c.id' => $pemohon_id));

            $bongkar_muat = $this->db->get_where('bongkar_muat', array('pemohon_id' => $pemohon_id));

            echo json_encode(
                array(
                    'detail' => $this->load->view('profile/tab_content_perusahaan',
                        array(
                            'profile'                => $this->load->view('profile/profile_perorangan', array('profile' => $pemohon), true),
                            'kapal'                  => $this->load->view('profile/data_kapal', array('kapal' => $kapal), true),
                            'sertifikat_keselamatan' => $this->load->view('profile/permohonan_sertifikat_keselamatan', array('sertifikat_keselamatan' => $sertifikat_keselamatan), true),
                            'bongkar_muat'           => $this->load->view('profile/permohonan_bongkar_muat', array('bongkar_muat' => $bongkar_muat), true),
                        ),
                        true),
                )
            );
        }
    }

    // public function ajax_ijazah_laut()
    // {
    //     header('content-type: application/json');
    //     $pemohon_id = $this->input->get('pemohon_id');

    //     $ijazah_laut = $this->db->get_where('ijazah_laut',array('pemohon_id' => $pemohon_id));

    //     echo json_encode(
    //         array(
    //             'ijazah_laut' => $this->load->view('ijazah_laut', array('ijazah_laut' => $ijazah_laut), true),
    //         )
    //     );
    // }

    public function berita()
    {
        try {
            $this->load->library(array('grocery_CRUD'));
            $crud = new Grocery_CRUD();

            $crud->set_table('berita');
            $crud->set_subject('Data Berita');

            // $crud->add_fields(array('level','username','password','email'));
            // $crud->edit_fields(array('level','username','email'));

            $crud->required_fields('judul', 'konten');
            $crud->set_field_upload('gambar', 'uploads/berita');

            $crud->columns('judul', 'konten');
            // $crud->unique_fields(array('username','email'));

            // $crud->unset_read_fields('password');
            $crud->field_type('tgl_post', 'hidden');
            $crud->field_type('slug', 'hidden');

            $crud->callback_after_insert(function ($post_array, $primary_key) {

                $this->db->where('id', $primary_key);
                $this->db->update('berita', array('tgl_post' => date('Y-m-d H:i:s')));

            });

            $this->breadcrumbs->push('Dashboard', '/manage');
            $this->breadcrumbs->push('Data Berita', '/manage/berita');

            $extra = array(
                'page_title' => 'Data Berita',
            );
            $output = $crud->render();

            $output = array_merge((array) $output, $extra);

            $this->_page_output($output);
        } catch (Exception $e) {
            show_error($e->getMessage() . ' --- ' . $e->getTraceAsString());
        }
    }

    public function ajax_alasan()
    {
        header('content-type: application/json');

        $jenis         = $this->input->post('jenis');
        $permohonan_id = $this->input->post('permohonan_id');

        if ($jenis === 'masa_layar') {
            $masa_layar = $this->db->get_where('masa_layar', array('id' => $permohonan_id))->row_array();

            echo json_encode($masa_layar['alasan_status']);
        } elseif ($jenis === 'bongkar_muat') {
            $bongkar_muat = $this->db->get_where('bongkar_muat', array('id' => $permohonan_id))->row_array();

            echo json_encode($bongkar_muat['alasan_status']);
        } else {
            $sertifikat_keselamatan = $this->db->get_where('sertifikat_keselamatan', array('id' => $permohonan_id))->row_array();

            echo json_encode($sertifikat_keselamatan['alasan_status']);
        }

    }

    public function ajax_komentar_rating()
    {
        header('content-type: application/json');

        $jenis         = $this->input->post('jenis');
        $permohonan_id = $this->input->post('permohonan_id');

        if ($jenis === 'masa_layar') {
            $masa_layar = $this->db->get_where('masa_layar', array('id' => $permohonan_id))->row_array();

            echo json_encode($masa_layar['komentar']);
        } elseif ($jenis === 'bongkar_muat') {
            $bongkar_muat = $this->db->get_where('bongkar_muat', array('id' => $permohonan_id))->row_array();

            echo json_encode($bongkar_muat['komentar']);
        } else {
            $sertifikat_keselamatan = $this->db->get_where('sertifikat_keselamatan', array('id' => $permohonan_id))->row_array();

            echo json_encode($sertifikat_keselamatan['komentar']);
        }

    }

    public function masa_layar($filter_status = '200.210.310')
    {
        try {

            $this->load->library('grocery_CRUD');
            $crud = new Grocery_CRUD();

            // if ($filter_status === 'selesai') {
            //     $crud->where('status', 'diambil');
            // } elseif ($filter_status === 'ditolak') {
            //     $crud->where('status', 'ditolak');
            // } elseif ($filter_status === 'proses') {
            //     $crud->in_where('status', "('baru','diterima')");
            // }

            $ex = explode('.', $filter_status);
            $crud->in_where("status", "('" . implode("','", $ex) . "')");

            $crud->set_table('masa_layar');
            $crud->set_subject('Data Permohonan Masa Layar');

            $crud->columns('kode', 'nama', 'buku_pelaut', 'sertifikat_laut', 'tgl_mohon', 'bukti_bayar', 'aksi');

            $crud->callback_column('kode', function ($value, $row) {
                return '<a href="#!" onClick="ajax_riwayat_permohonan(\'masa_layar\',' . $row->id . ')">PML-' . str_pad($row->id, 6, '0', STR_PAD_LEFT) . '</a>';
            });

            $crud->callback_column('nama', function ($value, $row) {
                $this->db->where('id', $row->pemohon_id);
                $pemohon = $this->db->get('pemohon')->row_array();

                return '<a href="#!" onclick="ajax_biodata(' . $row->pemohon_id . ')">' . strtoupper($pemohon['nama']) . '</a>';
            });

            $crud->callback_column('buku_pelaut', function ($value, $row) {

                $buku_pelaut = $this->db->get_where('buku_pelaut', array('pemohon_id' => $row->pemohon_id));

                $ret = '<table>';

                if ($buku_pelaut->num_rows() > 0) {
                    $bk = $buku_pelaut->row();
                    $ret .= '<tr><td>Nomor</td><td>' . $bk->nomor_buku . '</td></tr>';
                    if (!empty($buku_pelaut->file)) {
                        $ret .= '<tr><td>File</td><td><a target="_BLANK" href=' . site_url('uploads/dokumen/' . $bk->file) . '">Download</a></td></tr>';
                    } else {
                        $ret .= '<tr><td>File</td><td>Belum ada data</td></tr>';
                    }

                } else {
                    $ret .= '<tr><td>Nomor</td><td>Belum ada data</td></tr>';
                    $ret .= '<tr><td>File</td><td>Belum ada data</td></tr>';
                }

                $ret .= '</table>';

                return $ret;

            });

            $crud->callback_column('sertifikat_laut', function ($value, $row) {

                $this->db->order_by('tgl_terbit DESC');
                $this->db->limit(1);
                $sertifikat_pelaut = $this->db->get_where('sertifikat_pelaut', array('pemohon_id' => $row->pemohon_id));

                $ret = '<table>';

                if ($sertifikat_pelaut->num_rows()) {
                    $sp = $sertifikat_pelaut->row();

                    $ret .= '<tr><td>Nomor</td><td>' . $sp->nomor . '</td></tr>';
                    if (!empty($sp->file)) {
                        $ret .= '<tr><td>File</td><td><a target="_BLANK" href=' . site_url('uploads/dokumen/' . $sp->file) . '">Download</a></td></tr>';
                    } else {
                        $ret .= '<tr><td>File</td><td>Belum ada data</td></tr>';
                    }

                } else {

                    $ret .= '<tr><td>Nomor</td><td>Belum ada data</td></tr>';
                    $ret .= '<tr><td>File</td><td>Belum ada data</td></tr>';

                }

                $ret .= '</table>';

                return $ret;

            });

            $crud->callback_column('aksi', function ($value, $row) {
                $loading_div = '<img src="' . site_url('assets/manage/img/loading.gif') . '" id="p_loading_' . $row->id . '" style="display:none">';

                if ($row->status === '200') {
                    return $loading_div . '&nbsp;<span id="p_' . $row->id . '">BAYAR: <a class="text-primary" href="#!" onclick="ajax_status_permohonan(\'masa_layar\',\'210\',' . $row->id . ')">[DITERIMA]</a>&nbsp;|&nbsp;<a class="text-danger" href="#!" onclick="ajax_status_permohonan(\'masa_layar\',299,' . $row->id . ')">[DITOLAK]</a></span>';
                } elseif ($row->status === '210') {
                    return $loading_div . '&nbsp;<span id="p_' . $row->id . '">BERKAS: <a class="text-primary" href="#!" onclick="ajax_status_permohonan(\'masa_layar\',\'310\',' . $row->id . ')">[DITERIMA]</a>&nbsp;|&nbsp;<a class="text-danger" href="#!" onclick="ajax_status_permohonan(\'masa_layar\',399,' . $row->id . ')">[DITOLAK]</a></span>';
                } elseif ($row->status === '310') {
                    return '<span id="p_' . $row->id . '"><a class="text-success" href="#!" onclick="ajax_status_permohonan(\'masa_layar\',\'400\',' . $row->id . ')">[DOKUMEN DIAMBIL]</a></span>';
                } elseif ($row->status === '400') {
                    // return '<a style="color:orange" href="#!" onclick="ajax_komentar_rating(\'masa_layar\',' . $row->id . ')"' . make_ratings($row->rating_kepuasan) . '</a><br/><span class="text-secondary" id="p_' . $row->id . '">SELESAI (' . $row->tgl_update . ')</span>';

                    $div = '<a style="color:orange" href="#!" onclick="ajax_komentar_rating(\'masa_layar\',' . $row->id . ')"' . make_ratings($row->rating_kepuasan) . '</a><br/><span class="text-secondary" id="p_' . $row->id . '">SELESAI (' . convert_sql_date_to_date($row->tgl_update) . ')</span>';
                    $div .= '</br>Hari Proses&nbsp;:&nbsp;' . $row->total_harikerja_proses . '&nbsp;Hari (kerja)';
                    $div .= '</br><a href="'. site_url('manage/download_masalayar/' . $row->id . '/true') . '">Download dokumen</a>';
                    return $div;

                } elseif ($row->status === '299' || $row->status === '399') {
                    return $row->alasan_status;
                }

            });

            $crud->callback_column('bukti_bayar', function ($value, $row) {
                $biaya = 'Biaya:&nbsp;' . format_rupiah($row->biaya) . '</br>';
                if (empty($row->bukti_bayar)) {
                    return $biaya . 'Belum diunggah';
                } else {
                    return $biaya . '<a target="_BLANK" href="' . site_url('uploads/dokumen/' . $row->bukti_bayar) . '">Download</a>';
                }

            });

            $crud->unset_add();
            $crud->unset_edit();
            $crud->unset_read();
            $crud->unset_delete();

            $this->breadcrumbs->push('Dashboard', '/manage');
            $this->breadcrumbs->push('Permohonan', '/manage/permohonan');
            $this->breadcrumbs->push('Masa Layar', '/manage/masa-layar');

            // if ($filter_status === 'selesai') {
            //     $this->breadcrumbs->push('Masa layar&nbsp;-&nbsp<span class="text-secondary">[SELESAI]</span>', '/manage/masa-layar-selesai');
            // } elseif ($filter_status === 'ditolak') {
            //     $this->breadcrumbs->push('Masa layar&nbsp;-&nbsp<span class="text-danger">[DITOLAK]</span>', '/manage/masa-layar-ditolak');
            // } elseif ($filter_status === 'proses') {
            //     $this->breadcrumbs->push('Masa layar&nbsp;-&nbsp<span class="text-success">[DALAM PROSES]</span>', '/manage/masa-layar-proses');
            // }

            $extra = array(
                'page_title' => 'Permohonan Masa Layar',
                'filter'     => 'masa-layar',
            );

            $output = $crud->render();

            $output = array_merge((array) $output, $extra);

            $this->_page_output($output);
        } catch (Exception $e) {
            show_error($e->getMessage() . ' --- ' . $e->getTraceAsString());
        }
    }

    public function sertifikat_keselamatan($filter_status = 'proses')
    {

        try {

            $this->load->library('grocery_CRUD');
            $crud = new Grocery_CRUD();

            // if ($filter_status === 'selesai') {
            //     $crud->where('status', 'diambil');
            // } elseif ($filter_status === 'ditolak') {
            //     $crud->where('status', 'ditolak');
            // } elseif ($filter_status === 'proses') {
            //     $crud->in_where('status', "('baru','diterima')");
            // }

            $ex = explode('.', $filter_status);
            $crud->in_where("status", "('" . implode("','", $ex) . "')");

            $crud->set_table('sertifikat_keselamatan');
            $crud->set_subject('Data Permohonan Sertifikat Keselamatan');

            $crud->columns('kode', 'identitas_kapal', 'dokumen_kelengkapan', 'tgl_mohon', 'bukti_bayar', 'aksi');

            $crud->callback_column('kode', function ($value, $row) {

                return '<a href="#!" onClick="ajax_riwayat_permohonan(\'sertifikat_keselamatan\',' . $row->id . ')">PS-' . str_pad($row->id, 6, '0', STR_PAD_LEFT) . '</a>';
            });

            $crud->callback_column('identitas_kapal', function ($value, $row) {

                $this->db->select('a.nama_kapal,
                                   jenis_kapal,
                                   a.imo_number,
                                   a.grt,
                                   b.nama AS agen_kapal');
                $this->db->join('pemohon b', 'a.pemohon_id = b.id', 'left');
                $this->db->where('a.id', $row->kapal_id);
                $kapal = $this->db->get('kapal a')->row_array();

                return '<table>
                          <tr>
                            <td>Kapal</td>
                            <td>:</td>
                            <td>' . $kapal['nama_kapal'] . '</td>
                          </tr>
                          <tr>
                            <td>Jenis</td>
                            <td>:</td>
                            <td>' . $kapal['jenis_kapal'] . '</td>
                          </tr>
                          <tr>
                            <td>IMO</td>
                            <td>:</td>
                            <td>' . $kapal['imo_number'] . '</td>
                          </tr>
                          <tr>
                            <td>Grt</td>
                            <td>:</td>
                            <td>' . $kapal['grt'] . '</td>
                          </tr>
                          <tr>
                            <td>Agen</td>
                            <td>:</td>
                            <td>' . $kapal['agen_kapal'] . '</td>
                          </tr>
                        </table>';
            });

            // $crud->callback_column('status', function ($value, $row) {
            //     $loading_div = '<img src="' . site_url('assets/manage/img/loading.gif') . '" id="p_loading_' . $row->id . '" style="display:none">';

            //     if ($row->status === 'baru') {
            //         return $loading_div . '&nbsp;<span id="p_' . $row->id . '"><a class="text-primary" href="#!" onclick="ajax_status_permohonan(\'sertifikat_keselamatan\',\'diterima\',' . $row->id . ')">DISETUJUI</a>&nbsp;|&nbsp;<a class="text-danger" href="#!" onclick="ajax_status_permohonan(\'sertifikat_keselamatan\',\'ditolak\',' . $row->id . ')">DITOLAK</a></span>';
            //     } elseif ($row->status === 'diterima') {
            //         return $loading_div . '&nbsp;<span class="text-primary" id="p_' . $row->id . '"><a href="#!" onclick="ajax_status_permohonan(\'sertifikat_keselamatan\',\'diambil\',' . $row->id . ')">AMBIL BERKAS</a></span>';
            //     } elseif ($row->status === 'ditolak') {
            //         return '<span id="p_' . $row->id . '"><a class="text-danger" href="#!" onclick="ajax_alasan(\'sertifikat_keselamatan\',' . $row->id . ')">DITOLAK</a></span>';
            //     } elseif ($row->status === 'diambil') {
            //         return '<a style="color:orange" href="#!" onclick="ajax_komentar_rating(\'sertifikat_keselamatan\',' . $row->id . ')"' . make_ratings($row->rating_kepuasan) . '</a><br/><span class="text-secondary" id="p_' . $row->id . '">SELESAI (' . $row->tgl_update . ')</span>';
            //     }

            // });

            $crud->callback_column('aksi', function ($value, $row) {
                $loading_div = '<img src="' . site_url('assets/manage/img/loading.gif') . '" id="p_loading_' . $row->id . '" style="display:none">';

                if ($row->status === '200') {
                    return $loading_div . '&nbsp;<span id="p_' . $row->id . '"><a class="text-primary" href="#!" onclick="ajax_status_permohonan(\'sertifikat_keselamatan\',210,' . $row->id . ')">[BAYAR DITERIMA]</a>&nbsp;|&nbsp;<a class="text-danger" href="#!" onclick="ajax_status_permohonan(\'sertifikat_keselamatan\',299,' . $row->id . ')">[BAYAR DITOLAK]</a></span>';
                } elseif ($row->status === '210') {
                    return $loading_div . '&nbsp;<span id="p_' . $row->id . '"><a class="text-primary" href="#!" onclick="ajax_status_permohonan(\'sertifikat_keselamatan\',310,' . $row->id . ')">[BERKAS DITERIMA]</a>&nbsp;|&nbsp;<a class="text-danger" href="#!" onclick="ajax_status_permohonan(\'sertifikat_keselamatan\',399,' . $row->id . ')">[BERKAS DITOLAK]</a></span>';
                } elseif ($row->status === '310') {
                    return '<span id="p_' . $row->id . '"><a class="text-success" href="#!" onclick="ajax_status_permohonan(\'sertifikat_keselamatan\',400,' . $row->id . ')">[DOKUMEN DIAMBIL]</a></span>';
                } elseif ($row->status === '400') {
                    // return '<a style="color:orange" href="#!" onclick="ajax_komentar_rating(\'masa_layar\',' . $row->id . ')"' . make_ratings($row->rating_kepuasan) . '</a><br/><span class="text-secondary" id="p_' . $row->id . '">SELESAI (' . $row->tgl_update . ')</span>';

                    $div = '<a style="color:orange" href="#!" onclick="ajax_komentar_rating(\'sertifikat_keselamatan\',' . $row->id . ')"' . make_ratings($row->rating_kepuasan) . '</a><br/><span class="text-secondary" id="p_' . $row->id . '">SELESAI (' . convert_sql_date_to_date($row->tgl_update) . ')</span>';
                    $div .= '</br>Hari Proses&nbsp;:&nbsp;' . $row->total_harikerja_proses . '&nbsp;Hari (kerja)';
                    $div .= '</br><a target="_BLANK" href="'. site_url('manage/download_sertifikat/' . $row->id . '/true') . '">Download dokumen</a>';
                    return $div;

                } elseif ($row->status === '299' || $row->status === '399') {
                    return $row->alasan_status;
                }

            });

            $crud->callback_column('dokumen_kelengkapan', function ($value, $row) {

                $kapal = $this->db->get_where('kapal', array('id' => $row->kapal_id))->row();

                $su  = empty($kapal->file_surat_ukur) ? 'Belum diunggah' : '<a target="_BLANK" href="' . site_url('uploads/dokumen/' . $kapal->file_surat_ukur) . '">Download</a>';
                $sl  = empty($kapal->file_surat_laut) ? 'Belum diunggah' : '<a target="_BLANK" href="' . site_url('uploads/dokumen/' . $kapal->file_surat_laut) . '">Download</a>';
                $sks = empty($kapal->file_sertifikat_keselamatan) ? 'Belum diunggah' : '<a target="_BLANK" href="' . site_url('uploads/dokumen/' . $kapal->file_sertifikat_keselamatan) . '">Download</a>';
                $skl = empty($kapal->file_sertifikat_klasifikasi) ? 'Belum diunggah' : '<a target="_BLANK" href="' . site_url('uploads/dokumen/' . $kapal->file_sertifikat_klasifikasi) . '">Download</a>';
                $spm = empty($kapal->file_sertifikat_pmk) ? 'Belum diunggah' : '<a target="_BLANK" href="' . site_url('uploads/dokumen/' . $kapal->file_sertifikat_pmk) . '">Download</a>';
                $slf = empty($kapal->file_sertifikat_keselamatan) ? 'Belum diunggah' : '<a target="_BLANK" href="' . site_url('uploads/dokumen/' . $kapal->file_sertifikat_keselamatan) . '">Download</a>';

                return '<table>
                          <tr>
                            <td>Surat ukur</td>
                            <td>:</td>
                            <td>' . $su . '</td>
                          </tr>
                          <tr>
                            <td>Surat laut</td>
                            <td>:</td>
                            <td>' . $sl . '</td>
                          </tr>
                          <tr>
                            <td>Sertifikat keselamatan</td>
                            <td>:</td>
                            <td>' . $sks . '</td>
                          </tr>
                          <tr>
                            <td>Sertifikat klasifikasi</td>
                            <td>:</td>
                            <td>' . $skl . '</td>
                          </tr>
                          <tr>
                            <td>Sertifikat PMK</td>
                            <td>:</td>
                            <td>' . $spm . '</td>
                          </tr>
                          <tr>
                            <td>Sertifikat Liferaft</td>
                            <td>:</td>
                            <td>' . $slf . '</td>
                          </tr>
                        </table>';
            });

            $crud->callback_column('bukti_bayar', function ($value, $row) {
                $biaya = 'Biaya:&nbsp;' . format_rupiah($row->biaya) . '</br>';

                if (empty($row->bukti_bayar)) {
                    return $biaya . 'Belum diunggah';
                } else {
                    return $biaya . '<a target="_BLANK" href="' . site_url('uploads/dokumen/' . $row->bukti_bayar) . '">Download</a>';
                }

            });

            $crud->unset_add();
            $crud->unset_edit();
            $crud->unset_read();
            $crud->unset_delete();

            $this->breadcrumbs->push('Dashboard', '/manage');
            $this->breadcrumbs->push('Permohonan', '/manage/permohonan');
            $this->breadcrumbs->push('Sertifikat Keselamatan', '/manage/sertifikat-keselamatan');

            // if ($filter_status === 'selesai') {
            //     $this->breadcrumbs->push('Sertifikat Keselamatan&nbsp;-&nbsp<span class="text-secondary">[SELESAI]</span>', '/manage/sertifikat-keselamatan/selesai');
            // } elseif ($filter_status === 'ditolak') {
            //     $this->breadcrumbs->push('Sertifikat Keselamatan&nbsp;-&nbsp<span class="text-danger">[DITOLAK]</span>', '/manage/sertifikat-keselamatan/ditolak');
            // } elseif ($filter_status === 'proses') {
            //     $this->breadcrumbs->push('Sertifikat Keselamatan&nbsp;-&nbsp<span class="text-success">[DALAM PROSES]</span>', '/manage/sertifikat-keselamatan/proses');
            // }

            $extra = array(
                'page_title' => 'Permohonan Sertifikat Keselamatan Kapal',
                'filter'     => 'sertifikat-keselamatan',
            );
            $output = $crud->render();

            $output = array_merge((array) $output, $extra);

            $this->_page_output($output);
        } catch (Exception $e) {
            show_error($e->getMessage() . ' --- ' . $e->getTraceAsString());
        }
    }

    public function bongkar_muat($filter_status = '200.210.310')
    {
        try {

            $this->load->library('grocery_CRUD');
            $crud = new Grocery_CRUD();

            // if ($filter_status === 'selesai') {
            //     $crud->where('status', 'diambil');
            // } elseif ($filter_status === 'ditolak') {
            //     $crud->where('status', 'ditolak');
            // } elseif ($filter_status === 'proses') {
            //     $crud->in_where('status', "('baru','diterima')");
            // }

            $ex = explode('.', $filter_status);
            $crud->in_where("status", "('" . implode("','", $ex) . "')");

            $crud->set_table('bongkar_muat');
            $crud->set_subject('Data Permohonan Bongkar Muat');

            $crud->columns(
                'kode',
                'nama_perusahaan',
                'jenis_muatan',
                'angkutan',
                'file_surat_permohonan',
                'tgl_mohon',
                'bukti_bayar',
                'aksi'
            );

            $crud->callback_column('kode', function ($value, $row) {
                return '<a href="#!" onClick="ajax_riwayat_permohonan(\'bongkar_muat\',' . $row->id . ')">PBM-' . str_pad($row->id, 6, '0', STR_PAD_LEFT) . '</a>';
            });

            $crud->callback_column('nama_perusahaan', function ($value, $row) {
                $this->db->where('id', $row->pemohon_id);
                $pemohon = $this->db->get('pemohon')->row_array();

                return strtoupper($pemohon['nama']);
            });

            $crud->callback_column('jenis_muatan', function ($value, $row) {

                return $row->jenis_muatan . '&nbsp;(' . $row->bobot . '&nbsp;Ton)';

            });

            $crud->callback_column('angkutan', function ($value, $row) {

                return '<table>
                            <tr>
                                <td>Nama Kapal</td>
                                <td>' . $row->nama_kapal . '</td>
                            </tr>
                            <tr>
                                <td>Nopol Angkutan</td>
                                <td>' . $row->angkutan_nopol . '</td>
                            </tr>
                            <tr>
                                <td>Supir Angkutan</td>
                                <td>' . $row->angkutan_supir . '</td>
                            </tr>
                        </table>';

            });

            $crud->callback_column('file_surat_permohonan', function ($value, $row) {

                if (empty($row->file_surat_permohonan)) {
                    return 'Belum diunggah';
                } else {
                    return '<a target="_BLANK" href="' . site_url('uploads/dokumen/' . $row->file_surat_permohonan) . '">Download</a>';

                }

            });

            $crud->callback_column('aksi', function ($value, $row) {
                $loading_div = '<img src="' . site_url('assets/manage/img/loading.gif') . '" id="p_loading_' . $row->id . '" style="display:none">';

                // if ($row->status === 'baru') {
                //     return $loading_div . '&nbsp;<span id="p_' . $row->id . '"><a class="text-primary" href="#!" onclick="ajax_status_permohonan(\'bongkar_muat\',\'diterima\',' . $row->id . ')">DISETUJUI</a>&nbsp;|&nbsp;<a class="text-danger" href="#!" onclick="set_status_permohonan(\'bongkar_muat\',\'ditolak\',' . $row->id . ')">DITOLAK</a></span>';
                // } elseif ($row->status === 'diterima') {
                //     return $loading_div . '&nbsp;<span class="text-primary" id="p_' . $row->id . '"><a href="#!" onclick="ajax_status_permohonan(\'bongkar_muat\',\'diambil\',' . $row->id . ')">AMBIL BERKAS</a></span>';
                // } elseif ($row->status === 'ditolak') {
                //     return '<span id="p_' . $row->id . '"><a class="text-danger" href="#!" onclick="ajax_alasan(\'bongkar_muat\',' . $row->id . ')">DITOLAK</a></span>';
                // } elseif ($row->status === 'diambil') {
                //     return '<a style="color:orange" href="#!" onclick="ajax_komentar_rating(\'bongkar_muat\',' . $row->id . ')"' .  make_ratings($row->rating_kepuasan) . '</a><br/><span class="text-secondary" id="p_' . $row->id . '">SELESAI (' . $row->tgl_update . ')</span>';
                // }

                if ($row->status === '200') {
                    return $loading_div . '&nbsp;<span id="p_' . $row->id . '"><a class="text-primary" href="#!" onclick="ajax_status_permohonan(\'bongkar_muat\',210,' . $row->id . ')">[BAYAR DITERIMA]</a>&nbsp;|&nbsp;<a class="text-danger" href="#!" onclick="ajax_status_permohonan(\'bongkar_muat\',299,' . $row->id . ')">[BAYAR DITOLAK]</a></span>';
                } elseif ($row->status === '210') {
                    return $loading_div . '&nbsp;<span id="p_' . $row->id . '"><a class="text-primary" href="#!" onclick="ajax_status_permohonan(\'bongkar_muat\',310,' . $row->id . ')">[BERKAS DITERIMA]</a>&nbsp;|&nbsp;<a class="text-danger" href="#!" onclick="ajax_status_permohonan(\'bongkar_muat\',399,' . $row->id . ')">[BERKAS DITOLAK]</a></span>';
                } elseif ($row->status === '310') {
                    return '<span id="p_' . $row->id . '"><a class="text-success" href="#!" onclick="ajax_status_permohonan(\'bongkar_muat\',400,' . $row->id . ')">[DOKUMEN DIAMBIL]</a></span>';
                } elseif ($row->status === '400') {
                    
                    $div = '<a style="color:orange" href="#!" onclick="ajax_komentar_rating(\'bongkar_muat\',' . $row->id . ')"' . make_ratings($row->rating_kepuasan) . '</a><br/><span class="text-secondary" id="p_' . $row->id . '">SELESAI (' . convert_sql_date_to_date($row->tgl_update) . ')</span>';
                    $div .= '</br>Hari Proses&nbsp;:&nbsp;' . $row->total_harikerja_proses . '&nbsp;Hari (kerja)';
                    $div .= '</br><a target="_BLANK" href="'. site_url('manage/download_bongkarmuat/' . $row->id . '/true') . '">Download dokumen</a>';
                    return $div;

                } elseif ($row->status === '299' || $row->status === '399') {
                    return $row->alasan_status;
                }

            });

            $crud->callback_column('bukti_bayar', function ($value, $row) {

                $biaya = 'Biaya:&nbsp;' . format_rupiah($row->biaya) . '</br>';
                if (empty($row->bukti_bayar)) {
                    return $biaya . 'Belum diunggah';
                } else {
                    $div = $biaya . '<a target="_BLANK" href="' . site_url('uploads/dokumen/' . $row->bukti_bayar) . '">Download</a>';
                    return $div . '</br>Upload bayar&nbsp;:&nbsp;' . convert_sql_date_to_date($row->tgl_upload_bukti_bayar);
                }

            });

            $crud->display_as('file_surat_permohonan', 'Surat permohonan');
            $crud->display_as('nama_perusahaan', 'Perusahaan');
            $crud->display_as('jenis_muatan', 'Muatan');

            $crud->unset_add();
            $crud->unset_edit();
            $crud->unset_read();
            $crud->unset_delete();

            $this->breadcrumbs->push('Dashboard', '/manage');
            $this->breadcrumbs->push('Permohonan', '/manage/permohonan');
            $this->breadcrumbs->push('Bongkar Muat', '/manage/bongkar-muat');

            // if ($filter_status === 'selesai') {
            //     $this->breadcrumbs->push('Bongkar Muat&nbsp;-&nbsp<span class="text-secondary">[SELESAI]</span>', '/manage/bongkar-muat/selesai');
            // } elseif ($filter_status === 'ditolak') {
            //     $this->breadcrumbs->push('Bongkar Muat&nbsp;-&nbsp<span class="text-danger">[DITOLAK]</span>', '/manage/bongkar-muat/ditolak');
            // } elseif ($filter_status === 'proses') {
            //     $this->breadcrumbs->push('Bongkar Muat&nbsp;-&nbsp<span class="text-success">[DALAM PROSES]</span>', '/manage/bongkar-muat/proses');
            // }

            $extra = array(
                'page_title' => 'Permohonan Bongkar Muat',
                'filter'     => 'bongkar-muat',
            );
            $output = $crud->render();

            $output = array_merge((array) $output, $extra);

            $this->_page_output($output);
        } catch (Exception $e) {
            show_error($e->getMessage() . ' --- ' . $e->getTraceAsString());
        }
    }

    public function encrypt_password_callback($post_array, $primary_key = null)
    {
        $post_array['password'] = md5($post_array['password']);
        return $post_array;
    }

    //<admin_user>
    public function data_pengguna()
    {
        try {

            $this->load->library('grocery_CRUD');
            $crud = new Grocery_CRUD();

            $crud->set_table('user');
            $crud->set_subject('Data Pengguna');

            $crud->add_fields(array('level', 'email', 'password'));
            $crud->edit_fields(array('level', 'email'));

            $crud->required_fields('level', 'email', 'password');
            $crud->callback_before_insert(array($this, 'encrypt_password_callback'));

            $crud->columns('email', 'level');

            $crud->unset_read_fields('password');

            $extra  = array('page_title' => 'Data Pengguna');
            $output = $crud->render();

            $this->breadcrumbs->push('Dashboard', '/manage');
            $this->breadcrumbs->push('Data Pengguna', '/manage/data-pengguna');

            $output = array_merge((array) $output, $extra);

            $this->_page_output($output);
        } catch (Exception $e) {
            show_error($e->getMessage() . ' --- ' . $e->getTraceAsString());
        }
    }
    //</admin_user>

    public function data_pemohon($filter_jenis = 'semua')
    {
        try {

            $this->load->library('grocery_CRUD');
            $crud = new Grocery_CRUD();

            if ($filter_jenis === 'semua') {

            } elseif ($filter_jenis === 'perorangan') {
                $crud->where('jenis', 'perorangan');
            } elseif ($filter_jenis === 'perusahaan') {
                $crud->where('jenis', 'perusahaan');
            }

            $crud->set_table('pemohon');
            $crud->set_subject('Data Pemohon');

            $crud->columns('jenis', 'nama', 'email', 'no_telp', 'alamat', 'detail');

            $crud->display_as('jenis', 'Jenis Akun');

            $crud->callback_column('jenis', function ($value, $row) {
                return strtoupper($row->jenis);
            });

            $crud->callback_column('nama', function ($value, $row) {
                return strtoupper($row->nama);
            });

            $crud->callback_column('detail', function ($value, $row) {
                return '<a href="#!" onclick="detail_pemohon(\'' . $row->id . '\')">Lihat</a>';
            });

            $extra = array(
                'page_title' => 'Data Pemohon',
            );
            $output = $crud->render();

            $this->breadcrumbs->push('Dashboard', '/manage');
            $this->breadcrumbs->push('Data Pengguna', '/manage/data-pemohon');

            $output = array_merge((array) $output, $extra);

            $this->_page_output($output);

        } catch (Exception $e) {
            show_error($e->getMessage() . ' --- ' . $e->getTraceAsString());
        }
    }

    //<admin_user>
    public function data_biaya()
    {
        try {

            $this->load->library('grocery_CRUD');
            $crud = new Grocery_CRUD();

            $crud->set_table('biaya');
            $crud->set_subject('Data Biaya');

            // $crud->add_fields(array('level', 'email', 'password'));
            $crud->edit_fields(array('kode', 'alias', 'keterangan', 'nominal'));

            $crud->field_type('kode', 'readonly');
            $crud->field_type('alias', 'readonly');
            $crud->field_type('keterangan', 'readonly');

            $crud->display_as('alias', 'Jenis Muatan');

            $crud->required_fields('nominal');

            $crud->columns('kode', 'alias', 'nominal', 'keterangan');

            $extra  = array('page_title' => 'Data Biaya');
            $output = $crud->render();

            $this->breadcrumbs->push('Dashboard', '/pemohon');
            $this->breadcrumbs->push('Data Biaya', '/pemohon/data-biaya');

            $output = array_merge((array) $output, $extra);

            $this->_page_output($output);
        } catch (Exception $e) {
            show_error($e->getMessage() . ' --- ' . $e->getTraceAsString());
        }
    }
    //</admin_user>

}
