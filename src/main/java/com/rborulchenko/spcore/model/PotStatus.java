package com.rborulchenko.spcore.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

import javax.persistence.Entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
@Entity
@Builder
public class PotStatus extends BaseEntity {
    @JsonProperty("airTemperature")
    private int temperature;
    @JsonProperty("airHumidity")
    private int humidity;
    @JsonProperty("soilHumidity")
    private int soilMoisture;
    @JsonProperty("illuminationEnabled")
    private boolean illumination;
    @JsonProperty("collectionTime")
    private LocalDateTime collectingTime;
}
