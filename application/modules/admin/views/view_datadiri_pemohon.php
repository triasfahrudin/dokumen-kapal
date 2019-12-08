<form>
  <img class="displayed" id="file_foto" src="<?php echo site_url('uploads/foto_pemohon/' . $biodata['foto']) ?>" alt="" src="" width="170px" height="170px">
   <div id="form-part">
      <div class="form-group">
         <label for="nik">Nama</label>
         <input type="text" id="nama" class="form-control" value="<?php echo $biodata['nama']?>" readonly>
      </div>
      <div class="form-group">
         <label for="alamat_rumah">Alamat rumah</label>
         <input type="text" class="form-control" id="alamat" value="<?php echo $biodata['alamat']?>">
      </div>

      <div class="form-group">
         <label for="alamat_rumah">Nomor Buku Pelaut</label>
         <input type="text" class="form-control" id="no_buku_pelaut" value="<?php echo $biodata['no_buku_pelaut']?>">
      </div>
      
      <div class="row">
         <div class="col-xs-12 col-sm-6 col-md-6">
            <div class="form-group">
               <label for="no_hp">Nomor handphone</label>
               <input type="text" id="no_telp" class="form-control" value="<?php echo $biodata['no_telp']?>">
            </div>
         </div>
         <div class="col-xs-12 col-sm-6 col-md-6">
            <div class="form-group">
               <label for="email">Email</label>
               <input type="email" id="email" class="form-control" value="<?php echo $biodata['email']?>">
            </div>
         </div>
      </div>
      
   </div>
</form>