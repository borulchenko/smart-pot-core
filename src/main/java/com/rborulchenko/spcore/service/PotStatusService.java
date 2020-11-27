package com.rborulchenko.spcore.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.rborulchenko.spcore.model.PotStatus;
import com.rborulchenko.spcore.mqtt.Topic;
import com.rborulchenko.spcore.repository.PotStatusRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PotStatusService {
    private PotStatusRepository potStatusRepository;
    private MqttService mqttService;

    @CachePut(value = "statuses", key = "#potStatus.collectingHour")
    public void save(PotStatus potStatus) {
        potStatusRepository.save(potStatus);
    }

    public List<PotStatus> getAll() {
        return potStatusRepository.findAll();
    }

    public PotStatus getLatest() {
        Sort sortOrder = Sort.by("collectingTime").descending();
        Page<PotStatus> lastStatus = potStatusRepository.findAll(PageRequest.of(0, 1, sortOrder));

        return lastStatus.hasContent()
            ? lastStatus.getContent().get(0)
            : null;
    }
    @Scheduled(fixedDelayString = "${device.status.collect.delay}")
    public void collectStatus() {
        mqttService.publish(Topic.COLLECT_STATUS, "Collect current status.");
    }

    public void enableWatering() {
        mqttService.publish(Topic.ENABLE_WATERING, "Enable watering.");
    }

    public void enableLighting(boolean enableLighting) {
        mqttService.publish(Topic.ENABLE_LIGHTING, String.valueOf(enableLighting));
    }

    @Scheduled(fixedDelayString = "${device.status.clear.cache.delay}")
    @CacheEvict(value = "statuses", allEntries = true)
    public void clearCache() {
        System.out.println("clearCache() was called");
    }
}
