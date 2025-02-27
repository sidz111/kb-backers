package com.kb.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kb.Entity.Subscriber;
import com.kb.service.SubscriberService;

@Controller
public class HomeController {
	
	@Autowired
	SubscriberService subscriberService;

	@GetMapping("/")
	public String homePage() {
		return "index";
	}
	
	@PostMapping("/subscribe-us")
	public String addSubscribers(@RequestParam String email) {
		Subscriber s = new Subscriber();
		s.setEmail(email);
		s.setTimeDate(new Date().toString());
		subscriberService.addSubscriber(s);
		return "redirect:/";
	}
}
