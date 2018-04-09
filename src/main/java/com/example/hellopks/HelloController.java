package com.example.hellopks;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.TreeMap;

@Controller
public class HelloController {
    @GetMapping(path = "/")
    public String index(Model model) {
        model.addAttribute("env", new TreeMap<>(System.getenv()));
        model.addAttribute("props", new TreeMap<>(System.getProperties()));
        return "index";
    }
}
