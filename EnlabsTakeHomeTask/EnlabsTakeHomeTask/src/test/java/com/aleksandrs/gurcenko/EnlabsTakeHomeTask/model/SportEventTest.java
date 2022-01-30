package com.aleksandrs.gurcenko.EnlabsTakeHomeTask.model;

import com.aleksandrs.gurcenko.EnlabsTakeHomeTask.exception.SportStatusChangeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SportEventTest {


    private SportEvent sportEvent;
    private Instant time;

    @BeforeEach
    void setup() {
        sportEvent = new SportEvent();
        sportEvent.setSport("football");
        sportEvent.setName("TEST CUP 2022");
        time=Instant.now().plusSeconds(60 * 60);
        sportEvent.setUtcStartTime(time);
    }
    @Test
    void updateStatusFromInactiveToFinished() {
        assertThrows(SportStatusChangeException.class, () -> {
            sportEvent.updateStatus(SportEventStatus.FINISHED);
        });
    }

    @Test
    void updateStatusFromInactiveToActive() {
        sportEvent.setStatus(SportEventStatus.ACTIVE);
        assertThat(sportEvent.getStatus().equals(SportEventStatus.ACTIVE)).isTrue();
    }

    @Test
    void updateStatusFromInactiveToActiveStartTimeInThePast() {
        sportEvent.setUtcStartTime(Instant.now().minusSeconds(60*60*24));
        assertThrows(SportStatusChangeException.class, () -> {
            sportEvent.updateStatus(SportEventStatus.ACTIVE);
        });
    }

    @Test
    void updateStatusFromActiveToFinished() {
        sportEvent.setStatus(SportEventStatus.ACTIVE);
        sportEvent.updateStatus(SportEventStatus.FINISHED);
        assertThat(sportEvent.getStatus().equals(SportEventStatus.FINISHED)).isTrue();
    }

    @Test
    void updateStatusFromActiveToInactive() {
        sportEvent.setStatus(SportEventStatus.ACTIVE);
        assertThrows(SportStatusChangeException.class, () -> {
            sportEvent.updateStatus(SportEventStatus.INACTIVE);
        });
    }

    @Test
    void updateStatusFromFinishedToActive() {
        sportEvent.setStatus(SportEventStatus.FINISHED);
        assertThrows(SportStatusChangeException.class, () -> {
            sportEvent.updateStatus(SportEventStatus.ACTIVE);
        });
    }

    @Test
    void updateStatusFromFinishedToInactive() {
        sportEvent.setStatus(SportEventStatus.FINISHED);
        assertThrows(SportStatusChangeException.class, () -> {
            sportEvent.updateStatus(SportEventStatus.INACTIVE);
        });
    }





}