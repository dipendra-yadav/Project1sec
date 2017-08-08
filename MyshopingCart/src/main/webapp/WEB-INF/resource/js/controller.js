var myapp = angular.module("myapp", []).controller("productController",function($scope, $http) {
			
	
	        $scope.cart
			// To get all products
			$scope.getAllProducts = function() {
				alert('entering the getAllProducts ***');
				$http.get('http://localhost:1010/MyshopingCart/getProductsList').success(function(data) {
					$scope.products = data;
				})
			}

			// to add a Product to the cart
			$scope.addToCart = function(productId) {
				alert('entering the addToCart **');
				$http.put('http://localhost:1010/MyshopingCart/cart/add/'+ productId).success(function() {
							alert('Product Added Successfully');
						})
			}

			// to refresh the cart
			$scope.refreshCart = function(cartId) {
				alert("entering the refresh cart");
				$http.get('http://localhost:1010/MyshopingCart/cart/getCart/'+ cartId).success(function(data) {
					$scope.cart = data;
				})
			}
			
			

			// to get the cart
			$scope.getCart = function(cartId) {
				alert("entering the getCart");
				$scope.cartId = cartId;
				$scope.refreshCart(cartId);
			}

			// to remove a product o from the cart
			$scope.removeFromCart = function(cartItemId) {
				alert("entering the removeFromCart");
				
				$http.put('http://localhost:1010/MyshopingCart/cart/removecartitem/'+ cartItemId).success(function() {
								$scope.refreshCart($scope.cartId);
				});
			}

			// to clear the cart
			$scope.clearCart = function(cartId) {	
				alert("entering the clearCart***");
			    $http.put('http://localhost:1010/MyshopingCart/cart/removeAllItems/'+ cartId).success(function() {
					$scope.refreshCart($scope.cartId);
				});
			}

			// to calculate total price
			$scope.calculateGrandTotal = function() {
				alert("calculateGrandTotal**");
				var grandTotal = 0.0
				for (var i = 0; i < $scope.cart.cartItems.length; i++) {
					grandTotal = grandTotal + $scope.cart.cartItems[i].totalPrice;
				}
                return grandTotal;
			}

		});
