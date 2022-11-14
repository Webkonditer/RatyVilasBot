package com.vilassoftware.ratyvilasbot.handrer.callback.about;

import com.vilassoftware.ratyvilasbot.service.SendingMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AboutBot {

    private final SendingMessages sendingMessages;

    private static final String ABOUT_MESSAGE = "Бот создан в 2022 году командой разработчиков студии VilasSoftware." +
            "\n\nЗаказать подобный бот или любой другой можно здесь: @AlexanderVilas";

    public AboutBot(SendingMessages sendingMessages) {
        this.sendingMessages = sendingMessages;
    }

    public void aboutCallBack(long chatId) {
        sendingMessages.sendMessageWithMenu(chatId, ABOUT_MESSAGE);
    }
}
