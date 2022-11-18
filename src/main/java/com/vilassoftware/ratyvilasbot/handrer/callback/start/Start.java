package com.vilassoftware.ratyvilasbot.handrer.callback.start;

import com.vilassoftware.ratyvilasbot.service.SendingMessages;
import com.vilassoftware.ratyvilasbot.service.UserManagement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class Start {

    private static final String GREETING = "\uD83D\uDE00 Привет ";

    private static final String WELCOME_MESSAGE = "!\n\nЧтобы запустить бота, отправь, пожалуйста, мне свои контакты." +
            "\n\nДля этого нажми кнопку внизу и подтверди передучу контактов.";
    private static final String SHARE_PHONE_NUMBER = "Поделиться контактами";

    private final UserManagement userManagement;
    private final SendingMessages sendingMessages;

    public Start(UserManagement userManagement, SendingMessages sendingMessages) {
        this.userManagement = userManagement;
        this.sendingMessages = sendingMessages;
    }

    /**
     *Обработка команды /start.
     *
     * @param update  объект сообщения
     */
    public void startCallBack(Update update){
        Chat chat;
        if(update.hasMyChatMember()){
            chat = update.getMyChatMember().getChat();
        } else {
            chat = update.getMessage().getChat();
        }
        startCommandReceived(chat.getId(), chat.getFirstName());
    }

    /**
     *Отправка приветственного сообщения и кнопки запроса телефона.
     *
     * @param chatId  id текущего чата
     * @param firstName  имя пользователя
     */
    public void startCommandReceived(long chatId, String firstName) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(GREETING + firstName + WELCOME_MESSAGE);

        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        KeyboardButton keyboardButton = new KeyboardButton();

        keyboardButton.setText(SHARE_PHONE_NUMBER);
        keyboardButton.setRequestContact(true);
        keyboardFirstRow.add(keyboardButton);

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);

        sendingMessages.executeMessage(sendMessage);

    }

}
