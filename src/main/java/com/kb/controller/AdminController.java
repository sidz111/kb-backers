package com.kb.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kb.Entity.Cake;
import com.kb.service.CakeService;
import com.kb.service.OrderService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CakeService cakeService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("cakes", cakeService.getAllCakes());
        model.addAttribute("orders", orderService.getOrdersByTime(""));
        return "/admin/dashboard";
    }

    @GetMapping("/cakes")
    public String manageCakes(Model model) {
        model.addAttribute("cakes", cakeService.getAllCakes());
        return "admin/manage_cakes";
    }

    @GetMapping("/cake/add")
    public String showAddCakeForm(Model model) {
        model.addAttribute("cake", new Cake());
        return "admin/add_cake";
    }

    @PostMapping("/cake/add")
    public String addCake(@ModelAttribute Cake cake) {
        cakeService.addCake(cake);
        return "redirect:/admin/cakes";
    }

    @GetMapping("/cake/edit/{id}")
    public String showEditCakeForm(@PathVariable Long id, Model model) {
        Optional<Cake> cake = cakeService.getCakeById(id);
        cake.ifPresent(value -> model.addAttribute("cake", value));
        return "admin/edit_cake";
    }

    @PostMapping("/cake/edit")
    public String updateCake(@ModelAttribute Cake cake) {
        cakeService.updateCake(cake.getId(), cake);
        return "redirect:/admin/cakes";
    }

    @GetMapping("/cake/delete/{id}")
    public String deleteCake(@PathVariable Long id) {
        cakeService.deleteCake(id);
        return "redirect:/admin/cakes";
    }

    @GetMapping("/orders")
    public String viewOrders(Model model) {
        model.addAttribute("orders", orderService.getOrdersByTime(""));
        return "admin/orders";
    }
}
