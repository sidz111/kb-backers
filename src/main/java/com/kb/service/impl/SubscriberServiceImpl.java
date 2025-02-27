package com.kb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kb.Entity.Subscriber;
import com.kb.repository.SubscriberRepository;
import com.kb.service.SubscriberService;

@Service
public class SubscriberServiceImpl implements SubscriberService{
	
	@Autowired
	SubscriberRepository subscriberRepository;

	@Override
	public Subscriber addSubscriber(Subscriber subscriber) {
		return this.subscriberRepository.save(subscriber);
	}

	@Override
	public List<Subscriber> getAllSubscribers() {
		return this.subscriberRepository.findAll();
	}

}
