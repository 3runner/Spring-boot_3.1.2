package name.russkikh.controller;

import name.russkikh.model.User;
import name.russkikh.service.RoleService;
import name.russkikh.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @ModelAttribute
    private void addAttributes(Model model) {
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername();
        model.addAttribute("authorizedUser", username);
        model.addAttribute("userRoles", userService.findUserByName(username).get().rolesToString());
        model.addAttribute("allRoles", new HashSet<>(roleService.findAll()));
    }

    @GetMapping
    public String getUserPage(Model model, @ModelAttribute("user") User user) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @PostMapping("/new")
    public String createNewUser(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "new";
        }
        userService.save(user);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String updateUser(@PathVariable("id") long id, @ModelAttribute("user") User user,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            user.setId(id);
            return "edit";
        }
        userService.save(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable long id, Model model) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}
