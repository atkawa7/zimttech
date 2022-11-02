package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

//request a vehicle for a journey specifying destination, estimated distance in kilometers,
//start date and expected end date.
@Data
public class VehicleRequest {

    private String destination;
    private Integer distance; //IN KMs
    private LocalDate startDate;
    private LocalDate endDate;

}
