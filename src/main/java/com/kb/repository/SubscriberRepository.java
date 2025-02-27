package com.kb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kb.Entity.Subscriber;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long>{
	
	List<Subscriber> findByTimeDate(String timeDate);
	
}
