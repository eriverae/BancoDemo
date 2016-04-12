'use strict';
var app = angular.module('demobanco');
// Create a controller with name personsFormController to bind to the form section.
app.controller('clienteFormController', function ($scope, $rootScope, $stateParams, $state, 
          clienteService,modalService) {
  
  $scope.cliente={};
  
  if (angular.isDefined($stateParams.idCliente)){
    console.log('Cliente a modificar, ID = '+ $stateParams.idCliente);
    clienteService.get({id: $stateParams.idCliente}).$promise.then(
      function (data) {
        $scope.cliente = data;
        //A partir de Angular 1.3, ng-model requiere un objeto de tipo Date valido, no acepta un String
        $scope.cliente.fechaNacimiento = new Date($scope.cliente.fechaNacimiento); 
      },
      function () {
        // Broadcast the event for a server error.
        $rootScope.$broadcast('error');
      });
  }
  
  //TODO Reemplazar por consulta de items asociados a la enumeracion Java
  $scope.listaTiposIdentificacion = [
      {id: 'CC', name: 'Cedula'},
      {id: 'TI', name: 'T. Identidad'},
      {id: 'NIT', name: 'NIT'}
    ];
    
  //TODO Reemplazar por consulta de items asociados a la enumeracion en Java
  $scope.listaGeneros =[
    {id:'M', label:'Masculino'},
    {id:'F', label:'Femenino'}
  ];
  
  // Clears the form. Either by clicking the 'Clear' button in the form, or when a successfull save is performed.
  $scope.clearForm = function () {
    $scope.cliente = null;
    // Resets the form validation state.
    $scope.clienteForm.$setPristine();
    // Broadcast the event to also clear the grid selection.
    $rootScope.$broadcast('clear');
  };

  // Calls the rest method to save a Cliente.
  $scope.updateCliente = function () {
    clienteService.save($scope.cliente).$promise.then(
    function () {
      // Broadcast the event to refresh the grid.
      $rootScope.$broadcast('refreshGrid');
      // Broadcast the event to display a save message.
      $rootScope.$broadcast('clienteSaved');
      
    },
    function () {
      // Broadcast the event for a server error.
      $rootScope.$broadcast('error');
    });
  };

  // Picks up the event broadcasted when the person is selected from the grid and perform the person load by calling
  // the appropiate rest service.
  $scope.$on('clienteSelected', function (event, id) {
    console.log('Cliente seleccionado, ID = '+ id);
    $scope.cliente = clienteService.get({id: id});
  });
  
  $scope.$on('clienteSaved', function(){
    var modalOptions = {
          //closeButtonText: 'Cancelar',
          actionButtonText: 'Continuar',
          headerText: 'Resultado de operación',
          bodyText: 'Operación existosa!'
      };

      modalService.showModal({}, modalOptions).then(function () {
        $scope.clearForm();
        $state.go('clientes');
      });
  });
  
  $scope.cancelar = function(){
    $state.go('clientes');
  };
  
});
