/**
 * Angular controller for table selection
 */
app.controller('TablesController', function TablesController($rootScope, $location, $scope, $cookies, TablesService) {
    var tablesCount = 0;

    TablesService.getAllTables().then(function(response){
        $scope.tables = response.data;
        tablesCount = $scope.tables.length;
        fillTablesInfo();
    });

    $scope.confirmTableSelection = function () {
        $cookies.put("table", $scope.selectedTable.id);
        $location.path("/");
    };

    var fillTablesInfo = function() {
        for(var i=0; i < tablesCount; i++){
            $("#table"+($scope.tables[i].id)).html(
                "<div class='tableInfo'><div>"+$scope.tables[i].name+"<br>Kapacita: "+$scope.tables[i].size+"</div></div>"
            )
        }
    };

    var getTableById = function (id) {
        for(var i=0; i < tablesCount; i++){
           if($scope.tables[i].id === id) {
               return $scope.tables[i];
           }
        }
    };


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