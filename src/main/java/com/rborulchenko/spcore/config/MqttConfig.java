package com.rborulchenko.spcore.config;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class MqttConfig {
    @Value("${mqtt.url}")
    private String url;

    @Bean
    public IMqttClient mqttClient() throws MqttException {
        log.info("Connecting to the mqtt broker with url: {}", url);
        IMqttClient mqttClient = new MqttClient(url, "smart-pot-core");
        mqttClient.connect(mqttConnectOptions());
        return mqttClient;
    }

    @Bean
    @ConfigurationProperties(prefix = "mqtt")
    public MqttConnectOptions mqttConnectOptions() {
        return new MqttConnectOptions();
    }

}
