"use strict";

var modelStorage = {

    filesUploadProxy: function (resourse, formData, callback) {

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