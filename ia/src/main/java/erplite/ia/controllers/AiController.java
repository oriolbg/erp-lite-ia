package erplite.ia.controllers;

import erplite.ia.aop.AIObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
@Slf4j
public class AiController {

    private final ChatClient ollama;
    private final ChatClient gemini;

    public AiController(
            @Qualifier(value = "ollama") ChatClient ollama,
            @Qualifier(value = "gemini") ChatClient gemini) {
        this.ollama = ollama;
        this.gemini = gemini;
    }

    @AIObserver
    @GetMapping(path="/chat")
    public String chat(@RequestParam String prompt, @RequestParam String agent){

        var client = switch(agent.toLowerCase()){
            case "ollama" -> {
                log.info("Init Chat Client OLLAMA");
                yield this.ollama;
            }
            case "gemini" -> {
                log.info("Init Chat Client GEMINI");
                yield this.gemini;
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + agent);
        };

        return client
                .prompt()
                .user(prompt)
                .call()
                .content();
    }

}
