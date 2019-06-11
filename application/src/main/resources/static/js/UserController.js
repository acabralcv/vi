

var modelUser = {

    data: [],

    getProfiles: function (userId) { //get user's profiles

        var modelGet = {
            user_id: userId,
            page: 0,
            size: 20,
            sort: 'dateCreated,desc'
        }

        serviceProxy.getJsonData("api/users/users-profiles", modelGet, function (dataResponse) {

            if (dataResponse && dataResponse.data && dataResponse.data.length > 0) {
                modelUser.showUserProfiles(dataResponse.data)
            }else
                modelUser.showUserProfiles([])
        })
    },

    getImages: function (userId) { //get list of user's images

        var modelGet = {
            userId: userId,
            page: 0,
            size: 20,
            sort: 'dateCreated,desc'
        }
        
        serviceProxy.getJsonData("api/storage/user-images", modelGet, function (dataResponse) {
            console.log(dataResponse)
            if (dataResponse && dataResponse.data && dataResponse.data && dataResponse.data.content.length > 0) {
                modelUser.showImage(dataResponse.data.content)
            }
        })
    },

    showImage: function (dataImages) { //show image stream
        
        var srtImages = '';

       for(var i =0; i< dataImages.length; i++){
           
           var oImage = dataImages[i]
           
           srtImages += '<div class="md-image-card">';
                srtImages += '<span class="fas fa-plus-circle"></span>';
                srtImages += '<img width="128px" height="128px" src="/api/storage/images-details?&id=' + oImage.storageId + '" />';
           srtImages += '</div>';

        }
        $("#listaUserImagesTable").html(srtImages)        
    },

    addProfileImage: function (userId, profile_image_id) { //add user profile image

        var modelPots = {
            profile_image_id: profile_image_id,
            id: userId
        }

        serviceProxy.postJsonData("api/users/add-profile-image",modelPots, {}, function (dataResponse) {
           if(dataResponse && dataResponse.statusAction == 1){
               modelApp.showSuccessMassage("Imagem do perfil associado com sucesso", false)
               location.reload()
           }else
               modelApp.showErrorMassage(dataResponse.message, false)
        })
    },

    addProfile: function (userId) { //add user profiles

        var modelPots = {
            profileId: $("#prfile_id").val(),
            userId: userId
        }

        modelApp.startLoading()
        serviceProxy.postJsonData("api/users/add-profile",modelPots, {}, function (dataResponse) {

            modelApp.stopLoading()

           if(dataResponse && dataResponse.statusAction == 1){
               modelApp.showSuccessMassage("Perfil associado com sucesso.", false)
               modelUser.getProfiles(userId)
           }else
               modelApp.showErrorMassage(dataResponse.message, false)
        })
    },

    uploadImages: function (userId) { //upload multiply images

        var imagesInput = document.getElementById('InputImageUploads');

        if (imagesInput && imagesInput.files) {

            $.each(imagesInput.files, function (idx, anexoImage) {

                modelApp.startLoading()
                
                //ImageController.js    
                modelImage.upload(userId, imagesInput, $('#imageFileObservacao').val(), function(dataResponse){

                    modelApp.stopLoading()

                    if(dataResponse && dataResponse.data ){
                        //in case of multiply upload, we assign the last one as user profile
                        modelUser.addProfileImage(userId, dataResponse.data.id)
                    }
                })
            })
        }
    },

    openUserImagesModal: function (userId) { //open modal for user images 
        modelModal.open() //ModalControllers.js
        modelUser.getImages(userId);
    },

    showUserProfiles: function (data) { //show user profiles list

        var items = '';
        $.each(data, function (idx, oPerifl) {
            items += "<tr>";
            items += "<td>" + oPerifl.name + "</td>";
            items += "<td>Action</td>";
            items += "</tr>";
        })

        if(items == '')
            items = '&nbsp;&nbsp; <br /> [Nenhum perfil/cargo associado.]  <br /> ';

        $('#listaUserPerfilTable tbody').html(items)
    }

}


$(document).ready(function() {
    if( $("#action_user_id") && $("#action_user_id").val()){
        modelUser.getProfiles($("#action_user_id").val())
    }
})