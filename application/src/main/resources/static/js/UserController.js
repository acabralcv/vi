

var modelUser = {

    data: [],

    getUserProfiles: function (userId) {

        var modelGet = {
            user_id: userId,
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

    getUserImages: function (userId) {

        var modelGet = {
            userId: userId,
            page: 0,
            size: 20,
            sort: 'dateCreated,desc'
        }

        modelApp.getJsonData("api/storage/user-images", modelGet, function (dataResponse) {
            console.log(modelGet)
            console.log(dataResponse)
            if (dataResponse && dataResponse.data && dataResponse.data.length > 0) {
                modelUser.showUserProfiles(dataResponse.data)
            }else
                modelUser.showUserProfiles([])
        })
    },

    addUserProfile: function (userId) {

        var modelPots = {
            profileId: $("#prfile_id").val(),
            userId: userId
        }

        modelApp.postJsonData("api/users/add-profile",modelPots, {}, function (dataResponse) {
           if(dataResponse && ataResponse.statusAction == 1){
               modelApp.showSuccessMassage("Perfil associado com sucesso", false)
               modelUser.getUserProfiles(userId)
           }else
               modelApp.showErrorMassage(dataResponse.message, false)
        })
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

                console.log(userId)

                modelFile.FilesUploadProxy('api/storage/exchange-image', formData, function (dataResponse) {
                    console.log(dataResponse)
                    // if (dataResponse.content && dataResponse.content.length > 0) {
                    //
                    // }
                })
            })
        }
    },

    openUserImagesModal: function (userId) {

        $("#UserImageModal").modal()
        modelUser.getUserImages(userId);
    },

    showUserProfiles: function (data) {

        var items = '';
        $.each(data, function (idx, oPerifl) {
            items += "<tr>";
            items += "<td>" + oPerifl.name + "</td>";
            items += "<td>Action</td>";
            items += "</tr>";
        })

        $('#listaUserPerfilTable tbody').html(items)
    }

}


$(document).ready(function() {
    if( $("#action_user_id") && $("#action_user_id").val()){
        modelUser.getUserProfiles($("#action_user_id").val())
    }
})