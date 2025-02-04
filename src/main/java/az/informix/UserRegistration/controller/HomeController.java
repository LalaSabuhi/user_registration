package az.informix.UserRegistration.controller;

import az.informix.UserRegistration.model.UserDtls;
import az.informix.UserRegistration.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/signin")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(HttpSession httpSession, Model model){
        String msg = (String) httpSession.getAttribute("msg");
        if(msg != null){
            model.addAttribute("msg", msg);
            httpSession.removeAttribute("msg");
        }
        return "register";
    }
    @PostMapping("/createUser")
    public String createUser(@ModelAttribute UserDtls user, HttpSession httpSession) {
        boolean f = userService.checkEmail(user.getEmail());
        if (f) {
            httpSession.setAttribute("msg", "Email id already exists");
        } else {
            UserDtls userDtls = userService.createUser(user);
            if (userDtls != null) {
                httpSession.setAttribute("msg", "Register Successfully");
            } else {
                httpSession.setAttribute("msg", "Something went wrong");
            }
        }
        return "redirect:/register";
    }

}
