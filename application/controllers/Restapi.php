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

        $this->db->select('nama_kapal AS nama,jenis_kapal AS jenis,imo_number');
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

        $this->db->select("CONCAT('Kapal :',nama_kapal) AS namaKapal,CONCAT('Jabatan :',jabatan) AS jabatan,CONCAT('Dari ',' : ',DATE_FORMAT(tgl_naik, '%d/%m/%Y'),' s/d ',DATE_FORMAT(tgl_turun, '%d/%m/%Y')) AS tanggal");
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

        $this->db->select("nama_sertifikat,CONCAT('Penerbit : ',penerbit) AS penerbit,CONCAT('Masa Berlaku ',' : ',DATE_FORMAT(tgl_terbit, '%d/%m/%Y'),' s/d ',DATE_FORMAT(tgl_berakhir, '%d/%m/%Y')) AS tanggal");
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

        $pemohon_id = $this->input->post('id');
        $jenis      = $this->input->post('jenis');

        $upload['upload_path']   = './uploads/dokumen';
        $upload['allowed_types'] = 'pdf';
        $upload['encrypt_name']  = true;
        $upload['overwrite']     = true;
        $upload['max_size']      = 1024;

        $this->load->library('upload', $upload);

        if (!$this->upload->do_upload('filename')) {

            echo json_encode(
                array(
                    'message' => $this->upload->display_errors(),
                    'status'  => 'FAILED',

                )
            );

        } else {

            $success   = $this->upload->data();
            $file_name = $success['file_name'];

            if ($jenis === '"buku_pelaut"') {
                $this->db->query(
                    "INSERT INTO buku_pelaut (pemohon_id,file)
                    VALUES($pemohon_id,'$file_name')
                    ON DUPLICATE KEY UPDATE file = '$file_name'"
                );
            }

            // $this->db->where('id', $pemohon_id);
            // $this->db->update('pelaut', array('foto' => $file_name));

            echo json_encode(
                array(
                    'message' => $this->db->error()['code'],
                    'status'  => "OK:$jenis",

                )
            );
        }
    }

    /*
    @Field("id") int pemohon_id,
    @Field("nama") String nama,
    @Field("email") String email,
    @Field("no_telp") String no_telp,
    @Field("alamat") String alamat
     */

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

    //===========================================================================bongkar muat

    //===========================================================================masa layar

    public function get_masalayar()
    {

        header('content-type: application/json');

        $pemohon_id = $this->input->post('pemohon_id');
        $qry        = $this->db->get_where('permohonan_masa_layar', array('pelaut_id' => $pemohon_id));
        echo json_encode(
            array(
                'message' => $qry->row(),
                'status'  => 'OK',
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
