<div class="card" style="margin-top: 10px">
  <h5 class="card-header">Buku pelaut</h5>
  <div class="card-body">
    <form>
       <div id="form-part">
          <div class="form-group">
             <label for="nik">Nomor Buku</label>
             <input type="text" class="form-control" value="<?php echo $buku_pelaut['nomor_buku']?>" readonly>
          </div>
          <div class="form-group">
             <label for="alamat_rumah">Kode Pelaut</label>
             <input type="text" class="form-control" value="<?php echo $buku_pelaut['kode_pelaut']?>">
          </div>
          <div class="form-group">
             <label for="alamat_rumah">Nomor Daftar</label>
             <input type="text" class="form-control" value="<?php echo $buku_pelaut['nomor_daftar']?>">
          </div>
       </div>
    </form>

  </div>
</div>
<hr>
<div class="card" style="margin-top: 10px">
  <h5 class="card-header">Riwayat Pelayaran</h5>
  <div class="card-body">
    <table class="table" cellspacing="0">
      <thead>
          <tr>
              <th>Nama Kapal</th>
              <th>Tenaga Mesin</th>
              <th>Jabatan</th>
              <th>Tgl Naik</th>
              <th>Tgl Turun</th>
          </tr>
      </thead>
      <tbody>
          <?php foreach ($riwayat_pelayaran->result_array() as $row) { ?>
          <tr>
              <td><?php echo $row['nama_kapal']?></td>
              <td><?php echo $row['tenaga_mesin']?></td>
              <td><?php echo $row['jabatan']?></td>
              <td><?php echo $row['tgl_naik']?></td>
              <td><?php echo $row['tgl_turun']?></td>
          </tr>
          <?php } ?>                
      </tbody>
  </table>
  </div>
</div>  
