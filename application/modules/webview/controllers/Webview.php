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

    public function notifikasi($pemohon_id){
        $data = array(
            'notif_list'   => $this->db->get_where('notifikasi',array('pemohon_id' => $pemohon_id)),
            'page_name' => 'notifikasi'
        );
        $this->_page_output($data);
    }
}
