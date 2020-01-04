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
            redirect(site_url('web'), 'reload');
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

    public function laporan()
    {

        $level = array('admin', 'kepala');

        if (!in_array($this->user_level, $level)) {
            redirect(site_url('web'), 'reload');
        }

        $data['page_name']  = 'laporan';
        $data['page_title'] = 'Laporan';
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

        $jenis         = $this->input->post('jenis');
        $permohonan_id = $this->input->post('permohonan_id');
        $status        = $this->input->post('status');

        if ($status === '399' || $status == '299') {

            $alasan = $this->input->post('alasan');

            $this->db->where('id', $permohonan_id);
            $this->db->update($jenis, array('status' => $status, 'alasan_status' => $alasan));

            echo json_encode('OK @' . date("YmdHis"));

        } else {

            $this->db->where('id', $permohonan_id);
            $this->db->update($jenis, array('status' => $status));

            echo json_encode('OK @' . date("YmdHis"));
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

                $buku_pelaut = $this->db->get_where('buku_pelaut', array('pemohon_id' => $row->pemohon_id))->row();

                $ret = '<table>';
                $ret .= '<tr><td>Nomor</td><td>' . $buku_pelaut->nomor_buku . '</td></tr>';
                if (!empty($buku_pelaut->file)) {
                    $ret .= '<tr><td>File</td><td><a href=' . site_url('uploads/dokumen/' . $buku_pelaut->file) . '">Download</a></td></tr>';
                } else {
                    $ret .= '<tr><td>File</td><td>Belum ada data</td></tr>';
                }

                $ret .= '</table>';

                return $ret;

            });

            $crud->callback_column('sertifikat_laut', function ($value, $row) {

                $this->db->order_by('tgl_terbit DESC');
                $this->db->limit(1);
                $sertifikat_pelaut = $this->db->get_where('sertifikat_pelaut', array('pemohon_id' => $row->pemohon_id))->row();

                $ret = '<table>';
                $ret .= '<tr><td>Nomor</td><td>' . $sertifikat_pelaut->nomor . '</td></tr>';
                if (!empty($sertifikat_pelaut->file)) {
                    $ret .= '<tr><td>File</td><td><a href=' . site_url('uploads/dokumen/' . $sertifikat_pelaut->file) . '">Download</a></td></tr>';
                } else {
                    $ret .= '<tr><td>File</td><td>Belum ada data</td></tr>';
                }

                $ret .= '</table>';

                return $ret;

            });

            $crud->callback_column('aksi', function ($value, $row) {
                $loading_div = '<img src="' . site_url('assets/manage/img/loading.gif') . '" id="p_loading_' . $row->id . '" style="display:none">';

                if ($row->status === '200') {
                    return $loading_div . '&nbsp;<span id="p_' . $row->id . '"><a class="text-primary" href="#!" onclick="ajax_status_permohonan(\'masa_layar\',210,' . $row->id . ')">[BAYAR DITERIMA]</a>&nbsp;|&nbsp;<a class="text-danger" href="#!" onclick="ajax_status_permohonan(\'masa_layar\',299,' . $row->id . ')">[BAYAR DITOLAK]</a></span>';
                } elseif ($row->status === '210') {
                    return $loading_div . '&nbsp;<span id="p_' . $row->id . '"><a class="text-primary" href="#!" onclick="ajax_status_permohonan(\'masa_layar\',310,' . $row->id . ')">[BERKAS DITERIMA]</a>&nbsp;|&nbsp;<a class="text-danger" href="#!" onclick="ajax_status_permohonan(\'masa_layar\',399,' . $row->id . ')">[BERKAS DITOLAK]</a></span>';
                } elseif ($row->status === '310') {
                    return '<span id="p_' . $row->id . '"><a class="text-success" href="#!" onclick="ajax_status_permohonan(\'masa_layar\',400,' . $row->id . ')">[DOKUMEN DIAMBIL]</a></span>';
                } elseif ($row->status === '400') {
                    return '<a style="color:orange" href="#!" onclick="ajax_komentar_rating(\'masa_layar\',' . $row->id . ')"' . make_ratings($row->rating_kepuasan) . '</a><br/><span class="text-secondary" id="p_' . $row->id . '">SELESAI (' . $row->tgl_update . ')</span>';
                } elseif ($row->status === '299' || $row->status === '399') {
                    return $row->alasan_status;
                }

            });

            $crud->callback_column('bukti_bayar', function ($value, $row) {
                $biaya = 'Biaya:&nbsp;' . format_rupiah($row->biaya) . '</br>';
                if (empty($row->bukti_bayar)) {
                    return $biaya . 'Belum diunggah';
                } else {
                    return $biaya . '<a href="' . site_url('uploads/bukti_bayar/' . $row->bukti_bayar) . '">Download</a>';
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
                    return '<a style="color:orange" href="#!" onclick="ajax_komentar_rating(\'masa_layar\',' . $row->id . ')"' . make_ratings($row->rating_kepuasan) . '</a><br/><span class="text-secondary" id="p_' . $row->id . '">SELESAI (' . $row->tgl_update . ')</span>';
                } elseif ($row->status === '299' || $row->status === '399') {
                    return $row->alasan_status;
                }

            });

            $crud->callback_column('dokumen_kelengkapan', function ($value, $row) {

                $kapal = $this->db->get_where('kapal', array('id' => $row->kapal_id))->row();

                $su  = empty($kapal->file_surat_ukur) ? 'Belum diunggah' : '<a href="' . site_url('uploads/dokumen/' . $kapal->file_surat_ukur) . '">Download</a>';
                $sl  = empty($kapal->file_surat_laut) ? 'Belum diunggah' : '<a href="' . site_url('uploads/dokumen/' . $kapal->file_surat_laut) . '">Download</a>';
                $sks = empty($kapal->file_sertifikat_keselamatan) ? 'Belum diunggah' : '<a href="' . site_url('uploads/dokumen/' . $kapal->file_sertifikat_keselamatan) . '">Download</a>';
                $skl = empty($kapal->file_sertifikat_klasifikasi) ? 'Belum diunggah' : '<a href="' . site_url('uploads/dokumen/' . $kapal->file_sertifikat_klasifikasi) . '">Download</a>';
                $spm = empty($kapal->file_sertifikat_pmk) ? 'Belum diunggah' : '<a href="' . site_url('uploads/dokumen/' . $kapal->file_sertifikat_pmk) . '">Download</a>';
                $slf = empty($kapal->file_sertifikat_keselamatan) ? 'Belum diunggah' : '<a href="' . site_url('uploads/dokumen/' . $kapal->file_sertifikat_keselamatan) . '">Download</a>';

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
                    return $biaya . '<a href="' . site_url('uploads/bukti_bayar/' . $row->bukti_bayar) . '">Download</a>';
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
                    return '<a href="' . site_url('uploads/dokumen/' . $row->file_surat_permohonan) . '">Download</a>';
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
                    return '<a style="color:orange" href="#!" onclick="ajax_komentar_rating(\'bongkar_muat\',' . $row->id . ')"' . make_ratings($row->rating_kepuasan) . '</a><br/><span class="text-secondary" id="p_' . $row->id . '">SELESAI (' . $row->tgl_update . ')</span>';
                } elseif ($row->status === '299' || $row->status === '399') {
                    return $row->alasan_status;
                }

            });

            $crud->callback_column('bukti_bayar', function ($value, $row) {

                $biaya = 'Biaya:&nbsp;' . format_rupiah($row->biaya) . '</br>';
                if (empty($row->bukti_bayar)) {
                    return $biaya . 'Belum diunggah';
                } else {
                    return $biaya . '<a href="' . site_url('uploads/bukti_bayar/' . $row->bukti_bayar) . '">Download</a>';
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
