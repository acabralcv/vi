

var modelUser = {

    data: [],

    getUserProfiles: function () {

        var modelGet = {
            user_id: null,
            page: 0,
            size: 20,
            sort: 'dateCreated,desc'
        }

        modelApp.getJsonData("api/profiles/users-profiles", modelGet, function (dataResponse) {
            if (dataResponse.content && dataResponse.content.length > 0) {
                modelQuestion.showTableGridView("questios_table_box", dataResponse.content)
            }
        })
    },

    gaddUserProfile: function () {

        var modelPots = {
            id: null,
            user_id: null
        }

        modelApp.posJsonData("api/profiles/add-user-profile",modelPots, {}, function (dataResponse) {
           console.log(dataResponse)
        })
    },

    showTableGridView: function (tableElemID, modelData) {

        var items = '';

        if (!$('#' + tableElemID))
            alert("Table identifiier not found")

        $.each(modelData, function (idx, modelItem) {
            console.log(modelItem)
            items += "<tr>";
            items += "<td>" + idx + "</td>";
            items += "<td>" + modelItem.title + "</td>";
            items += "<td>Action</td>";
            items += "</tr>";
        })

        $('#' + tableElemID + ' tbody').html(items)

    }

}