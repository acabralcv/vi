"use strict";

var baseUrl = 'http://localhost:8081/',
    serviceAccessToken = 'access_token=Igh6KZaqq99UBUZwpY1nD-7ZpwAPpROx-ejeBMm9CMoLz4hs1WwKKgSQWgMocYNWaOQCz44kMq38uXKVr90BP7kPjyTw5QwOQ-yN96Mqg-rjH4OiBnAA_M8F3di4xZvE';

var modelApp = {

    defaultMessage: 'Por favor aguarde enquanto o sistema processa esta tarefa.',

    showSuccessMassage: function (message, reload) { //show global success message

        $("#globalSuccessMessageArea").html(message).show()

        if(reload)
            location.reload();

        setTimeout(function () {
            $("#globalSuccessMessageArea").html(message).hide()
        }, 8000)
    },

    showErrorMassage: function (message, reload) { //show global error message

        console.log(message)

        $("#globalAlertMessageArea").html(message).show()

        if(reload)
            location.reload();

        setTimeout(function () {
            $("#globalAlertMessageArea").html(message).hide()
        }, 8000)
    },
    
    startLoading: function(message){ //global message elements difined in \templates\layouts\layout.html
        
        var message = message ? message : this.defaultMessage; 
        
        $('#globalRequestMessage').html(message)

        $('#globalLoadingRequest').show();
        $('#globalLoadingRequest').fadeIn()

        console.log($('#globalLoadingRequest') ? 1 : 0)
    },
    
    stopLoading: function(){
        $("#globalLoadingRequest").hide()
        $('#globalLoadingRequest').fadeOut()
    }
}

setTimeout(function(){
    modelApp.startLoading()
}, 500)

setTimeout(function(){
    modelApp.stopLoading()
}, 1000)