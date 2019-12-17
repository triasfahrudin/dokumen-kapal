<div class="card" style="margin-top: 10px">
  <h5 class="card-header">Data kapal</h5>
  <div class="card-body">

    <table class="table" cellspacing="0">
        <thead>
            <tr>
                <th>Nama Kapal</th>
                <th>Jenis</th>
                <th>IMO Number</th>
                <th>GRT</th>
                <th>Kapasitas</th>
                <th>Dokumen</th>
            </tr>
        </thead>
        <tbody>
            <?php foreach ($kapal->result_array() as $row) { ?>
            <tr>
                <td><?php echo $row['nama_kapal']?></td>
                <td><?php echo $row['jenis_kapal']?></td>
                <td><?php echo $row['imo_number']?></td>
                <td><?php echo $row['grt']?></td>
                <td>
                    <table>
                        <tr>
                            <td>Penumpang</td>
                            <td><?php echo $row['kapasitas_penumpang']?></td>
                        </tr>
                        <tr>
                            <td>Roda 2</td>
                            <td><?php echo $row['kapasitas_roda_dua']?></td>
                        </tr>
                        <tr>
                            <td>Roda 4</td>
                            <td><?php echo $row['kapasitas_roda_empat']?></td>
                        </tr>
                    </table>
                </td>
                <td>
                    <table>
                        <tr>
                            <td>Surat Ukur</td>
                            <td><?php echo (empty($row['file_surat_ukur']) ? 'Belum upload' : '<a href="' . site_url('uploads/dokumen/' . $row['file_surat_ukur']) . '">Download</a>');?></td>
                        </tr>
                        <tr>
                            <td>Surat Laut</td>
                            <td><?php echo (empty($row['file_surat_laut']) ? 'Belum upload' : '<a href="' . site_url('uploads/dokumen/' . $row['file_surat_laut']) . '">Download</a>');?></td>
                        </tr>
                        <tr>
                            <td>Sertifikat Keselamatan</td>
                            <td><?php echo (empty($row['file_sertifikat_keselamatan']) ? 'Belum upload' : '<a href="' . site_url('uploads/dokumen/' . $row['file_sertifikat_keselamatan']) . '">Download</a>');?></td>
                        </tr>
                        <tr>
                            <td>Sertifikat Klasifikasi</td>
                            <td><?php echo (empty($row['file_sertifikat_klasifikasi']) ? 'Belum upload' : '<a href="' . site_url('uploads/dokumen/' . $row['file_sertifikat_klasifikasi']) . '">Download</a>');?></td>
                        </tr>
                        <tr>
                            <td>Sertifikat PMK</td>
                            <td><?php echo (empty($row['file_sertifikat_pmk']) ? 'Belum upload' : '<a href="' . site_url('uploads/dokumen/' . $row['file_sertifikat_pmk']) . '">Download</a>');?></td>
                        </tr>
                        <tr>
                            <td>Sertifikat Life Raft</td>
                            <td><?php echo (empty($row['file_sertifikat_liferaft']) ? 'Belum upload' : '<a href="' . site_url('uploads/dokumen/' . $row['file_sertifikat_liferaft']) . '">Download</a>');?></td>
                        </tr>
     
                    </table>
                </td>
            </tr>
            <?php } ?>                
        </tbody>
    </table>

  </div>
</div>

