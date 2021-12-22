package kuzoliza.entrySystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/accessByNumber")
    public String hasAccess(@RequestParam String number, Model model) {
        User user = userRepository.findByTelephoneNumber(number);
        String hasAccess = "Есть";
        String hasNoAccess = "Нет";

        if (user == null ) {
            model.addAttribute("accessNum", hasNoAccess);
            return "accessByNumber";
        } else {
            if (user.isAccess() == false) {
                model.addAttribute("accessNum", hasNoAccess);
            } else {
                model.addAttribute("accessNum", hasAccess);
            }
            return "accessByNumber";
        }
    }

    @GetMapping("/accessBySurname")
    public String hasAccess(@RequestParam(required = false) String name, @RequestParam String surname, Model model) {
        List<User> users = getUsers(name, surname);
        model.addAttribute("users", users);
        return "access";
    }

    @GetMapping("/quantity")
    public String peopleQuantity(Model model) {
        model.addAttribute("quantity", userRepository.findAllByInsideTrue().size());
        return "quantity";
    }

    @GetMapping("/insideByNumber")
    public String isInside(@RequestParam String number, Model model) {
        User user = userRepository.findByTelephoneNumber(number);
        String inside = "Да";
        String outside = "Нет";

        if (user == null) {
            model.addAttribute("insideNum", outside);
            return "insideByNumber";
        } else {
            if (user.isInside() == false) {
                model.addAttribute("insideNum", outside);
            } else {
                model.addAttribute("insideNum", inside);
            }
            return "insideByNumber";
        }
    }

    @GetMapping("/insideBySurname")
    public String isInside(@RequestParam(required = false) String name, @RequestParam String surname, Model model) {
        List<User> users = getUsers(name, surname);
        model.addAttribute("users", users);
        return "inside";
    }

    private List<User> getUsers(@Nullable String name, String surname) {
        List<User> users;
        if (name == null || name.isEmpty()) {
            users = userRepository.findAllBySurname(surname);
        } else {
            users = userRepository.findAllByNameAndSurname(name, surname);
        }
        return users;
    }

    @GetMapping("/status")
    public String status() {
        return "Server is working";
    }

}
