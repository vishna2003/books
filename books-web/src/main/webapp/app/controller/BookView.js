'use strict';

/**
 * Book view controller.
 */
App.controller('BookView', function($scope, $stateParams, Restangular) {
  Restangular.one('book', $stateParams.id).get().then(function(data) {
    $scope.book = data;
  })
});