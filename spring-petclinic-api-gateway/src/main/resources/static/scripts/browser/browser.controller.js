'use strict';

angular.module('browser')
    .controller('BrowserController', ['$http', function ($http) {
        var self = this;

        $http.get('api/communications/browse/created-owner').then(function (resp) {
            self.owners = resp.data;
        });
    }]);
