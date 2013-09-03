'use strict';

/**
 * Book add controller.
 */
App.controller('BookAdd', function($scope, $http, $stateParams, Restangular) {
  /**
   * Open barcode scanner with a return URL on this page.
   */
  $scope.openBarcodeScanner = function() {
    // TODO Get the current URL from document.location
    var retUrl =  encodeURIComponent(document.location.origin + document.location.pathname + '#/book/add/{CODE}');
    document.location.href = 'zxing://scan/?ret=' + retUrl + '&SCAN_FORMATS=EAN_13';
  };

  /**
   * Add a book manually from ISBN.
   */
  $scope.addBook = function() {
    Restangular.one('book').put({
      isbn: $scope.isbn
    }).then(function() {
          $scope.isbn = '';
          alert('Book added');
        }, function(response) {
          alert(response.data.message);
        });
  };

  /**
   * Import books.
   */
  $scope.importBooks = function () {
    var formData = new FormData();
    formData.append('file', $scope.importFile);

    // Send the file
    $http.put('api/book/import',
        formData, {
          headers: { 'Content-Type': false },
          transformRequest: function(data) { return data; }
        }).then(function() {
          alert('Import in progress, it may take a while.');
        });
  };

  // Add book passed in parameter
  if ($stateParams.isbn) {
    $scope.isbn  = $stateParams.isbn;
    $scope.addBook();
  }
});