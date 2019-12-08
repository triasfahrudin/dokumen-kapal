<?php

defined('BASEPATH') or exit('No direct script access allowed');

class Admin extends MX_Controller
{
    private $user_id;

    public function __construct()
    {
        parent::__construct();
        date_default_timezone_set('Asia/Jakarta');

        $this->load->database();
        $this->load->helper(array('url', 'libs'));
        $this->load->library(array('form_validation', 'session', 'breadcrumbs'));

        $level = $this->session->userdata('user_level');
        $this->breadcrumbs->load_config('default');

        if ($level !== 'admin') {
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

    public function set_status_permohonan()
    {
        header('content-type: application/json');

        $jenis         = $this->input->post('jenis');
        $permohonan_id = $this->input->post('permohonan_id');
        $status        = $this->input->post('status');

        if ($status === 'ditolak') {

            $alasan = $this->input->post('alasan');

            $this->db->where('id', $permohonan_id);
            $this->db->update('permohonan_' . $jenis, array('status' => 'ditolak', 'alasan_status' => $alasan));

            echo json_encode('OK @' . date("YmdHis"));

        } elseif ($status === 'diterima' || $status === 'diambil') {

            $this->db->where('id', $permohonan_id);
            $this->db->update('permohonan_' . $jenis, array('status' => $status));

            echo json_encode('OK @' . date("YmdHis"));
        }

    }

    public function tampil_biodata()
    {
        header('content-type: application/json');
        $pemohon_id = $this->input->get('pemohon_id');

        $this->db->select("email,nama,foto,alamat,no_telp,no_buku_pelaut,masa_berlaku,file_buku_pelaut");

        $biodata = $this->db->get_where('pelaut', array('id' => $pemohon_id))->row_array();

        echo json_encode(
            array(
                'biodata' => $this->load->view('view_datadiri_pemohon', array('biodata' => $biodata), true),
            )
        );

    }

    public function alasan_status()
    {
        header('content-type: application/json');

        $jenis         = $this->input->post('jenis');
        $permohonan_id = $this->input->post('permohonan_id');

        if ($jenis === 'masa_layar') {
            $masa_layar = $this->db->get_where('permohonan_masa_layar', array('id' => $permohonan_id))->row_array();

            echo json_encode($masa_layar['alasan_status']);
        }

    }

    public function permohonan_masa_layar($filter_status = 'proses')
    {
        try {

            $this->load->library('grocery_CRUD');
            $crud = new Grocery_CRUD();

            //echo $filter_status;

            if ($filter_status === 'selesai') {
                $crud->where('status', 'diambil');
            } elseif ($filter_status === 'ditolak') {
                $crud->where('status', 'ditolak');
            } elseif ($filter_status === 'proses') {
                $crud->in_where('status', "('baru','diterima')");
                // $crud->where('status <>', 'ditolak');
                // $crud->where('status <>', 'diambil');
                // return;
            }

            $crud->set_table('permohonan_masa_layar');
            $crud->set_subject('Data Permohonan Masa Layar');

            $crud->columns('kode', 'nama', 'buku_pelaut', 'ijazah_laut', 'tgl_mohon', 'bukti_bayar', 'status');

            $crud->callback_column('kode', function ($value, $row) {

                return 'PML-' . str_pad($row->id, 6, '0', STR_PAD_LEFT);
            });

            $crud->callback_column('nama', function ($value, $row) {
                $this->db->where('id', $row->pelaut_id);
                $pelaut = $this->db->get('pelaut')->row_array();

                return '<a href="#!" onclick="tampil_biodata(' . $row->pelaut_id . ')">' . strtoupper($pelaut['nama']) . '</a>';
            });

            $crud->callback_column('ijazah_laut', function ($value, $row) {
                $this->db->where('id', $row->pelaut_id);
                $pelaut = $this->db->get('pelaut')->row_array();

                return '<a href="' . site_url('admin/ijazah-laut/' . $row->pelaut_id) . '">Lihat Data</a>';
            });

            $crud->callback_column('status', function ($value, $row) {
                $loading_div = '<img src="' . site_url('assets/admin/img/loading.gif') . '" id="p_loading_' . $row->id . '" style="display:none">';

                if ($row->status === 'baru') {
                    return $loading_div . '&nbsp;<span id="p_' . $row->id . '"><a class="text-primary" href="#!" onclick="set_status_permohonan(\'masa_layar\',\'diterima\',' . $row->id . ')">DISETUJUI</a>&nbsp;|&nbsp;<a class="text-danger" href="#!" onclick="set_status_permohonan(\'masa_layar\',\'ditolak\',' . $row->id . ')">DITOLAK</a></span>';
                } elseif ($row->status === 'diterima') {
                    return $loading_div . '&nbsp;<span class="text-primary" id="p_' . $row->id . '"><a href="#!" onclick="set_status_permohonan(\'masa_layar\',\'diambil\',' . $row->id . ')">AMBIL BERKAS</a></span>';
                } elseif ($row->status === 'ditolak') {
                    return '<span id="p_' . $row->id . '"><a class="text-danger" href="#!" onclick="alasan_status(\'masa_layar\',' . $row->id . ')">DITOLAK</a></span>';
                } elseif ($row->status === 'diambil') {
                    return '<span class="text-secondary" id="p_' . $row->id . '">SELESAI (' . convert_sql_date_to_date($row->tgl_update) . ')</span>';
                }

            });

            $crud->callback_column('buku_pelaut', function ($value, $row) {

                $this->db->where('id', $row->pelaut_id);
                $pelaut = $this->db->get('pelaut')->row_array();

                if (empty($pelaut['file_buku_pelaut'])) {
                    return 'Belum diunggah';
                } else {
                    return '<a href="' . site_url('uploads/buku_pelaut/' . $pelaut['file_buku_pelaut']) . '">Download</a>';
                }

            });

            $crud->callback_column('bukti_bayar', function ($value, $row) {

                if (empty($row->bukti_bayar)) {
                    return 'Belum diunggah';
                } else {
                    return '<a href="' . site_url('uploads/bukti_bayar/' . $row->bukti_bayar) . '">Download</a>';
                }

            });

            $crud->unset_add();
            $crud->unset_edit();
            $crud->unset_read();
            $crud->unset_delete();

            $this->breadcrumbs->push('Dashboard', '/admin');
            $this->breadcrumbs->push('Permohonan', '/admin/permohonan');

            if ($filter_status === 'selesai') {
                $this->breadcrumbs->push('Masa layar&nbsp;-&nbsp<span class="text-secondary">[SELESAI]</span>', '/admin/permohonan-masa-layar-selesai');
            } elseif ($filter_status === 'ditolak') {
                $this->breadcrumbs->push('Masa layar&nbsp;-&nbsp<span class="text-danger">[DITOLAK]</span>', '/admin/permohonan-masa-layar-ditolak');
            } elseif ($filter_status === 'proses') {
                $this->breadcrumbs->push('Masa layar&nbsp;-&nbsp<span class="text-success">[DALAM PROSES]</span>', '/admin/permohonan-masa-layar-proses');
            }

            $extra = array(
                'page_title'            => 'Permohonan Masa Layar',
                'show_filter_masalayar' => true,
            );

            $output = $crud->render();

            $output = array_merge((array) $output, $extra);

            $this->_page_output($output);
        } catch (Exception $e) {
            show_error($e->getMessage() . ' --- ' . $e->getTraceAsString());
        }
    }

    public function permohonan_sertifikat_keselamatan($filter_status = 'proses')
    {

        try {

            $this->load->library('grocery_CRUD');
            $crud = new Grocery_CRUD();

            if ($filter_status === 'selesai') {
                $crud->where('status', 'diambil');
            } elseif ($filter_status === 'ditolak') {
                $crud->where('status', 'ditolak');
            } elseif ($filter_status === 'proses') {
                $crud->in_where('status', "('baru','diterima')");
            }

            $crud->set_table('permohonan_sertifikat_kapal');
            $crud->set_subject('Data Permohonan Sertifikat Keselamatan');

            $crud->columns('kode', 'identitas_kapal',  'dokumen_kelengkapan', 'biaya', 'tgl_mohon', 'bukti_bayar', 'status');

            $crud->callback_column('kode', function ($value, $row) {

                return 'PS-' . str_pad($row->id, 6, '0', STR_PAD_LEFT);
            });

            $crud->callback_column('identitas_kapal', function ($value, $row) {
                
                $this->db->select('a.nama_kapal,jenis_kapal,a.imo_number,a.grt,b.nama AS agen_kapal');
                $this->db->join('perusahaan b','a.perusahaan_id = b.id','left');
                $this->db->where('a.id', $row->kapal_id);
                $kapal = $this->db->get('kapal a')->row_array();

                return '<table>
                          <tr>
                            <td>Kapal</td><td>:</td><td>' . $kapal['nama_kapal']. '</td>
                          </tr>
                          <tr>
                            <td>Jenis</td><td>:</td><td>' . $kapal['jenis_kapal']. '</td>
                          </tr>
                          <tr>
                            <td>IMO</td><td>:</td><td>' . $kapal['imo_number']. '</td>
                          </tr>
                          <tr>
                            <td>Grt</td><td>:</td><td>' . $kapal['grt']. '</td>
                          </tr>
                          <tr>
                            <td>Agen</td><td>:</td><td>' . $kapal['agen_kapal']. '</td>
                          </tr>
                        </table>';
            });

            $crud->callback_column('status', function ($value, $row) {
                $loading_div = '<img src="' . site_url('assets/admin/img/loading.gif') . '" id="p_loading_' . $row->id . '" style="display:none">';

                if ($row->status === 'baru') {
                    return $loading_div . '&nbsp;<span id="p_' . $row->id . '"><a class="text-primary" href="#!" onclick="set_status_permohonan(\'sertifikat_kapal\',\'diterima\',' . $row->id . ')">DISETUJUI</a>&nbsp;|&nbsp;<a class="text-danger" href="#!" onclick="set_status_permohonan(\'sertifikat_kapal\',\'ditolak\',' . $row->id . ')">DITOLAK</a></span>';
                } elseif ($row->status === 'diterima') {
                    return $loading_div . '&nbsp;<span class="text-primary" id="p_' . $row->id . '"><a href="#!" onclick="set_status_permohonan(\'sertifikat_kapal\',\'diambil\',' . $row->id . ')">AMBIL BERKAS</a></span>';
                } elseif ($row->status === 'ditolak') {
                    return '<span id="p_' . $row->id . '"><a class="text-danger" href="#!" onclick="alasan_status(\'sertifikat_kapal\',' . $row->id . ')">DITOLAK</a></span>';
                } elseif ($row->status === 'diambil') {
                    return '<span class="text-secondary" id="p_' . $row->id . '">SELESAI (' . convert_sql_date_to_date($row->tgl_update) . ')</span>';
                }

            });

            $crud->callback_column('dokumen_kelengkapan', function ($value, $row) {
                
                $su = empty($row->file_surat_ukur) ? 'Belum diunggah' : '<a href="' . site_url('uploads/sertifikat_kapal/' . $row->file_surat_ukur) . '">Download</a>';
                $sl = empty($row->file_surat_laut) ? 'Belum diunggah' : '<a href="' . site_url('uploads/sertifikat_kapal/' . $row->file_surat_laut) . '">Download</a>';    
                $sks = empty($row->file_sertifikat_keselamatan) ? 'Belum diunggah' : '<a href="' . site_url('uploads/sertifikat_kapal/' . $row->file_sertifikat_keselamatan) . '">Download</a>';
                $skl = empty($row->file_sertifikat_klasifikasi) ? 'Belum diunggah' : '<a href="' . site_url('uploads/sertifikat_kapal/' . $row->file_sertifikat_klasifikasi) . '">Download</a>';
                $spm = empty($row->file_sertifikat_pmk) ? 'Belum diunggah' : '<a href="' . site_url('uploads/sertifikat_kapal/' . $row->file_sertifikat_pmk) . '">Download</a>';
                $slf = empty($row->file_sertifikat_keselamatan) ? 'Belum diunggah' : '<a href="' . site_url('uploads/sertifikat_kapal/' . $row->file_sertifikat_keselamatan) . '">Download</a>';

                return '<table>
                          <tr>
                            <td>Surat ukur</td><td>:</td>
                            <td>' . $su. '</td>
                          </tr>
                          <tr>
                            <td>Surat laut</td><td>:</td>
                            <td>' . $sl. '</td>
                          </tr>
                          <tr>
                            <td>Srtifikat keselamatan</td><td>:</td>
                            <td>' . $sks. '</td>
                          </tr>
                          <tr>
                            <td>Sertifikat klasifikasi</td><td>:</td>
                            <td>' . $skl. '</td>
                          </tr>
                          <tr>
                            <td>Sertifikat PMK</td><td>:</td>
                            <td>' . $spm. '</td>
                          </tr>
                          <tr>
                            <td>Sertifikat Liferaft</td><td>:</td>
                            <td>' . $slf. '</td>
                          </tr>
                        </table>';
            });
            
            $crud->callback_column('bukti_bayar', function ($value, $row) {

                if (empty($row->bukti_bayar)) {
                    return 'Belum diunggah';
                } else {
                    return '<a href="' . site_url('uploads/bukti_bayar/' . $row->bukti_bayar) . '">Download</a>';
                }

            });

            
            $crud->unset_add();
            $crud->unset_edit();
            $crud->unset_read();
            $crud->unset_delete();

            $this->breadcrumbs->push('Dashboard', '/admin');
            $this->breadcrumbs->push('Permohonan', '/admin/permohonan');

            if ($filter_status === 'selesai') {
                $this->breadcrumbs->push('Sertifikat Keselamatan&nbsp;-&nbsp<span class="text-secondary">[SELESAI]</span>', '/admin/permohonan-sertifikat-keselamatan-selesai');
            } elseif ($filter_status === 'ditolak') {
                $this->breadcrumbs->push('Sertifikat Keselamatan&nbsp;-&nbsp<span class="text-danger">[DITOLAK]</span>', '/admin/permohonan-sertifikat-keselamatan-ditolak');
            } elseif ($filter_status === 'proses') {
                $this->breadcrumbs->push('Sertifikat Keselamatan&nbsp;-&nbsp<span class="text-success">[DALAM PROSES]</span>', '/admin/permohonan-sertifikat-keselamatan-proses');
            }

            $extra = array(
                'page_title'              => 'Permohonan Sertifikat Keselamatan Kapal',
                'show_filter_sertifikatkapal' => true,
            );
            $output = $crud->render();

            $output = array_merge((array) $output, $extra);

            $this->_page_output($output);
        } catch (Exception $e) {
            show_error($e->getMessage() . ' --- ' . $e->getTraceAsString());
        }
    }

    public function permohonan_bongkar_muat($filter_status = 'proses')
    {
        try {

            $this->load->library('grocery_CRUD');
            $crud = new Grocery_CRUD();

            if ($filter_status === 'selesai') {
                $crud->where('status', 'diambil');
            } elseif ($filter_status === 'ditolak') {
                $crud->where('status', 'ditolak');
            } elseif ($filter_status === 'proses') {
                $crud->in_where('status', "('baru','diterima')");
            }

            $crud->set_table('permohonan_bongkar_muat');
            $crud->set_subject('Data Permohonan Bongkar Muat');

            $crud->columns('kode', 'nama_perusahaan',  'jenis_muatan', 'biaya','file_surat_permohonan', 'tgl_mohon', 'bukti_bayar', 'status');

            $crud->callback_column('kode', function ($value, $row) {

                return 'PBM-' . str_pad($row->id, 6, '0', STR_PAD_LEFT);
            });

            $crud->callback_column('nama_perusahaan', function ($value, $row) {
                $this->db->where('id', $row->perusahaan_id);
                $perusahaan = $this->db->get('perusahaan')->row_array();

                return strtoupper($perusahaan['nama']);
            });

            $crud->callback_column('status', function ($value, $row) {
                $loading_div = '<img src="' . site_url('assets/admin/img/loading.gif') . '" id="p_loading_' . $row->id . '" style="display:none">';

                if ($row->status === 'baru') {
                    return $loading_div . '&nbsp;<span id="p_' . $row->id . '"><a class="text-primary" href="#!" onclick="set_status_permohonan(\'bongkar_muat\',\'diterima\',' . $row->id . ')">DISETUJUI</a>&nbsp;|&nbsp;<a class="text-danger" href="#!" onclick="set_status_permohonan(\'bongkar_muat\',\'ditolak\',' . $row->id . ')">DITOLAK</a></span>';
                } elseif ($row->status === 'diterima') {
                    return $loading_div . '&nbsp;<span class="text-primary" id="p_' . $row->id . '"><a href="#!" onclick="set_status_permohonan(\'bongkar_muat\',\'diambil\',' . $row->id . ')">AMBIL BERKAS</a></span>';
                } elseif ($row->status === 'ditolak') {
                    return '<span id="p_' . $row->id . '"><a class="text-danger" href="#!" onclick="alasan_status(\'bongkar_muat\',' . $row->id . ')">DITOLAK</a></span>';
                } elseif ($row->status === 'diambil') {
                    return '<span class="text-secondary" id="p_' . $row->id . '">SELESAI (' . convert_sql_date_to_date($row->tgl_update) . ')</span>';
                }

            });

            $crud->callback_column('file_surat_permohonan', function ($value, $row) {

                if (empty($row->file_surat_permohonan)) {
                    return 'Belum diunggah';
                } else {
                    return '<a href="' . site_url('uploads/bongkar_muat/' . $row->file_surat_permohonan) . '">Download</a>';
                }

            });

            $crud->callback_column('bukti_bayar', function ($value, $row) {

                if (empty($row->bukti_bayar)) {
                    return 'Belum diunggah';
                } else {
                    return '<a href="' . site_url('uploads/bukti_bayar/' . $row->bukti_bayar) . '">Download</a>';
                }

            });

            $crud->callback_column('jenis_muatan', function ($value, $row) {

                return $row->jenis_muatan . '&nbsp;(' . $row->bobot . '&nbsp;Ton)';

            });

            $crud->display_as('file_surat_permohonan', 'Surat permohonan');
            $crud->display_as('nama_perusahaan', 'Perusahaan');
            $crud->display_as('jenis_muatan', 'Muatan');

            $crud->unset_add();
            $crud->unset_edit();
            $crud->unset_read();
            $crud->unset_delete();

            $this->breadcrumbs->push('Dashboard', '/admin');
            $this->breadcrumbs->push('Permohonan', '/admin/permohonan');

            if ($filter_status === 'selesai') {
                $this->breadcrumbs->push('Bongkar Muat&nbsp;-&nbsp<span class="text-secondary">[SELESAI]</span>', '/admin/permohonan-bongkar-muat-selesai');
            } elseif ($filter_status === 'ditolak') {
                $this->breadcrumbs->push('Bongkar Muat&nbsp;-&nbsp<span class="text-danger">[DITOLAK]</span>', '/admin/permohonan-bongkar-muat-ditolak');
            } elseif ($filter_status === 'proses') {
                $this->breadcrumbs->push('Bongkar Muat&nbsp;-&nbsp<span class="text-success">[DALAM PROSES]</span>', '/admin/permohonan-bongkar-muat-proses');
            }

            $extra = array(
                'page_title'              => 'Permohonan Bongkar Muat',
                'show_filter_bongkarmuat' => true,
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

    public function ijazah_laut($pelaut_id)
    {
        try {

            $this->load->library('grocery_CRUD');
            $crud = new Grocery_CRUD();

            $crud->where('pelaut_id', $pelaut_id);
            $crud->set_table('ijazah_laut');
            $crud->set_subject('Data Ijazah laut');

            // $crud->add_fields(array('level', 'email', 'password'));
            // $crud->edit_fields(array('level', 'email'));

            // $crud->required_fields('level', 'email', 'password');
            // $crud->callback_before_insert(array($this, 'encrypt_password_callback'));

            $crud->columns('nama', 'penerbit', 'tgl_terbit', 'file');

            $crud->callback_column('file', function ($value, $row) {

                if (empty($row->file)) {
                    return 'Belum diunggah';
                } else {
                    return '<a href="' . site_url('uploads/ijazah_laut/' . $row->file) . '">Download</a>';
                }

            });

            $crud->unset_add();
            $crud->unset_edit();
            $crud->unset_read();
            $crud->unset_delete();

            // $crud->unset_read_fields('password');

            $this->breadcrumbs->push('Dashboard', '/admin');
            $this->breadcrumbs->push('Permohonan', '/admin/permohonan');
            $this->breadcrumbs->push('Masa layar', '/admin/permohonan-masa-layar');
            $this->breadcrumbs->push('Ijazah laut', '/admin/ijazah-laut/' . $pelaut_id);

            $extra  = array('page_title' => 'Data Ijazah Laut');
            $output = $crud->render();

            $output = array_merge((array) $output, $extra);

            $this->_page_output($output);
        } catch (Exception $e) {
            show_error($e->getMessage() . ' --- ' . $e->getTraceAsString());
        }
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

            $this->breadcrumbs->push('Dashboard', '/admin');
            $this->breadcrumbs->push('Data Pengguna', '/admin/data-pengguna');

            $output = array_merge((array) $output, $extra);

            $this->_page_output($output);
        } catch (Exception $e) {
            show_error($e->getMessage() . ' --- ' . $e->getTraceAsString());
        }
    }
    //</admin_user>

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

            $this->breadcrumbs->push('Dashboard', '/admin');
            $this->breadcrumbs->push('Data Biaya', '/admin/data-biaya');

            $output = array_merge((array) $output, $extra);

            $this->_page_output($output);
        } catch (Exception $e) {
            show_error($e->getMessage() . ' --- ' . $e->getTraceAsString());
        }
    }
    //</admin_user>

}
