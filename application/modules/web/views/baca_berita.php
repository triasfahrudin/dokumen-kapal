<!-- Title -->
        <h1 class="mt-4"><?php echo $berita_konten['judul']?></h1>

        <!-- Author -->
        <p class="lead"> Oleh <a href="#">Admin</a>  </p>

        <hr>

        <!-- Date/Time -->
        <p>Di posting pada <?php echo tgl_panjang($berita_konten['tgl_post'])?> </p>

        <hr>

        <!-- Preview Image -->
        <img class="img-fluid rounded" src="<?php echo load_image('uploads/berita/' . $berita_konten['gambar'],900,300,0,1)?>" alt="">

        <hr>

        <?php echo $berita_konten['konten'];?>
        <hr>

        