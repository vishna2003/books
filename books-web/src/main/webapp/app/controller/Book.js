'use strict';

/**
 * Book controller.
 */
App.controller('Book', function($scope, $timeout, Restangular) {
  /**
   * Books list sort status.
   */
  $scope.sortColumn = 3;
  $scope.asc = true;
  $scope.offset = 0;
  $scope.limit = 20;
  $scope.search = '';

  // A timeout promise is used to slow down search requests to the server
  // We keep track of it for cancellation purpose
  var timeoutPromise;

  /**
   * Reload books.
   */
  $scope.loadBooks = function() {
    $scope.offset = 0;
    $scope.pageBooks();
  };

  /**
   * Load books.
   */
  $scope.pageBooks = function() {
    Restangular.one('book').getList('list', {
      offset: $scope.offset,
      limit: $scope.limit,
      sort_column: $scope.sortColumn,
      asc: $scope.asc,
      search: $scope.search
    }).then(function(data) {
          $scope.books = data.books;
        });
  };

  /**
   * Watch for search scope change.
   */
  $scope.$watch('search', function(prev, next) {
    if (timeoutPromise) {
      // Cancel previous timeout
      $timeout.cancel(timeoutPromise);
    }

    // Call API later
    timeoutPromise = $timeout(function () {
      $scope.loadBooks();
    }, 200);
  }, true);
});