package com.rborulchenko.spcore.mqtt;

import static com.rborulchenko.spcore.mqtt.Topic.STORE_STATUS;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.rborulchenko.spcore.mqtt.listeners.CurrentStatusMessageListener;
import com.rborulchenko.spcore.service.MqttService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class MqttSubscriber {
    private final MqttService mqttService;
    private final CurrentStatusMessageListener currentStatusMessageListener;

    @PostConstruct
    public void init() {
        mqttService.subscribe(STORE_STATUS, currentStatusMessageListener);
        log.info("Subscribed to the {} topic.", STORE_STATUS);
    }
}
