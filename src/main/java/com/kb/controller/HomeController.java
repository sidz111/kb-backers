package com.kb.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kb.Entity.User;
import com.kb.service.CakeService;
import com.kb.service.OrderService;
import com.kb.service.ReviewService;
import com.kb.service.SubscriberService;
import com.kb.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	ReviewService reviewService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private SubscriberService subscriberService;

	@Autowired
	private CakeService cakeService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String homePage(Model model) {
		model.addAttribute("cakes", cakeService.getAvailableCakes());
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("user", userService.getUserByEmail(email));
		model.addAttribute("cakes", cakeService.getAllCakes());
		String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("myOrders", orderService.getOrdersByUser(userService.getUserByEmail(userEmail)));
		model.addAttribute("reviews", reviewService.getAllReviews());
		return "index";
	}

	@GetMapping("/login-page")
	public String getLoginPage() {
		return "login";
	}

	@PostMapping("/add-user")
	public String addUser(@RequestParam("name") String name, 
	                      @RequestParam("email") String email,
	                      @RequestParam("password") String password, 
	                      @RequestParam("address") String address,
	                      @RequestParam("contact") String contact, 
	                      @RequestParam("photo") MultipartFile photo, 
	                      Model model) throws IOException {

		// Check if user already exists
		if (userService.getUserByEmail(email) != null) {
			model.addAttribute("userExist", "User Already Exists");
			return "login";
		}

		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));
		user.setRole("ROLE_BUYER");
		user.setContact(contact);
		user.setAddress(address);

		// Save Image to Static Folder (src/main/resources/static/uploads)
		String imageName = "default.jpg"; // Default image if none is uploaded

		if (!photo.isEmpty()) {
			imageName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
			Path uploadDir = Paths.get("src/main/resources/static/uploads");

			// Create directory if it doesn't exist
			if (!Files.exists(uploadDir)) {
				Files.createDirectories(uploadDir);
			}

			// Save the file
			Path filePath = uploadDir.resolve(imageName);
			Files.copy(photo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		}

		user.setPhoto(imageName);
		userService.createUser(user);

		return "redirect:/login-page";
	}
}
