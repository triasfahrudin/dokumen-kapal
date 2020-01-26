<link href="https://cdn.jsdelivr.net/npm/select2@4.0.12/dist/css/select2.min.css" rel="stylesheet" />
<script src="https://cdn.jsdelivr.net/npm/select2@4.0.12/dist/js/select2.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>

<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Material+Icons">
<link rel="stylesheet" href="https://unpkg.com/bootstrap-material-design@4.1.1/dist/css/bootstrap-material-design.min.css" integrity="sha384-wXznGJNEXNG1NFsbm0ugrLFMQPWswR3lds2VeinahP8N0zJw9VWSopbjv2x7WCvX" crossorigin="anonymous">
<!-- <link href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css" rel="stylesheet"> -->
<link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Roboto+Slab:400,700|Material+Icons">

<style type="text/css">
div.card {
    border: 0;
    margin-bottom: 30px;
    margin-top: 30px;
    border-radius: 6px;
    color: rgba(0,0,0,.87);
    background: #fff;
    width: 100%;
    box-shadow: 0 2px 2px 0 rgba(0,0,0,.14), 0 3px 1px -2px rgba(0,0,0,.2), 0 1px 5px 0 rgba(0,0,0,.12);
}

div.card.card-plain {
    background: transparent;
    box-shadow: none;
}
div.card .card-header {
    border-radius: 3px;
    padding: 1rem 15px;
    margin-left: 15px;
    margin-right: 15px;
    margin-top: -30px;
    border: 0;
    background: linear-gradient(60deg,#eee,#bdbdbd);
}

.card-plain .card-header:not(.card-avatar) {
    margin-left: 0;
    margin-right: 0;
}

.div.card .card-body{
    padding: 15px 30px;
}

div.card .card-header-primary {
    background: linear-gradient(60deg,#ab47bc,#7b1fa2);
    box-shadow: 0 5px 20px 0 rgba(0,0,0,.2), 0 13px 24px -11px rgba(156,39,176,.6);
}

div.card .card-header-danger {
    background: linear-gradient(60deg,#ef5350,#d32f2f);
    box-shadow: 0 5px 20px 0 rgba(0,0,0,.2), 0 13px 24px -11px rgba(244,67,54,.6);
}


.card-nav-tabs .card-header {
    margin-top: -30px!important;
}

.card .card-header .nav-tabs {
    padding: 0;
}

.nav-tabs {
    border: 0;
    border-radius: 3px;
    padding: 0 15px;
}

.nav {
    display: flex;
    flex-wrap: wrap;
    padding-left: 0;
    margin-bottom: 0;
    list-style: none;
}

.nav-tabs .nav-item {
    margin-bottom: -1px;
}

.nav-tabs .nav-item .nav-link.active {
    background-color: hsla(0,0%,100%,.2);
    transition: background-color .3s .2s;
}

.nav-tabs .nav-item .nav-link{
    border: 0!important;
    color: #fff!important;
    font-weight: 500;
}

.nav-tabs .nav-item .nav-link {
    color: #fff;
    border: 0;
    margin: 0;
    border-radius: 3px;
    line-height: 24px;
    text-transform: uppercase;
    font-size: 12px;
    padding: 10px 15px;
    background-color: transparent;
    transition: background-color .3s 0s;
}

.nav-link{
    display: block;
}

.nav-tabs .nav-item .material-icons {
    margin: -1px 5px 0 0;
    vertical-align: middle;
}

.nav .nav-item {
    position: relative;
}
footer{
    margin-top:100px;
    color: #555;
    background: #fff;
    padding: 25px;
    font-weight: 300;
    
}	

</style>

<div class="container">
	<div class="row">
	    <div class="col-md-12">
	    	<div class="card">
			  <div class="card-header">
			    Featured
			  </div>
			  <div class="card-body">
			  	<div class="alert alert-success">
				    <strong>Catatan:</strong> Data yang ditampilkan adalah adat permohonan yang sudah dilakukan validasi pembayaran
				</div>

			  	<form method="POST" action="">
				  <div class="form-group">
				    <label for="exampleInputEmail1">Filter tahun</label>
				    <!-- <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email"> -->
				    <?php if(!empty($selected_tahun)){ ?>
				    <select class="form-control js-example-basic-multiple" name="tahun[]" multiple="multiple" style="width: 100%">
					  <?php foreach ($tahun->result_array() as $key) { ?>
					  <option <?php echo (in_array($key['tahun'],$selected_tahun) ? 'selected' : '');?> value="<?php echo $key['tahun']?>"><?php echo $key['tahun']?></option>
					  <?php } ?>
					</select>
					<?php }else{ ?>
					<select class="form-control js-example-basic-multiple" name="tahun[]" multiple="multiple" style="width: 100%">
					  <?php foreach ($tahun->result_array() as $key) { ?>
					  <option value="<?php echo $key['tahun']?>"><?php echo $key['tahun']?></option>
					  <?php } ?>
					</select>	
					<?php } ?>
				    <small id="emailHelp" class="form-text text-muted">Filter berdasarkan tahun permohonan. Anda dapat memasukkan lebih dari satu filter tahun</small>
				  </div>				  
				  <button type="submit" class="btn btn-primary btn-raised">Submit</button>
				</form>	

				<!-- <canvas id="myChart"></canvas>		    -->
			  </div>
			</div>
	    </div>

	    <div class="col-md-12">
	    	<!-- Tabs with icons on Card -->
            <div class="card card-nav-tabs">
                <div class="card-header card-header-primary">
                    <!-- colors: "header-primary", "header-info", "header-success", "header-warning", "header-danger" -->
                    <div class="nav-tabs-navigation">
                        <div class="nav-tabs-wrapper">
                            <ul class="nav nav-tabs" data-tabs="tabs">
                                <li class="nav-item">
                                    <a class="nav-link active" href="#profile" data-toggle="tab">
                                        <i class="fas fa-table fa-2x"></i>
                                        Tabel
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#messages" data-toggle="tab">
                                        <i class="fas fa-chart-line fa-2x"></i>
                                        Grafik
                                    </a>
                                </li>                                    
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="card-body ">
                    <div class="tab-content">
                        <div class="tab-pane active" id="profile">

                        	
                        	<table id="example" class="display" style="width:100%">
						        <thead>
						            <tr>
						            	<th>Tgl. Mohon</th>						            	
						                <th>Nama</th>
						                <th>TTL</th>
						                <th>Buku pelaut</th>
						                <th>Sertifikat</th>						                	
						                <th>Status</th>						                
						            </tr>
						        </thead>
						        <tbody>
						    		<?php if(!empty($selected_tahun)){

						    			$tahun = "";
						    			foreach ($selected_tahun as $key) {
						    				$tahun .= "'". $key . "',";
						    			}

						    			$tahun = substr($tahun, 0 , -1);

						    			// $tahun .= "'";

						    			$sql = "SELECT a.tgl_mohon,						    			               
						    			               b.nama,
						    			               CONCAT(b.tempat_lahir, '/' , b.tanggal_lahir) AS ttl,
						    			               c.nomor_buku AS nomor_buku,
						    			               CONCAT(d.nama_sertifikat,' - ', d.tgl_terbit) AS sertifikat,
						    			               e.arti_singkat AS status
						    			        FROM masa_layar a
						    			        LEFT JOIN pemohon b ON a.pemohon_id = b.id
						    			        LEFT JOIN buku_pelaut c ON b.id = c.pemohon_id
						    			        LEFT JOIN (SELECT pemohon_id,
								                                 nama_sertifikat,
								                                 tgl_terbit,
								                                 MAX(id)
								                          FROM sertifikat_pelaut
								                          GROUP BY pemohon_id) d ON c.pemohon_id = d.pemohon_id
								                LEFT JOIN kode_status e ON a.status = e.kode_angka          
						    			        WHERE YEAR(a.tgl_mohon) IN ($tahun) 
						    			              AND status IN('210','310','399','400')";		
						    			$bms = $this->db->query($sql);
						    			foreach ($bms->result_array() as $bm) { ?>
						    				<tr>
						    					<td><?php echo $bm['tgl_mohon'];?></td>						    					
						    					<td><?php echo $bm['nama'];?></td>
						    					<td><?php echo $bm['ttl'];?></td>
						    					<td><?php echo $bm['nomor_buku'];?></td>
						    					<td><?php echo $bm['sertifikat'];?></td>						    					
						    					<td><?php echo $bm['status'];?></td>
						    				</tr>
						    			<?php }


						    		 };?>        
						        </tbody>							        
						    </table>
                            
                            <script type="text/javascript">
                            	$(document).ready(function() {
								    $('#example').DataTable();
								} );
                            </script>
                        </div>
                        <div class="tab-pane" id="messages">
                           <canvas id="myChart"></canvas>		     
                        </div>                           
                    </div>
                </div>
            </div>
	    </div>

	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
	    $('.js-example-basic-multiple').select2({
	    	"language": {
			       "noResults": function(){
			           return "Belum ada data";
			       }
			   },
			    escapeMarkup: function (markup) {
			        return markup;
			    }
	    });
	});

	window.chartColors = {
		red: 'rgb(255, 99, 132)',
		orange: 'rgb(255, 159, 64)',
		yellow: 'rgb(255, 205, 86)',
		green: 'rgb(75, 192, 192)',
		blue: 'rgb(54, 162, 235)',
		purple: 'rgb(153, 102, 255)',
		grey: 'rgb(201, 203, 207)'
	};


	<?php if(!empty($selected_tahun)){ 
		
		$alpha = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 
		          'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 
		          'W', 'X', 'Y', 'Z'];
		$color = ['red','orange','yellow','green','blue','purple','grey'];

		$loop = 1;
		$sql = "SELECT ";
		$join = "";
				
		foreach ($selected_tahun as $key) {
			$sql .= "IFNULL(" . $alpha[$loop] . ".jml,0) AS jml_". $alpha[$loop] .",";
			$join .= "LEFT JOIN (SELECT MONTH(tgl_mohon) AS bln,
			                     		COUNT(id) AS jml FROM masa_layar 
			                     WHERE YEAR(tgl_mohon) = ". $key ."
			                           AND status IN('210','310','399','400') 
			                     GROUP BY MONTH(tgl_mohon)) ". $alpha[$loop] ." ON A.id = " . $alpha[$loop] .".bln ";
			$loop++;
		}

		$sql =  substr($sql,0,-1) . " FROM bulan A ";
		$sql .= $join;
		$sql .= " GROUP BY A.id";

		//echo $sql;

		$chart = $this->db->query($sql);

	} ?>	

	<?php if(!empty($selected_tahun)){ ?>
	var lineChartData = {
			labels: ['Januari', 'Februari', 'Maret', 'April', 
			         'Mei', 'Juni', 'Juli','Agustus',
			         'September','Oktober','November','Desember'],
			datasets: [
			<?php 
				$datasets = "";
				$loop = 1;
				foreach ($selected_tahun as $key) { 
					
					$data = "";
					foreach ($chart->result_array() as $ch) {
						$data .= $ch["jml_" . $alpha[$loop]] . ",";
					}

					$datasets .= 
					"{
						label: 'Tahun ". $key ."',
						borderColor: window.chartColors.". $color[$loop - 1] .",
						backgroundColor: window.chartColors.". $color[$loop - 1] .",
						fill: false,
						lineTension: 0,           
						data: [". substr($data, 0,-1) ."],
						yAxisID: 'y-axis',
					},";

					$loop++;
				}

				echo $datasets;

			?>	


			
		  ]
		};

		window.onload = function() {
			var ctx = document.getElementById('myChart').getContext('2d');
			window.myLine = Chart.Line(ctx, {
				data: lineChartData,
				options: {
					responsive: true,
					hoverMode: 'index',
					stacked: false,
					title: {
						display: true,
						text: 'Jumlah permohonan masa layar per tahun'
					},
					scales: {
						yAxes: [
							{
								type: 'linear', // only linear but allow scale type registration. This allows extensions to exist solely for log scale for instance
								display: true,
								position: 'left',
								id: 'y-axis',
								ticks: {
									precision:0
								}
							}
							
						],

					}
				}
			});
		};
	<?php } ?>

</script>