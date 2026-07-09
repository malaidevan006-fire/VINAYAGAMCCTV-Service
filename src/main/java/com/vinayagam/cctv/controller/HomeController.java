package com.vinayagam.cctv.controller;

import com.vinayagam.cctv.model.CustomerAccount;
import com.vinayagam.cctv.model.QuoteRequest;
import com.vinayagam.cctv.repository.CustomerAccountRepository;
import com.vinayagam.cctv.repository.QuoteRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
public class HomeController {

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Home Page
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("quoteRequest", new QuoteRequest());
        return "index";
    }

    // Services Page
    @GetMapping("/services")
    public String services(Model model) {
        model.addAttribute("quoteRequest", new QuoteRequest());
        return "services";
    }

    // Projects Page
    @GetMapping("/projects")
    public String projects(Model model) {
        return "projects";
    }

    // About Page
    @GetMapping("/about")
    public String about(Model model) {
        return "about";
    }

    // Contact Page
    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("quoteRequest", new QuoteRequest());
        return "contact";
    }

    // Submit Quote Form
    @PostMapping("/submit-quote")
    public String submitQuote(@Valid @ModelAttribute QuoteRequest quoteRequest,
                               BindingResult result,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("quoteRequest", quoteRequest);
            model.addAttribute("error", "Please fill all required fields correctly.");
            return "index";
        }

        String name = "Guest";
        if (quoteRequest != null) {
            quoteRepository.save(quoteRequest);
            if (quoteRequest.getName() != null && !quoteRequest.getName().trim().isEmpty()) {
                name = quoteRequest.getName();
            }
        }
        redirectAttributes.addFlashAttribute("success",
            "Thank you " + name + "! We will contact you soon.");
        return "redirect:/";
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("customerAccount", new CustomerAccount());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("customerAccount") CustomerAccount customerAccount,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (customerAccountRepository.findByEmail(customerAccount.getEmail()).isPresent()) {
            model.addAttribute("error", "An account with this email already exists.");
            return "signup";
        }

        // Hash the password before saving
        customerAccount.setPassword(passwordEncoder.encode(customerAccount.getPassword()));
        customerAccountRepository.save(customerAccount);

        redirectAttributes.addFlashAttribute("success", "Account created successfully. Please sign in.");
        return "redirect:/signin";
    }

    @GetMapping("/signin")
    public String signinPage(Model model) {
        model.addAttribute("customerAccount", new CustomerAccount());
        return "signin";
    }

    @PostMapping("/signin")
    public String signin(@ModelAttribute("customerAccount") CustomerAccount customerAccount,
                         RedirectAttributes redirectAttributes,
                         Model model,
                         HttpSession session) {
        return customerAccountRepository.findByEmail(customerAccount.getEmail())
                .filter(account -> passwordEncoder.matches(customerAccount.getPassword(), account.getPassword()))
                .map(account -> {
                    session.setAttribute("customerId", account.getId());
                    session.setAttribute("customerName", account.getFullName());
                    session.setAttribute("customerEmail", account.getEmail());
                    redirectAttributes.addFlashAttribute("success", "Welcome back, " + account.getFullName() + "!");
                    return "redirect:/";
                })
                .orElseGet(() -> {
                    model.addAttribute("error", "Invalid email or password.");
                    return "signin";
                });
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Object customerId = session.getAttribute("customerId");
        if (customerId == null) {
            redirectAttributes.addFlashAttribute("error", "Please sign in to view your profile.");
            return "redirect:/signin";
        }

        model.addAttribute("customerName", session.getAttribute("customerName"));
        model.addAttribute("customerEmail", session.getAttribute("customerEmail"));
        return "profile";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("success", "You have been logged out successfully.");
        return "redirect:/";
    }

    // Admin: View all quotes
    @GetMapping("/admin/quotes")
    public String viewQuotes(Model model) {
        model.addAttribute("quotes", quoteRepository.findAll());
        return "admin/quotes";
    }
}
