package com.example.demo.api;

import com.example.demo.domain.Vehicle;
import com.example.demo.domain.VehicleAllocation;
import com.example.demo.domain.VehicleAllocationRepository;
import com.example.demo.domain.VehicleRepository;
import com.example.demo.dto.CreateVehicle;
import com.example.demo.dto.RequestStatus;
import com.example.demo.dto.Role;
import com.example.demo.dto.VehicleRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service("vehicleService")
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleAllocationRepository vehicleAllocationRepository;
    private final CurrentUserService currentUserService;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, VehicleAllocationRepository vehicleAllocationRepository, CurrentUserService currentUserService) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleAllocationRepository = vehicleAllocationRepository;
        this.currentUserService = currentUserService;
    }

    @Override
    public Vehicle createVehicle(CreateVehicle createVehicle) {
       final var result  =  vehicleRepository.findVehicleByRegistrationNumber(
               createVehicle.getRegistrationNumber());
       if(result != null){
           return result;
       }

       final var vehicle = new Vehicle();
       vehicle.setRegistrationNumber(createVehicle.getRegistrationNumber());
        return vehicleRepository.save(vehicle);
    }

    @Override
    public List<Vehicle> listVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    public VehicleAllocation createRequest(VehicleRequest vehicleRequest) {
        var user  = currentUserService.current();
        if(user.getRole()  == Role.DRIVER){
            VehicleAllocation allocation =  new VehicleAllocation();
            allocation.setRequestor(user);
            allocation.setStatus(RequestStatus.PENDING);
            allocation.setEndDate(vehicleRequest.getEndDate());
            allocation.setStartDate(vehicleRequest.getStartDate());
           return vehicleAllocationRepository.save(allocation);
        }
        throw new UnsupportedOperationException("Failed to create allocation");
    }

    @Override
    public VehicleAllocation approveRequest(Long id) {
        var user  = currentUserService.current();
        if(user.getRole() == Role.SUPERVISOR){
            var o = vehicleAllocationRepository.findById(id);
            if(o.isPresent()){
                var  allocation  = o.orElseThrow();
                allocation.setApprover(user);
                allocation.setStatus(RequestStatus.APPROVED);
                return vehicleAllocationRepository.save(allocation);
            }
        }
        throw new UnsupportedOperationException("Failed to create allocation");
    }

    @Override
    public VehicleAllocation rejectRequest(Long id) {
        var user  = currentUserService.current();
        if(user.getRole() == Role.SUPERVISOR){
            var o = vehicleAllocationRepository.findById(id);
            if(o.isPresent()){
                var  allocation  = o.orElseThrow();
                allocation.setApprover(user);
                allocation.setStatus(RequestStatus.REJECTED);
                return vehicleAllocationRepository.save(allocation);
            }
        }
        throw new UnsupportedOperationException("Failed to create allocation");
    }

    @Override
    public List<VehicleAllocation> listJourneyRequests() {
        return vehicleAllocationRepository.findAll();
    }

    @Override
    public VehicleAllocation endCurrentJourney(Long id, LocalDate newDate) {
        var user  = currentUserService.current();
        if(user.getRole() == Role.SUPERVISOR){
            var o = vehicleAllocationRepository.findById(id);
            VehicleAllocation allocation;
            if(o.isPresent()  && (allocation = o.orElseThrow()).getStatus() == RequestStatus.APPROVED){
                allocation.setActualEndDate(newDate);
                allocation.setEndedBy(user);
                return vehicleAllocationRepository.save(allocation);
            }
        }
        throw new UnsupportedOperationException("Failed to create allocation");
    }
}
