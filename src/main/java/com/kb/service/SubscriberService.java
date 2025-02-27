package com.kb.service;

import java.util.List;

import com.kb.Entity.Subscriber;

public interface SubscriberService {

	Subscriber addSubscriber(Subscriber subscriber);
	
	List<Subscriber> getAllSubscribers();
}
