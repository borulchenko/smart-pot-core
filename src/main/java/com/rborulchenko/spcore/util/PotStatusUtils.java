package com.rborulchenko.spcore.util;

import com.rborulchenko.spcore.model.PotStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PotStatusUtils {
    public static List<PotStatus> buildStatuses() {
        List<PotStatus> statuses = new ArrayList<>();
        LocalDateTime now = LocalDate.now().atTime(10, 0).minusHours(2);
        statuses.add(buildPotStatus(24, 46, 75, true, now.minusHours(2)));
        statuses.add(buildPotStatus(23, 52, 69, true, now.minusHours(4)));
        statuses.add(buildPotStatus(21, 56, 56, true, now.minusHours(6)));
        statuses.add(buildPotStatus(21, 55, 50, true, now.minusHours(8)));
        statuses.add(buildPotStatus(24, 42, 85, false, now.minusHours(10)));
        statuses.add(buildPotStatus(22, 48, 83, false, now.minusHours(12)));

        return statuses;
    }

    private static PotStatus buildPotStatus(int temperature, int humidity, int soilMoisture, boolean illumination,
            LocalDateTime collectingTime) {
        return PotStatus.builder()
                .temperature(temperature)
                .humidity(humidity)
                .soilMoisture(soilMoisture)
                .illumination(illumination)
                .collectingTime(collectingTime)
                .build();
    }
}
