package com.example.trading.service;

import com.example.trading.dto.UserDto;
import com.example.trading.entity.User;
import com.example.trading.exception.KafkaServerDownException;
import com.example.trading.exception.PanNumberAlreadyExistsException;
import com.example.trading.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserProducerTest {

    @Mock
    private KafkaTemplate<String, UserDto> kafkaTemplate;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserProducer userProducer;

    public UserProducerTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void registerUserSuccess(){
        UserDto userDto = new UserDto("Arjun", "arjun@example.com", "9898989898", "ABCDE1234F", "Passport", "A1234567");

        when(userRepo.findByPanNumber(userDto.getPanNumber())).thenReturn(Optional.empty());
        userProducer.registerUser(userDto);

        verify(userRepo, times(1)).findByPanNumber(userDto.getPanNumber());
        verify(kafkaTemplate, times(1)).send("user_topic", userDto);
    }

    @Test
    public void registerUserUserPanNumberAlreadyExists(){
        UserDto userDto = new UserDto("Arjun", "arjun@example.com", "9898989898", "ABCDE1234F", "Passport", "A1234567");
        User existingUser = new User();

        when(userRepo.findByPanNumber(userDto.getPanNumber())).thenReturn(Optional.of(existingUser));
        assertThrows(PanNumberAlreadyExistsException.class, () -> {
            userProducer.registerUser(userDto);
        });

        verify(userRepo, times(1)).findByPanNumber(userDto.getPanNumber());
        verify(kafkaTemplate, never()).send("user_topic", userDto);

    }

    @Test
    public void registerUserKafkaServerDown(){
        UserDto userDto = new UserDto("Arjun", "arjun@example.com", "9898989898", "ABCDE1234F", "Passport", "A1234567");

        when(userRepo.findByPanNumber(userDto.getPanNumber())).thenReturn(Optional.empty());
        doThrow(new KafkaException("Kafka server is down")).when(kafkaTemplate).send("user_topic", userDto);

        assertThrows(KafkaServerDownException.class, () -> {
            userProducer.registerUser(userDto);
        });

        verify(userRepo, times(1)).findByPanNumber(userDto.getPanNumber());
        verify(kafkaTemplate, times(1)).send("user_topic", userDto);
    }
}
