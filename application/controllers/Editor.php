<?php

defined('BASEPATH') or exit('No direct script access allowed');

class Editor extends CI_Controller
{
    private $user_id;
    private $user_level;

    public function __construct()
    {
        parent::__construct();
        // date_default_timezone_set('Asia/Jakarta');

        // $this->load->database();
        $this->load->helper(array('url'));
        $this->load->library(array('session'));

        $this->user_level = $this->session->userdata('user_level');

        // $user_id = $this->session->userdata('user_id');
        // if (empty($user_id)) {
        //     redirect(site_url('web'), 'reload');
        // }
        $level = array('admin', 'petugas', 'kepala');
        if (!in_array($this->user_level, $level)) {
            redirect(site_url('web'), 'reload');
        }


        // $level = $this->session->userdata('user_level');
        // if ($level !== 'admin') {
        //     redirect(site_url('web'), 'reload');
        // }

        // $this->output->set_header('Last-Modified: ' . gmdate('D, d M Y H:i:s') . ' GMT');
        // $this->output->set_header('Cache-Control: no-store, no-cache, must-revalidate, no-transform, max-age=0, post-check=0, pre-check=0');
        // $this->output->set_header('Pragma: no-cache');
        // $this->output->set_header('Expires: Mon, 26 Jul 1997 05:00:00 GMT');
    }

    public function getGUID()
    {
        if (function_exists('com_create_guid')) {
            return com_create_guid();
        } else {
            mt_srand((double) microtime() * 10000); //optional for php 4.2.0 and up.
            $charid = strtoupper(md5(uniqid(rand(), true)));
            $hyphen = chr(45); // "-"
            $uuid   =  substr($charid, 0, 8) . $hyphen
            . substr($charid, 8, 4) . $hyphen
            . substr($charid, 12, 4) . $hyphen
            . substr($charid, 16, 4) . $hyphen
            . substr($charid, 20, 12);            
            return $uuid;
        }
    }
    public function upload()
    {

        if (empty($_FILES['file'])) {
            exit();
        }

        if ($_FILES['file']['size'] > 1048576) {
            echo site_url('uploads/ukuran_terlalu_besar.jpg');
            exit();
        }

        usleep(100);

        $errorImgFile        = site_url("uploads/uploadfailed.jpg");
        $temp                = explode(".", $_FILES["file"]["name"]);
        $newfilename         = $this->getGUID() . '.' . end($temp);
        $destinationFilePath = './uploads/' . $newfilename;
        if (!move_uploaded_file($_FILES['file']['tmp_name'], $destinationFilePath)) {
            echo $errorImgFile;
        } else {
            echo site_url('uploads/' . $newfilename); // $destinationFilePath;
        }

    }
}
