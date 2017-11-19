/**
 * Module for index.html page
 * @type {angular.Module}
 */

var app = angular.module('smartRestaurantApp', ['base64']).config(function($httpProvider, $base64) {
    var auth = $base64.encode("anonymous:cvut2017");
    $httpProvider.defaults.headers.common.Authorization = 'Basic ' + auth;
});
var apiPrefix = "api/v1";




