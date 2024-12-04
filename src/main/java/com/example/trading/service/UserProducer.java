package com.example.trading.service;

import com.example.trading.dto.UserDto;
import com.example.trading.entity.User;
import com.example.trading.exception.KafkaServerDownException;
import com.example.trading.exception.PanNumberAlreadyExistsException;
import com.example.trading.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProducer {

    @Autowired
    private KafkaTemplate<String, UserDto> kafkaTemplate;

    @Autowired
    private UserRepo userRepo;

    private static final String TOPIC = "user_topic";

    public void registerUser(UserDto userDto){
        Optional<User> existingUser = userRepo.findByPanNumber(userDto.getPanNumber());
        if(existingUser.isPresent()){
            throw new PanNumberAlreadyExistsException("Registration Failed!!!! PAN details already exist for some other user");
        }
        try{
            kafkaTemplate.send(TOPIC, userDto);
        }catch (KafkaException e){
            throw new KafkaServerDownException("Kafka server is down. Please try again later.");
        }

    }
}
