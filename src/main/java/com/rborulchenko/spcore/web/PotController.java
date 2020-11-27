package com.rborulchenko.spcore.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rborulchenko.spcore.model.PotStatus;
import com.rborulchenko.spcore.mqtt.Topic;
import com.rborulchenko.spcore.service.PotStatusService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/pot")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@Slf4j
public class PotController {
    private PotStatusService potStatusService;

    @GetMapping("/statuses")
    public ResponseEntity<List<PotStatus>> getAllStatuses() {
        log.info("Inside get all statuses endpoint.");
        List<PotStatus> allStatuses = potStatusService.getAll();
        return ResponseEntity.ok(allStatuses);
    }

    @GetMapping("/status/latest")
    public ResponseEntity<PotStatus> getLatestStatus() {
        log.info("Inside get latest status endpoint.");
        return ResponseEntity.ok(potStatusService.getLatest());
    }

    @GetMapping("/status/collect")
    public ResponseEntity<String> collectStatus() {
        log.info("Inside collect status from device endpoint.");
        potStatusService.collectStatus();
        return ResponseEntity.ok(String.format("%s topic was published", Topic.COLLECT_STATUS.name()));
    }

    @GetMapping("/enableWatering")
    public ResponseEntity<String> enableWatering() {
        log.info("Inside enable watering in device endpoint.");
        potStatusService.enableWatering();
        return ResponseEntity.ok(String.format("%s topic was published", Topic.ENABLE_WATERING.name()));
    }

    @GetMapping("/config")
    public ResponseEntity<String> enableLighting(
            @RequestParam("illuminationEnabled") boolean enableLighting
    ) {
        log.info("Inside enable lighting in device endpoint.");
        PotStatus latest = potStatusService.getLatest();
        latest.setIllumination(enableLighting);
        potStatusService.save(latest);

        potStatusService.enableLighting(enableLighting);
        return ResponseEntity.ok(String.format("%s topic was published", Topic.ENABLE_LIGHTING.name()));
    }
}