package com.bridgelabz.bookstorecartservice.controller;

import com.bridgelabz.bookstorecartservice.dto.CartDTO;
import com.bridgelabz.bookstorecartservice.model.CartModel;
import com.bridgelabz.bookstorecartservice.service.ICartService;
import com.bridgelabz.bookstorecartservice.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @author : Ashwini Rathod
 * @version: 1.0
 * @since : 21-09-2022
 * Purpose : controller for the Cart Service
 */

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    ICartService cartService;


    @GetMapping("/welcome")
    public String welcomeMessage() {
        return "Welcome to bookstore project";
    }

    @PostMapping("/addToCart")
    public ResponseEntity<Response> createNote(@Valid @RequestBody CartDTO cartDTO, @RequestHeader String token, @PathVariable Long cartId ) {
        CartModel cartModel = cartService.addToCart(cartDTO, token, cartId);
        Response response = new Response(200, "Added to cart successfully", token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("updateCart/{id}")
    public ResponseEntity<Response> updateCart(@Valid @RequestBody CartDTO cartDTO, @PathVariable Long cartId, @RequestHeader String token) {
        CartModel cartModel = cartService.updateCart(cartDTO, cartId, token);
        Response response = new Response(200, "Cart updated successfully", cartModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getCartItems/{id}")
    public ResponseEntity<Response> geCartItemById(@PathVariable Long cartId, @RequestHeader String token) {
        Optional<CartModel> cartModel = cartService.getCartById(cartId, token);
        Response response = new Response(200, "Cart items by id fetch successfully", cartModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAllCartItems")
    public ResponseEntity<Response> getAllCartItems(@RequestHeader String token) {
        List<CartModel> cartModel = cartService.getAllItemsOfCart(token);
        Response response = new Response(200, "Get all cart items successfully", cartModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/deleteCart/{id}")
    public ResponseEntity<Response> deleteFromCart(@RequestHeader String token, @PathVariable Long cartId) {
        Response cartModel = cartService.deleteFromCart(token, cartId);
        Response response = new Response(200, "delete notes successfully", cartModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
