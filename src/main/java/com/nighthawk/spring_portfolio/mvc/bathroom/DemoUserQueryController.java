package com.nighthawk.spring_portfolio.mvc.bathroom;

import com.nighthawk.spring_portfolio.mvc.bathroom.DemoUser;
import com.nighthawk.spring_portfolio.mvc.bathroom.DemoUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// import java.time.LocalDate;
// import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/users")
public class DemoUserQueryController {

    @Autowired
    private DemoUserRepository demoUserRepository;

    // Method to query users and display on HTML page
    @GetMapping
    public String getUsers(@RequestParam(required = false) String userName,
                           @RequestParam(required = false) Long userId,
                           Model model) {
        List<DemoUser> users;
        if (userName != null) {
            users = demoUserRepository.findByUserName(userName);
        } else if (userId != null) {
            users = demoUserRepository.findByUserId(userId);
        } else {
            // users = userRepository.findAll();
            users = null;
        }
        model.addAttribute("users", users);
        return "demo_userQuery";
    }

}
