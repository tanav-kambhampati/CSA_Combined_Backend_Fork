package com.nighthawk.spring_portfolio.mvc.assignments;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mvc/assignments")
public class AssignmentsViewController {
    @GetMapping("/assignments")
    public String assignments() {
        return "assignments";
    }
}