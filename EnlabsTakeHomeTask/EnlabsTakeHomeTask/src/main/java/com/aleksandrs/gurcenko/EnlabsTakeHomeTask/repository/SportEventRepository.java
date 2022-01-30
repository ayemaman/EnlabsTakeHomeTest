package com.aleksandrs.gurcenko.EnlabsTakeHomeTask.repository;

import com.aleksandrs.gurcenko.EnlabsTakeHomeTask.model.SportEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SportEventRepository extends JpaRepository<SportEvent,Long> {
    Optional<SportEvent> findEventById(Long id);

    @Query(value = "Select * FROM sport_events s WHERE s.status= :status",
            nativeQuery = true)
    Optional<List<SportEvent>> findEventsByStatus(String status);

    @Query(value = "Select * FROM sport_events s WHERE s.sport= :sport",
            nativeQuery = true)
    Optional<List<SportEvent>> findEventsBySport(String sport);

    @Query(value = "Select * FROM sport_events s WHERE s.sport= :sport AND s.status=:status",
            nativeQuery = true)
    Optional<List<SportEvent>> findEventsByStatusAndSportType(String status, String sport);


}
