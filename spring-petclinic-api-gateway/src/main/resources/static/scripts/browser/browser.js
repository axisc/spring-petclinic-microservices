'use strict';

angular.module('browser', ['ui.router'])
	.config(['$stateProvider', function($stateProvider){
		$stateProvider
			.state('browse', {
				parent: 'app',
				url: '/browse/created-owner',
				template: '<browser></browser>'
				
			})
		
	}]);