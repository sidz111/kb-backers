package com.kb.service;

import java.util.List;
import com.kb.Entity.Review;

public interface ReviewService {
    Review addReview(Review review);
    List<Review> getAllReviews();
    void deleteReviewById(Long id);  // Fixed typo in method name
    Review updateReview(Review review);
}