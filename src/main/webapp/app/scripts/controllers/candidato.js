'use strict';

angular.module('iguassuApp')
.controller('CandidatoCtrl', function ($rootScope, $timeout, $log, $http, $upload, $modal, $scope, $routeParams, $document, $location, Candidato, Pais, toastr, createAddress) {

  $scope.endereco = {};

  $scope.pagina = 0;

  $scope.today = new Date();

  $rootScope.getCursos();

  $rootScope.getCargos();
  
  $scope.onFileSelect = function($files) {
    for (var i = 0; i < $files.length; i++) {
      var file = $files[i];
      $scope.upload = $upload.upload({
        url: 'candidatos/'+$routeParams.id+'/foto',
        file: file,
      }).progress(function(evt) {
        console.log('percent: ' + parseInt(100.0 * evt.loaded / evt.total));
      }).success(function(data, status, headers, config) {
        toastr.success('Atualize a página para conferir a nova foto','Foto atualizada com sucesso');
        $scope.candidato.pathFoto = data.pathFoto;
        $scope.url = 'Iguassu/app' + $scope.candidato.pathFoto;
      }).error(function() {
        toastr.error('Verifique se o arquivo é muito grande','Falha ao atualizar foto');
      });
    }
  };

  $scope.init = function(){
    if ($routeParams.id) {
      Candidato.get({id: $routeParams.id}, function(data){
        $scope.candidato = data;
        $scope.url = 'Iguassu/app' + $scope.candidato.pathFoto;
        Candidato.getContrato({id: $routeParams.id}, function(data){
          if (data[0]) {
            $scope.contrato = '/Iguassu' + data[0];
          };
        });
        Candidato.getLancamentos({id: $routeParams.id}, function(data){
          $scope.lancamentosDoCandidato = data[0];
        });
        $scope.endereco = createAddress.desformateEndereco(data.endereco);
      });
      $scope.cursosDoCandidato = Candidato.getCursos({id: $routeParams.id});
      $scope.experienciasDoCandidato = Candidato.getExperiencias({id: $routeParams.id});
      $rootScope.openAll();
    }else{
      $scope.clear();
    };
    $scope.getCandidatos();
  };

  $scope.save = function(){
    $scope.candidato.endereco = $scope.endereco;
    var msg = 'cadastrado com sucesso';
    if($scope.candidato.id){
      msg = 'atualizado com sucesso';
    };
    if ($scope.candidato.cpf == '') {
      $scope.candidato.cpf = null;
    };
    $scope.candidato.endereco = createAddress.formateSaveEndereco($scope.endereco);
    Candidato.save($scope.candidato, function(data){
      $scope.candidato = data;
      $location.path('/candidatos/'+$scope.candidato.id);
      toastr.success(msg,$scope.candidato.nome);
      $document.scrollTopAnimated(0, 700);
      $scope.init();
    }, function(){
      toastr.error('Verifique se há dados inconsistentes ou se o CPF já esta cadastrado','Não foi possível salvar essas informações');
    });
  };

  $scope.search = function(){
    $scope.candidato.endereco = createAddress.formateEndereco($scope.endereco);
    console.log($scope.candidato);
    Candidato.query({pagina: $scope.pagina}, $scope.candidato, function(data){
      $scope.candidatos = data;
    });
  };

  $scope.next = function(){
    $scope.pagina = $scope.pagina + 1;
    Candidato.query({pagina: $scope.pagina}, $scope.candidato, function(data){
      if (data.length===0) {
        $scope.pagina = $scope.pagina - 1;
      }else{
        $scope.candidatos = data;
      };
    });
  }

  $scope.older = function(){
    $scope.pagina = $scope.pagina - 1;
    Candidato.query({pagina: $scope.pagina}, $scope.candidato, function(data){
      $scope.candidatos = data;
    });
  }

  $scope.renovarContrato = function(){
    Candidato.renovarContrato({id: $routeParams.id}, function(data){
      $scope.candidato = data;
      $location.path('/candidatos/'+$scope.candidato.id);
      toastr.success('Contrato renovado pelo usuário ' + $scope.usuario.nome,'Contrato renovado com sucesso');
      $document.scrollTopAnimated(0, 700);
      $scope.init();
    }, function(data){
      console.log(data);
      toastr.error('Não foi possível renovar o contrato do candidato, verifique se o mesmo tem alguma conta a pagar com a empresa');
    });
  }

  $scope.edit = function(candidato){
    $document.scrollTopAnimated(0, 700);
    $location.path('/candidatos/'+candidato.id);
  }

  $scope.clear = function(){
    $scope.endereco = null;
    $scope.endereco = createAddress.desformateEndereco($scope.endereco);
    $scope.endereco = {};
    $document.scrollTopAnimated(0, 700);
    $scope.candidato = {};
    $scope.cursosDoCandidato = {};
    $scope.experienciasDoCandidato = {};
    if ($routeParams.id) {
      $location.path('/candidatos');
    }
  }

  $scope.openCurso = function(candidatoCurso) {
    $scope.getEmpresas(); 
    $scope.getCursos();
    $modal.open({
      templateUrl : 'cursosDoCandidato.html',
      controller : 'CandidatoCursoCtrl',
      size : 'md',
      resolve : {
       bundle : function() {
          return {
              candidatoCurso : candidatoCurso,
              candidato : $scope.candidato
          }
        }
      }
    }).result.then(function() {
        $scope.cursosDoCandidato = Candidato.getCursos({id: $routeParams.id});
      }, function(){
        $scope.cursosDoCandidato = Candidato.getCursos({id: $routeParams.id});
    });
  };

  $scope.openExperiencia = function(experiencia) {
    $scope.getEmpresas();
    $scope.getCargos();
    $modal.open({
        templateUrl : 'experienciasDoCandidato.html',
        controller : 'CandidatoExperienciaCtrl',
        size : 'md',
        resolve : {
         bundle : function() {
            return {
                experiencia : experiencia,
                candidato : $scope.candidato
            }
          }
        }
      }).result.then(function() {
        $scope.experienciasDoCandidato = Candidato.getExperiencias({id: $routeParams.id});
      }, function(){
        $scope.experienciasDoCandidato = Candidato.getExperiencias({id: $routeParams.id});
    });
  };

  $scope.openDatePicker = function($event) { 
    $event.preventDefault();
    $event.stopPropagation();   
    $scope.opened = !$scope.opened;
  };

  $scope.openDateContratoPicker = function($event) {
    $event.preventDefault();
    $event.stopPropagation();   
    $scope.openedDateContrato = !$scope.openedDateContrato;
  };

}).controller('CandidatoExperienciaCtrl', function ($scope, $modalInstance,  $modal, Candidato, toastr, bundle) {

  $scope.experiencia = bundle.experiencia;
  $scope.candidato = bundle.candidato;

  $scope.today = new Date();

  $scope.save = function(){
    var msg = 'Expereiência adicionada com sucesso';
    if($scope.experiencia.id) {msg = 'Experiência atualizada com sucesso'; var b = true;}

    $scope.experiencia.candidato = $scope.candidato;

    Candidato.saveExperiencia($scope.experiencia, function(data){
      toastr.success(msg);
      $scope.close();
    }, function(error){
      toastr.error('Erro ao salvar experiência');
    });
  };

  $scope.delete = function(){
    Candidato.deleteExperiencia({id:$scope.experiencia.id}, function(data){
     toastr.success('Experiência removida com sucesso');
     $scope.close();
    }, function(error){
     toastr.error('Erro ao remover experiência');
    });
  };

  $scope.clear = function(){
    $scope.experiencia = {};
  };

  $scope.close = function() {
    $modalInstance.close();
  };

  $scope.openDatePickerDataInicio = function($event) {
    $event.preventDefault();
    $event.stopPropagation();
    $scope.openedDataInicio = !$scope.openedDataInicio;
  };

  $scope.openDatePickerDataTermino = function($event) {
    $event.preventDefault();
    $event.stopPropagation();
    $scope.openedDataTermino = !$scope.openedDataTermino;
  };

}).controller('CandidatoCursoCtrl', function ($scope, $modalInstance,  $modal, Candidato, toastr, bundle) {

  $scope.candidatoCurso = bundle.candidatoCurso;
  $scope.candidato = bundle.candidato;

  $scope.save = function(){
    var msg = 'Expereiência adicionada com sucesso';
    if($scope.candidatoCurso.id) {msg = 'Experiência atualizada com sucesso';}

    $scope.candidatoCurso.candidato = $scope.candidato;
    
    Candidato.saveCurso($scope.candidatoCurso, function(data){
      toastr.success(msg);
      $scope.close();
    }, function(error){
      toastr.error('Erro ao salvar curso do candidato');
    });
  };

  $scope.delete = function(){
    Candidato.deleteCurso({id:$scope.candidatoCurso.id}, function(data){
     toastr.success('Curso do candidato removido com sucesso');
     $scope.close();
    }, function(error){
      console.log(error);
      toastr.error(error, 'Erro ao remover curso do candidato');
    });
  };

  $scope.handlerPeriodos = function(){
    $scope.candidatoCurso.periodosConcluidos = $scope.candidatoCurso.quantidadeDePeriodos;
    if ($scope.candidatoCurso.situacaoDoCurso=='concluido') {
      $scope.candidatoCurso.periodosConcluidos = $scope.candidatoCurso.quantidadeDePeriodos;
    }else{
      $scope.candidatoCurso.periodosConcluidos = null;
    };
    console.log($scope.candidatoCurso.periodosConcluidos);
  };

  $scope.clear = function(){
    $scope.candidatoCurso = {};
  };

  $scope.close = function() {
    $modalInstance.close();
  };

});

