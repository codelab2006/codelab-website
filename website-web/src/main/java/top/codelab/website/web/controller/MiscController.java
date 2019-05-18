package top.codelab.website.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import top.codelab.website.service.api.MiscService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
class MiscController {

    private static final String VIEW_ABOUT = "about";
    private static final String VIEW_WORKSPACE = "workspace";
    private static final String VIEW_EXCEPTION = "exception";

    private static final String ATTR_CURRENT_DATE_TIME = "currentDateTime";

    @Autowired
    private MiscService miscService;

    @GetMapping("/about")
    String about() {
        return VIEW_ABOUT;
    }

    @GetMapping("/workspace")
    ModelAndView workspace() {
        ModelAndView modelAndView = new ModelAndView(VIEW_WORKSPACE);
        modelAndView.addObject(ATTR_CURRENT_DATE_TIME, this.nowString());
        return modelAndView;
    }

    @PostMapping("/refresh")
    String refresh(@RequestParam("password") String password) {
        if (!this.generatePassword().equals(password)) {
            return "redirect:/workspace";
        }
        this.miscService.refresh();
        return "redirect:/";
    }

    private LocalDateTime now() {
        return LocalDateTime.now();
    }

    private String nowString() {
        return this.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a"));
    }

    private String generatePassword() {
        return this.now().format(DateTimeFormatter.ofPattern("yyyHHMMmmdd"));
    }

    @GetMapping("/exception")
    String exception() {
        return VIEW_EXCEPTION;
    }
}
