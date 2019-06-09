var modelImage = {
    
    getImageDeatils: function (dataImages, ElementBoxID) {

        var srtImages = '';

        $.each(dataImages, function(idx, oImage){

            srtImages += '<div class="md-image-card">';
            srtImages += '<span class="fas fa-plus-circle"></span>';
            srtImages += '<img width="128px" height="128px" src="/api/storage/images-details?&id=' + oImage.storageId + '" />';
            srtImages += '</div>';

        })
        
        $(ElementBoxID).html(srtImages)
    },
    

    uploadImages: function (userId) {

        var imagesInput = document.getElementById('InputImageUploads');

        if (imagesInput && imagesInput.files) {

            $.each(imagesInput.files, function (idx, anexoImage) {

                var formData = new FormData();
                formData.append('document_type', 'OTHER');
                formData.append('file', anexoImage);
                formData.append('file_type', "IMAGE");
                formData.append('userId', userId);
                formData.append('description', $('#imageFileObservacao').val());

                modelStorage.filesUploadProxy('api/storage/exchange-image', formData, function (dataResponse) {
                    if(dataResponse && dataResponse.data ){
                        modelUser.addUserProfileImage(userId, dataResponse.data.id)
                    }
                })
            })
        }
    },

    openUImagesModal: function () {

        $("#UserImageModal").modal()
    },
}

var modelRecluso = {

    data: [],

    getReclusoImages: function (userId) {

        var modelGet = {
            userId: userId,
            page: 0,
            size: 20,
            sort: 'dateCreated,desc'
        }

        modelApp.getJsonData("api/storage/recluso-images", modelGet, function (dataResponse) {
            console.log(dataResponse)
            if (dataResponse && dataResponse.data && dataResponse.data && dataResponse.data.content.length > 0) {
                modelUser.getUserImageDeatils(dataResponse.data.content)
            }
        })
    },

    addReclusoProfileImage: function (reclusoId, profile_image_id) {
        
        var modelPots = {
            id: reclusoId,
            profileImage: {id : profile_image_id }
        }

        modelApp.postJsonData("api/reclusos/add-profile-image",modelPots, {}, function (dataResponse) {
            console.log(dataResponse)
            if(dataResponse && dataResponse.statusAction == 1){
                alert()
                modelApp.showSuccessMassage("Imagem do perfil do recluso associado com sucesso", false)
               // location.reload()
            }else
                modelApp.showErrorMassage(dataResponse.message, false)
        })
    },


    uploadReclusoImages: function (reclusoId) {

        var imagesInput = document.getElementById('InputImageUploads');

        if (imagesInput && imagesInput.files) {

            $.each(imagesInput.files, function (idx, anexoImage) {

                var formData = new FormData();
                formData.append('document_type', 'OTHER');
                formData.append('file', anexoImage);
                formData.append('file_type', "IMAGE");
                formData.append('reclusoId', reclusoId);
                formData.append('description', $('#imageFileObservacao').val());

                modelStorage.filesUploadProxy('api/storage/exchange-image', formData, function (dataResponse) {
                    console.log(dataResponse)
                    if(dataResponse && dataResponse.data ){
                        modelRecluso.addReclusoProfileImage(reclusoId, dataResponse.data.id)
                    }
                })
            })
        }
    },
    
    

    openReclusoImagesModal: function (userId) {

        modelImage.openUImagesModal()
        
        console.log(userId)
        
        modelUser.getUserImages(userId);
    },


}


$(document).ready(function() {
    if( $("#action_user_id") && $("#action_user_id").val()){
        modelUser.getUserProfiles($("#action_user_id").val())
    }
})