"use strict";

var baseUrl = 'http://localhost:8081/',
    serviceAccessToken = 'access_token=Igh6KZaqq99UBUZwpY1nD-7ZpwAPpROx-ejeBMm9CMoLz4hs1WwKKgSQWgMocYNWaOQCz44kMq38uXKVr90BP7kPjyTw5QwOQ-yN96Mqg-rjH4OiBnAA_M8F3di4xZvE';
/*

var modelApp = {

    getServiceUrl: function(resource, params){

        var _params = '';

        for (var key in params) {
            _params = _params + '&' + key + '=' + params[key];
        }

        return baseUrl + resource; //+ '?' + _params; // + '&access_token=' + serviceAccessToken ;
        // return baseUrl + 'access_token=' + serviceAccessToken + '&r=' + resource + _params;

    },

    getJsonData: function(resourse, params, callback) {

        var url = this.getServiceUrl(resourse, params);

        $.get(url, function (data) {

            if (data && data.content) {

                callback(data);
            } else {
                callback(null);
            }
        })
            .fail(function (a, b, c) {
                console.error(c);
                callback(null);
            })
    },

    postJsonData: function(resourse, postData, params, callback) {

        Object.toparams = function ObjecttoParams(obj) {
            var p = [];
            for (var key in obj) {
                p.push(key + '=' + encodeURIComponent(obj[key]));
            }
            return p.join('&');
        };

        postData = Object.toparams(postData);

        var url = this.getServiceUrl(resourse, params);

        $.post(url, postData, function (data) {

            if (data && data._data) {

                callback(data);
            } else {
                callback(null);
                console.error(data)
            }
        })
            .fail(function (a, b, c) {
                console.error(a);
                callback(null);
            })
    }

};
*/
