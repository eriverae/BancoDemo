var app = angular.module('demobanco');
// Create a controller with name clientesListController to bind to the grid section.
app.controller('clientesListController', function ($scope, $rootScope,$state ,clienteService, modalService) {
    // Initialize required information: sorting, the first page to show and the grid options.
    $scope.sortInfo = {fields: ['id'], directions: ['asc']};
    $scope.clientes = {currentPage: 1};
    $scope.searchText = null;

    $scope.gridOptions = {
        data: 'clientes.list',
        useExternalSorting: true,
        sortInfo: $scope.sortInfo,

        columnDefs: [
            { field: 'id', displayName: 'Id'},
            { field: 'tipoIdentificacion', displayName: 'Tipo ID' },
            { field: 'identificacion', displayName: 'Numero'},
            { field: 'nombre', displayName: 'Nombre'},
            { field: 'genero', displayName: 'Genero'},
            { field: '', width: 80, 
                cellTemplate: '<span class="glyphicon glyphicon-trash remove" ng-click="deleteRow(row)"></span>'+
                '<span class="glyphicon glyphicon-edit modify" ng-click="updateRow(row)"></span>' }
        ],

        multiSelect: false,
        selectedItems: [],
        // Broadcasts an event when a row is selected, to signal the form that it needs to load the row data.
        afterSelectionChange: function (rowItem) {
            if (rowItem.selected) {
                $rootScope.$broadcast('clienteSelected', $scope.gridOptions.selectedItems[0].id);
                console.log('Se emitio evento <clienteSelected>');
            }
        }
    };
    
    $scope.searchTextChanged = function(){
      console.log('Ingreso a funcion searchTextChanged');
    };

    // Refresh the grid, calling the appropriate rest method.
    $scope.refreshGrid = function () {
        var listClientesArgs = {
            page: $scope.clientes.currentPage,
            sortFields: $scope.sortInfo.fields[0],
            sortDirections: $scope.sortInfo.directions[0]
        };

        clienteService.get(listClientesArgs, function (data) {
            $scope.clientes = data;
        });
    };

    // Broadcast an event when an element in the grid is deleted. No real deletion is perfomed at this point.
    $scope.deleteRow = function (row) {
      var clientName = row.entity.nombre;
      var modalOptions = {
          closeButtonText: 'Cancelar',
          actionButtonText: 'Eliminar Cliente',
          headerText: 'Eliminar ' + clientName,
          bodyText: '¿Esta seguro de eliminar este cliente?'
      };

      modalService.showModal({}, modalOptions).then(function (result) {
        $rootScope.$broadcast('deleteCliente', row.entity.id);
      });
      
    };
    
    $scope.updateRow = function(row){
      var idCliente = row.entity.id;
      $state.go("modificarCliente", {'idCliente':idCliente});
    };

    // Watch the sortInfo variable. If changes are detected than we need to refresh the grid.
    // This also works for the first page access, since we assign the initial sorting in the initialize section.
    $scope.$watch('sortInfo', function () {
        $scope.clientes = {currentPage: 1};
        $scope.refreshGrid();
    }, true);

    // Do something when the grid is sorted.
    // The grid throws the ngGridEventSorted that gets picked up here and assigns the sortInfo to the scope.
    // This will allow to watch the sortInfo in the scope for changed and refresh the grid.
    $scope.$on('ngGridEventSorted', function (event, sortInfo) {
      $scope.sortInfo = sortInfo;
    });

    // Picks the event broadcasted when a person is saved or deleted to refresh the grid elements with the most
    // updated information.
    $scope.$on('refreshGrid', function () {
      $scope.refreshGrid();
    });
    
    // Picks the event broadcasted when the form is cleared to also clear the grid selection.
    $scope.$on('clear', function () {
        $scope.gridOptions.selectAll(false);
    });
    
    // Picks us the event broadcasted when the person is deleted from the grid and perform the actual person delete by
    // calling the appropiate rest service.
    $scope.$on('deleteCliente', function (event, id) {
      console.log('Evento eliminar cliente :' + id);
      clienteService.delete({id: id}).$promise.then(
          function () {
              // Broadcast the event to refresh the grid.
              $rootScope.$broadcast('refreshGrid');
              // Broadcast the event to display a delete message.
              $rootScope.$broadcast('clienteDeleted');
              //$scope.clearForm();
          },
          function () {
              // Broadcast the event for a server error.
              $rootScope.$broadcast('error');
          });
    });
});

// Create a controller with name alertMessagesController to bind to the feedback messages section.
app.controller('alertMessagesController', function ($scope) {
    // Picks up the event to display a saved message.
    $scope.$on('clienteSaved', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Record saved successfully!' }
        ];
    });

    // Picks up the event to display a deleted message.
    $scope.$on('clienteDeleted', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Record deleted successfully!' }
        ];
    });

    // Picks up the event to display a server error message.
    $scope.$on('error', function () {
        $scope.alerts = [
            { type: 'danger', msg: 'There was a problem in the server!' }
        ];
    });

    $scope.closeAlert = function (index) {
        $scope.alerts.splice(index, 1);
    };
});
