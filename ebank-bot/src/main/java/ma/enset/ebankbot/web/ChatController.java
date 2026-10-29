package ma.enset.ebankbot.web;

import lombok.RequiredArgsConstructor;
import ma.enset.ebankbot.agent.EbankAIAgent;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Yousef ELHAID
 */
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final EbankAIAgent ebankAIAgent;

    @GetMapping(value = "/chat", produces = MediaType.TEXT_PLAIN_VALUE)
    public String chat(@RequestParam(defaultValue = "Hello") String query,
                       @RequestParam(defaultValue = "web") String conversationId) {
        return ebankAIAgent.chat(conversationId, query);
    }
}