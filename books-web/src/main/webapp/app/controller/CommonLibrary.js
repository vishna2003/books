'use strict';

App.controller('CommonLibrary', function($scope, $location, BookService) {
    var ctrl = this;
    ctrl.filters = {};
    ctrl.filteredBooks = [];
    ctrl.newBook = {}; // Model for the new book form

    ctrl.applyFilters = function() {
        BookService.getBooks(ctrl.filters).then(function(response) {
            ctrl.filteredBooks = response.data;
        });
    };

    // Initialize with all books
    ctrl.applyFilters();

    // Function to redirect to the add book page
    ctrl.goToAddBookPage = function() {
      $location.path('/add-book');
  };

    // Placeholder function for adding a book
    ctrl.addBook = function() {
        // Implement the logic to add a book using BookService or similar
        console.log('Adding book:', ctrl.newBook);
        // After adding the book, redirect back to the book list or show a success message
    };
});