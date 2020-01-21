<!-- <div class="card" style="margin-top: 10px">
  <h5 class="card-header">Riwayat Permohonan</h5>
  <div class="card-body"> -->
    <table class="table" cellspacing="0">
        <thead>
            <tr>
                <th>Tanggal</th>
                <th>Kode</th>
                <th>Status</th>
                <th>Keterangan</th>
            </tr>
        </thead>
        <tbody>
            <?php foreach ($riwayat->result_array() as $row) { ?>
            <tr>
                <td><?php echo $row['tgl']?></td>
                <td><?php echo $row['status']?></td>
                <td><?php echo $row['arti']?></td>
                <td><?php echo $row['keterangan']?></td>
            </tr>
            <?php } ?>                
        </tbody>
    </table>
    <!-- </div>
</div> -->