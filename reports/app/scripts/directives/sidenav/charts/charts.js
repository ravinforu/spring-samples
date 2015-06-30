'use strict';

angular.module('yapp')
	.directive('charts',function(){
		return {
        templateUrl:'scripts/directives/sidenav/charts/charts.html?v='+window.app_version,
        restrict: 'E',
        replace: true,
    	}
	});


