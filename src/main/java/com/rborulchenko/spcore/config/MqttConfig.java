package com.rborulchenko.spcore.config;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig {
    @Value("${mqtt.host}")
    private String host;
    @Value("${mqtt.port}")
    private String port;

    @Bean
    public IMqttClient mqttClient() throws MqttException {
        IMqttClient mqttClient = new MqttClient(getBrokerUrl(), "smart-pot-core");
        mqttClient.connect(mqttConnectOptions());
        return mqttClient;
    }

    @Bean
    @ConfigurationProperties(prefix = "mqtt")
    public MqttConnectOptions mqttConnectOptions() {
        return new MqttConnectOptions();
    }

    private String getBrokerUrl() {
        return String.format("tcp://%s:%s", host, port);
    }
}
