'use strict';
// Declare app level module which depends on views, and components
var app = angular.module('demobanco', ['ui.router','ngResource', 'ui.bootstrap','ngResource','ngGrid','app.utils']);

app.config(['$stateProvider','$urlRouterProvider', function($stateProvider,$urlRouterProvider) {
  $urlRouterProvider.otherwise("/home");
  
  $stateProvider.
    state('home',{
      url: '/home',
      templateUrl: 'home.html'
    })
      
    .state('clientes',{
      url:'/clientes',
      templateUrl: 'clientes/clientes.html',
      controller: 'clientesListController'
    })
  
    .state('crearCliente',{
      url: '/crearCliente',
      templateUrl: 'clientes/formularioCliente.html',
      controller: 'clienteFormController'
    })
    
    .state('modificarCliente',{
      url: '/modificarCliente',
      templateUrl: 'clientes/formularioCliente.html',
      controller: 'clienteFormController',
      params : {'idCliente':null}
    });
    /*
    $routeProvider
    .when('/clientes', {
        templateUrl : 'clientes/clientes.html',
        controller  : 'clientesListController'
    })
    
    .when('/crearModificarCliente', {
        templateUrl : 'clientes/formularioCliente.html',
        controller  : 'clienteFormController'
    })
                
    .otherwise({redirectTo: '/view1'});
    */
}]);
