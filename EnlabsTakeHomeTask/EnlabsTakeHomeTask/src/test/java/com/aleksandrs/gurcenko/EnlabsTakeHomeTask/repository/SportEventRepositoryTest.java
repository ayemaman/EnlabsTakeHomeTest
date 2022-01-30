package com.aleksandrs.gurcenko.EnlabsTakeHomeTask.repository;

import com.aleksandrs.gurcenko.EnlabsTakeHomeTask.model.SportEvent;
import com.aleksandrs.gurcenko.EnlabsTakeHomeTask.model.SportEventStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SportEventRepositoryTest {

    @Autowired
    private SportEventRepository underTest;

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    @Test
    void testFindById() {
        //given
        SportEvent sportEvent=new SportEvent();
        sportEvent.setSport("football");
        sportEvent.setName("TEST CUP 2022");
        sportEvent.setUtcStartTime(Instant.now().plusSeconds(60 * 60));
        SportEvent event= underTest.save(sportEvent);
        //when
        Optional<SportEvent> event1= underTest.findEventById(event.getId());
        //then
        assertThat(event1.isPresent()).isTrue();
    }

    @Test
    void findEventsByStatus() {
        //given
        SportEvent sportEvent=new SportEvent();
        sportEvent.setSport("football");
        sportEvent.setName("TEST CUP 2022");
        sportEvent.setUtcStartTime(Instant.now().plusSeconds(60 * 60));
        underTest.save(sportEvent);

        sportEvent=new SportEvent();
        sportEvent.setSport("football");
        sportEvent.setName("TEST CUP 2022v1");
        sportEvent.setUtcStartTime(Instant.now().plusSeconds(60 * 60));
        sportEvent.setStatus(SportEventStatus.ACTIVE);
        underTest.save(sportEvent);

        sportEvent=new SportEvent();
        sportEvent.setSport("football");
        sportEvent.setName("TEST CUP 2022v2");
        sportEvent.setUtcStartTime(Instant.now().plusSeconds(60 * 60));
        sportEvent.setStatus(SportEventStatus.ACTIVE);
        underTest.save(sportEvent);

        //when
        Optional<List<SportEvent>> result=underTest.findEventsByStatus(SportEventStatus.ACTIVE.name());

        //then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().size()==2).isTrue();
        assertThat(result.get().get(0).getStatus()==SportEventStatus.ACTIVE).isTrue();
    }

    @Test
    void findEventsBySport() {
        //given
        SportEvent sportEvent=new SportEvent();
        sportEvent.setSport("FOOTBALL");
        sportEvent.setName("TEST CUP 2022");
        sportEvent.setUtcStartTime(Instant.now().plusSeconds(60 * 60));
        underTest.save(sportEvent);

        sportEvent=new SportEvent();
        sportEvent.setSport("football");
        sportEvent.setName("TEST CUP 2022v1");
        sportEvent.setUtcStartTime(Instant.now().plusSeconds(60 * 60));
        underTest.save(sportEvent);

        sportEvent=new SportEvent();
        sportEvent.setSport("basketball");
        sportEvent.setName("TEST CUP 2022v2");
        sportEvent.setUtcStartTime(Instant.now().plusSeconds(60 * 60));
        underTest.save(sportEvent);

        //when
        Optional<List<SportEvent>> result=underTest.findEventsBySport("FOOTBALL");

        //then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().size()==2).isTrue();
    }

    @Test
    void findEventsByStatusAndSportType() {
        //given
        SportEvent sportEvent=new SportEvent();
        sportEvent.setSport("FOOTBALL");
        sportEvent.setName("TEST CUP 2022");
        sportEvent.setUtcStartTime(Instant.now().plusSeconds(60 * 60));
        underTest.save(sportEvent);

        sportEvent=new SportEvent();
        sportEvent.setSport("football");
        sportEvent.setName("TEST CUP 2022v1");
        sportEvent.setUtcStartTime(Instant.now().plusSeconds(60 * 60));
        underTest.save(sportEvent);

        sportEvent=new SportEvent();
        sportEvent.setSport("football");
        sportEvent.setName("TEST CUP 2022v2");
        sportEvent.setStatus(SportEventStatus.ACTIVE);
        sportEvent.setUtcStartTime(Instant.now().plusSeconds(60 * 60));
        underTest.save(sportEvent);

        sportEvent=new SportEvent();
        sportEvent.setSport("basketball");
        sportEvent.setName("TEST CUP 2022v3");
        sportEvent.setUtcStartTime(Instant.now().plusSeconds(60 * 60));
        underTest.save(sportEvent);

        //when
        Optional<List<SportEvent>> result=underTest.findEventsByStatusAndSportType(SportEventStatus.INACTIVE.name(), "FOOTBALL");

        //then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().size()==2).isTrue();

    }


}