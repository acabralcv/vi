

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
            if (dataResponse.content && dataResponse.content.length > 0) {
                modelUser.showUserProfiles(dataResponse.content)
            }
        })
    },

    addUserProfile: function (userId) {

        var modelPots = {
            profileId: $("#prfile_id").val(),
            userId: userId
        }

        console.log(modelPots)

        modelApp.postJsonData("api/users/add-profile",modelPots, {}, function (dataResponse) {
            console.log(dataResponse)
           if(dataResponse.statusAction == 1){
               modelUser.getUserProfiles(userId)
               alert(1)
            }else
                alert(0)
        })
    },

    showUserProfiles: function (data) {

        var items = '';

        $.each(data, function (idx, oPerifl) {
            console.log(oPerifl)
            items += "<tr>";
            items += "<td>" + oPerifl.name + "</td>";
            items += "<td>Action</td>";
            items += "</tr>";
        })

        $('#listaUserPerfilTable tbody').html(items)

    }

}