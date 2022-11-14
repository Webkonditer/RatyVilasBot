package com.vilassoftware.ratyvilasbot.handrer.command;

import com.vilassoftware.ratyvilasbot.bot.TelegramBot;
import com.vilassoftware.ratyvilasbot.handrer.callback.delete.DeleteReminder;
import com.vilassoftware.ratyvilasbot.handrer.callback.setutc.SetUtc;
import com.vilassoftware.ratyvilasbot.handrer.callback.unsubscribe.UnsubscribeUser;
import com.vilassoftware.ratyvilasbot.repository.NotificationTaskRepository;
import com.vilassoftware.ratyvilasbot.service.SendingMessages;
import com.vilassoftware.ratyvilasbot.service.UserManagement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Slf4j
public class ButtonHandler {

    private final UserManagement userManagement;
    private final SetUtc setUtc;
    private final DeleteReminder deleteReminder;
    private final UnsubscribeUser unsubscribeUser;

    public ButtonHandler(NotificationTaskRepository notificationTaskRepository, TelegramBot telegramBot, SendingMessages sendingMessages, UserManagement userManagement, SetUtc setUtc, DeleteReminder deleteReminder, UnsubscribeUser unsubscribeUser) {
        this.userManagement = userManagement;
        this.setUtc = setUtc;
        this.deleteReminder = deleteReminder;
        this.unsubscribeUser = unsubscribeUser;
    }

    public void processingOfButtons(Update update){
        String callBackData = update.getCallbackQuery().getData();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        if (callBackData.contains("delete")) {
            deleteReminder.delete(callBackData, chatId, messageId);
        } else if (callBackData.equals(DeleteReminder.CANCEL_BUTTON)) {
            deleteReminder.cancelDelete(chatId, messageId);
        }else if (callBackData.equals("setUtcAll")) {
            setUtc.getAll(chatId, messageId);
        }else if (callBackData.startsWith("setUtc_")) {
            userManagement.setUtc(chatId, callBackData);
            setUtc.sendReply(chatId, messageId);
        }else if (callBackData.equals("unsubscribeNo")) {
            unsubscribeUser.doNotUnsubscribe(chatId, messageId);
        }else if (callBackData.equals("unsubscribeYes")) {
            userManagement.deleteUser(chatId);
            unsubscribeUser.Unsubscribe(chatId, messageId);
        }
    }
}
