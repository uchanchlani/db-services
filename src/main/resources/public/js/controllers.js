angular.module('app.controllers', []).controller('CustomerListController', function($scope, $state, popupService, $window, Customer) {
	$scope.customers = Customer.query(); //fetch all customers. Issues a GET to /api/vi/customers

  $scope.deleteCustomer = function(customer) { // Delete a Customer. Issues a DELETE to /api/v1/customers/:id
    if (popupService.showPopup('Really delete this?')) {
      customer.$delete(function() {
        $scope.customers = Customer.query(); 
        $state.go('customers');
      });
    }
  };
}).controller('CustomerViewController', function($scope, $stateParams, Customer) {
  $scope.customer = Customer.get({ id: $stateParams.id }); //Get a single customer.Issues a GET to /api/v1/customers/:id
}).controller('CustomerCreateController', function($scope, $state, $stateParams, Customer) {
  $scope.customer = new Customer();  //create new customer instance. Properties will be set via ng-model on UI

  $scope.addCustomer = function() { //create a new customer. Issues a POST to /api/v1/customers
    $scope.customer.$save(function() {
      $state.go('customers'); // on success go back to the list i.e. customers state.
    });
  };
}).controller('CustomerEditController', function($scope, $state, $stateParams, Customer) {
  $scope.updateCustomer = function() { //Update the edited customer. Issues a PUT to /api/v1/customers/:id
    $scope.customer.$update(function() {
      $state.go('customers'); // on success go back to the list i.e. customers state.
    });
  };

  $scope.loadCustomer = function() { //Issues a GET request to /api/v1/customers/:id to get a customer to update
    $scope.customer = Customer.get({ id: $stateParams.id });
  };

  $scope.loadCustomer(); // Load a customer which can be edited on UI
});
