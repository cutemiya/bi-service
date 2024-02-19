package com.example.newspot.controller;

import com.example.newspot.models.Dto.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import java.util.Locale;

@RestController
@EnableAutoConfiguration
public class BaseController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/greeting123")
    public User sampleEndpoint(HttpServletRequest request) {
        var ctx = request.getServletContext();

        var response = new User();
        response.Id = ctx.getAttribute("accountId").toString();
        response.Email = ctx.getAttribute("email").toString();
        response.Username = ctx.getAttribute("username").toString();
        response.Role = ctx.getAttribute("role").toString();

        return response;
    }

}