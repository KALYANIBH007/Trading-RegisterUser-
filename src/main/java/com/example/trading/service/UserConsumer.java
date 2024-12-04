package com.example.trading.service;

import com.example.trading.dto.UserDto;
import com.example.trading.entity.Document;
import com.example.trading.entity.LoginDetails;
import com.example.trading.entity.User;
import com.example.trading.repository.DocumentRepo;
import com.example.trading.repository.LoginDetailsRepo;
import com.example.trading.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class UserConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserConsumer.class);

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private DocumentRepo documentRepo;

    @Autowired
    private LoginDetailsRepo loginDetailsRepo;

    @KafkaListener(topics = "user_topic", groupId = "tradeGrp")
    public void consumeUser(UserDto userDto){

        Document document = new Document();
        document.setDocumentName(userDto.getDocumentName());
        document.setDocumentNumber(userDto.getDocumentNumber());
        documentRepo.save(document);

        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setPhoneNo(userDto.getPhoneNo());
        user.setPanNumber(userDto.getPanNumber());
        user.setDocumentId(document.getDocumentId());
        user.setUserCreatedDate(LocalDate.now());
        userRepo.save(user);

        LoginDetails loginDetails = new LoginDetails();
        loginDetails.setLoginId(user.getUserName() + "_" + UUID.randomUUID().toString().substring(0,8));
        loginDetails.setPassword(UUID.randomUUID().toString());
        loginDetails.setLoggedInStatus("ACTIVE");
        loginDetails.setUserId(user.getUserId());
        loginDetailsRepo.save(loginDetails);

        LOGGER.info("==== Data saved in the database ==== ");

    }

}
