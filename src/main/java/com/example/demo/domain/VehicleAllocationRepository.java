package com.example.demo.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleAllocationRepository extends JpaRepository<VehicleAllocation, Long>, JpaSpecificationExecutor<VehicleAllocation> {
}
