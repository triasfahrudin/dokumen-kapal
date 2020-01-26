<!DOCTYPE html>
<html lang="en">
   <head>
      <meta charset="utf-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
      <meta name="description" content="">
      <meta name="author" content="">
      <title>App Dokumen </title>
      <!-- Custom fonts for this template-->
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.2/css/all.min.css" integrity="sha256-zmfNZmXoNWBMemUOo1XUGFfc0ihGGLYdgtJS3KCr/l0=" crossorigin="anonymous" />
      <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
      <!-- Custom styles for this template-->
      <link href="<?php echo site_url('assets/manage/css/sb-admin-2.min.css');?>" rel="stylesheet">
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" />
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/pace/1.0.2/themes/red/pace-theme-flash.css" />
      <style type="text/css"> .checked {  color: orange; } </style>
      <?php
         if(isset($css_files)){
           foreach($css_files as $file): ?>
      <link type="text/css" rel="stylesheet" href="<?php echo $file; ?>" />
      <?php endforeach;
         }else{ ?>
      <link href="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css" rel="stylesheet">
      <link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.11/summernote-lite.css" rel="stylesheet">
      <?php }; ?>
      <?php
         if(isset($js_files)){
           foreach($js_files as $file): ?>
      <script src="<?php echo $file; ?>"></script>
      <?php endforeach;
         }else{ ?>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
      <script src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
      <script src="<?php echo site_url('assets/summernote/dist/summernote-lite.js')?>"></script> 
      <!-- <script src="https://cdn.datatables.net/1.10.15/js/dataTables.bootstrap.min.js"></script> -->
      <?php }; ?>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.bundle.min.js" integrity="sha256-fzFFyH01cBVPYzl16KT40wqjhgPtq6FFUB6ckN2+GGw=" crossorigin="anonymous"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js" integrity="sha256-H3cjtrm/ztDeuhCN9I4yh4iN2Ybx/y1RM7rMmAesA0k=" crossorigin="anonymous"></script>
      <script src="<?php echo site_url('assets/manage/js/sb-admin-2.min.js');?>"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/pace/1.0.2/pace.min.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" integrity="sha256-Uv9BNBucvCPipKQ2NS9wYpJmi8DTOEfTA/nH2aoJALw=" crossorigin="anonymous"></script>
      
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
      <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
      
      <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>

      <script type="text/javascript">var base_path = "<?php echo site_url('manage')?>";</script>
      <script src="<?php echo site_url('assets/manage/js/download.js')?>?timestamps=<?php echo date("YmdHis")?>"></script>
      <script src="<?php echo site_url('assets/manage/js/admin.js')?>?timestamps=<?php echo date("YmdHis")?>"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/Cookies.js/0.3.1/cookies.js"></script>

      <script src="https://cdn.jsdelivr.net/mojs/latest/mo.min.js" defer></script>
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/noty/3.1.4/noty.min.css" />
      <script src="https://cdnjs.cloudflare.com/ajax/libs/noty/3.1.4/noty.min.js"></script>


      <style type="text/css">
         body{color:black;} 
         .table {color: black}
         .form-control {color: black}

         .bell{
              /*display:block;*/
              /*width: 40px;*/
              /*height: 40px;*/
              font-size: 40px;
              /*margin:50px auto 0;*/
              color: #9e9e9e;
              -webkit-animation: ring 4s .7s ease-in-out infinite;
              -webkit-transform-origin: 50% 4px;
              -moz-animation: ring 4s .7s ease-in-out infinite;
              -moz-transform-origin: 50% 4px;
              animation: ring 4s .7s ease-in-out infinite;
              transform-origin: 50% 4px;
            }

            @-webkit-keyframes ring {
              0% { -webkit-transform: rotateZ(0); }
              1% { -webkit-transform: rotateZ(30deg); }
              3% { -webkit-transform: rotateZ(-28deg); }
              5% { -webkit-transform: rotateZ(34deg); }
              7% { -webkit-transform: rotateZ(-32deg); }
              9% { -webkit-transform: rotateZ(30deg); }
              11% { -webkit-transform: rotateZ(-28deg); }
              13% { -webkit-transform: rotateZ(26deg); }
              15% { -webkit-transform: rotateZ(-24deg); }
              17% { -webkit-transform: rotateZ(22deg); }
              19% { -webkit-transform: rotateZ(-20deg); }
              21% { -webkit-transform: rotateZ(18deg); }
              23% { -webkit-transform: rotateZ(-16deg); }
              25% { -webkit-transform: rotateZ(14deg); }
              27% { -webkit-transform: rotateZ(-12deg); }
              29% { -webkit-transform: rotateZ(10deg); }
              31% { -webkit-transform: rotateZ(-8deg); }
              33% { -webkit-transform: rotateZ(6deg); }
              35% { -webkit-transform: rotateZ(-4deg); }
              37% { -webkit-transform: rotateZ(2deg); }
              39% { -webkit-transform: rotateZ(-1deg); }
              41% { -webkit-transform: rotateZ(1deg); }

              43% { -webkit-transform: rotateZ(0); }
              100% { -webkit-transform: rotateZ(0); }
            }

            @-moz-keyframes ring {
              0% { -moz-transform: rotate(0); }
              1% { -moz-transform: rotate(30deg); }
              3% { -moz-transform: rotate(-28deg); }
              5% { -moz-transform: rotate(34deg); }
              7% { -moz-transform: rotate(-32deg); }
              9% { -moz-transform: rotate(30deg); }
              11% { -moz-transform: rotate(-28deg); }
              13% { -moz-transform: rotate(26deg); }
              15% { -moz-transform: rotate(-24deg); }
              17% { -moz-transform: rotate(22deg); }
              19% { -moz-transform: rotate(-20deg); }
              21% { -moz-transform: rotate(18deg); }
              23% { -moz-transform: rotate(-16deg); }
              25% { -moz-transform: rotate(14deg); }
              27% { -moz-transform: rotate(-12deg); }
              29% { -moz-transform: rotate(10deg); }
              31% { -moz-transform: rotate(-8deg); }
              33% { -moz-transform: rotate(6deg); }
              35% { -moz-transform: rotate(-4deg); }
              37% { -moz-transform: rotate(2deg); }
              39% { -moz-transform: rotate(-1deg); }
              41% { -moz-transform: rotate(1deg); }

              43% { -moz-transform: rotate(0); }
              100% { -moz-transform: rotate(0); }
            }

            @keyframes ring {
              0% { transform: rotate(0); }
              1% { transform: rotate(30deg); }
              3% { transform: rotate(-28deg); }
              5% { transform: rotate(34deg); }
              7% { transform: rotate(-32deg); }
              9% { transform: rotate(30deg); }
              11% { transform: rotate(-28deg); }
              13% { transform: rotate(26deg); }
              15% { transform: rotate(-24deg); }
              17% { transform: rotate(22deg); }
              19% { transform: rotate(-20deg); }
              21% { transform: rotate(18deg); }
              23% { transform: rotate(-16deg); }
              25% { transform: rotate(14deg); }
              27% { transform: rotate(-12deg); }
              29% { transform: rotate(10deg); }
              31% { transform: rotate(-8deg); }
              33% { transform: rotate(6deg); }
              35% { transform: rotate(-4deg); }
              37% { transform: rotate(2deg); }
              39% { transform: rotate(-1deg); }
              41% { transform: rotate(1deg); }

              43% { transform: rotate(0); }
              100% { transform: rotate(0); }
            }
      </style>
   </head>
   <body id="page-top">
      <!-- Page Wrapper -->
      <div id="wrapper">
         <!-- Sidebar -->
         <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">
            <!-- Sidebar - Brand -->
            <a class="sidebar-brand d-flex align-items-center justify-content-center" href="index.html">
               <div class="sidebar-brand-icon rotate-n-15">
                  <i class="fas fa-address-card"></i>
               </div>
               <div class="sidebar-brand-text mx-3"><?php echo $this->session->userdata('user_fullname');?></div>
            </a>
            <!-- Divider -->
            <hr class="sidebar-divider my-0">
            <!-- Nav Item - Dashboard -->
            <li class="nav-item active">
               <a class="nav-link" href="<?php echo site_url('manage')?>">
               <i class="fas fa-fw fa-tachometer-alt"></i>
               <span>Dashboard</span></a>
            </li>
            <!-- Divider -->
            <hr class="sidebar-divider">
            <!-- Heading -->
            
            <!-- Nav Item - Pages Collapse Menu -->

            <?php  $user_level = $this->session->userdata('user_level'); ?>

            <?php 
              $level = array('admin');
              if (in_array($user_level, $level)) { ?>
            
            <div class="sidebar-heading">
               Permohonan
            </div>
            <li class="nav-item">
               <a class="nav-link" href="<?php echo site_url('manage/permohonan')?>">
               <i class="fas fa-fw fa-cog"></i>
               <span>Permohonan</span>
               </a>                
            </li>
            <hr class="sidebar-divider">
            <!-- Heading -->
            <div class="sidebar-heading">
               Master Data
            </div>
            <li class="nav-item">
               <a class="nav-link" href="<?php echo site_url('manage/data-pengguna')?>">
               <i class="fas fa-fw fa-table"></i>
               <span>Data Pengguna</span></a>
            </li>
            <li class="nav-item">
               <a class="nav-link" href="<?php echo site_url('manage/data-pemohon')?>">
               <i class="fas fa-fw fa-table"></i>
               <span>Data Pemohon</span></a>
            </li>
            <li class="nav-item">
               <a class="nav-link" href="<?php echo site_url('manage/data-biaya')?>">
               <i class="fas fa-fw fa-table"></i>
               <span>Data Biaya</span></a>
            </li>
            <!-- <li class="nav-item">
               <a class="nav-link" href="<?php echo site_url('manage/berita')?>">
               <i class="fas fa-fw fa-newspaper"></i>
               <span>Data Berita</span></a>
            </li> -->
            <hr class="sidebar-divider">
            <!-- Heading -->
            <div class="sidebar-heading">
               Report
            </div>
            <li class="nav-item">
               <a class="nav-link" href="<?php echo site_url('manage/laporan')?>">
               <i class="fas fa-fw fa-chart-bar"></i>
               <span>Laporan</span></a>
            </li>
             <div class="sidebar-heading">
               Settings
            </div>
            <li class="nav-item">
               <a class="nav-link" href="<?php echo site_url('manage/settings')?>">
               <i class="fas fa-fw fa-chart-bar"></i>
               <span>Setting Aplikasi</span></a>
            </li>
            <?php  } ?>


            <?php 
              $level = array('petugas');
              if (in_array($user_level, $level)) { ?>
            
            <li class="nav-item">
               <a class="nav-link" href="<?php echo site_url('manage/permohonan')?>">
               <i class="fas fa-fw fa-cog"></i>
               <span>Permohonan</span>
               </a>                
            </li>            
            <?php  } ?>

             <?php 
              $level = array('kepala');
              if (in_array($user_level, $level)) { ?>
            
               <div class="sidebar-heading">
                  Report
               </div>
               <li class="nav-item">
                  <a class="nav-link" href="<?php echo site_url('manage/laporan')?>">
                  <i class="fas fa-fw fa-chart-bar"></i>
                  <span>Laporan</span></a>
               </li>
            
            <?php  } ?>


            
         </ul>
         <!-- End of Sidebar -->
         
         <!-- Content Wrapper -->
         <div id="content-wrapper" class="d-flex flex-column">
            <!-- Main Content -->
            <div id="content">
               <!-- Topbar -->
               <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">
                  
                  <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                  <i class="fa fa-bars"></i>
                  </button>  

                  <ul class="navbar-nav ml-auto">
                     <!-- Nav Item - Alerts -->
                     <li class="nav-item dropdown no-arrow mx-1">
                       <a class="nav-link dropdown-toggle" href="#" id="alertsDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                         <i id="bell_notification" class="bell fas fa-bell fa-fw" style="font-size: 40px;color: #f6c23e"></i>
                         <!-- Counter - Alerts -->
                         <!-- <h4><span class="badge badge-danger badge-counter" id="dd_alert_total">0</span></h4> -->
                       </a>
                       <!-- Dropdown - Alerts -->
                       <div class="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="alertsDropdown">
                         <h6 class="dropdown-header">
                           Alerts Center
                         </h6>
                         <a class="dropdown-item d-flex align-items-center" href="<?php echo site_url('manage/masa-layar/200.210')?>">
                           <div class="mr-3">
                             <div class="icon-circle bg-primary">
                               <i class="fas fa-anchor fa-2x text-white"></i>
                             </div>
                           </div>
                           <div>
                             <!-- <div class="small text-gray-500">December 12, 2019</div> -->
                             Ada <div style="display: inline-block;" id="dd_alert_masa_layar">0</div> Permohonan Masa Layar yang perlu diproses
                           </div>
                         </a>
                         <a class="dropdown-item d-flex align-items-center" href="<?php echo site_url('manage/sertifikat-keselamatan/200.210')?>">
                           <div class="mr-3">
                             <div class="icon-circle bg-success">
                               <i class="fas fa-ship fa-2x text-white"></i>
                             </div>
                           </div>
                           <div>
                             <!-- <div class="small text-gray-500">December 7, 2019</div> -->
                             Ada <div style="display: inline-block;" id="dd_alert_sertifikat_keselamatan">0</div> Permohonan Sertifikat Keselamatan Penumpang yang perlu diproses
                           </div>
                         </a>
                         <a class="dropdown-item d-flex align-items-center" href="<?php echo site_url('manage/bongkar-muat/200.210')?>">
                           <div class="mr-3">
                             <div class="icon-circle bg-warning">
                               <i class="fas fa-truck fa-2x text-white"></i>
                             </div>
                           </div>
                           <div>
                             <!-- <div class="small text-gray-500">December 2, 2019</div> -->
                             Ada <div style="display: inline-block;" id="dd_alert_bongkar_muat">0</div> Permohonan Dokumen Bongkar Muat Barang Berbahaya yang perlu diproses
                           </div>
                         </a>
                         <!-- <a class="dropdown-item text-center small text-gray-500" href="<?php echo site_url('manage/bongkar-muat/200.210')?>">Show All Alerts</a> -->
                       </div>
                        <script type="text/javascript">
                            (function worker() {
                              $('#bell_notification').removeClass("bell");
                              $.ajax({
                                url: '<?php echo site_url('manage/notifikasi/upper_alert')?>',
                                success: function(data) {
                                  $('#dd_alert_total').html(data.dd_alert_total);

                                  if(data.dd_alert_total > 0){
                                    $('#bell_notification').addClass("bell");
                                  }
                                  $('#dd_alert_sertifikat_keselamatan').html(data.dd_alert_sertifikat_keselamatan);
                                  $('#dd_alert_bongkar_muat').html(data.dd_alert_bongkar_muat);
                                  $('#dd_alert_masa_layar').html(data.dd_alert_masa_layar);
                                },
                                complete: function() {
                                  setTimeout(worker, 5000);
                                }
                              });
                            })();
                           
                        </script>
                     </li>
                     <div class="topbar-divider d-none d-sm-block"></div>
                     

                     <!-- Nav Item - User Information -->
                     <li class="nav-item dropdown no-arrow">
                        <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <span class="mr-2 d-none d-lg-inline text-gray-600 small"><?php echo $this->session->userdata('user_fullname')?></span>
                        <img class="img-profile rounded-circle" src="<?php echo load_image('assets/manage/img/no_photo.png',60,60)?>">
                        </a>
                        <!-- Dropdown - User Information -->
                        <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown">
                           <a class="dropdown-item" href="#">
                           <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                           Profile
                           </a>
                           <!-- <a class="dropdown-item" href="#">
                              <i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>
                              Settings
                              </a> -->
                           <div class="dropdown-divider"></div>
                           <a class="dropdown-item" href="#!" data-toggle="modal" data-target="#logoutModal">
                           <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                           Logout
                           </a>
                        </div>
                     </li>
                  </ul>
               </nav>
               <!-- End of Topbar -->
               <!-- Begin Page Content -->
               <div class="container-fluid">
                  <div class="row">
                     <div class="col-md-auto">
                        <nav aria-label="breadcrumb">
                           <?php echo $this->breadcrumbs->show();?>
                        </nav>
                     </div>
                     <div class="col-sm">
                        <?php if(isset($filter)){ ?>
                        <!-- <div class="dropdown float-right">
                           <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                           Data Filter
                           </button>
                           <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                              <a class="dropdown-item" href="<?php echo site_url('manage/' . $filter . '/proses')?>">Proses</a>
                              <a class="dropdown-item" href="<?php echo site_url('manage/' . $filter . '/selesai')?>">Selesai</a>
                              <a class="dropdown-item" href="<?php echo site_url('manage/' . $filter . '/ditolak')?>">Ditolak</a>
                           </div>
                        </div> -->
                        <?php } ?>                        
                     </div>
                  </div>
                  <?php 

                     $permohonan = array('masa-layar','sertifikat-keselamatan','bongkar-muat');
                     $uri2 = $this->uri->segment(2,'-');
                     $uri3 = $this->uri->segment(3,'');

                     if(in_array($uri2, $permohonan)){ ?>

                     <div class="row" style="padding-bottom: 10px">
                        <ul class="nav nav-pills nav-fill">
                          <li class="nav-item">
                            <a class="nav-link <?php echo ($uri3 === '200.210') ? 'active' : '';?>" href="<?php echo site_url('manage/' . $uri2 . '/200.210')?>">Dalam Proses <span class="badge badge-success" id="tab_proses">0</span></a>
                          </li>                      
                          <li class="nav-item">
                            <a class="nav-link <?php echo ($uri3 === '310') ? 'active' : '';?>" href="<?php echo site_url('manage/' . $uri2 . '/310')?>">Menunggu diambil <span class="badge badge-warning" id="tab_tungguambil">0</span></a>
                          </li>
                          <li class="nav-item">
                            <a class="nav-link <?php echo ($uri3 === '400') ? 'active' : '';?>" href="<?php echo site_url('manage/' . $uri2 . '/400')?>">Selesai <span class="badge badge-secondary" id="tab_selesai">0</span></a>
                          </li>
                           <li class="nav-item">
                            <a class="nav-link <?php echo ($uri3 === '299.399') ? 'active' : '';?>" href="<?php echo site_url('manage/' . $uri2 . '/299.399')?>">Revisi Berkas <span class="badge badge-danger" id="tab_revisi">0</span></a>
                          </li>                       
                        </ul>
                     </div>

                     <script type="text/javascript">
                        
                        (function worker() {
                           $.ajax({
                             url: '<?php echo site_url('manage/notifikasi/tab_permohonan/' . $uri2)?>',
                             success: function(data) {
                               $('#tab_proses').html(data.tab_proses);
                               $('#tab_tungguambil').html(data.tab_tungguambil);
                               $('#tab_selesai').html(data.tab_selesai);
                               $('#tab_revisi').html(data.tab_revisi);
                             },
                             complete: function() {
                               setTimeout(worker, 5000);
                             }
                           });
                         })();
                     </script>

                  <?php } ?>
                  
                  <div class="row">
                     <?php if(isset($output)){ echo $output; }else{ include $page_name . ".php";} ?>
                  </div>
               </div>
               <!-- /.container-fluid -->
            </div>
            <!-- End of Main Content -->
            <!-- Footer -->
            <footer class="sticky-footer bg-white">
               <div class="container my-auto">
                  <div class="copyright text-center my-auto">
                     <span>Copyright &copy; Your Website 2019</span>
                  </div>
               </div>
            </footer>
            <!-- End of Footer -->
         </div>
         <!-- End of Content Wrapper -->
      </div>
      <!-- End of Page Wrapper -->
      <!-- Scroll to Top Button-->
      <a class="scroll-to-top rounded" href="#page-top">
      <i class="fas fa-angle-up"></i>
      </a>
      <!-- Logout Modal-->
      <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
         <div class="modal-dialog" role="document">
            <div class="modal-content">
               <div class="modal-header">
                  <h5 class="modal-title" id="exampleModalLabel">Ingin keluar?</h5>
                  <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">×</span>
                  </button>
               </div>
               <div class="modal-body">Pilih "Logout" dibawah jika anda ingin keluar aplikasi.</div>
               <div class="modal-footer">
                  <button class="btn btn-secondary" type="button" data-dismiss="modal">Batal</button>
                  <a class="btn btn-primary" href="<?php echo site_url('signout')?>">Logout</a>
               </div>
            </div>
         </div>
      </div>
      <div class="modal fade" id="modalBiodata" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
         <div class="modal-dialog" style=" min-width: 100%; margin: 0;" role="document">
            <div class="modal-content">
               <div class="modal-header">
                  <h5 class="modal-title" id="exampleModalLabel">Data Diri Pemohon</h5>
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                  </button>
               </div>
               <div class="modal-body" id="tabBiodata">
               </div>
               <div class="modal-footer">
                  <button type="button" class="btn btn-secondary" data-dismiss="modal">Tutup</button>
                  <!-- <button type="button" class="btn btn-primary">Save changes</button> -->
               </div>
            </div>
         </div>
      </div>
      <div class="modal fade" id="modalIjazahLaut" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
         <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
               <div class="modal-header">
                  <h5 class="modal-title" id="exampleModalLabel">Data Ijazah Laut</h5>
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                  </button>
               </div>
               <div class="modal-body" id="contentIjazahLaut">
               </div>
               <div class="modal-footer">
                  <button type="button" class="btn btn-secondary" data-dismiss="modal">Tutup</button>
                  <!-- <button type="button" class="btn btn-primary">Save changes</button> -->
               </div>
            </div>
         </div>
      </div>
      <div class="modal fade" id="modalAlasanStatus" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
         <div class="modal-dialog" role="document">
            <div class="modal-content">
               <div class="modal-header">
                  <h5 class="modal-title" id="exampleModalLabel">Alasan</h5>
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                  </button>
               </div>
               <div class="modal-body" id="mb-alasan-status">
               </div>
               <div class="modal-footer">
                  <button type="button" class="btn btn-secondary" data-dismiss="modal">Tutup</button>
                  <!-- <button type="button" class="btn btn-primary">Save changes</button> -->
               </div>
            </div>
         </div>
      </div>

      <div class="modal fade" id="modalKomentarRating" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
         <div class="modal-dialog" role="document">
            <div class="modal-content">
               <div class="modal-header">
                  <h5 class="modal-title" id="exampleModalLabel">Alasan</h5>
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                  </button>
               </div>
               <div class="modal-body" id="mb-komentar-rating">
               </div>
               <div class="modal-footer">
                  <button type="button" class="btn btn-secondary" data-dismiss="modal">Tutup</button>
                  <!-- <button type="button" class="btn btn-primary">Save changes</button> -->
               </div>
            </div>
         </div>
      </div>

      <div class="modal fade" id="modalRiwayatPermohonan" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
         <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
               <div class="modal-header">
                  <h5 class="modal-title" id="exampleModalLabel">Riwayat Permohonan</h5>
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                  </button>
               </div>
               <div class="modal-body" id="contentRiwayatPermohonan">
               </div>
               <div class="modal-footer">
                  <button type="button" class="btn btn-secondary" data-dismiss="modal">Tutup</button>                  
               </div>
            </div>
         </div>
      </div>


     <script type="text/javascript">


        (function worker() {
           $.ajax({
             url: '<?php echo site_url('manage/notifikasi/riwayat_permohonan.200/')?>',
             success: function(data) {
               
               if(data.jml_notif > 0){

                new Noty({    
                  theme: 'relax',
                  type: 'success',
                  text: data.pesan, 
                  timeout : 3000,
                  closeWith:['click'],
                  callbacks: {
                    onClick: function(){
                      window.open(data.url,"_self");
                    }

                  },
                  animation: {
                      open: function (promise) {
                          var n = this;
                          var Timeline = new mojs.Timeline();
                          var body = new mojs.Html({
                              el        : n.barDom,
                              x         : {500: 0, delay: 0, duration: 500, easing: 'elastic.out'},
                              isForce3d : true,
                              onComplete: function () {
                                  promise(function(resolve) {
                                      resolve();
                                  })
                              }
                          });

                          var parent = new mojs.Shape({
                              parent: n.barDom,
                              width      : 200,
                              height     : n.barDom.getBoundingClientRect().height,
                              radius     : 0,
                              x          : {[150]: -150},
                              duration   : 1.2 * 500,
                              isShowStart: true
                          });

                          n.barDom.style['overflow'] = 'visible';
                          parent.el.style['overflow'] = 'hidden';

                          var burst = new mojs.Burst({
                              parent  : parent.el,
                              count   : 10,
                              top     : n.barDom.getBoundingClientRect().height + 75,
                              degree  : 90,
                              radius  : 75,
                              angle   : {[-90]: 40},
                              children: {
                                  fill     : '#EBD761',
                                  delay    : 'stagger(500, -50)',
                                  radius   : 'rand(8, 25)',
                                  direction: -1,
                                  isSwirl  : true
                              }
                          });

                          var fadeBurst = new mojs.Burst({
                              parent  : parent.el,
                              count   : 2,
                              degree  : 0,
                              angle   : 75,
                              radius  : {0: 100},
                              top     : '90%',
                              children: {
                                  fill     : '#EBD761',
                                  pathScale: [.65, 1],
                                  radius   : 'rand(12, 15)',
                                  direction: [-1, 1],
                                  delay    : .8 * 500,
                                  isSwirl  : true
                              }
                          });

                          Timeline.add(body, burst, fadeBurst, parent);
                          Timeline.play();
                      },
                      close: function (promise) {
                          var n = this;
                          new mojs.Html({
                              el        : n.barDom,
                              x         : {0: 500, delay: 10, duration: 500, easing: 'cubic.out'},
                              skewY     : {0: 10, delay: 10, duration: 500, easing: 'cubic.out'},
                              isForce3d : true,
                              onComplete: function () {
                                  promise(function(resolve) {
                                      resolve();
                                  })
                              }
                          }).play();
                      }
                  }   


                }).show();

               }


             },
             complete: function() {
               setTimeout(worker, 5000);
             }
           });
         })();

        
     </script>

   </body>
</html>