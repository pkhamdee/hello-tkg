package com.example.hellopks.tweeter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Controller
public class TweetController {
    private final TweetMapper tweetMapper;
    private final Scheduler scheduler = Schedulers.single(); // only demo purpose to reduces extra memory

    public TweetController(TweetMapper tweetMapper) {
        this.tweetMapper = tweetMapper;
    }

    @GetMapping("/tweets")
    public String listTweets(Model model) {
        Mono<List<Tweet>> tweets = Mono.fromCallable(this.tweetMapper::findAll) //
                .subscribeOn(this.scheduler);
        model.addAttribute("tweets", tweets);
        return "tweets";
    }

    @PostMapping("/tweets")
    public Mono<String> postTweets(@ModelAttribute Mono<TweetForm> form) {
        return form.map(TweetForm::toModel) //
                .publishOn(this.scheduler) //
                .doOnSuccess(this.tweetMapper::insert) //
                .then(Mono.just("redirect:/tweets"));
    }

    static class TweetForm {
        private String username;
        private String text;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Tweet toModel() {
            return new Tweet(this.text, this.username);
        }
    }
}
