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
        $data = array('page_name' => 'beranda');
        $this->_page_output($data);
    }


    public function kontak()
    {
        $data = array('page_name' => 'kontak');
        $this->_page_output($data);
    }


    private function _paginate($base_url, $total_rows, $per_page, $uri_segment)
    {
        $config = array(
            'base_url'    => $base_url,
            'total_rows'  => $total_rows,
            'per_page'    => $per_page,
            'uri_segment' => $uri_segment,
        );

        /*
        <div class="pagination">
        <a class="prev page-numbers" href="#"><span class="icon-text">◂</span>Previous Page</a>
        <a class="page-numbers" href="#">1</a>
        <span class="page-numbers current">2</span>
        <a class="page-numbers" href="#">3</a>
        <a class="page-numbers" href="#">4</a>
        <a class="page-numbers" href="#">5</a>
        <a class="page-numbers" href="#">6</a>
        <a class="next page-numbers" href="#">Next Page<span class="icon-text">▸</span></a>
        </div>

         */
        // $config['anchor_class'] = 'class="page-numbers" ';
        $config['attributes'] = array('class' => 'page-link');

        $config['first_link']      = 'First';
        $config['first_tag_open']  = '';
        $config['first_tag_close'] = '';

        $config['last_link']      = 'Last';
        $config['last_tag_open']  = '';
        $config['last_tag_close'] = '';

        $config['next_link']      = 'Lanjut';
        $config['next_tag_open']  = '<li class="page-item">';
        $config['next_tag_close'] = ' </li>';

        $config['prev_link']      = 'Sebelumnya';
        $config['prev_tag_open']  = '<li class="page-item">';
        $config['prev_tag_close'] = '</li>';

        $config['cur_tag_open']  = '<li class="page-item active"><a class="page-link" href="#">';
        $config['cur_tag_close'] = '<span class="sr-only">(current)</span></a></li>';

        $config['num_tag_open']  = '<li class="page-item">';
        $config['num_tag_close'] = '</li>';

        return $config;
    }

    public function baca_berita()
    {

        // $user_ip = getUserIP();
        $slug = $this->uri->segment(2, 'no-slug');

        if ($slug === 'no-slug') {
            redirect(site_url(), 'reload');
        }

        $cek_blog = $this->db->get_where('berita', array('slug' => $slug));
        if ($cek_blog->num_rows() == 0) {
            redirect(site_url(), 'reload');
        }

        $data = array(
            'berita_konten' => $cek_blog->row_array(),
            'page_name'     => 'baca_berita',

        );
        $this->_page_output($data);
    }

    public function berita()
    {

        $this->load->library('pagination');

        $url         = site_url('web/berita/');
        $total_rows  = $this->db->get('berita')->num_rows();
        $uri_segment = 3;
        $per_page    = 3;

        $config = $this->_paginate($url, $total_rows, $per_page, $uri_segment);
        $this->pagination->initialize($config);

        $awal  = $this->uri->segment(3, 0);
        $akhir = $config['per_page'];

        $this->db->limit($akhir, $awal);
        $this->db->order_by('tgl_post DESC');
        $berita = $this->db->get("berita");

        $data = array(
            'berita'    => $berita,
            'page_name' => 'berita',
        );
        $this->_page_output($data);
    }

}
