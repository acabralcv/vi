

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

    openFotoModal: function () {
alert()
        $("#ImageFileUpload").modal()
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