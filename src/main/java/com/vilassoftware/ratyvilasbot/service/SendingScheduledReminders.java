package com.vilassoftware.ratyvilasbot.service;

import com.vilassoftware.ratyvilasbot.model.NotificationTask;
import com.vilassoftware.ratyvilasbot.repository.NotificationTaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
@Slf4j
public class SendingScheduledReminders {

    private final NotificationTaskRepository notificationTaskRepository;

    private final SendingMessages sendingMessages;

    public SendingScheduledReminders(NotificationTaskRepository notificationTaskRepository, SendingMessages sendingMessages) {
        this.notificationTaskRepository = notificationTaskRepository;
        this.sendingMessages = sendingMessages;
    }

    /**
     * Отправляет напоминания по расписанию.
     *
     */
    @Scheduled(cron = "${cron.scheduler}")
    private void sendReminders() throws TelegramApiException {

        var reminders = notificationTaskRepository.findTheCurrentOnesForTheMoment(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        for (NotificationTask reminder: reminders){
            sendingMessages.sendMessage(reminder.getUser().getChatId(), reminder.getReminderText());
            notificationTaskRepository.deleteById(reminder.getId());
            log.info("Scheduled messages to user " + reminder.getUser().getChatId() + " sent: " + reminder.getReminderText());
        }

    }
}
