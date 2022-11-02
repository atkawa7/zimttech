package com.example.demo.contollers;

import com.example.demo.api.AccountService;
import com.example.demo.api.VehicleService;
import com.example.demo.domain.Vehicle;
import com.example.demo.domain.VehicleAllocation;
import com.example.demo.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class DemoController {

    private final AccountService accountService;
    private final VehicleService vehicleService;

    public DemoController(AccountService accountService, VehicleService vehicleService) {
        this.accountService = accountService;
        this.vehicleService = vehicleService;
    }

    @PostMapping(value = "/accounts/login")
    @Operation(
            operationId = "loginAccount",
            method = "POST",
            summary = "Login account",
            description = "Login account",
            tags = "Accounts",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SuccessOrFailure.class))),

            }
    )
    public ResponseEntity<SuccessOrFailure> login(@RequestBody LoginRequest loginRequest){
        return ok(accountService.createToken(loginRequest));
    }

    @PostMapping(value = "/accounts/register")
    @Operation(
            operationId = "createAccount",
            method = "POST",
            summary = "Create account",
            description = "Create account",
            tags = "Accounts",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SuccessOrFailure.class))),

            }
    )
    public ResponseEntity<SuccessOrFailure> createAccount(@RequestBody CreateAccount createAccount){
        return ok(accountService.createAccount(createAccount));
    }


    @Operation(
            operationId = "createVehicle",
            method = "POST",
            summary = "Create new vehicle",
            description = "Create new vehicle",
            tags = "Vehicles",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Vehicle.class))),

            },
            security = @SecurityRequirement(name = "api-key")
    )
    @PostMapping(value = "/vehicles")
    public ResponseEntity<Vehicle> createVehicle(@RequestBody CreateVehicle createVehicle){
        return ok(vehicleService.createVehicle(createVehicle));
    }

    @Operation(
            operationId = "listVehicles",
            method = "GET",
            summary = "List all vehicles",
            description = "List new vehicles",
            tags = "Vehicles",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Vehicle.class))),

            },
            security = @SecurityRequirement(name = "api-key")
    )
    @GetMapping(value = "/vehicles")
    public ResponseEntity<List<Vehicle>> listVehicle(){
        return ok(vehicleService.listVehicles());
    }



    @Operation(
            operationId = "createVehicleRequest",
            method = "POST",
            summary = "List all vehicles",
            description = "List new vehicles",
            tags = "Vehicles",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Vehicle.class))),

            },
            security = @SecurityRequirement(name = "api-key")
    )
    @PostMapping(value = "/vehicles-request")
    public ResponseEntity<VehicleAllocation> createVehicleRequest(@RequestBody VehicleRequest vehicleRequest){
        return ok(vehicleService.createRequest(vehicleRequest));
    }

    @Operation(
            operationId = "approveRequest",
            method = "POST",
            tags = "Vehicles",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Vehicle.class))),

            },
            security = @SecurityRequirement(name = "api-key")
    )
    @PostMapping(value = "/vehicles-request/{id}/approve")
    public ResponseEntity<VehicleAllocation> approveRequest(@PathVariable("id")Long id){
        return ok(vehicleService.approveRequest(id));
    }

    @Operation(
            operationId = "rejectRequest",
            method = "POST",
            tags = "Vehicles",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Vehicle.class))),

            },
            security = @SecurityRequirement(name = "api-key")
    )
    @PostMapping(value = "/vehicles-request/{id}/reject")
    public ResponseEntity<VehicleAllocation> rejectRequest(@PathVariable("id")Long id){
        return ok(vehicleService.rejectRequest(id));
    }

    @Operation(
            operationId = "listJourneyRequests",
            method = "POST",
            tags = "Vehicles",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Vehicle.class))),

            },
            security = @SecurityRequirement(name = "api-key")
    )
    @GetMapping(value = "/vehicles-request")
    public ResponseEntity<List<VehicleAllocation>> listJourneyRequests(){
        return ok(vehicleService.listJourneyRequests());
    }

}
