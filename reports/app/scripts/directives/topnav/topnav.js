'use strict';

angular.module('yapp')
	.directive('topnav',function(){
		return {
	        templateUrl:'scripts/directives/topnav/topnav.html?v='+window.app_version,
	        restrict: 'E',
	        replace: true,
	        controller: function($scope){
	        	
	        	$scope.toggleBodyLayout = function(){

			        $('body').toggleClass('box-section');

	        	}

	        	$scope.showMenu = function(){

			        $('#app-container').toggleClass('push-right');

	        	}

	        	


	        	$scope.changeTheme = function(setTheme){

					$('<link>')
					  .appendTo('head')
					  .attr({type : 'text/css', rel : 'stylesheet'})
					  .attr('href', 'styles/app-'+setTheme+'.css');
					  console.log('hey');

					// $.get('/api/change-theme?setTheme='+setTheme);

				}
	        }
    	}
	});


