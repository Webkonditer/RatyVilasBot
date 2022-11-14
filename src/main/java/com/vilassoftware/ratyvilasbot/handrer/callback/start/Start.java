package com.vilassoftware.ratyvilasbot.handrer.callback.start;

import com.vilassoftware.ratyvilasbot.service.SendingMessages;
import com.vilassoftware.ratyvilasbot.service.UserManagement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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

    private static final String WELCOME_MESSAGE = "\n\nЯ - твоя вторая память. Можешь смело доверить мне напоминать тебе о важных событиях. \n\n" +
            "Если твой часовой пояс отличается от Московского, нажми сюда /set_utc \n\n" +
            "Теперь давай создадим твое первое напоминание. \nНажми кнопку \"Создать новое\" в меню ниже.";

    private final UserManagement userManagement;
    private final SendingMessages sendingMessages;

    public Start(UserManagement userManagement, SendingMessages sendingMessages) {
        this.userManagement = userManagement;
        this.sendingMessages = sendingMessages;
    }

    /**
     *Обработка команды /start.
     *
     * @param chatId  id текущего чата
     * @param update  объект сообщения
     */
    public void startCallBack(long chatId, Update update){
            userManagement.registerUser(update.getMessage());
            startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
    }

    /**
     *Отправка приветственного сообщения и выбор часового пояса.
     *
     * @param chatId  id текущего чата
     * @param firstName  имя пользователя
     */
    public void startCommandReceived(long chatId, String firstName) {

//        String message = GREETING + firstName + "! " + WELCOME_MESSAGE;
//        sendingMessages.sendMessageWithMenu(chatId, message);
//        log.info("A welcome message has been sent to the user " + firstName + ", Id: " + chatId);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("You send /start");

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

        keyboardButton.setText("Share your number >");
        keyboardButton.setRequestContact(true);
        keyboardFirstRow.add(keyboardButton);

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);

        sendingMessages.executeMessage(sendMessage);

    }

}


