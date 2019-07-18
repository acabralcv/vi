
var modelRecluso = {

    data: [],

    getLocalizacao: function (tipoLocalizacao, elemName, elemValue, elementCallbackID) {

        //tipoLocalizacao => 'complexo', 'setor', 'ala' or 'cela'
        //console.log(tipoLocalizacao, elemName,  elemValue, elementCallbackID)

        var resource = 'api/' + tipoLocalizacao,
            modelGet = {
                page: 0,
                size: 20,
                sort: 'dateCreated,desc'
             }

        //parent_id do que vai ser filtrado no serviço Ex: id_cadeia, id_complexo, ...
        //Ex: complexos de uma determinada cadeia: api/complexos?id_cadeia=7f40d4ae-e59f-40bd-a182-820aaa7376cc&..
        modelGet["" + elemName] = elemValue,
        //
        serviceProxy.getJsonData(resource, modelGet, function (dataResponse) {
            if (dataResponse && dataResponse.data && dataResponse.data && dataResponse.data.length > 0) {
                modelRecluso.fillDropdown(elementCallbackID, dataResponse.data, "Selecionar")
            }else
                modelRecluso.fillDropdown(elementCallbackID, [], "Selecionar")
        })
    },

    getReclusoImages: function (reclusoId) {

        var modelGet = {
            userId: reclusoId,
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


    getReclusoCelas: function (reclusoId) {

        var modelGet = {
            recluso_id: reclusoId,
        }

        serviceProxy.getJsonData("api/recluso-celas", modelGet, function (dataResponse) {
            console.log(dataResponse)
            if (dataResponse && dataResponse.data && dataResponse.data && dataResponse.data.length > 0) {
                modelRecluso.showReclusoCelas(dataResponse.data)
            }
        })
    },

    getReclusoCelaHistorico: function (reclusoId) {

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

    addReclusoCela: function (reclusoId) {

        if( !$('#recluso_cela_ala_id') || !$('#recluso_cela_ala_id').val()){
            alert("Por favor seleciona a 'Cela'")
            return false;
        }

        var modelPots = {
            recluso: {id:reclusoId},
            cela: {id : $('#recluso_cela_ala_id').val() }
        }

        modelApp.startLoading()
        serviceProxy.postJsonData("api/reclusos/add-recluso-cela", modelPots, {}, function (dataResponse) {

            modelApp.stopLoading()

            if(dataResponse && dataResponse.statusAction == 1){
                modelApp.showSuccessMassage("Recluso alocado na cela com sucesso", false)
                modelRecluso.getReclusoCelas($("#action_recluso_id").val())
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

    fillDropdown: function (elementId, dataLocalizacao, pronptText) {

        var dropDistribuicaoOp = $("select" + elementId);
        dropDistribuicaoOp.find('option')
            .remove()
            .end()
            .append('<option value="">' + pronptText + '</option>')
            .val('');

        $.each(dataLocalizacao, function(idx, modelLocalizacao) {
            dropDistribuicaoOp.append($('<option></option>').val(modelLocalizacao.id).html(modelLocalizacao.nome));
        });
    },

    showReclusoCelas: function (data) { //show recluso celas list

        var items = '';
        $.each(data, function (idx, oReclusoCela) {
            var dateRemocao = (oReclusoCela.dateRemocao != null && oReclusoCela.dateRemocao != "null") ? oReclusoCela.dateRemocao : '';

            items += "<tr>";
            items += "<td>" + oReclusoCela.cela.ala.setor.complexo.cadeia.nome + "</td>";
            items += "<td>" + oReclusoCela.cela.ala.setor.complexo.nome + "</td>";
            items += "<td>" + oReclusoCela.cela.ala.setor.nome + "</td>";
            items += "<td>" + oReclusoCela.cela.ala.nome + "</td>";
            items += "<td>" + oReclusoCela.cela.nome + "</td>";
            items += "<td>" + dateRemocao + "</td>";
            items += "<td>Action</td>";
            items += "</tr>";
        })

        if(items == '')
            items = '&nbsp;&nbsp; <br /> [Nenhum perfil/cargo associado.]  <br /> ';

        $('#listaUserPerfilTable tbody').html(items)
    }


}



$(document).ready(function() {

    //get recluso localização historicos
    if($("#action_recluso_id") && $("#action_recluso_id").val()){
        modelRecluso.getReclusoCelas($("#action_recluso_id").val())
    }
})