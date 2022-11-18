package com.vilassoftware.ratyvilasbot.handrer.callback.menulevel;

import com.vilassoftware.ratyvilasbot.handrer.command.Command;
import com.vilassoftware.ratyvilasbot.model.Button;
import com.vilassoftware.ratyvilasbot.model.MenuLevel;
import com.vilassoftware.ratyvilasbot.repository.MenuLevelRepository;
import com.vilassoftware.ratyvilasbot.service.SendingMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CreateMenuLevel {

    private final SendingMessages sendingMessages;
    private final MenuLevelRepository menuLevelRepository;

    public CreateMenuLevel(SendingMessages sendingMessages, MenuLevelRepository menuLeveRepository) {
        this.sendingMessages = sendingMessages;
        this.menuLevelRepository = menuLeveRepository;
    }

    /**
     *Выдача меню по уровням.
     *
     * @param chatId  id текущего чата
     */
    public void getMenu(long chatId, int level, int position) {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(menuLevelRepository.getTextByLevelAndPosition(level, position));
        List<MenuLevel> buttons = menuLevelRepository.getButtonsByLevelAndPosition(level + 1, position);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        for (MenuLevel button : buttons) {
            //System.out.println(button);
            KeyboardRow row = new KeyboardRow();
            row.add(button.getButtonText());
            keyboardRows.add(row);
        }

        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setKeyboard(keyboardRows);//Формирование клавиатуры
        message.setReplyMarkup(keyboardMarkup);//Добавление клавиатуры
        //----------------------------------------

        sendingMessages.executeMessage(message);

    }


}
