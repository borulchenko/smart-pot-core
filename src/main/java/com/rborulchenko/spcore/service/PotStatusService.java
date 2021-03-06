package com.rborulchenko.spcore.service;

import java.util.List;

import com.rborulchenko.spcore.util.PotStatusUtils;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class PotStatusService {
    private final PotStatusRepository potStatusRepository;
    private final MqttService mqttService;

    @CachePut(value = "statuses", key = "#potStatus.collectingHour")
    public void save(PotStatus potStatus) {
        log.info("Saving status {}", potStatus);
        potStatusRepository.save(potStatus);
    }

    public List<PotStatus> getAll() {
        Sort sortOrder = Sort.by("collectingTime").ascending();
        Page<PotStatus> lastStatus = potStatusRepository.findAll(PageRequest.of(0, 10, sortOrder));

        if (!lastStatus.hasContent()) {
            List<PotStatus> initializedStatuses = PotStatusUtils.buildStatuses();
            potStatusRepository.saveAll(initializedStatuses);
            return initializedStatuses;
        }
        return lastStatus.getContent();
    }

    public PotStatus getLatest() {
        log.info("Getting the latest status");
        Sort sortOrder = Sort.by("collectingTime").descending();
        Page<PotStatus> lastStatus = potStatusRepository.findAll(PageRequest.of(0, 1, sortOrder));
        log.info("Retrieved latest status");
        return lastStatus.hasContent()
            ? lastStatus.getContent().get(0)
            : new PotStatus();
    }
    @Scheduled(fixedDelayString = "${device.status.collect.delay}")
    public void collectStatus() {
        log.info("Collecting status");
        mqttService.publish(Topic.COLLECT_STATUS, "Collect current status.");
    }

    public void enableWatering() {
        log.info("Enabling watering");
        mqttService.publish(Topic.ENABLE_WATERING, "Enable watering.");
    }

    public void enableLighting(boolean enableLighting) {
        log.info("Enabling lighting");
        mqttService.publish(Topic.ENABLE_LIGHTING, String.valueOf(enableLighting));
    }

    @Scheduled(fixedDelayString = "${device.status.clear.cache.delay}")
    @CacheEvict(value = "statuses", allEntries = true)
    public void clearCache() {
        log.info("Releasing the cache");
    }
}
