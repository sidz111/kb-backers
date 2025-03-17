package com.kb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kb.Entity.Cake;
import com.kb.Entity.Review;
import com.kb.Entity.User;
import com.kb.repository.CakeRepository;
import com.kb.repository.UserRepository;
import com.kb.service.OrderService;
import com.kb.service.ReviewService;

@Controller
@RequestMapping("/buyer/reviews")
public class BuyerReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CakeRepository cakeRepository;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/add/{cakeId}")
    public String showAddReviewForm(@PathVariable Long cakeId, 
                                  Model model) {
        Cake cake = cakeRepository.findById(cakeId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid cake Id:" + cakeId));
        
        Review review = new Review();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        
        review.setUser(userRepository.findByEmail(email));
        review.setCake(cake);
        
        model.addAttribute("review", review);
        model.addAttribute("cake", cake);
        return "buyer/add-review";
    }

    // Process review submission
    @PostMapping("/add")
    public String addReview(Review review) {
    	String email = SecurityContextHolder.getContext().getAuthentication().getName();
        
        review.setUser(userRepository.findByEmail(email));
        reviewService.addReview(review);
        return "redirect:/buyer/reviews/success";
    }

    // Success page after adding review
    @GetMapping("/success")
    public String reviewSuccess() {
        return "buyer/review-success";
    }
    
    @GetMapping("/cancel-order/{orderId}")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
    	orderService.deleteOrderById(orderId);
    	return "redirect:/";
    }
}