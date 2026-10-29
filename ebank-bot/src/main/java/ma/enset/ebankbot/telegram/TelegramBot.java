package ma.enset.ebankbot.telegram;

import lombok.extern.slf4j.Slf4j;
import ma.enset.ebankbot.agent.EbankAIAgent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 *
 * @author Yousef ELHAID
 */
@Slf4j
@Component
public class TelegramBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

    private final EbankAIAgent ebankAIAgent;
    private final TelegramClient telegramClient;
    private final String botToken;

    public TelegramBot(EbankAIAgent ebankAIAgent, @Value("${telegram.token}") String botToken) {
        this.ebankAIAgent = ebankAIAgent;
        this.botToken = botToken;
        this.telegramClient = new OkHttpTelegramClient(botToken);
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) return;

        String chatId = update.getMessage().getChatId().toString();
        String question = update.getMessage().getText();

        try {
            telegramClient.execute(SendChatAction.builder()
                    .chatId(chatId)
                    .action(ActionType.TYPING.toString())
                    .build());
            String answer = ebankAIAgent.chat("telegram-" + chatId, question);
            telegramClient.execute(SendMessage.builder().chatId(chatId).text(answer).build());
        } catch (TelegramApiException e) {
            log.error("Telegram error for chat {}", chatId, e);
        }
    }
}