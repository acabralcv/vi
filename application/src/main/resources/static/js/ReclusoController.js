
var modelRecluso = {

    data: [],

    getReclusoImages: function (userId) {

        var modelGet = {
            userId: userId,
            page: 0,
            size: 20,
            sort: 'dateCreated,desc'
        }

        serviceProxy.getJsonData("api/storage/recluso-images", modelGet, function (dataResponse) {
            console.log(dataResponse)
            if (dataResponse && dataResponse.data && dataResponse.data && dataResponse.data.content.length > 0) {
                modelUser.getUserImageDeatils(dataResponse.data.content)
            }
        })
    },

    addProfileImage: function (reclusoId, profile_image_id) {

        var modelPots = {
            id: reclusoId,
            profileImage: {id : profile_image_id }
        }

        modelApp.startLoading()
        serviceProxy.postJsonData("api/reclusos/add-profile-image",modelPots, {}, function (dataResponse) {
            modelApp.stopLoading()

            if(dataResponse && dataResponse.statusAction == 1){
                modelApp.showSuccessMassage("Imagem do perfil do recluso associado com sucesso", false)
                location.reload()
            }else
                modelApp.showErrorMassage(dataResponse.message, false)
        })
    },

    remove: function (reclusoId) {

        var modelPots = {id: reclusoId}
        modelApp.startLoading()
        serviceProxy.postJsonData("api/reclusos/delete",modelPots, {}, function (dataResponse) {
            modelApp.stopLoading()

            if(dataResponse && dataResponse.statusAction == 1){
                modelApp.showSuccessMassage("Tarefa removido com sucesso", false)
                location.reload()
            }else
                modelApp.showErrorMassage(dataResponse.message, false)
        })
    },

    uploadImages: function (reclusoId) {

        var imagesInput = document.getElementById('InputImageUploads');

        if (imagesInput && imagesInput.files) {

            //ImageController.js
            modelImage.upload(reclusoId, imagesInput, $('#imageFileObservacao').val(), function(dataResponse){

                modelApp.stopLoading()

                if(dataResponse && dataResponse.data ){
                    //in case of multiply upload, we assign the last one as recluso profile
                    modelRecluso.addProfileImage(reclusoId, dataResponse.data.id)
                }
            })
        }
    },

    openReclusoImagesModal: function (userId) {
        modelModal.open() //ModalControllers.js
        modelRecluso.getReclusoImages(userId);
    },

}
