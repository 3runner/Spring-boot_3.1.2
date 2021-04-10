package name.russkikh.controller;

import name.russkikh.model.User;
import name.russkikh.service.RoleService;
import name.russkikh.service.AdminService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@Controller
public class AdminController {
    private final AdminService adminService;
    private final RoleService roleService;

    public AdminController(AdminService adminService, RoleService roleService) {
        this.adminService = adminService;
        this.roleService = roleService;
    }

    @ModelAttribute
    private void addAttributes(Model model) {
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername();
        model.addAttribute("authorizedUser", username);
        model.addAttribute("userRoles", adminService.findUserByName(username).get().rolesToString());
        model.addAttribute("allRoles", new HashSet<>(roleService.findAll()));
    }

    @GetMapping("/admin")
    public String getUserPage(Model model, @ModelAttribute("user") User user) {
        model.addAttribute("users", adminService.findAll());
        return "users";
    }

    @GetMapping("/user/new")
    public String createNewUser(@ModelAttribute("user") User user,
                                Model model) {
        model.addAttribute("allRoles", new HashSet<>(roleService.findAll()));
        return "new";
    }

    @PostMapping("/user/new")
    public String createNewUser(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "new";
        }
        adminService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/user/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", adminService.getOne(id));
        model.addAttribute("allRoles", new HashSet<>(roleService.findAll()));
        return "edit";
    }

    @PatchMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @ModelAttribute("user") User user,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            user.setId(id);
            return "edit";
        }
        adminService.save(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable long id, Model model) {
        adminService.deleteById(id);
        return "redirect:/admin";
    }
}
