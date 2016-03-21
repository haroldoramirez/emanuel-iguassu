'use strict';

/**
 * @ngdoc service
 * @name iguassuApp.Vaga
 * @description
 * # Vaga
 * Service in the iguassuApp.
 */
angular.module('iguassuApp')
  .service('Vaga', ['$resource', 'BaseUrl',
  function($resource, BaseUrl){
   return $resource(BaseUrl + '/vagas/:id', {}, {
   	 	query: {method: 'POST', url: BaseUrl + '/vagas/:pagina', isArray: true},
     	getAll: {method: 'GET', url: BaseUrl + '/vagas/', isArray: true},
     	getReportDisponiveis: {method: 'GET', url: BaseUrl + '/vagas/disponiveis', isArray: false},
    	getReportOcupadas: {method: 'GET', url: BaseUrl + '/vagas/ocupadas', isArray: false}
    });
  }]);
