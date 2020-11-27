package com.rborulchenko.spcore.service;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

import com.rborulchenko.spcore.mqtt.QOS;
import com.rborulchenko.spcore.mqtt.Topic;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MqttService {
    private IMqttClient mqttClient;

    public void publish(Topic topic, String payload) {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(payload.getBytes());
        mqttMessage.setQos(QOS.ONCE.getLevelValue());
        mqttMessage.setRetained(true);

        try {
            mqttClient.publish(topic.name(), mqttMessage);
        } catch (MqttException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void subscribe(Topic topic, IMqttMessageListener mqttMessageListener) {
        try {
            if (!mqttClient.isConnected()) {
                mqttClient.connect();
            }
            mqttClient.subscribe(topic.name(), mqttMessageListener);
        } catch (MqttException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
