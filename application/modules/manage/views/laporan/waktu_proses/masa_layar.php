<link href="https://cdn.jsdelivr.net/npm/select2@4.0.12/dist/css/select2.min.css" rel="stylesheet" />
<script src="https://cdn.jsdelivr.net/npm/select2@4.0.12/dist/js/select2.min.js"></script>

<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>

<div class="container">
	<div class="row">
	    <div class="col-md-12">
	    	<div class="card">
			  <div class="card-header">
			    Featured
			  </div>
			  <div class="card-body">
			  	<div class="alert alert-success">
				    <strong>Catatan:</strong> Waktu proses adalah jangka waktu antara bukti bayar diunggah sampai selesai validasi berkas
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
				  <button type="submit" class="btn btn-primary">Submit</button>
				</form>	

				<canvas id="myChart"></canvas>		   
			  </div>
			</div>
	    </div>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
	    $('.js-example-basic-multiple').select2();
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
			$sql .= "IFNULL(" . $alpha[$loop] . ".waktuproses,0) AS waktuproses_". $alpha[$loop] .",";
			$join .= "LEFT JOIN (SELECT MONTH(tgl_mohon) AS bln,
			                     		AVG(total_harikerja_proses) AS waktuproses 
			                     FROM masa_layar 
			                     WHERE YEAR(tgl_mohon) = ". $key ." AND `status` IN('310','400') 
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
						$data .= $ch["waktuproses_" . $alpha[$loop]] . ",";
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
						text: 'Rata-rata waktu proses Masa Layar per tahun (rata-rata hari)'
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