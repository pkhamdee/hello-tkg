package com.example.hellotkg;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Controller
public class HelloController {
    final Random random;
    {
        try {
            random = SecureRandom.getInstance("NativePRNGNonBlocking");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    public void bug() {
        if (random.nextInt(100) < 3) {
            throw new IllegalStateException("B U G!!");
        }
    }

    @GetMapping(path = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String index(Model model) {
        this.bug();
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

    @ResponseBody
    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, Object> json() {
        this.bug();
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
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("node", node);
        body.put("pod", pod);
        body.put("container", container);
        return body;
    }
}
