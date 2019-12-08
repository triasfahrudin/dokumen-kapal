<?php

defined('BASEPATH') or exit('No direct script access allowed');
//https://betterexplained.com/articles/how-to-optimize-your-site-with-gzip-compression/
if (substr_count($_SERVER['HTTP_ACCEPT_ENCODING'], 'gzip')) {
    ob_start("ob_gzhandler");
} else {
    ob_start();
}

class Web extends CI_Controller
{
    private $data = array();

    public function __construct()
    {
        parent::__construct();

        date_default_timezone_set('Asia/Jakarta');

        $this->load->helper(array('url', 'libs', 'form'));
        $this->load->database();

        $this->load->libraries(array('session', 'form_validation'));


    }


    public function index()
    {
        $this->load->library('recaptcha');

        if (!empty($_POST)) {

            switch ($_POST['submit']) {

                case 'login':

                    if (strrpos(current_url(), 'localhost') === false) {
                        $captcha_answer = $this->input->post('g-recaptcha-response');
                        $response       = $this->recaptcha->verifyResponse($captcha_answer);

                        if (!$response['success']) {
                            redirect(site_url('web'), 'reload');
                        }
                    }

                    // $captcha_answer = $this->input->post('g-recaptcha-response');
                    // $response       = $this->recaptcha->verifyResponse($captcha_answer);

                    // if ($response['success']) {
                    $this->form_validation->set_rules('username', 'NIK / Email', 'required');
                    $this->form_validation->set_rules('password', 'Password', 'required');

                    if ($this->form_validation->run() == true) {

                        $username = $this->input->post('username');
                        $password = md5($this->input->post('password'));

                        $this->db->where('password', $password);
                        $this->db->group_start();
                        $this->db->where('nik', $username);
                        $this->db->or_where('email', $username);
                        $this->db->group_end();
                        $cek = $this->db->get('pendaftar');

                        if ($cek->num_rows() > 0) {
                            $pendaftar = $cek->row_array();

                            $this->session->set_userdata(
                                array(
                                    'user_id'           => $pendaftar['id'],
                                    'user_kategori_id'  => $pendaftar['kategori_id'],
                                    'user_nik'          => $pendaftar['nik'],
                                    'user_email'        => $pendaftar['email'],
                                    'user_nama_lengkap' => $pendaftar['nama_lengkap'],
                                    'user_level'        => 'peserta',
                                )
                            );

                            //$this->load->helper('penilaian');
                            //update_bobot_penilaian($pendaftar['id']);

                            redirect(site_url('peserta/index'), 'reload');
                        } else {
                            $this->alert->set('alert-danger', "User tidak ditemukan! Periksa kembali Email atau NIK dan Password yang anda masukkan", false);
                        }
                    } else {
                        $this->alert->set('alert-danger', "Periksa kembali data yang anda masukkan", false);
                    }
                    // } else {
                    // $this->alert->set('alert-danger', 'Validasi reCaptcha Error', false);
                    // }

                    redirect(site_url('web'), 'reload');

                    break;

                case 'reset-password':

                    $email = $this->input->post('email');
                    $cek   = $this->db->get_where('pendaftar', array('email' => $email));
                    if ($cek->num_rows() > 0) {

                        // $token_reset_password = generate_uuid();
                        //
                        // $this->db->where('email',$email);
                        // $this->db->update('pendaftar',array('token_reset_password' => $token_reset_password ));

                        //function send_email($recipient_email_address,$subject,$message,$attachment){
                        // $message = "Seseorang telah melakukan permintaan reset password akun anda <br />";
                        // $message .= "Jika anda tidak merasa melakukan hal ini, jangan hiraukan permintaan ini<br />";
                        // $message .= "Klik <a href='" . site_url('web/reset-password/' . $token_reset_password) . "'>disini</a> jika anda ingin mereset password anda";
                        $new_pass = generateRandomString(6);

                        $this->db->where('email', $email);
                        $this->db->update('pendaftar', array('password' => md5($new_pass)));

                        $message = "Berikut ini adalah password anda yang baru : " . $new_pass;

                        $message .= "<hr />";
                        $message .= "{timestamp:" . date("Y-m-d H:i:s") . "}";
                        send_email($email, 'reset password', $message, 'none');
                        // echo "<script>alert('Silahkan buka email anda untuk langkah selanjutnya');</script>";
                        $this->alert->set('alert-success', 'Silahkan cek email untuk langkah selanjutnya', false);
                    }

                    redirect(site_url('web'), 'reload');

                    break;
            }

            // var_dump($response);
        }

       
        $this->load->view('web/master_view.php', $this->data);

        // compress_output();
    }
}
