package com.vilassoftware.ratyvilasbot.repository;

import com.vilassoftware.ratyvilasbot.model.NotificationTask;
import com.vilassoftware.ratyvilasbot.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "SELECT id, reminder_text, reminder_time, user_id from users_data_table " +
            "join notification_task nt on users_data_table.chat_id = nt.user_id  where chat_id = ?1 " +
            "order by reminder_time", nativeQuery = true)
    public List<NotificationTask> findAllUsersReminders(long chatId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users_data_table SET user_phone = ?2  where chat_id = ?1 ", nativeQuery = true)
    void setUserPhoneByChatID(Long chatId, String userPhone);

    @Query(value = "SELECT user_phone from users_data_table where chat_id = ?1", nativeQuery = true)
    String getUserPhoneByChatID(Long chatId);
}
