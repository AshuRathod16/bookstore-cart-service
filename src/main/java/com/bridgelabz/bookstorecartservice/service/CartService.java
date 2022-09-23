package com.bridgelabz.bookstorecartservice.service;

import com.bridgelabz.bookstorecartservice.dto.CartDTO;
import com.bridgelabz.bookstorecartservice.exception.CartException;
import com.bridgelabz.bookstorecartservice.model.CartModel;
import com.bridgelabz.bookstorecartservice.repository.CartRepository;
import com.bridgelabz.bookstorecartservice.util.Response;
import com.bridgelabz.bookstorecartservice.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService{

    @Autowired
    CartRepository cartRepository;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    MailService mailService;

    @Autowired
    RestTemplate restTemplate;

    /**
     * @param token, noteDTO
     * Purpose: Creating method to add items to cart
     * @author Ashwini Rathod
     */

    @Override
    public CartModel addToCart(CartDTO cartDTO, String token, Long bookId) {
        boolean isUserPresent = restTemplate.getForObject("http://BOOKSTORE-USER-SERVICE:8083/user/verifyEmail/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = tokenUtil.decodeToken(token);
            CartModel cartModel = new CartModel(cartDTO);
			cartModel.setUserId(userId);
			cartModel.setBookId(bookId);
			cartRepository.save(cartModel);
            String body = "Added to cart successfully with cartId" + cartModel.getCartId();
            String subject = "Added to cart successfully";
            return cartModel;
        }
        throw new CartException(400, " No item added to cart ");
    }

    /**
     * @param token,noteDTO,noteId
     * Purpose: Creating method to update cart
     * @author Ashwini Rathod
     */

    @Override
    public CartModel updateCart(CartDTO cartDTO, Long cartId, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://BOOKSTORE-USER-SERVICE:8083/user/verifyEmail/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<CartModel> isUserIdPresent = cartRepository.findByUserId(userId);
            if (isUserIdPresent.isPresent()) {
                Optional<CartModel> isCartPresent = cartRepository.findById(cartId);
                if (isCartPresent.isPresent()) {
                    isCartPresent.get().setQuantity(cartDTO.getQuantity());
                    cartRepository.save(isCartPresent.get());
                    String body = "Cart updated successfully with bookId" + isCartPresent.get().getBookId();
                    String subject = "Cart updated successfully";
                    mailService.send(String.valueOf(isCartPresent.get().getBookId()), subject, body);
                    return isCartPresent.get();
                }
            }
            throw new CartException(400, "Cart not found");
        }
        throw new CartException(400, "Token is invalid");

    }

    /**
     * @param token
     * purpose: Creating Method to get all cart items
     * @author Ashwini Rathod
     */

    @Override
    public List<CartModel> getAllItemsOfCart(String token) {
        boolean isUserPresent = restTemplate.getForObject("http://BOOKSTORE-USER-SERVICE:8083/user/verifyEmail/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<CartModel> isUserIdPresent = cartRepository.findByUserId(userId);
            if (isUserIdPresent.isPresent()) {
                List<CartModel> readAllNotes = cartRepository.findAll();
                if (readAllNotes.size() > 0) {
                    return readAllNotes;
                }
            }
            throw new CartException(400, "Cart with this id not found");
        }
        throw new CartException(400, "Token is invalid");
    }

    /**
     * @param token,cartId
     * purpose: Creating method to get cart items by id
     * @author Ashwini Rathod
     */

    @Override
    public Optional<CartModel> getCartById(Long cartId, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://BOOKSTORE-USER-SERVICE:8083/user/verifyEmail/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<CartModel> isUserIdPresent = cartRepository.findByUserId(userId);
            if (isUserIdPresent.isPresent()) {
                Optional<CartModel> isNotePresent = cartRepository.findById(cartId);
                if (isNotePresent.isPresent()) {
                    return isNotePresent;
                }
            }
            throw new CartException(400, "Cart not found");
        }
        throw new CartException(400, "Token is invalid");
    }

    /**
     * @param token,cartId
     * purpose: Creating method to delete cart
     * @author Ashwini Rathod
     */


    @Override
    public Response deleteFromCart(String token, Long cartId) {
        boolean isUserPresent = restTemplate.getForObject("http://BOOKSTORE-USER-SERVICE:8083/user/verifyEmail/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<CartModel> isUserIdPresent = cartRepository.findByUserId(userId);
            if (isUserIdPresent.isPresent()) {
                Optional<CartModel> isIdPresent = cartRepository.findById(cartId);
                if (isIdPresent.isPresent()) {
                    return new Response(200, "Successfully", isIdPresent.get());
                }
            }
            throw new CartException(400, "User not found");
        }
        throw new CartException(400, "Invalid token");
    }
}
