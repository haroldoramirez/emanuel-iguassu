'use strict';

/**
 * @ngdoc service
 * @name iguassuApp.createAddress
 * @description
 * # createAddress
 * Factory in the iguassuApp.
 */
angular.module('iguassuApp')
  .service('createAddress', function () {
    return {
      formateEndereco: function (endereco) {    
        var enderecoResult = {
          bairro : {
            id : null,
            cidade : {
              id : null,
              estado : {
                id : null,
                pais : {
                  id : null,
                }
              }
            }
          }
        };
          if (endereco) {
           if (endereco.rua) enderecoResult.rua = endereco.rua;
           if (endereco.complemento) enderecoResult.complemento = endereco.complemento;
           if (endereco.numero) enderecoResult.numero = endereco.numero;
           if (endereco.cep) enderecoResult.cep = endereco.cep;
           if (endereco.bairro) enderecoResult.bairro = endereco.bairro;
           if (endereco.cidade) enderecoResult.bairro.cidade = endereco.cidade;
           if (endereco.estado) enderecoResult.bairro.cidade.estado = endereco.estado;
           if (endereco.pais) enderecoResult.bairro.cidade.estado.pais = endereco.pais; 
          };
          return enderecoResult;
      },
      desformateEndereco: function (endereco) {
        var enderecoResult = {
                        bairro : null,
                        cidade : null,
                        estado : null,
                        pais : null
                      };
        if (endereco) {
          if (endereco.rua) enderecoResult.rua = endereco.rua;
          if (endereco.complemento) enderecoResult.complemento = endereco.complemento;
          if (endereco.numero) enderecoResult.numero = endereco.numero;
          if (endereco.cep) enderecoResult.cep = endereco.cep;
          if (endereco.bairro) {
            enderecoResult.bairro = endereco.bairro;
            if (endereco.bairro.cidade){
              enderecoResult.cidade = endereco.bairro.cidade;
            }
            if (endereco.bairro.cidade.estado){
              enderecoResult.estado = endereco.bairro.cidade.estado;
            } 
            if (endereco.bairro.cidade.estado.pais){
              enderecoResult.pais = endereco.bairro.cidade.estado.pais;
            }  
          }
        };
        return enderecoResult;
      },
      formateSaveEndereco: function (endereco) {
        if (endereco.bairro) {
          if (!endereco.bairro.id) {
            endereco.bairro = null;
          } else {
            endereco = this.formateEndereco(endereco);
          };  
        };
        return endereco;
      }
    };
  });

