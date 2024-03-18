angular.module('booksApp').controller('CommonLibrary', function($scope, BookService) {
    var ctrl = this;
    ctrl.filters = {};
    ctrl.filteredBooks = [];
  
    ctrl.applyFilters = function() {
      BookService.getBooks(ctrl.filters).then(function(response) {
        ctrl.filteredBooks = response.data;
      });
    };
  
    // Initialize with all books
    ctrl.applyFilters();
  });