package com.kb.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.kb.Entity.Cake;
import com.kb.Entity.User;
import com.kb.repository.UserRepository;
import com.kb.service.CakeService;
import com.kb.service.OrderService;
import com.kb.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CakeService cakeService;

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("cakes", cakeService.getAllCakes());
        model.addAttribute("orders", orderService.getOrdersByTime(""));
        model.addAttribute("buyers", userRepository.findByRole("ROLE_BUYER"));
        return "/admin/dashboard";
    }

    @GetMapping("/cakes")
    public String manageCakes(Model model) {
        model.addAttribute("cakes", cakeService.getAllCakes());
        model.addAttribute("orders", orderService.getOrdersByTime(""));
        model.addAttribute("buyers", userRepository.findByRole("ROLE_BUYER"));
        return "admin/manage_cakes";
    }

    @PostMapping("/cake/add")
    public String addCake(
        @RequestParam("name") String name,
        @RequestParam("price") Double price,
        @RequestParam("isAvailable") Boolean isAvailable,
        @RequestParam("description") String description,
        @RequestParam("photo") MultipartFile photo,
        Model model
    ) throws IOException {
        
        Cake c = new Cake();
        c.setName(name);
        c.setPrice(price);
        c.setIsAvailable(isAvailable);
        c.setDescription(description);
        
        String cakeImage = "default_cake.jpg";
        
        if (!photo.isEmpty()) {
            cakeImage = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
            Path uploadDir = Paths.get("src/main/resources/static/cakes");

            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            Path filePath = uploadDir.resolve(cakeImage);
            Files.copy(photo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        
        c.setPhoto(cakeImage);
        cakeService.addCake(c);
        model.addAttribute("cakes", cakeService.getAllCakes());
        model.addAttribute("orders", orderService.getOrdersByTime(""));
        model.addAttribute("buyers", userRepository.findByRole("ROLE_BUYER"));
        return "redirect:/admin/cakes"; // Redirect after submission
    }

    
    @GetMapping("/cake/add")
    public String showAddCakeForm(Model model) {
        model.addAttribute("cake", new Cake());
        model.addAttribute("cakes", cakeService.getAllCakes());
        model.addAttribute("orders", orderService.getOrdersByTime(""));
        model.addAttribute("buyers", userRepository.findByRole("ROLE_BUYER"));
        return "admin/add_cake";
    }




//    @PostMapping("/cake/add")
//    public String addCake(@ModelAttribute Cake cake) {
//        cakeService.addCake(cake);
//        return "redirect:/admin/cakes";
//    }

    @GetMapping("/cake/edit/{id}")
    public String showEditCakeForm(@PathVariable Long id, Model model) {
        Optional<Cake> cake = cakeService.getCakeById(id);
        cake.ifPresent(value -> model.addAttribute("cake", value));
        return "admin/edit_cake";
    }

    @PostMapping("/cake/edit")
    public String updateCake(
            @RequestParam("id") Long id, // Get the ID of the cake to update
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("isAvailable") Boolean isAvailable,
            @RequestParam("description") String description,
            @RequestParam(value = "photo", required = false) MultipartFile photo, // The photo is optional
            Model model
    ) throws IOException {

        // Retrieve the cake from the database
        Optional<Cake> c = cakeService.getCakeById(id);

        // Update the cake properties
        c.get().setName(name);
        c.get().setPrice(price);
        c.get().setIsAvailable(isAvailable);
        c.get().setDescription(description);

        // Handle photo upload if a new photo is provided
        if (photo != null && !photo.isEmpty()) {
            String cakeImage = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
            Path uploadDir = Paths.get("src/main/resources/static/cakes");

            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            Path filePath = uploadDir.resolve(cakeImage);
            Files.copy(photo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            c.get().setPhoto(cakeImage);
        }
        // Save the updated cake back to the database
        cakeService.updateCake(c.get().getId(), c);

        // Optional: Add additional data for the view (if needed)
        model.addAttribute("cakes", cakeService.getAllCakes());
        model.addAttribute("orders", orderService.getOrdersByTime(""));
        model.addAttribute("buyers", userRepository.findByRole("ROLE_BUYER"));

        return "redirect:/admin/cakes"; // Redirect to the cakes list page after the update
    }


    @GetMapping("/cake/delete/{id}")
    public String deleteCake(@PathVariable Long id) {
        cakeService.deleteCake(id);
        return "redirect:/admin/cakes";
    }
    
    @GetMapping("/allUsers")
    public String getAllUsers(Model model) {
    	model.addAttribute("cakes", cakeService.getAllCakes());
        model.addAttribute("orders", orderService.getOrdersByTime(""));
        model.addAttribute("buyers", userRepository.findByRole("ROLE_BUYER"));
    	return "admin/all-users";
    }
}
