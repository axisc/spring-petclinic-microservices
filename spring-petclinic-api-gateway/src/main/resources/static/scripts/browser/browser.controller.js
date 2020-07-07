'use strict';

angular.module('browser')
    .controller('BrowserController', function ($http,$interval) {
        var self = this;
        $http.get('api/communications/browse/created-owner').then(function (resp) {
        	self.owners = resp.data;
        });
        
        $interval(function(){
            $http.get('api/communications/browse/created-owner').then(function (resp) {
            	self.owners = resp.data;
            });
        }, 10000);
        
    });
