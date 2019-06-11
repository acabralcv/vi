
var modelImage = {

    //recluso
    //user
    showImagesLabels: function(){ //show images labels when uploading

        var strFiles = '',
            anexoInput = document.getElementById('InputImageUploads');
        if ((anexFile = anexoInput.files ? anexoInput.files : [])) {

            $.each(anexoInput.files, function(idx, modelFile) {
                strFiles += ' <div class="col">' + modelFile.name + '</div>';
            });

            $('#ImagesUploadWidgetBtn').prop('disabled', false);
            $('#ImagesUploadWidgetFileName').html(strFiles);
        }
    },
    
    upload: function (userId, imagesInput, description, callback) {

        console.log('Uploading images...')

        //var imagesInput = document.getElementById('InputImageUploads');

        if (imagesInput && imagesInput.files) {

            $.each(imagesInput.files, function (idx, anexoImage) {

                var formData = new FormData();
                formData.append('document_type', 'OTHER');
                formData.append('file', anexoImage);
                formData.append('file_type', "IMAGE");
                formData.append('userId', userId);
                formData.append('description', description);
                
                console.log(formData)

                modelStorage.filesUploadProxy('api/storage/exchange-image', formData, function (dataResponse) {
                    console.log(dataResponse)
                    if(dataResponse && dataResponse.data ){
                       callback(dataResponse)
                    }else{
                        modelApp.showErrorMassage(dataResponse.message, false)
                        callback(null)
                    }
                })
            })
        }
    },
}