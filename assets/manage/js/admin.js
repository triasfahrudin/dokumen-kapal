  
  
  function ajax_alasan(jenis,permohonan_id){
     $.post(base_path + "/ajax_alasan",
        { 
          jenis:jenis,
          permohonan_id : permohonan_id 
        }
      ).done( function( data ){
        $('#mb-alasan-status').html(data);
        $('#modalAlasanStatus').modal('show');
      })
  }
  
  function detail_pemohon(pemohon_id){
    $.get(base_path + "/ajax_detail_pemohon",{ pemohon_id:pemohon_id })
      .done( function( data ){
        $('#tabBiodata').html(data.detail);
        $('#modalBiodata').modal('show');
      })
  }

  function ajax_biodata(pemohon_id){
      $.get(base_path + "/ajax_biodata",{ pemohon_id:pemohon_id })
      .done( function( data ){
        $('#tabBiodata').html(data.biodata);
        $('#modalBiodata').modal('show');
      })
  }

  function ajax_ijazah_laut(pemohon_id){
      $.get(base_path + "/ajax_ijazah_laut",{ pemohon_id:pemohon_id })
      .done( function( data ){
        $('#contentIjazahLaut').html(data.ijazah_laut);
        $('#modalIjazahLaut').modal('show');
      })
  }


  function ajax_status_permohonan(jenis,status,permohonan_id) {
     

      if(status == 'diterima'){
         
         var return_confirm = confirm('Apakah anda yakin ?');          
         
         if(return_confirm){

           var fd = new FormData();
           fd.append('jenis', jenis);
           fd.append('status',status);
           fd.append('permohonan_id', permohonan_id);           

           $.ajax({
               url: base_path + "/ajax_status_permohonan",
               type: "POST",
               data: fd,
               dataType: 'json',
               processData: false,
               contentType: false,
               beforeSend:function() 
               {
                 $('#p_loading_' + permohonan_id).show();
               },
               success: function(data)
               {                               
                 $('#p_' + permohonan_id).addClass('text-primary');   
                 $('#p_' + permohonan_id).html('<a href="#!" onclick="ajax_status_permohonan(\'' + jenis + '\',\'diambil\',' + permohonan_id + ')">AMBIL BERKAS</a>');          
                 $('#p_loading_' + permohonan_id).hide();
               }
             }).done(function( msg ) { });
         }

      }else if(status == 'diambil'){

         var return_confirm = confirm('Apakah anda yakin ?');          
         
         if(return_confirm){

           var fd = new FormData();
           fd.append('jenis', jenis);
           fd.append('status',status);
           fd.append('permohonan_id', permohonan_id);           

           $.ajax({
               url: base_path + "/ajax_status_permohonan",
               type: "POST",
               data: fd,
               dataType: 'json',
               processData: false,
               contentType: false,
               beforeSend:function() 
               {
                 $('#p_loading_' + permohonan_id).show();
               },
               success: function(data)
               {                               
                 $('#p_' + permohonan_id).removeClass('text-primary').addClass('text-secondary');   
                 $('#p_' + permohonan_id).html('SELESAI');          
                 $('#p_loading_' + permohonan_id).hide();
               }
             }).done(function( msg ) { });
         }
          
      }else if(status == 'ditolak'){
         
         $.confirm({
              title: 'Form penolakan permohonan',
              content: '' +
              '<form action="" class="formName">' +
              '   <div class="form-group">' +
              '     <label>Alasan penolakan</label>' +
              '     <textarea class="form-control alasan" required></textarea>' +
              '   </div>' +
              '</form>',
              buttons: {
                  formSubmit: {
                      text: 'Kirim',
                      btnClass: 'btn-blue',
                      action: function () {
                          var alasan = this.$content.find('.alasan').val();
                          if(!alasan){
                              $.alert('Masukkan alasan penolakan!');
                              return false;
                          }
                           var fd = new FormData();
                           fd.append('jenis', jenis);
                           fd.append('permohonan_id', permohonan_id);
                           fd.append('status','ditolak');
                           fd.append('alasan',alasan);

                           $.ajax({
                             url: base_path + "/ajax_status_permohonan",
                             type: "POST",
                             data: fd,
                             dataType: 'json',
                             processData: false,
                             contentType: false,
                             beforeSend:function() 
                             {
                               $('#p_loading_' + permohonan_id).show();
                             },
                             success: function(data)
                             {                               
                               $('#p_' + permohonan_id).addClass('text-danger');   
                               $('#p_' + permohonan_id).html('DITOLAK');  
                               $('#p_loading_' + permohonan_id).hide();        
                             }
                           }).done(function( msg ) {
                             
                           });
                      }
                  },
                  cancel: function () {
                      //close

                  },
              },
              onContentReady: function () {
                  // bind to events
                  var jc = this;
                  this.$content.find('form').on('submit', function (e) {
                      // if the user submits the form by pressing enter in the field.
                      e.preventDefault();
                      jc.$$formSubmit.trigger('click'); // reference the button and click it
                  });
              }
          });
      }
    }