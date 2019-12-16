<?php

defined('BASEPATH') or exit('No direct script access allowed');

class Signin extends CI_Controller
{
    private $data = array();

    public function __construct()
    {
        parent::__construct();

        date_default_timezone_set('Asia/Jakarta');

        $this->load->database();

        $this->load->library(array('form_validation', 'recaptcha','session'));
        $this->load->helper(array('url', 'libs'));
    }

    public function index()
    {
        // $this->db->where('username' ,'admin');
        // $this->db->update('admin_users',array('password' => $this->bcrypt->hash_password('admin')));
        // echo get_settings('google_auth');

        if (!empty($_POST)) {
            if (strrpos(current_url(), 'localhost') === false) {
                $captcha_answer = $this->input->post('g-recaptcha-response');
                $response       = $this->recaptcha->verifyResponse($captcha_answer);

                if (!$response['success']) {
                    redirect(site_url('web'), 'reload');
                }
            }

            //$captcha_answer = $this->input->post('g-recaptcha-response');
            //$response       = $this->recaptcha->verifyResponse($captcha_answer);

            // if ($response['success']) {
            $this->form_validation->set_rules('email', 'Email', 'required|valid_email');
            $this->form_validation->set_rules('password', 'Password', 'required');

            if ($this->form_validation->run() == true) {

                $email = $this->input->post('email');
                $password = $this->input->post('password');

                $cek = $this->db->get_where('user', array('email' => $email, 'password' => md5($password)));
                if ($cek->num_rows() > 0) {

                    $user = $cek->row_array();

                    $this->session->set_userdata(
                        array(
                            'user_id'                 => $user['id'],
                            'user_email'              => $user['email'],
                            'user_level'              => $user['level']                        
                        )
                    );

                    redirect(site_url('manage'), 'reload');
                }
            }
            // }
        }

        $this->load->view('signin');
    }

}
