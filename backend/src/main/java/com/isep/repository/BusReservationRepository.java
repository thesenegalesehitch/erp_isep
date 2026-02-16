package com.isep.repository;

import com.isep.model.Bus;
import com.isep.model.BusReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusReservationRepository extends JpaRepository<BusReservation, Long> {
    List<BusReservation> findByBus(Bus bus);
}

