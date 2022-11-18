package com.vilassoftware.ratyvilasbot.handrer.command;

import com.vilassoftware.ratyvilasbot.handrer.callback.about.AboutBot;
import com.vilassoftware.ratyvilasbot.handrer.callback.help.Help;
import com.vilassoftware.ratyvilasbot.handrer.callback.UnknownCommand;
import com.vilassoftware.ratyvilasbot.handrer.callback.delete.DeleteReminder;
import com.vilassoftware.ratyvilasbot.handrer.callback.menulevel.CreateMenuLevel;
import com.vilassoftware.ratyvilasbot.handrer.callback.newreminder.CreateNewReminder;
import com.vilassoftware.ratyvilasbot.handrer.callback.setutc.SetUtc;
import com.vilassoftware.ratyvilasbot.handrer.callback.show.ShowAllReminders;
import com.vilassoftware.ratyvilasbot.handrer.callback.start.Start;
import com.vilassoftware.ratyvilasbot.bot.TelegramBot;
import com.vilassoftware.ratyvilasbot.handrer.callback.unsubscribe.UnsubscribeUser;
import com.vilassoftware.ratyvilasbot.service.UserManagement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Component
@Slf4j
public class CommandHandler {

    private final TelegramBot telegramBot;
    private final DeleteReminder deleteReminder;
    private final CreateNewReminder createNewReminder;
    private final UnknownCommand unknownCommand;
    private final ShowAllReminders showAllReminders;
    private final Start start;
    private final Help help;
    private final AboutBot aboutBot;
    private final SetUtc setUtc;
    private final UnsubscribeUser unsubscribeUser;
    private final UserManagement userManagement;
    private final CreateMenuLevel createMenuLevel;

    public CommandHandler(TelegramBot telegramBot, DeleteReminder newReminder, DeleteReminder deleteReminder, CreateNewReminder createNewReminder, Help help, UnknownCommand unknownCommand, ShowAllReminders showAllReminders, Start start, AboutBot aboutBot, SetUtc setUtc, UnsubscribeUser unsubscribeUser, UserManagement userManagement, CreateMenuLevel createMenuLevel) {
        this.telegramBot = telegramBot;
        this.deleteReminder = deleteReminder;
        this.createNewReminder = createNewReminder;
        this.help = help;
        this.unknownCommand = unknownCommand;
        this.showAllReminders = showAllReminders;
        this.start = start;
        this.aboutBot = aboutBot;
        this.setUtc = setUtc;
        this.unsubscribeUser = unsubscribeUser;
        this.userManagement = userManagement;
        this.createMenuLevel = createMenuLevel;
    }

    /**
     *Обработка входящих команд.
     *
     * @param update  объект запроса
     * @param chatId  id чата
     * @param messageText  текст запроса
     */
    public void commandProcessing(Update update, long chatId, String messageText){
        Optional<Command> command = Command.parseCommand(messageText);
        if(command.isPresent()) {
            if(!userManagement.checkUserPhoneNumber(chatId)){      //Если пользователь не предоставил телефон
                start.startCallBack(update);
                return;
            }
            switch (command.get()) {
                case START_COMAND -> createMenuLevel.getMenu(chatId, 1, 1);
                case HELP_COMAND -> help.helpCallBack(chatId);
                case CREATE_COMAND -> createNewReminder.preparingToCreateNewReminder(chatId);
                case SHOW_COMAND -> showAllReminders.showMyReminders(chatId);
                case DELETE_COMAND -> deleteReminder.preparingForDeletion(chatId);
                case ABOUT_COMAND -> aboutBot.aboutCallBack(chatId);
                case SET_UTS -> setUtc.preparingForSetUtc(chatId);
                case UNSUBSCRIBE_USER -> unsubscribeUser.preparingUnsubscribe(chatId);
            }
        } else{
            unknownCommand.unknownCommandCallBack(chatId);//Если введена неизвестная команда
        }
    }

}
