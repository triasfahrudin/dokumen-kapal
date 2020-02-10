<div class="card" style="margin-top: 10px">
  <h5 class="card-header">Sertifikat Pelaut</h5>
  <div class="card-body">
    <table class="table" cellspacing="0">
        <thead>
            <tr>
                <th>Nama Sertifikat</th>
                <th>Penerbit</th>
                <th>Tgl Terbit</th>
                
            </tr>
        </thead>
        <tbody>
            <?php foreach ($sertifikat_pelaut->result_array() as $row) { ?>
            <tr>
                <td><?php echo $row['nama_sertifikat']?></td>
                <td><?php echo $row['penerbit']?></td>
                <td><?php echo $row['tgl_terbit']?></td>
                         
            </tr>
            <?php } ?>                
        </tbody>
    </table>
  </div>
</div>

