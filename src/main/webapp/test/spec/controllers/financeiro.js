'use strict';

describe('Controller: FinanceiroCtrl', function () {

  // load the controller's module
  beforeEach(module('iguassuApp'));

  var FinanceiroCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    FinanceiroCtrl = $controller('FinanceiroCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
