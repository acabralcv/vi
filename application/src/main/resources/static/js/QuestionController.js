"use strict";

function TamplateView() {

    var labelName,
        valueField,
        set = function (labelName, valueField) {
            this.labelName = labelName;
            this.valueField = valueField;
            return this;
    }

}

var modelQuestion = {

    data: [],

    getQuestions: function () {

        var modelGet = {
            page: 0,
            size: 20,
            sort: 'dateCreated,desc'
        }

        modelApp.getJsonData("questions", modelGet, function (dataResponse) {
            if(dataResponse.content && dataResponse.content.length > 0){
                modelQuestion.showTableGridView("questios_table_box", dataResponse.content)
            }
        })
    },

    getQuestions1: function () {

        var modelGet = {
            page: 0,
            size: 15,
            sort: 'title,desc'
        }

        modelApp.getJsonData("questions", modelGet, function (dataResponse) {
            if(dataResponse.content && dataResponse.content.length > 0){
                modelQuestion.showTableGridView1("employeesTable", dataResponse.content)
            }
        })
    },
    
    showTableGridView: function (tableElemID, modelData) {

        var items = '';

        if(!$('#' + tableElemID))
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

    },
    
    showTableGridView1: function (tableElemID, modelData) {

        var table = $('#employeesTable').DataTable({
                data: modelData, //[{id:1, title: 'ola'}],
                columns: [
                    //{ data: 'id' },
                    { data: 'title' },
                    { data: 'dateCreated' },
                    { data: 'datedUpdated' },
                    { data: 'status' },
                ]
            });
        
    }
    
}


modelQuestion.getQuestions();

modelQuestion.getQuestions1();

  