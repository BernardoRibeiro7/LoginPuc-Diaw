package br.com.puc.loginpuc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.puc.loginpuc.dto.RegisterForm;
import br.com.puc.loginpuc.service.UserService;
import jakarta.validation.Valid;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("registerForm") RegisterForm form,
            BindingResult result) {

        if (!form.getPassword().equals(form.getConfirmPassword())) {
            result.rejectValue(
                "confirmPassword",
                "password.mismatch",
                "As senhas não coincidem."
            );
        }

        if (userService.usernameExists(form.getUsername())) {
            result.rejectValue(
                "username",
                "username.exists",
                "Este usuário já está cadastrado."
            );
        }

        if (userService.emailExists(form.getEmail())) {
            result.rejectValue(
                "email",
                "email.exists",
                "Este email já está cadastrado."
            );
        }

        if (result.hasErrors()) {
            return "register";
        }

        userService.register(form);

        return "redirect:/login?registered";
    }

    @GetMapping("/recoverpassword")
    public String recoverPassword() {
        return "recoverpassword";
    }
}