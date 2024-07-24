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

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }
    @PostMapping("/createUser")
    public String createUser(Model model, @ModelAttribute UserDtls user, HttpSession httpSession){
        boolean f= userService.checkEmail(user.getEmail());
        if(f){

            httpSession.setAttribute("msg", "Email id already exists");
        }else{
            UserDtls userDtls = userService.createUser(user);
            if(userDtls != null){
                System.out.println("Registered Successfully");
            }else{
                System.out.println("Something went wrong");
            }
        }

        return "redirect:/register";
    }
}
