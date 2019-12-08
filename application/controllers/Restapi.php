<?php defined('BASEPATH') or exit('No direct script access allowed');

class Restapi extends CI_Controller
{
    public function __construct()
    {

        parent::__construct();

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


        if($ijazah_laut_id == 0){
        	//insert
        }else{
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
