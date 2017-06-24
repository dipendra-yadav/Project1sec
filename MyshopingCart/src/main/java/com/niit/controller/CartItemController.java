package com.niit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.niit.dao.CartDAO;
import com.niit.dao.CartItemDAO;
import com.niit.dao.CustomerDAO;
import com.niit.dao.ProductDAO;
import com.niit.domain.Cart;
import com.niit.domain.CartItem;
import com.niit.domain.Customer;
import com.niit.domain.Product;

@Controller
public class CartItemController {

	@Autowired
	CustomerDAO cdao;

	@Autowired
	ProductDAO pdao;

	@Autowired
	CartItemDAO cartItemdao;
	
	@Autowired
	CartDAO  cartdao;

	@RequestMapping("/cart/add/{productId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void addCartItem(@PathVariable(value = "productId") int productId) {

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();// get
																									// user

		String username = user.getUsername();
		Customer customer = cdao.getCustomerByUsername(username);// get customer

		Cart cart = customer.getCart();// get cart
		List<CartItem> cartItems = cart.getCartItems();// get cartItems

		Product product = pdao.getProductByID(productId);
		for (int i = 0; i < cartItems.size(); i++) {
			CartItem cartItem = cartItems.get(i);
			if (product.getId() == cartItem.getProduct().getId()) {
				cartItem.setQuantity(cartItem.getQuantity() + 1);// increment
																	// the
																	// quantity
				cartItem.setTotalPrice(cartItem.getQuantity() * product.getPrice());// update
																					// the
																					// total
																					// price
				cartItemdao.addCartItem(cartItem);// update the quantity in the
													// cartitem

				return;
			}

		}

	}

	@RequestMapping("/cart/removecartitem/{cartItemId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void removeCartItem(@PathVariable(value = "cartItemId") int cartItemId) {
		CartItem cartItem = cartItemdao.getCartItem(cartItemId);
		cartItemdao.removeCartItem(cartItem);
	}

	@RequestMapping("/cart/removeAllItems/{cartId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void removeAllCartItem(@PathVariable(value = "cartId") int cartId) {
		Cart cart = cartdao.getCartByCartId(cartId);
		cartItemdao.removeAllCartItems(cart);
	}

}