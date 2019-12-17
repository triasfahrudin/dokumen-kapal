        <h1 class="my-4">Berita & Pemberitahuan
          <!-- <small>Secondary Text</small> -->
        </h1>

        <?php foreach ($berita->result_array() as $b) { ?>
        <!-- Blog Post -->
        <div class="card mb-4">
          <img class="card-img-top" src="<?php echo load_image('uploads/berita/' . $b['gambar'],750,300,0,1)?>" alt="Berita">
          <div class="card-body">
            <h2 class="card-title"><?php echo $b['judul']?></h2>
            <p class="card-text">
              <?php echo limit_text($b['konten'],300)?>
            </p>
            <a href="<?php echo site_url('baca-berita/' . $b['slug'])?>" class="btn btn-primary">Selengkapnya &rarr;</a>
          </div>
          <div class="card-footer text-muted">
            Di posting pada <?php echo tgl_panjang($b['tgl_post'])?> oleh
            <a href="#!">Admin</a>
          </div>
        </div>
        <?php } ?>

        
        <!-- Pagination -->
        <nav aria-label="Page navigation">
          <ul class="pagination justify-content-center mb-4">
            <?php echo $this->pagination->create_links();?>
          </ul>
        </nav>
