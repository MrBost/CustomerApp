package com.fbn.case_study.controller;

import com.fbn.case_study.dto.CustomerDto;
import com.fbn.case_study.entity.Customer;
import com.fbn.case_study.mapper.CustomerMapper;
import com.fbn.case_study.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/api/v1/rm/customer")
@RequiredArgsConstructor
public class CustomerRMApiController {
    private final CustomerService customerService;

    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model) {
        model.addAttribute("customers", customerService.list(page, size));
        return "customers/list";
    }

    @GetMapping("/new")
    public String showCreate(Model model) {
        model.addAttribute("form", new CustomerDto.Request());
        model.addAttribute("types", Customer.CustomerType.values());
        model.addAttribute("classes", Customer.Classification.values());
        return "customers/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("form") CustomerDto.Request form, BindingResult errors, RedirectAttributes ra) {
        if (errors.hasErrors()) return "customers/form";
        customerService.create(form);
        ra.addFlashAttribute("msg", "Customer created");
        return "redirect:/api/v1/rm/customer";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable String id, Model model) {
        model.addAttribute("customer", customerService.get(id));
        return "customers/view";
    }

}
