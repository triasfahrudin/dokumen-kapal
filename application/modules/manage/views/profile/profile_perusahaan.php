<form>
  <img class="displayed" id="file_foto" src="<?php echo site_url('uploads/foto_pemohon/' . $profile['foto']) ?>" alt="" src="" width="170px" height="170px">
   <div id="form-part">
      <div class="form-group">
         <label for="nik">Nama</label>
         <input type="text" id="nama" class="form-control" value="<?php echo $profile['nama']?>" readonly>
      </div>
      <div class="form-group">
         <label for="alamat_rumah">Alamat rumah</label>
         <input type="text" class="form-control" id="alamat" value="<?php echo $profile['alamat']?>">
      </div>
      <div class="row">
         <div class="col-xs-12 col-sm-6 col-md-6">
            <div class="form-group">
               <label for="no_hp">Nomor handphone</label>
               <input type="text" id="no_telp" class="form-control" value="<?php echo $profile['no_telp']?>">
            </div>
         </div>
         <div class="col-xs-12 col-sm-6 col-md-6">
            <div class="form-group">
               <label for="email">Email</label>
               <input type="email" id="email" class="form-control" value="<?php echo $profile['email']?>">
            </div>
         </div>
      </div>      
   </div>
</form>