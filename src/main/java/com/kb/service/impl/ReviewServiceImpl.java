package com.kb.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kb.Entity.Review;
import com.kb.repository.ReviewRepository;
import com.kb.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {
    
    @Autowired
    ReviewRepository reviewRepository;

    @Override
    public Review addReview(Review review) {
        return this.reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReviews() {
        return this.reviewRepository.findAll();
    }

    @Override
    public void deleteReviewById(Long id) {  // Fixed typo from deleteReviceById
        this.reviewRepository.deleteById(id);
    }

    @Override
    public Review updateReview(Review review) {
        Optional<Review> existingReview = reviewRepository.findById(review.getId());
        if (existingReview.isEmpty()) {
            return null;
        }
        // Update fields
        Review r = existingReview.get();
        r.setRating(review.getRating());
        r.setComment(review.getComment());
        return reviewRepository.save(r);
    }
}