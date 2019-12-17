<div class="card" style="margin-top: 10px">
  <h5 class="card-header">Sertifikat Keselamatan</h5>
  <div class="card-body">
    <table class="table" cellspacing="0">
        <thead>
            <tr>
                <th>Tanggal Mohon</th>
                <th>Tanggal Selesai</th>
                <th>Status permohonan</th>
            </tr>
        </thead>
        <tbody>
            <?php foreach ($sertifikat_keselamatan->result_array() as $row) { ?>
            <tr>
                <td><?php echo $row['tgl_mohon']?></td>
                <td><?php echo $row['tgl_update']?></td>
                <td><?php echo $row['status']?></td>
            </tr>
            <?php } ?>                
        </tbody>
    </table>
  </div>
</div>

