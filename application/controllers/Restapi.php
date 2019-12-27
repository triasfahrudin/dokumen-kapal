<?php

//use Restserver\Libraries\REST_Controller;
defined('BASEPATH') or exit('No direct script access allowed');

// This can be removed if you use __autoload() in config.php OR use Modular Extensions
/** @noinspection PhpIncludeInspection */
//To Solve File REST_Controller not found

//require APPPATH . 'libraries/REST_Controller.php';
//require APPPATH . 'libraries/Format.php';

class Restapi extends CI_Controller
{
    // use REST_Controller {
    //     REST_Controller::__construct as private __resTraitConstruct;
    // }

    public function __construct()
    {
        parent::__construct();

        //$this->__resTraitConstruct();

        date_default_timezone_set('Asia/Jakarta');

        $this->load->database();
        $this->load->helper(array('url', 'libs'));

    }

    public function index()
    {
        header("content-type: application/json");

        echo json_encode(
            array(
                'message' => 'index :: RestAPI',
                'status'  => 'success',
            )
        );
    }

    public function get_kapal()
    {

        header("content-type: application/json");

        $pemohon_id = $this->input->get('pemohon_id');

        $this->db->select('id,
                           nama_kapal,
                           jenis_kapal,
                           IFNULL(imo_number,"-") AS imo_number,
                           IFNULL(grt,"0") AS grt,
                           IFNULL(kapasitas_penumpang,"0") AS kapasitas_penumpang,
                           IFNULL(kapasitas_roda_dua,"0") AS kapasitas_roda_dua,
                           IFNULL(kapasitas_roda_empat,"0") AS kapasitas_roda_empat');
        $qry = $this->db->get_where('kapal', array('pemohon_id' => $pemohon_id));

        echo json_encode(
            array(
                'kapalList' => $qry->result(),
            )
        );

    }

    public function get_riwayatpelayaran()
    {

        header("content-type: application/json");

        $pemohon_id = $this->input->get('pemohon_id');

        $this->db->select("id,
                           nama_kapal,
                           tenaga_mesin,
                           jabatan,
                           DATE_FORMAT(tgl_naik, '%d/%m/%Y') AS tgl_naik,
                           DATE_FORMAT(tgl_turun, '%d/%m/%Y') AS tgl_turun");
        $qry = $this->db->get_where('riwayat_pelayaran', array('pemohon_id' => $pemohon_id));

        echo json_encode(
            array(
                'riwayatPelayaranList' => $qry->result(),
            )
        );

    }

    public function get_sertifikatpelaut()
    {

        header("content-type: application/json");

        $pemohon_id = $this->input->get('pemohon_id');

        $this->db->select("id,
                           nama_sertifikat,
                           nomor,
                           penerbit,
                           DATE_FORMAT(tgl_terbit, '%d/%m/%Y') AS tgl_terbit,
                           DATE_FORMAT(tgl_berakhir, '%d/%m/%Y') AS tgl_berakhir");
        $qry = $this->db->get_where('sertifikat_pelaut', array('pemohon_id' => $pemohon_id));

        echo json_encode(
            array(
                'sertifikatPelautList' => $qry->result(),
            )
        );

    }

    public function get_profile($id)
    {
        $qry                         = $this->db->get_where('pemohon', array('id' => $id));
        $user                        = $qry->row_array();
        $response["error"]           = false;
        $response["user"]["id"]      = $user["id"];
        $response["user"]["nama"]    = $user["nama"];
        $response["user"]["email"]   = $user["email"];
        $response["user"]["alamat"]  = $user["alamat"];
        $response["user"]["no_telp"] = $user["no_telp"];

        echo json_encode($response);
    }

    public function get_bukupelaut($id)
    {
        $qry                                     = $this->db->get_where('buku_pelaut', array('pemohon_id' => $id));
        $buku_pelaut                             = $qry->row_array();
        $response["error"]                       = false;
        $response["buku_pelaut"]["id"]           = $buku_pelaut["id"];
        $response["buku_pelaut"]["nomor_buku"]   = $buku_pelaut["nomor_buku"];
        $response["buku_pelaut"]["kode_pelaut"]  = $buku_pelaut["kode_pelaut"];
        $response["buku_pelaut"]["nomor_daftar"] = $buku_pelaut["nomor_daftar"];

        echo json_encode($response);
    }

    public function notification()
    {

    }

    public function insertupdate_kapal()
    {

        header('content-type: application/json');

        $id                   = $this->input->post('id');
        $pemohon_id           = $this->input->post('pemohon_id');
        $nama_kapal           = $this->input->post('nama_kapal');
        $jenis_kapal          = $this->input->post('jenis_kapal');
        $imo_number           = $this->input->post('imo_number');
        $grt                  = $this->input->post('grt');
        $kapasitas_penumpang  = $this->input->post('kapasitas_penumpang');
        $kapasitas_roda_dua   = $this->input->post('kapasitas_roda_dua');
        $kapasitas_roda_empat = $this->input->post('kapasitas_roda_empat');

        if ($id == 0) {

            $this->db->insert('kapal',
                array(
                    'pemohon_id'           => $pemohon_id,
                    'nama_kapal'           => $nama_kapal,
                    'jenis_kapal'          => $jenis_kapal,
                    'imo_number'           => $imo_number,
                    'grt'                  => $grt,
                    'kapasitas_penumpang'  => $kapasitas_penumpang,
                    'kapasitas_roda_dua'   => $kapasitas_roda_dua,
                    'kapasitas_roda_empat' => $kapasitas_roda_empat,
                )
            );

            echo json_encode(
                array(
                    'status'    => "Upload berhasil",
                    'error_msg' => $this->db->error()['code'],
                    'error'     => false,
                    'last_id'   => $this->db->insert_id(),

                )
            );

        } else {

            $this->db->where('id', $id);
            $this->db->update('kapal',
                array(
                    'nama_kapal'           => $nama_kapal,
                    'jenis_kapal'          => $jenis_kapal,
                    'imo_number'           => $imo_number,
                    'grt'                  => $grt,
                    'kapasitas_penumpang'  => $kapasitas_penumpang,
                    'kapasitas_roda_dua'   => $kapasitas_roda_dua,
                    'kapasitas_roda_empat' => $kapasitas_roda_empat,
                )
            );

            echo json_encode(
                array(
                    'status'    => "Upload berhasil",
                    'error_msg' => $this->db->error()['code'],
                    'error'     => false,
                    'last_id'   => $id,

                )
            );
        }

    }

    public function insertupdate_riwayatpelayaran()
    {

        header('content-type: application/json');

        $id           = $this->input->post('id');
        $pemohon_id   = $this->input->post('pemohon_id');
        $nama_kapal   = $this->input->post('nama_kapal');
        $tenaga_mesin = $this->input->post('tenaga_mesin');
        $jabatan      = $this->input->post('jabatan');
        $tgl_naik     = $this->input->post('tgl_naik');
        $tgl_turun    = $this->input->post('tgl_turun');

        if ($id == 0) {

            $this->db->insert('riwayat_pelayaran',
                array(
                    'pemohon_id'   => $pemohon_id,
                    'nama_kapal'   => $nama_kapal,
                    'tenaga_mesin' => $tenaga_mesin,
                    'jabatan'      => $jabatan,
                    'tgl_naik'     => convert_date_to_sql_date($tgl_naik, 'd/m/Y'),
                    'tgl_turun'    => convert_date_to_sql_date($tgl_turun, 'd/m/Y'),
                )
            );

            echo json_encode(
                array(
                    'status'    => "Data berhasil simpan",
                    'error_msg' => $this->db->error()['code'],
                    'error'     => false,
                    'last_id'   => $this->db->insert_id(),

                )
            );

        } else {

            $this->db->where('id', $id);
            $this->db->update('riwayat_pelayaran',
                array(
                    'nama_kapal'   => $nama_kapal,
                    'tenaga_mesin' => $tenaga_mesin,
                    'jabatan'      => $jabatan,
                    'tgl_naik'     => convert_date_to_sql_date($tgl_naik, 'd/m/Y'),
                    'tgl_turun'    => convert_date_to_sql_date($tgl_turun, 'd/m/Y'),
                )
            );

            echo json_encode(
                array(
                    'status'    => "Data berhasil simpan",
                    'error_msg' => $this->db->error()['code'],
                    'error'     => false,
                    'last_id'   => $id,

                )
            );
        }

    }

    public function insertupdate_sertifikatpelaut()
    {

        header('content-type: application/json');

        $id              = $this->input->post('id');
        $pemohon_id      = $this->input->post('pemohon_id');
        $nama_sertifikat = $this->input->post('nama_sertifikat');
        $nomor           = $this->input->post('nomor');
        $penerbit        = $this->input->post('penerbit');
        $tgl_terbit      = $this->input->post('tgl_terbit');

        if ($id == 0) {

            $this->db->insert('sertifikat_pelaut',
                array(
                    'pemohon_id'      => $pemohon_id,
                    'nama_sertifikat' => $nama_sertifikat,
                    'nomor'           => $nomor,
                    'penerbit'        => $penerbit,
                    'tgl_terbit'      => convert_date_to_sql_date($tgl_terbit, 'd/m/Y'),

                )
            );

            echo json_encode(
                array(
                    'status'    => "Data berhasil simpan",
                    'error_msg' => $this->db->error()['code'],
                    'error'     => false,
                    'last_id'   => $this->db->insert_id(),

                )
            );

        } else {

            $this->db->where('id', $id);
            $this->db->update('sertifikat_pelaut',
                array(
                    'nama_sertifikat' => $nama_sertifikat,
                    'nomor'           => $nomor,
                    'penerbit'        => $penerbit,
                    'tgl_terbit'      => convert_date_to_sql_date($tgl_terbit, 'd/m/Y'),
                )
            );

            echo json_encode(
                array(
                    'status'    => "Data berhasil simpan",
                    'error_msg' => $this->db->error()['code'],
                    'error'     => false,
                    'last_id'   => $id,

                )
            );
        }

    }

    public function uploadfile()
    {
        /*
        $originalImgName = $_FILES['filename']['name'];
        $tempName        = $_FILES['filename']['tmp_name'];
        $folder          = "uploads/buku_pelaut/";
        $url             = site_url($originalImgName);

        if (move_uploaded_file($tempName, $folder . $originalImgName)) {

        //     $query = "INSERT INTO upload_image_video (pathToFile) VALUES ('$url')";
        //     if (mysqli_query($con, $query)) {

        //         $query    = "SELECT * FROM upload_image_video WHERE pathToFile='$url'";
        //         $result   = mysqli_query($con, $query);
        //         $emparray = array();
        //         if (mysqli_num_rows($result) > 0) {
        //             while ($row = mysqli_fetch_assoc($result)) {
        //                 $emparray[] = $row;
        //             }
        //             echo json_encode(array("status" => "true", "message" => "Successfully file added!", "data" => $emparray));

        //         } else {
        //             echo json_encode(array("status" => "false", "message" => "Failed!"));
        //         }

        //     } else {
        //         echo json_encode(array("status" => "false", "message" => "Failed!"));
        //     }
        //     //echo "moved to ".$url;
        } else {
        echo json_encode(array("status" => "false", "message" => "Failed!"));
        }
         */

        header('content-type: application/json');

        $jenis = $this->input->post('jenis');

        $upload['upload_path']   = './uploads/dokumen';
        $upload['allowed_types'] = 'pdf|jpg|jpeg|png|bmp';
        $upload['encrypt_name']  = true;
        $upload['overwrite']     = true;
        $upload['max_size']      = 1024;

        $this->load->library('upload', $upload);

        if (!$this->upload->do_upload('filename')) {

            echo json_encode(
                array(
                    'status'    => "Upload GAGAL",
                    'error_msg' => $this->upload->display_errors(),
                    'error'     => true,
                    'last_id'   => $id,

                )
            );

        } else {

            $success   = $this->upload->data();
            $file_name = $success['file_name'];

            switch ($jenis) {
                case '"buku_pelaut"':
                    $pemohon_id = $this->input->post('id');

                    $this->db->query(
                        "INSERT INTO buku_pelaut (pemohon_id,file)
                        VALUES($pemohon_id,'$file_name')
                        ON DUPLICATE KEY UPDATE file = '$file_name'"
                    );

                    echo json_encode(
                        array(
                            'status'    => "Upload berhasil",
                            'error_msg' => $this->db->error()['code'],
                            'error'     => false,

                        )
                    );

                    break;

                case '"kapal_surat_ukur"':

                    $id = $this->input->post('id');

                    if ($id == 0) {
                        //insert
                        $pemohon_id = $this->input->post('pemohon_id');

                        $this->db->insert('kapal',
                            array(
                                'pemohon_id'      => $pemohon_id,
                                'file_surat_ukur' => $file_name,
                            )
                        );

                        echo json_encode(
                            array(
                                'status'    => "Upload berhasil",
                                'error_msg' => $this->db->error()['code'],
                                'error'     => false,
                                'last_id'   => $this->db->insert_id(),

                            )
                        );

                    } else {

                        $this->db->where('id', $id);
                        $this->db->update('kapal', array('file_surat_ukur' => $file_name));

                        echo json_encode(
                            array(
                                'status'    => "Upload berhasil",
                                'error_msg' => $this->db->error()['code'],
                                'error'     => false,
                                'last_id'   => $id,

                            )
                        );
                    }

                    break;

                case '"sertifikat_pelaut"':

                    $id = $this->input->post('id');

                    if ($id == 0) {

                        $pemohon_id = $this->input->post('pemohon_id');

                        $this->db->insert('sertifikat_pelaut',
                            array(
                                'pemohon_id' => $pemohon_id,
                                'file'       => $file_name,
                            )
                        );

                        echo json_encode(
                            array(
                                'status'    => "Upload berhasil",
                                'error_msg' => $this->db->error()['code'],
                                'error'     => false,
                                'last_id'   => $this->db->insert_id(),

                            )
                        );

                    } else {

                        $this->db->where('id', $id);
                        $this->db->update('sertifikat_pelaut', array('file' => $file_name));

                        echo json_encode(
                            array(
                                'status'    => "Upload berhasil",
                                'error_msg' => $this->db->error()['code'],
                                'error'     => false,
                                'last_id'   => $id,

                            )
                        );
                    }

                    break;

                case '"kapal_surat_laut"':

                    $id = $this->input->post('id');

                    if ($id == 0) {
                        //insert
                        $pemohon_id = $this->input->post('pemohon_id');

                        $this->db->insert('kapal',
                            array(
                                'pemohon_id'      => $pemohon_id,
                                'file_surat_laut' => $file_name,
                            )
                        );

                        echo json_encode(
                            array(
                                'status'    => "Upload berhasil",
                                'error_msg' => $this->db->error()['code'],
                                'error'     => false,
                                'last_id'   => $this->db->insert_id(),

                            )
                        );

                    } else {

                        $this->db->where('id', $id);
                        $this->db->update('kapal', array('file_surat_laut' => $file_name));

                        echo json_encode(
                            array(
                                'status'    => "Upload berhasil",
                                'error_msg' => $this->db->error()['code'],
                                'error'     => false,
                                'last_id'   => $id,

                            )
                        );
                    }

                    break;

                case '"kapal_sertifikat_keselamatan"':

                    $id = $this->input->post('id');

                    if ($id == 0) {
                        //insert
                        $pemohon_id = $this->input->post('pemohon_id');

                        $this->db->insert('kapal',
                            array(
                                'pemohon_id'                  => $pemohon_id,
                                'file_sertifikat_keselamatan' => $file_name,
                            )
                        );

                        echo json_encode(
                            array(
                                'status'    => "Upload berhasil",
                                'error_msg' => $this->db->error()['code'],
                                'error'     => false,
                                'last_id'   => $this->db->insert_id(),

                            )
                        );

                    } else {

                        $this->db->where('id', $id);
                        $this->db->update('kapal', array('file_sertifikat_keselamatan' => $file_name));

                        echo json_encode(
                            array(
                                'status'    => "Upload berhasil",
                                'error_msg' => $this->db->error()['code'],
                                'error'     => false,
                                'last_id'   => $id,

                            )
                        );
                    }

                    break;

                case '"kapal_sertifikat_klasifikasi"':

                    $id = $this->input->post('id');

                    if ($id == 0) {
                        //insert
                        $pemohon_id = $this->input->post('pemohon_id');

                        $this->db->insert('kapal',
                            array(
                                'pemohon_id'                  => $pemohon_id,
                                'file_sertifikat_klasifikasi' => $file_name,
                            )
                        );

                        echo json_encode(
                            array(
                                'status'    => "Upload berhasil",
                                'error_msg' => $this->db->error()['code'],
                                'error'     => false,
                                'last_id'   => $this->db->insert_id(),

                            )
                        );

                    } else {

                        $this->db->where('id', $id);
                        $this->db->update('kapal', array('file_sertifikat_klasifikasi' => $file_name));

                        echo json_encode(
                            array(
                                'status'    => "Upload berhasil",
                                'error_msg' => $this->db->error()['code'],
                                'error'     => false,
                                'last_id'   => $id,

                            )
                        );
                    }

                    break;

                case '"kapal_sertifikat_pmk"':

                    $id = $this->input->post('id');

                    if ($id == 0) {
                        //insert
                        $pemohon_id = $this->input->post('pemohon_id');

                        $this->db->insert('kapal',
                            array(
                                'pemohon_id'          => $pemohon_id,
                                'file_sertifikat_pmk' => $file_name,
                            )
                        );

                        echo json_encode(
                            array(
                                'status'    => "Upload berhasil",
                                'error_msg' => $this->db->error()['code'],
                                'error'     => false,
                                'last_id'   => $this->db->insert_id(),

                            )
                        );

                    } else {

                        $this->db->where('id', $id);
                        $this->db->update('kapal', array('file_sertifikat_pmk' => $file_name));

                        echo json_encode(
                            array(
                                'status'    => "Upload berhasil",
                                'error_msg' => $this->db->error()['code'],
                                'error'     => false,
                                'last_id'   => $id,

                            )
                        );
                    }

                    break;

                case '"kapal_sertifikat_liferaft"':

                    $id = $this->input->post('id');

                    if ($id == 0) {
                        //insert
                        $pemohon_id = $this->input->post('pemohon_id');

                        $this->db->insert('kapal',
                            array(
                                'pemohon_id'               => $pemohon_id,
                                'file_sertifikat_liferaft' => $file_name,
                            )
                        );

                        echo json_encode(
                            array(
                                'status'    => "Upload berhasil",
                                'error_msg' => $this->db->error()['code'],
                                'error'     => false,
                                'last_id'   => $this->db->insert_id(),

                            )
                        );

                    } else {

                        $this->db->where('id', $id);
                        $this->db->update('kapal', array('file_sertifikat_liferaft' => $file_name));

                        echo json_encode(
                            array(
                                'status'    => "Upload berhasil",
                                'error_msg' => $this->db->error()['code'],
                                'error'     => false,
                                'last_id'   => $id,

                            )
                        );
                    }

                    break;

                case '"masa_layar"':
                    $id = $this->input->post('id');

                    $this->db->where('id', $id);

                    $this->db->update('masa_layar',
                        array(
                            'bukti_bayar'            => $file_name,
                            'tgl_upload_bukti_bayar' => date("Y-m-d H:i:s"),
                            'status'                 => 'baru',
                        )
                    );

                    echo json_encode(
                        array(
                            'status'    => "Upload berhasil",
                            'error_msg' => $this->db->error()['code'],
                            'error'     => false,
                            'last_id'   => $id,

                        )
                    );

                case '"sertifikat_keselamatan"':
                    $id = $this->input->post('id');

                    $this->db->where('id', $id);
                    $this->db->update('sertifikat_keselamatan',
                        array(
                            'bukti_bayar'            => $file_name,
                            'tgl_upload_bukti_bayar' => date("Y-m-d H:i:s"),
                            'status'                 => 'baru',
                        )
                    );

                    echo json_encode(
                        array(
                            'status'    => "Upload berhasil",
                            'error_msg' => $this->db->error()['code'],
                            'error'     => false,
                            'last_id'   => $id,

                        )
                    );

                case '"bongkar_muat"':
                    $id = $this->input->post('id');

                    $this->db->where('id', $id);
                    $this->db->update('bongkar_muat',
                        array(
                            'bukti_bayar'            => $file_name,
                            'tgl_upload_bukti_bayar' => date("Y-m-d H:i:s"),
                            'status'                 => 'baru',
                        )
                    );

                    echo json_encode(
                        array(
                            'status'    => "Upload berhasil",
                            'error_msg' => $this->db->error()['code'],
                            'error'     => false,
                            'last_id'   => $id,

                        )
                    );

                default:
                    # code...
                    break;
            }

        }
    }

    public function delete_kapal($id)
    {

        header("content-type: application/json");

        // $id = $this->input->get('id');

        $this->db->where('id', $id);
        $this->db->delete('kapal');

        echo json_encode(
            array(
                'status'    => "Hapus data berhasil",
                'error_msg' => $this->db->error()['code'],
                'error'     => false,
                'last_id'   => $id,

            )
        );

    }

    public function delete_riwayatpelayaran($id)
    {

        header("content-type: application/json");

        // $id = $this->input->get('id');

        $this->db->where('id', $id);
        $this->db->delete('riwayat_pelayaran');

        echo json_encode(
            array(
                'status'    => "Hapus data berhasil",
                'error_msg' => $this->db->error()['code'],
                'error'     => false,
                'last_id'   => $id,

            )
        );

    }

    public function delete_sertifikatpelaut($id)
    {

        header("content-type: application/json");

        // $id = $this->input->get('id');

        $this->db->where('id', $id);
        $this->db->delete('sertifikat_pelaut');

        echo json_encode(
            array(
                'status'    => "Hapus data berhasil",
                'error_msg' => $this->db->error()['code'],
                'error'     => false,
                'last_id'   => $id,

            )
        );

    }

    public function update_profile()
    {
        header('content-type: application/json');

        if (isset($_POST['id'])
            && isset($_POST['nama'])
            && isset($_POST['email'])
            && isset($_POST['no_telp'])
            && isset($_POST['alamat'])
        ) {

            $id      = $this->input->post('id');
            $nama    = $this->input->post('nama');
            $email   = $this->input->post('email');
            $no_telp = $this->input->post('no_telp');
            $alamat  = $this->input->post('alamat');

            $this->db->where('id', $id);
            $this->db->update('pemohon',
                array(
                    'nama'    => $nama,
                    'email'   => $email,
                    'no_telp' => $no_telp,
                    'alamat'  => $alamat,
                )
            );

            $response["error"] = false;
            echo json_encode($response);

        }
    }

    public function update_bukupelaut()
    {
        header('content-type: application/json');

        if (isset($_POST['pemohon_id'])
            && isset($_POST['nomor_buku'])
            && isset($_POST['kode_pelaut'])
            && isset($_POST['nomor_daftar'])) {

            $pemohon_id   = $this->input->post('pemohon_id');
            $nomor_buku   = $this->input->post('nomor_buku');
            $kode_pelaut  = $this->input->post('kode_pelaut');
            $nomor_daftar = $this->input->post('nomor_daftar');

            $this->db->query(
                "INSERT INTO buku_pelaut (pemohon_id,nomor_buku,kode_pelaut,nomor_daftar)
                 VALUES($pemohon_id,'$nomor_buku','$kode_pelaut','$nomor_daftar')
                 ON DUPLICATE KEY UPDATE nomor_buku = '$nomor_buku',
                                         kode_pelaut = '$kode_pelaut',
                                         nomor_daftar = '$nomor_daftar'"
            );

            $response["error"] = false;
            echo json_encode($response);

        }
    }

    public function register()
    {
        header('content-type: application/json');
        if (isset($_POST['jenis']) && isset($_POST['nama']) && isset($_POST['email']) && isset($_POST['password'])) {
            $jenis    = $_POST['jenis'];
            $nama     = $_POST['nama'];
            $email    = $_POST['email'];
            $password = $_POST['password'];

            $qry = $this->db->get_where('pemohon', array('email' => $email));

            if ($qry->num_rows() > 0) {
                $response["error"]     = true;
                $response["error_msg"] = 'Email ' . $email . " sudah digunakan! Gunakan Email lainnya";
                echo json_encode($response);
            } else {
                // create a new user
                // $user = $db->StoreUserInfo($name, $email, $password, $gender, $age);
                $this->db->query("SET sql_mode = '' ");
                $this->db->insert('pemohon', array(
                    'jenis'    => $jenis,
                    'nama'     => $nama,
                    'email'    => $email,
                    'password' => md5($password),
                )
                );

                $response["error"] = false;
                echo json_encode($response);

            }
        }
    }

    public function send_tokenid()
    {
        header('content-type: application/json');

        if (isset($_POST['pemohon_id']) && isset($_POST['token_id'])) {

            $pemohon_id = $_POST['pemohon_id'];
            $token_id   = $_POST['token_id'];

            $this->db->where('id', $pemohon_id);
            $this->db->update('pemohon', array('token_id' => $token_id));

            $response["error"]     = false;
            $response["error_msg"] = "Send Token BERHASIL";

            echo json_encode($response);

        } else {

            $response["error"]     = true;
            $response["error_msg"] = "Send Token ID GAGAL!";
            echo json_encode($response);
        }

    }

    public function login()
    {
        header('content-type: application/json');

        if (isset($_POST['email']) && isset($_POST['password'])) {

            // receiving the post params
            $email    = $_POST['email'];
            $password = $_POST['password'];

            // get the user by email and password
            $qry = $this->db->get_where('pemohon', array('email' => $email, 'password' => md5($password)));

            if ($qry->num_rows() > 0) {
                // use is found
                $user                       = $qry->row_array();
                $response["error"]          = false;
                $response["user"]["id"]     = $user["id"];
                $response["user"]["jenis"]  = $user["jenis"];
                $response["user"]["foto"]   = $user["foto"];
                $response["user"]["npwp"]   = $user["npwp"];
                $response["user"]["alamat"] = $user["alamat"];
                $response["user"]["nama"]   = $user["nama"];
                $response["user"]["email"]  = $user["email"];
                $response["user"]["telp"]   = $user["no_telp"];

                // $this->output->set_header('HTTP/1.0 200 OK');
                // $this->output->set_header('HTTP/1.1 200 OK');

                echo json_encode($response);
                // $this->response($response,200);
            } else {
                // user is not found with the credentials
                $response["error"]     = true;
                $response["error_msg"] = "Detail Login salah.Silahkan coba kembali!";
                echo json_encode($response);
                // $this->response($response,502);
            }
        } else {
            // required post params is missing
            $response["error"]     = true;
            $response["error_msg"] = "Parameter email atau password yang dibutuhkan tidak diisi!";
            //$this->response($response,502);
            echo json_encode($response);
        }
    }

    //

    public function get_sertifikatkeselamatan()
    {

        header("content-type: application/json");

        $pemohon_id = $this->input->get('pemohon_id');

        $this->db->select("a.id,
                           LPAD(a.id,6,'0') AS kode,
                           b.nama_kapal,
                           DATE_FORMAT(a.tgl_mohon, '%d/%m/%Y') AS tgl_mohon,
                           DATE_FORMAT(a.tgl_update,'%d/%m/%Y') AS tgl_update,
                           a.status,
                           a.rating_kepuasan,
                           a.komentar");
        $this->db->join('kapal b', 'a.kapal_id = b.id', 'left');
        $this->db->join('pemohon c', 'b.pemohon_id = c.id', 'left');

        $qry = $this->db->get_where('sertifikat_keselamatan a', array('c.id' => $pemohon_id));

        echo json_encode(
            array(
                'sertifikatKeselamatanList' => $qry->result(),
            )
        );

    }

    //===========================================================================bongkar muat

    public function update_rating()
    {

        header('content-type: application/json');

        $jenis           = $this->input->post('jenis');
        $id              = $this->input->post('id');
        $rating_kepuasan = $this->input->post('rating_kepuasan');
        $komentar        = $this->input->post('komentar');

        $table = "bongkar_muat";

        if ($jenis === "bongkar_muat") {
            $table = "bongkar_muat";
        } elseif ($jenis === "masa_layar") {
            $table = "masa_layar";
        } elseif ($jenis === "sertifikat_keselamatan") {
            $table = "sertifikat_keselamatan";
        }

        $this->db->where('id', $id);
        $this->db->update($table,
            array(
                'rating_kepuasan' => $rating_kepuasan,
                'komentar'        => $komentar,
            )
        );

        echo json_encode(
            array(
                'status'    => "Upload berhasil",
                'error_msg' => $this->db->error()['code'],
                'error'     => false,
                'last_id'   => $id,

            )
        );

    }

    public function get_bongkarmuat()
    {

        header("content-type: application/json");

        $pemohon_id = $this->input->get('pemohon_id');

        $this->db->select("a.id,
                           LPAD(a.id,6,'0') AS kode,
                           DATE_FORMAT(a.tgl_mohon, '%d/%m/%Y') AS tgl_mohon,
                           DATE_FORMAT(a.tgl_update,'%d/%m/%Y') AS tgl_update,
                           a.status,
                           a.rating_kepuasan,
                           a.komentar");
        $qry = $this->db->get_where('bongkar_muat a', array('pemohon_id' => $pemohon_id));

        echo json_encode(
            array(
                'bongkarMuatList' => $qry->result(),
            )
        );

    }

    //===========================================================================masa layar

    public function get_masalayar()
    {

        header("content-type: application/json");

        $pemohon_id = $this->input->get('pemohon_id');

        $this->db->select("a.id,
                           LPAD(a.id,6,'0') AS kode,
                           DATE_FORMAT(a.tgl_mohon, '%d/%m/%Y') AS tgl_mohon,
                           DATE_FORMAT(a.tgl_update,'%d/%m/%Y') AS tgl_update,
                           a.status,
                           a.rating_kepuasan,
                           a.komentar");
        $qry = $this->db->get_where('masa_layar a', array('pemohon_id' => $pemohon_id));

        echo json_encode(
            array(
                'masaLayarList' => $qry->result(),
            )
        );

    }

    public function post_foto()
    {
        header('content-type: application/json');

        $pemohon_id = $this->input->post('pemohon_id');

        $upload['upload_path']   = './uploads/foto_pemohon';
        $upload['allowed_types'] = 'jpeg|jpg|png|bmp';
        $upload['encrypt_name']  = true;
        $upload['overwrite']     = true;
        $upload['max_size']      = 1024;

        $this->load->library('upload', $upload);

        if (!$this->upload->do_upload('foto')) {

            echo json_encode(
                array(
                    'message' => $this->upload->display_errors(),
                    'status'  => 'FAILED',

                )
            );

        } else {

            $success   = $this->upload->data();
            $file_name = $success['file_name'];

            $this->db->where('id', $pemohon_id);
            $this->db->update('pelaut', array('foto' => $file_name));

            echo json_encode(
                array(
                    'message' => $this->db->error()['code'],
                    'status'  => 'OK',

                )
            );
        }
    }

    public function post_ijazah_laut($ijazah_laut_id = 0)
    {

        header('content-type: application/json');

        $pemohon_id = $this->input->post('pemohon_id');
        $nama       = $this->input->post('nama');
        $penerbit   = $this->input->post('penerbit');
        $tgl_terbit = $this->input->post('tgl_terbit');

        $data = array(
            'pelaut_id'  => $pemohon_id,
            'nama'       => $nama,
            'penerbit'   => $penerbit,
            'tgl_terbit' => $tgl_terbit,
        );

        if (!empty($_FILES['file']['name'])) {
            $upload['upload_path']   = './uploads/foto_pemohon';
            $upload['allowed_types'] = 'jpeg|jpg|png|bmp';
            $upload['encrypt_name']  = true;
            $upload['overwrite']     = true;
            $upload['max_size']      = 1024;

            $this->load->library('upload', $upload);

            if (!$this->upload->do_upload('file')) {

                // echo json_encode(
                //     array(
                //         'message' => $this->upload->display_errors(),
                //         'status'  => 'FAILED',

                //     )
                // );

            } else {

                $success   = $this->upload->data();
                $file_name = $success['file_name'];

                $data['file'] = $file_name;

                // echo json_encode(
                //     array(
                //         'message' => $this->db->error()['code'],
                //         'status'  => 'OK',

                //     )
                // );
            }
        }

        if ($ijazah_laut_id == 0) {
            //insert
        } else {
            //update
        }

    }

    //===========================================================================sertifikat kapal
    public function post_sertifikat()
    {
        header('content-type: application/json');

        $kapal_id = $this->input->post('kapal_id');

        $this->db->insert('permohonan_sertifikat_kapal', array('kapal_id' => $kapal_id));

        echo json_encode(
            array(
                'message' => $this->db->error()['code'],
                'status'  => 'OK',

            )
        );

    }

    public function post_dok_sertifikat()
    {
        header('content-type: application/json');

        $id        = $this->input->post('id');
        $jenis_dok = $this->input->post('jenis_dokumen');

        $upload['upload_path']   = './uploads/sertifikat_kapal';
        $upload['allowed_types'] = 'jpeg|jpg|png|bmp';
        $upload['encrypt_name']  = true;
        $upload['overwrite']     = true;
        $upload['max_size']      = 1024;

        $this->load->library('upload', $upload);

        if (!$this->upload->do_upload('dok')) {

            echo json_encode(
                array(
                    'message' => $this->upload->display_errors(),
                    'status'  => 'FAILED',

                )
            );

        } else {

            $success   = $this->upload->data();
            $file_name = $success['file_name'];

            $this->db->where('id', $id);
            $this->db->update('permohonan_sertifikat_kapal', array($jenis_dok => $file_name));

            echo json_encode(
                array(
                    'message' => $this->db->error()['code'],
                    'status'  => 'OK',

                )
            );
        }
    }

}
