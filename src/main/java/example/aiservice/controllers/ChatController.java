package example.aiservice.controllers;

import dev.langchain4j.service.TokenStream;
import example.aiservice.customerservice.CustomerServiceAgent;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@RestController()
@CrossOrigin(origins = "*")
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

    @GetMapping(value = "/chatstream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<String> chatStream(@RequestParam("question") String question) {
        Sinks.Many<String> sink = Sinks.many().unicast().onBackpressureBuffer();

        TokenStream ts = agent.chatStream(question);
        ts.onNext(sink::tryEmitNext)
                .onError(sink::tryEmitError)
                .start();

        return sink.asFlux();
    }
}
