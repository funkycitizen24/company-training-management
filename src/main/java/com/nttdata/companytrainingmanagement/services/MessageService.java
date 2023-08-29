package com.nttdata.companytrainingmanagement.services;

import java.util.List;

public interface MessageService {
    void sendMessage(String topic, String message);
    List<String> readMessages(String topic);


}
