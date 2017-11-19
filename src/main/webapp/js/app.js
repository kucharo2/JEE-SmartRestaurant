/**
 * Module for index.html page
 * @type {angular.Module}
 */

var app = angular.module('smartRestaurantApp', ['ngRoute', 'base64', 'ngMaterial', 'ngAria']).config(function($routeProvider, $httpProvider, $base64) {
    var auth = $base64.encode("anonymous:cvut2017");
    $httpProvider.defaults.headers.common.Authorization = 'Basic ' + auth;
    $routeProvider
        .when("/", {
            templateUrl : "offer.html"
        })
        .when("/login", {
            templateUrl : "login.html"
        })
        .when("/tables", {
            templateUrl : "tables.html"
        })
        .when("/register", {
            templateUrl : "register.html"
        })
        .otherwise({
            templateUrl : "notFound.html"
        });
});
var apiPrefix = "api/v1";




