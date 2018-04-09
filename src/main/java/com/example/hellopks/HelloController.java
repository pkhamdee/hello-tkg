package com.example.hellopks;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Controller
public class HelloController {
    @GetMapping(path = "/")
    public String index(Model model) {
        Map<String, String> env = System.getenv();
        Map<String, String> node = env.entrySet().stream() //
                .filter(e -> e.getKey().startsWith("NODE")) //
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (k, v) -> v, TreeMap::new));
        Map<String, String> pod = env.entrySet().stream() //
                .filter(e -> e.getKey().startsWith("POD")) //
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (k, v) -> v, TreeMap::new));
        Map<String, String> container = env.entrySet().stream() //
                .filter(e -> e.getKey().startsWith("CONTAINER")) //
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (k, v) -> v, TreeMap::new));
        model.addAttribute("node", node);
        model.addAttribute("pod", pod);
        model.addAttribute("container", container);
        model.addAttribute("env", new TreeMap<>(env));
        model.addAttribute("props", new TreeMap<>(System.getProperties()));
        return "index";
    }
}
