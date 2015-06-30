'use strict';

angular.module('yapp')
	.directive('calendar',function(){
		return {
        templateUrl:'scripts/directives/sidenav/calendar/calendar.html?v='+window.app_version,
        restrict: 'E',
        replace: true,
    	}
	});


