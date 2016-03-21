'use strict';

/**
 * @ngdoc service
 * @name iguassuApp.Encaminhamento
 * @description
 * # Encaminhamento
 * Service in the iguassuApp.
 */
angular.module('iguassuApp')
  .service('Encaminhamento', ['$resource', 'BaseUrl',
    function($resource, BaseUrl){
     return $resource(BaseUrl + '/encaminhamentos/:id', {}, {
     	query: {method: 'POST', url: BaseUrl + '/encaminhamentos/:pagina', isArray: true},
     	getContrato: {method: 'GET', url: BaseUrl + '/encaminhamentos/:id/contrato', isArray:true},
      getAll: {method: 'GET', url: BaseUrl + '/encaminhamentos/', isArray: true},
      getReportNaoPagos: {method: 'GET', url: BaseUrl + '/encaminhamentos/naopagos', isArray: false},
      getReportEmAndamento: {method: 'GET', url: BaseUrl + '/encaminhamentos/emandamento', isArray: false}
    });
  }]);