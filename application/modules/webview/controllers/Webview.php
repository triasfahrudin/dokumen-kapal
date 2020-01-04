<?php


class Webview extends CI_Controller
{
    public function __construct()
    {
        parent::__construct();

        date_default_timezone_set('Asia/Jakarta');

        $this->load->helper(array('url', 'libs', 'form'));
        $this->load->database();

        $this->load->libraries(array('session', 'form_validation'));

    }

    public function _page_output($data = null)
    {
        $this->load->view('master_view.php', $data);
    }

    public function index()
    {
        $data = array(
            'content'   => get_settings('app_info_beranda'),
            'page_name' => 'beranda'
        );
        $this->_page_output($data);
    }
}
