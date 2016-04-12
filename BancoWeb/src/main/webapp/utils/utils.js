/* 
 * Codigo de referencia tomado del siguiente Blog
 * http://weblogs.asp.net/dwahlin/building-an-angularjs-modal-service
 * 
 */
var utils = angular.module('app.utils', []);
utils.service('modalService', ['$modal', function ($modal) {
  var modalDefaults = {
    backdrop: true,
    keyboard: true,
    modalFade: true,
    templateUrl: 'utils/modalConfirm.html'
  };

  var modalOptions = {
    closeButtonText: 'Cancelar',
    actionButtonText: 'OK',
    headerText: '¿Confirmar?...',
    bodyText: '¿Realizar esta accion?'
  };

  this.showModal = function (customCfgDefault, customModalOptions) {
    if (!customCfgDefault)
      customCfgDefault = {};
    customCfgDefault.backdrop = 'static';
    return this.show(customCfgDefault, customModalOptions);
  };

  this.show = function (customModalDefaults, customModalOptions) {
    //Create temp objects to work with since we're in a singleton service
    var tempModalDefaults = {};
    var tempModalOptions = {};

    //Map angular-ui modal custom defaults to modal defaults defined in service
    angular.extend(tempModalDefaults, modalDefaults, customModalDefaults);

    //Map modal.html $scope custom properties to defaults defined in service
    angular.extend(tempModalOptions, modalOptions, customModalOptions);

    if (!tempModalDefaults.controller) {
      tempModalDefaults.controller = function ($scope, $modalInstance) {
        $scope.modalOptions = tempModalOptions;
        $scope.modalOptions.ok = function (result) {
          $modalInstance.close(result);
        };
        $scope.modalOptions.close = function (result) {
          $modalInstance.dismiss('cancel');
        };
      };
    }

    return $modal.open(tempModalDefaults).result;
  };
 }]);



