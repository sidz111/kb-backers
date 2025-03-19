package com.kb.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kb.Entity.Cake;
import com.kb.Entity.Order;
import com.kb.Entity.User;
import com.kb.service.CakeService;
import com.kb.service.OrderService;
import com.kb.service.UserService;

@Controller
@RequestMapping("/buyer")
public class BuyerController {

    @Autowired
    private CakeService cakeService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String buyerDashboard(Model model) {
        model.addAttribute("cakes", cakeService.getAvailableCakes());
        return "buyer/dashboard";
    }

//    @GetMapping("/order/{cakeId}")
//    public String placeOrder(@PathVariable Long cakeId, Model model) {
//        model.addAttribute("cakeId", cakeId);
//        return "buyer/order_form";
//    }

    @GetMapping("/order/{cakeId}")
    public String confirmOrder(@PathVariable("cakeId") Long cakeId, @RequestParam("mode") String mode) {
        Cake cake = cakeService.getCakeById(cakeId).orElse(null);
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserById(userService.getUserByEmail(userEmail).getId()).orElse(null);

        if (cake != null && user != null) {
            Order order = new Order();
            order.setUser(user);
            order.setCake(cake);
            order.setTime(new Date().toString());
            order.setIsConfirm(false);
            order.setMode(mode);
            orderService.placeOrder(order);
        }

        return "redirect:/";
    }

    @GetMapping("/orders")
    public String viewOrders(@RequestParam Long userId, Model model) {
        User user = userService.getUserById(userId).orElse(null);
        if (user != null) {
            model.addAttribute("orders", orderService.getOrdersByUser(user));
        }
        return "buyer/orders";
    }
}
