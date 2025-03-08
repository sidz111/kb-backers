package com.kb.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kb.Entity.Subscriber;
import com.kb.service.CakeService;
import com.kb.service.OrderService;
import com.kb.service.SubscriberService;
import com.kb.service.UserService;

@Controller
public class HomeController {

	@Autowired
	SubscriberService subscriberService;

	@Autowired
	private CakeService cakeService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String homePage(Model model) {
		model.addAttribute("cakes", cakeService.getAvailableCakes());
		return "index";
	}
	
	@GetMapping("/login-page")
	public String geLogInPage() {
		return "login";
	}

	@PostMapping("/subscribe-us")
	public String addSubscribers(@RequestParam String email) {
		Subscriber s = new Subscriber();
		s.setEmail(email);
		s.setTimeDate(new Date().toString());
		subscriberService.addSubscriber(s);
		return "index";
	}
}
