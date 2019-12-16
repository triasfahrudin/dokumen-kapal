<nav>
    <div class="nav nav-tabs nav-fill" id="nav-tab" role="tablist">
        <a class="nav-item nav-link active" id="nav-profile-tab" data-toggle="tab" href="#nav-profile" role="tab" aria-controls="nav-profile" aria-selected="true">Profile</a>
        <a class="nav-item nav-link" id="nav-kapal-tab" data-toggle="tab" href="#nav-kapal" role="tab" aria-controls="nav-kapal" aria-selected="true">Data Kapal</a>
        <a class="nav-item nav-link" id="nav-sertifikat-tab" data-toggle="tab" href="#nav-sertifikat" role="tab" aria-controls="nav-sertifikat" aria-selected="true">Riwayat Sertifikat Keselamatan</a>
        <a class="nav-item nav-link" id="nav-bongkarmuat-tab" data-toggle="tab" href="#nav-bongkarmuat" role="tab" aria-controls="nav-bongkarmuat" aria-selected="true">Riwayat Bongkar Muat</a>    
    </div>
</nav>
<div class="tab-content" id="nav-tabContent">
    <div class="tab-pane fade show active" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">
        <?php echo $profile?>
    </div>
    <div class="tab-pane fade" id="nav-kapal" role="tabpanel" aria-labelledby="nav-kapal-tab">
        <?php echo $kapal?>
    </div>
    <div class="tab-pane fade" id="nav-sertifikat" role="tabpanel" aria-labelledby="nav-sertifikat-tab">
        <?php echo $sertifikat_keselamatan?>
    </div>
    <div class="tab-pane fade" id="nav-bongkarmuat" role="tabpanel" aria-labelledby="nav-bongkarmuat-tab">
        <?php echo $bongkar_muat?>
    </div>
</div>