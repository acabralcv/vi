
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

        var url = this.getServiceUrl(resourse, params);

        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(postData),
            success: function(data) {
                if (data && data) {

                    callback(data);
                } else {
                    callback(null);
                    console.error(data)
                }

            }
        });

    }

};