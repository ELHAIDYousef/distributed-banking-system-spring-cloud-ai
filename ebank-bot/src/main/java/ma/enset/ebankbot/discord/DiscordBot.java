package ma.enset.ebankbot.discord;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import ma.enset.ebankbot.agent.EbankAIAgent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author Yousef ELHAID
 */
@Slf4j
@Component
public class DiscordBot extends ListenerAdapter {

    private static final int DISCORD_MAX_LENGTH = 2000;

    private final EbankAIAgent ebankAIAgent;
    private final String botToken;
    private JDA jda;

    public DiscordBot(EbankAIAgent ebankAIAgent, @Value("${discord.token}") String botToken) {
        this.ebankAIAgent = ebankAIAgent;
        this.botToken = botToken;
    }

    @PostConstruct
    public void start() {
        jda = JDABuilder.createLight(botToken,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.DIRECT_MESSAGES,
                        GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(this)
                .build();
    }

    @PreDestroy
    public void stop() {
        if (jda != null) jda.shutdown();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String question = event.getMessage().getContentRaw();
        if (question.isBlank()) return;

        String channelId = event.getChannel().getId();
        event.getChannel().sendTyping().queue();

        try {
            String answer = ebankAIAgent.chat("discord-" + channelId, question);
            if (answer.length() > DISCORD_MAX_LENGTH) {
                answer = answer.substring(0, DISCORD_MAX_LENGTH - 3) + "...";
            }
            event.getChannel().sendMessage(answer).queue();
        } catch (Exception e) {
            log.error("Discord error for channel {}", channelId, e);
            event.getChannel().sendMessage("Sorry, something went wrong.").queue();
        }
    }
}