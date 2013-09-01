'use strict';

/**
 * Login controller.
 */
App.controller('Login', function($scope, $rootScope, $state, User) {
  $scope.login = function() {
    User.login($scope.user).then(function() {
      $rootScope.userInfo = User.userInfo(true);
      $state.transitionTo('book.default');
    }, function() {
      alert('Username or password invalid');
    });
  };
});