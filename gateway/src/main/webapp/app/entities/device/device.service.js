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
                        data.created = DateUtils.convertDateTimeFromServer(data.created);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
