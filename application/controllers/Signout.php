<?php defined('BASEPATH') OR exit('No direct script access allowed');

class Signout extends CI_Controller
{

    public function __construct()
    {
        parent::__construct();

        $this->load->database();
        $this->load->helper(array('url','userlog'));
        $this->load->library('session');

    }

    public function index()
    {
        $level = $this->session->userdata('user_level');

        if($level !== 'peserta'){
            log_add(
                array(                          
                    'jenis'   => 'logout'
                )
            );
    
        }
        
        $this->session->sess_destroy();
        redirect(site_url('web'), 'reload');
    }
}
