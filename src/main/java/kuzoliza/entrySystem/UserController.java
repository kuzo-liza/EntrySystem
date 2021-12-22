package kuzoliza.entrySystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/accessByNumber")
    public boolean hasAccess(@RequestParam String number) {
        User user = userRepository.findByTelephoneNumber(number);
        if (user == null) {
            return false;
        } else {
            return user.isAccess();
        }
    }

    @GetMapping("/accessBySurname")
    public List<User> hasAccess(@RequestParam(required = false) String name, @RequestParam String surname) {
        return getUsers(name, surname);
    }

    @GetMapping("/quantity")
    public String peopleQuantity(Model model) {
        model.addAttribute("quantity", userRepository.findAllByInsideTrue().size());
        return "quantity";
    }

    @GetMapping("/insideByNumber")
    public boolean isInside(@RequestParam String number) {
        User user = userRepository.findByTelephoneNumber(number);
        if (user == null) {
            return false;
        } else {
            return user.isInside();
        }
    }

    @GetMapping("/insideBySurname")
    public List<User> isInside(@RequestParam(required = false) String name, @RequestParam String surname) {
        return getUsers(name, surname);
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
