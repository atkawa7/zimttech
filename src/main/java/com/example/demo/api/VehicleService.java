package com.example.demo.api;

import com.example.demo.domain.Vehicle;
import com.example.demo.domain.VehicleAllocation;
import com.example.demo.dto.CreateVehicle;
import com.example.demo.dto.VehicleRequest;

import java.time.LocalDate;
import java.util.List;

public interface VehicleService {
    Vehicle createVehicle(CreateVehicle createVehicle);

    List<Vehicle> listVehicles();

    VehicleAllocation createRequest(VehicleRequest vehicleRequest);

    VehicleAllocation approveRequest(Long id);

    VehicleAllocation rejectRequest(Long id);

    List<VehicleAllocation> listJourneyRequests();

    VehicleAllocation endCurrentJourney(Long id, LocalDate newDate);

}
