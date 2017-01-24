(function() {
    'use strict';
    angular
        .module('gatewayApp')
        .factory('Device', Device);

    Device.$inject = ['$resource', 'DateUtils'];

    function Device ($resource, DateUtils) {
        var resourceUrl =  'goblob/' + 'api/devices/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.created = DateUtils.convertLocalDateFromServer(data.created);
                        data.signedIn = DateUtils.convertLocalDateFromServer(data.signedIn);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.created = DateUtils.convertLocalDateToServer(copy.created);
                    copy.signedIn = DateUtils.convertLocalDateToServer(copy.signedIn);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.created = DateUtils.convertLocalDateToServer(copy.created);
                    copy.signedIn = DateUtils.convertLocalDateToServer(copy.signedIn);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
