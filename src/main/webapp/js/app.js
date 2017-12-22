/**
 * Module for index.html page
 * @type {angular.Module}
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 */
var app = angular.module('smartRestaurantApp', ['ngRoute', 'base64', 'ngCookies', 'ngMaterial', 'ngAria']).config(function($routeProvider, $httpProvider, $base64, $mdToastProvider) {

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
        .when("/cashDesk", {
            templateUrl : "cashDesk.html"
        })
        .when("/register", {
            templateUrl : "register.html"
        })
        .otherwise({
            templateUrl : "notFound.html"
        });

    $mdToastProvider.addPreset('error500', {
        options: function() {
            return {
                template:
                '<md-toast>' +
                '<div class="md-toast-content error">' +
                'Při požadavku na server nastala chyba! Zkuste opakovat akci později.' +
                '</div>' +
                '</md-toast>',
                controllerAs: 'toast',
                bindToController: true,
                position: 'bottom right',
                hideDelay: 3500
            };
        }
    });
});
var apiPrefix = "api/v1";







