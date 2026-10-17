package ma.enset.ebankbot.agent;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;

/**
 *
 * @author Yousef ELHAID
 */
@Service
public class EbankAIAgent {

    private final ChatClient chatClient;

    public EbankAIAgent(ChatClient.Builder builder,
                        ChatMemory chatMemory,
                        ToolCallbackProvider tools) {
        this.chatClient = builder
                .defaultSystem("""
                        You are a banking assistant. You answer questions about the bank
                        customers and their bank accounts using the available tools.
                        If the tools don't give the information, say that you don't know.
                        Answer in the user's language.
                        """)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build()).defaultTools(tools)
                .build();
    }

    public String chat(String conversationId, String query) {
        return chatClient.prompt()
                .user(query)
                .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();
    }
}