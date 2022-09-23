package com.bridgelabz.bookstorecartservice.service;

import com.bridgelabz.bookstorecartservice.dto.CartDTO;
import com.bridgelabz.bookstorecartservice.model.CartModel;
import com.bridgelabz.bookstorecartservice.util.Response;

import java.util.List;
import java.util.Optional;

public interface ICartService {
    public CartModel addToCart(CartDTO cartDTO, String token, Long cartId);
    public CartModel updateCart(CartDTO cartDTO, Long cartId, String token);
    public List<CartModel> getAllItemsOfCart(String token);
    public Optional<CartModel> getCartById(Long cartId, String token);
    public Response deleteFromCart(String token, Long cartId);
}
