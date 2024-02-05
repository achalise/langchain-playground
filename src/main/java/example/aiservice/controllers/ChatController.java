package example.aiservice.controllers;

import example.aiservice.customerservice.CustomerServiceAgent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class ChatController {
    private CustomerServiceAgent agent;

    public ChatController(CustomerServiceAgent agent) {
        this.agent = agent;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam("question") String question) {
        System.out.println("Submitted query ");
        System.out.println(question);
        return agent.chat(question);
    }
}
