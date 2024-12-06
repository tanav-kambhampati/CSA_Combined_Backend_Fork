package com.nighthawk.spring_portfolio.mvc.bathroom;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TinkleViewController {
    @GetMapping("/admin")
    public String admin() {
        
        // load HTML VIEW (birds.html)
        return "admin";

    }
}
