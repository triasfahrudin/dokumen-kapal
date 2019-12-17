<nav>
    <div class="nav nav-tabs nav-fill" id="nav-tab" role="tablist">
        <a class="nav-item nav-link active" id="nav-profile-tab" data-toggle="tab" href="#nav-profile" role="tab" aria-controls="nav-profile" aria-selected="true">Profile</a>
        <a class="nav-item nav-link" id="nav-bukupelaut-tab" data-toggle="tab" href="#nav-bukupelaut" role="tab" aria-controls="nav-bukupelaut" aria-selected="true">Buku Pelaut</a>
        <a class="nav-item nav-link" id="nav-sertifikat-tab" data-toggle="tab" href="#nav-sertifikat" role="tab" aria-controls="nav-sertifikat" aria-selected="true">Sertifikat Laut</a>
        <a class="nav-item nav-link" id="nav-masalayar-tab" data-toggle="tab" href="#nav-masalayar" role="tab" aria-controls="nav-masalayar" aria-selected="true">Riwayat Masa Layar</a>    
    </div>
</nav>
<div class="tab-content" id="nav-tabContent">
    <div class="tab-pane fade show active" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">
        <?php echo $profile?>
    </div>
    <div class="tab-pane fade" id="nav-bukupelaut" role="tabpanel" aria-labelledby="nav-bukupelaut-tab">
        <?php echo $buku_pelaut?>
    </div>
    <div class="tab-pane fade" id="nav-sertifikat" role="tabpanel" aria-labelledby="nav-sertifikat-tab">
        <?php echo $sertifikat_pelaut?>
    </div>
    <div class="tab-pane fade" id="nav-masalayar" role="tabpanel" aria-labelledby="nav-masalayar-tab">
        <?php echo $masa_layar?>
    </div>
</div>