package com.rborulchenko.spcore.service;

import com.rborulchenko.spcore.repository.PotStatusRepository;
import com.rborulchenko.spcore.util.PotStatusUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
@AllArgsConstructor
public class PotStatusInitializer {
    private final PotStatusRepository potStatusRepository;

    @PostConstruct
    public void initStatuses() {
        log.info("Populating statuses with initial data...");
        potStatusRepository.saveAll(PotStatusUtils.buildStatuses());
    }
}
