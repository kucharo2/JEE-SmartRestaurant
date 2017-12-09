/**
 * Angular controller for table selection
 * @type {angular.controller}
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 */
app.controller('TablesController', function TablesController($rootScope, $location, $scope, $cookies, TablesService, ErrorService) {
    var tablesCount = 0;

    /**
     * Gets all tables from backend
     */
    TablesService.getAllTables().then(function(response){
        $scope.tables = response.data;
        tablesCount = $scope.tables.length;
        fillTablesInfo();
    }, ErrorService.serverErrorCallback);

    $scope.confirmTableSelection = function () {
        $cookies.put("table", $scope.selectedTable.id);
        $location.path("/");
    };

    /**
     * Fills table information with data from server
     */
    var fillTablesInfo = function() {
        for(var i=0; i < tablesCount; i++){
            $("#table"+($scope.tables[i].id)).html(
                "<div class='tableInfo'><div>"+$scope.tables[i].name+"<br>Kapacita: "+$scope.tables[i].size+"</div></div>"
            )
        }
    };

    /**
     * Gets table by id
     * @param id
     * @returns {*} table with specified id
     */
    var getTableById = function (id) {
        for(var i=0; i < tablesCount; i++){
           if($scope.tables[i].id === id) {
               return $scope.tables[i];
           }
        }
    };


    /**
     * Selects table with specified id
     * @param id
     */
    $scope.selectTable = function(id){
        for(var i=1; i <= tablesCount; i++){
            if(i === id){
                $('#table'+id).addClass('selected');
                $scope.selectedTable = getTableById(id);
                $('html, body').animate({ scrollTop:$("#tableSelected").offset().top}, 500);
            }else {
                $('#table'+i).removeClass('selected');
            }
        }
    }
});