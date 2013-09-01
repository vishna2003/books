'use strict';

/**
 * Book add controller.
 */
App.controller('BookAdd', function($scope) {
  /**
   * Open barcode scanner with a return URL on this page.
   */
  $scope.openBarcodeScanner = function() {
    document.location.href = 'zxing://scan/?ret=http%3A%2F%2F192.168.1.10%3A9999%2Fbooks-web%2F%23%2Fbook%2Fadd%2F%7BCODE%7D&SCAN_FORMATS=EAN_13';
  }
});