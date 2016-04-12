'use strict';

var myModule = angular.module('demobanco');
// Service that provides persons operations
myModule.factory('clienteService', function ($resource) {
    return $resource('/Banco/api/clientes/:id');
});



