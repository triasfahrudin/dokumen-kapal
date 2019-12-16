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
      <link href="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css" rel="stylesheet" >
      <?php }; ?>
      
      <?php
         if(isset($js_files)){
           foreach($js_files as $file): ?>
      <script src="<?php echo $file; ?>"></script>
      <?php endforeach;
         }else{ ?>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
      <script src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
      <!-- <script src="https://cdn.datatables.net/1.10.15/js/dataTables.bootstrap.min.js"></script> -->
      <?php }; ?>

      
      <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.bundle.min.js" integrity="sha256-fzFFyH01cBVPYzl16KT40wqjhgPtq6FFUB6ckN2+GGw=" crossorigin="anonymous"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js" integrity="sha256-H3cjtrm/ztDeuhCN9I4yh4iN2Ybx/y1RM7rMmAesA0k=" crossorigin="anonymous"></script>
      <script src="<?php echo site_url('assets/manage/js/sb-admin-2.min.js');?>"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/pace/1.0.2/pace.min.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" integrity="sha256-Uv9BNBucvCPipKQ2NS9wYpJmi8DTOEfTA/nH2aoJALw=" crossorigin="anonymous"></script>
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
      <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
      <script type="text/javascript">var base_path = "<?php echo site_url('manage')?>";</script>
      <script src="<?php echo site_url('assets/manage/js/admin.js')?>?timestamps=<?php echo date("YmdHis")?>"></script>
      
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
            <div class="sidebar-heading">
               Menu
            </div>
            <!-- Nav Item - Pages Collapse Menu -->
            <li class="nav-item">
               <a class="nav-link" href="<?php echo site_url('manage/permohonan')?>">
               <i class="fas fa-fw fa-cog"></i>
               <span>Permohonan</span>
               </a>                
            </li>
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
                        <div class="dropdown float-right">
                           <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                           Data Filter
                           </button>
                           <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                              <a class="dropdown-item" href="<?php echo site_url('manage/' . $filter . '/proses')?>">Proses</a>
                              <a class="dropdown-item" href="<?php echo site_url('manage/' . $filter . '/selesai')?>">Selesai</a>
                              <a class="dropdown-item" href="<?php echo site_url('manage/' . $filter . '/ditolak')?>">Ditolak</a>
                           </div>
                        </div>
                        <?php } ?>                        

                     </div>
                  </div>
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


   </body>
</html>