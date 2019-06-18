
var modelTask = {

    data: [],

    remove: function (id) {

        if (confirm("Tem certeza de que deseja excluir esta tarefa? \n ") == true && id) {
            var modelPots = {id: id}
            modelApp.startLoading()
            serviceProxy.postJsonData("api/tasks/delete", modelPots, {}, function (dataResponse) {
                modelApp.stopLoading()

                if (dataResponse && dataResponse.statusAction == 1) {
                    modelApp.showSuccessMassage("Tarefa removido com sucesso", false)
                    location.reload()
                } else
                    modelApp.showErrorMassage(dataResponse.message, false)
            })
        }
    },

}
