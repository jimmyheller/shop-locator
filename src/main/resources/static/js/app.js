var app = angular.module('store-loc', []);
app.controller('searchByCoordinates', function ($scope, $http) {
    console.log($scope.appMessage);
    $scope.submitByCoordinates = function () {
        debugger;
        if ($scope.latitude > 90 || $scope.latitude < -90) {
            $scope.appMessage = "please enter valid latitude";
            return;
        }
        if ($scope.longitude > 180 || $scope.longitude < -180) {
            $scope.appMessage = "please enter valid longitude";
            return;
        }
        var url = "/api/v1/stores/coordinates?lat=" + $scope.latitude + "&long=" + $scope.longitude;
        $http.get(url)
            .then(function (response) {
                $scope.data = response.data.data;
                $scope.appMessage = '';
            }, function () {
                $scope.appMessage = Response.data.message;
            });
    }
});