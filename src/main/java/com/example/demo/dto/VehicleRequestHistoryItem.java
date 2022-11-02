package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VehicleRequestHistoryItem {
    private Long id;
    private String destination;
    private Integer distance; //IN KMs
    private LocalDate startDate;
    private LocalDate endDate;
}
