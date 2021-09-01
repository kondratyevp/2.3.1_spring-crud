package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.dao.UserDao;
import web.model.User;

@Controller
public class UserController {

    private UserDao userDao;

    public UserController() {}

    @Autowired
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping ("/new")
    public String newUser (Model model) {
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping ("/new")
    public String create (@ModelAttribute("newUser") User user){
        userDao.add(user);
        System.out.println(userDao.getById(user.getId()));
        return "redirect:/users";
    }

    @GetMapping ("/users")
    public String listUsers(Model model) {
        userDao.allUsers();
        model.addAttribute("allusers", userDao.allUsers());
        model.addAttribute("idUser", userDao.getById(2));
        return "users";
    }

    @GetMapping ("/{id}/edit")
    public String edit(Model model, @PathVariable ("id") int id){
        model.addAttribute("user", userDao.getById(id));
        return "edit";
    }

    @PatchMapping ("/users/{id}")
    public String update (@ModelAttribute ("user") User user, @PathVariable ("id") int id){
        userDao.update(id, user);
        return "redirect:/users";
    }

    @DeleteMapping ("/users/{id}")
    public String delete (@PathVariable ("id") int id) {
        userDao.delete(id);
        return "redirect:/users";
    }
}
