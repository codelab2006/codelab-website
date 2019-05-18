package top.codelab.website.web.controller.read;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class TutorialReadController {

    private static final String VIEW_TUTORIALS = "tutorials";
    private static final String VIEW_TUTORIAL = "tutorial";

    @GetMapping("/tutorials")
    String tutorials() {
        return VIEW_TUTORIALS;
    }

    @GetMapping("/tutorials/{tutorialId}")
    String tutorial() {
        return VIEW_TUTORIAL;
    }
}
