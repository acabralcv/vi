"use strict";

var modelFile = {

    data: [],

    getFiles: function () {

        var modelGet = {
            page: 0,
            size: 20,
            sort: 'dateCreated,desc'
        };

        modelApp.getJsonData("files/get", modelGet, function (dataResponse) {
            if (dataResponse.content && dataResponse.content.length > 0) {

            }
        })
    },

    upload: function () {

        var anexoInput = document.getElementById('InputFileUploads');

        if (anexoInput.files) {

            /*$('#fileUploadBtn').prop('disabled', false);
            $('#fileUploadObservacao').prop('disabled', true);
            $('#InputFileUploads').prop('disabled', true);*/

            $.each(anexoInput.files, function (idx, anexoFile) {

                var formData = new FormData();
                //formData.append('_csrf', $('[name="_csrf"]').val());
                formData.append('target_table_id', 412423143124);
                formData.append('tipo_anexo', "ANEXO");
                formData.append('basePath', 'static/anexos');
                formData.append('file', anexoFile);
                formData.append('descricao', $('#notificacaoanexos-descricao').val());

                modelFile.FilesUploadProxy('files/exchange-file', formData, function (dataResponse) {
                    console.log(dataResponse);

                    if (dataResponse.content && dataResponse.content.length > 0) {

                    }
                })
            })
        }
    },

    FilesUploadProxy: function (resourse, formData, callback) {

        $.ajax({
            url: modelApp.getServiceUrl(resourse, []),
            /*; baseUrl  config.js  + resource*/
            type: 'POST',
            data: formData,
            cache: false,
            dataType: 'json',
            processData: false,
            /* Don't process the files */
            contentType: false,
            /* Set content type to false to tell the server its a query string request */
            success: function (data, textStatus, jqXHR) {

                if (typeof data.error === 'undefined') {
                    callback(data);
                } else {
                    console.log('ERRORS: ' + data.error);
                    callback(data.error);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log('ERRORS: ' + textStatus);
                callback(textStatus);
            }
        });

    }


};