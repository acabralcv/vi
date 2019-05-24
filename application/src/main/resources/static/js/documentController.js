

var modelDocument = {

    data: [],

    getDocuments: function () {

        var modelGet = {
            page: 0,
            size: 20,
            sort: 'dateCreated,desc'
        }

        modelApp.getJsonData("api/users/users-profiles", modelGet, function (dataResponse) {

            if (dataResponse && dataResponse.data && dataResponse.data.length > 0) {
                modelUser.showUserProfiles(dataResponse.data)
            }else
                modelUser.showUserProfiles([])
        })
    },

    uploadDocument: function () {

        var imagesInput = document.getElementById('InputDocumentsUploads');

        if (imagesInput && imagesInput.files) {

            $.each(imagesInput.files, function (idx, anexoImage) {

                var formData = new FormData();
                formData.append('document_type', 'OTHER');
                formData.append('file', anexoImage);
                formData.append('file_type', "DOCUMENT");
                formData.append('userId', null);
                formData.append('description', $('#documentFileObservacao').val());

                modelStorage.filesUploadProxy('api/storage/exchange-document', formData, function (dataResponse) {
                    console.log(dataResponse)
                    if(dataResponse && dataResponse.data ){
                        $('#InputDocumentsUploads').val(null)
                        $('#documentFileObservacao').val('')
                        //modelDocument.getDocuments( dataResponse.data.id)
                        $("#documentUploadModal").modal('hide')
                        location.reload()
                    }
                })
            })
        }
    },

    openDocumentModal: function () {
        $("#documentUploadModal").modal()
    },

    showDocsLabels: function () {
        var strFiles = '',
            anexoInput = document.getElementById('InputDocumentsUploads');
        if ((anexFile = anexoInput.files ? anexoInput.files : [])) {

            $.each(anexoInput.files, function(idx, modelFile) {
                strFiles += ' <div class="col">' + modelFile.name + '</div>';
            });

            $('#DocumentsUploadWidgetBtn').prop('disabled', false);
            $('#DocumentsUploadWidgetFileName').html(strFiles);
        }
    },


}
