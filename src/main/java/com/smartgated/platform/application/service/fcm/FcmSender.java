package com.smartgated.platform.application.service.fcm;

import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class FcmSender {

    public String sendNotification(String token, String title, String body, Map<String, String> data)
            throws FirebaseMessagingException {

        Message.Builder builder = Message.builder()
                .setToken(token)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build());

        if (data != null && !data.isEmpty()) {
            builder.putAllData(data);
        }

        return FirebaseMessaging.getInstance().send(builder.build());
    }


}
