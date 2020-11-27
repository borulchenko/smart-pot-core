package com.rborulchenko.spcore.mqtt.listeners;

import java.time.LocalDateTime;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rborulchenko.spcore.model.PotStatus;
import com.rborulchenko.spcore.repository.PotStatusRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class CurrentStatusMessageListener implements IMqttMessageListener {
    private PotStatusRepository potStatusRepository;
    private ObjectMapper objectMapper;

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        log.info("Received pot status: {}", mqttMessage.toString());

        PotStatus currentStatus = objectMapper.readValue(mqttMessage.toString(), PotStatus.class);
        currentStatus.setCollectingTime(LocalDateTime.now());
        potStatusRepository.save(currentStatus);
    }

}
