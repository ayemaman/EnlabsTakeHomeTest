package com.aleksandrs.gurcenko.EnlabsTakeHomeTask.service;

import com.aleksandrs.gurcenko.EnlabsTakeHomeTask.exception.*;
import com.aleksandrs.gurcenko.EnlabsTakeHomeTask.model.SportEvent;
import com.aleksandrs.gurcenko.EnlabsTakeHomeTask.model.SportEventStatus;
import com.aleksandrs.gurcenko.EnlabsTakeHomeTask.repository.SportEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class SportEventServiceTest {

    @Mock
    private SportEventRepository sportEventRepository;
    private SportEventService underTest;

    @BeforeEach
    void setUp() {
        underTest = new SportEventService(sportEventRepository);
    }

    @Test
    void testAddSportEvent() {
        //given
        SportEvent sportEvent=new SportEvent();
        sportEvent.setSport("football");
        sportEvent.setName("TEST CUP 2022");
        sportEvent.setUtcStartTime(Instant.now().plusSeconds(60 * 60));
        //when
        underTest.addSportEvent(sportEvent);
        //then
        ArgumentCaptor<SportEvent> sportEventArgumentCaptor=ArgumentCaptor.forClass(SportEvent.class);
        verify(sportEventRepository).save(sportEventArgumentCaptor.capture());
        SportEvent capturedSportEvent=sportEventArgumentCaptor.getValue();
        assertThat(capturedSportEvent).isEqualTo(sportEvent);
    }

    @Test
    void testGetSportEvents() {
        //when
        underTest.getSportEvents();
        //then
        verify(sportEventRepository).findAll();
    }

    @Test
    void testGetEventById() {
        SportEvent sportEvent=new SportEvent();
        sportEvent.setId(1L);
        sportEvent.setSport("football");
        sportEvent.setName("TEST CUP 2022");
        sportEvent.setUtcStartTime(Instant.now().plusSeconds(60 * 60));
        Optional<SportEvent> optionalSportEvent=Optional.of(sportEvent);

        given(sportEventRepository.findEventById(sportEvent.getId())).willReturn(optionalSportEvent);

        SportEvent sportEvent1= underTest.getEventById(1L);
        assertThat(sportEvent1.getName()).isSameAs(sportEvent.getName());


    }

    @Test
    void testGetEventByWrongId(){
        SportEvent sportEvent=new SportEvent();
        sportEvent.setId(1L);
        Optional<SportEvent> optionalEmptyEvent=Optional.empty();

        given(sportEventRepository.findEventById(sportEvent.getId())).willReturn(optionalEmptyEvent);

        assertThatThrownBy( () -> underTest.getEventById(1L)).isInstanceOf(SportEventWithIdNotFoundException.class)
                .hasMessage("Event with id "+sportEvent.getId()+" not found");


    }

    @Test
    void getSportEventsByStatus() {
        SportEvent sportEvent=new SportEvent();
        sportEvent.setSport("football");
        sportEvent.setName("TEST CUP 2022");
        sportEvent.setUtcStartTime(Instant.now().plusSeconds(60 * 60));
        SportEvent sportEvent2=new SportEvent();
        sportEvent2.setSport("football");
        sportEvent2.setName("TEST CUP 2022");
        sportEvent2.setUtcStartTime(Instant.now().plusSeconds(60 * 60));
        Optional<List<SportEvent>> optionalList=Optional.of(List.of(sportEvent,sportEvent2));

        given(sportEventRepository.findEventsByStatus(sportEvent.getStatus().name())).willReturn(optionalList);

        List<SportEvent> result=underTest.getSportEventsByStatus(sportEvent.getStatus());
        assertThat(result.size()).isSameAs(2);
        assertThat(result.get(0)).isSameAs(sportEvent);
    }

    @Test
    void getSportEventsByStatusWithNoEntries() {
        SportEvent sportEvent=new SportEvent();
        sportEvent.setSport("football");
        sportEvent.setName("TEST CUP 2022");
        sportEvent.setUtcStartTime(Instant.now().plusSeconds(60 * 60));

        Optional<List<SportEvent>> optionalList=Optional.empty();
        given(sportEventRepository.findEventsByStatus(sportEvent.getStatus().name())).willReturn(optionalList);


        assertThatThrownBy( () -> underTest.getSportEventsByStatus(sportEvent.getStatus())).isInstanceOf(NoSportEventsFoundByStatusException.class)
                .hasMessage("Could not retrieve events with status: "+sportEvent.getStatus());

    }



    @Test
    void getSportEventsBySportType() {
        SportEvent sportEvent=new SportEvent();
        sportEvent.setSport("football");
        sportEvent.setName("TEST CUP 2022");
        sportEvent.setUtcStartTime(Instant.now().plusSeconds(60 * 60));
        SportEvent sportEvent2=new SportEvent();
        sportEvent2.setSport("football");
        sportEvent2.setName("TEST CUP 2022");
        sportEvent2.setUtcStartTime(Instant.now().plusSeconds(60 * 60));
        Optional<List<SportEvent>> optionalList=Optional.of(List.of(sportEvent,sportEvent2));

        given(sportEventRepository.findEventsBySport(sportEvent.getSport())).willReturn(optionalList);

        List<SportEvent> result=underTest.getSportEventsBySportType(sportEvent.getSport());

        assertThat(result.size()).isSameAs(2);
        assertThat(result.get(0)).isSameAs(sportEvent);
    }

    @Test
    void getSportEventsBySportWithOptionalEmpty() {
        SportEvent sportEvent=new SportEvent();
        sportEvent.setSport("football");
        sportEvent.setName("TEST CUP 2022");
        sportEvent.setUtcStartTime(Instant.now().plusSeconds(60 * 60));

        Optional<List<SportEvent>> optionalList=Optional.empty();
        given(sportEventRepository.findEventsBySport(sportEvent.getSport())).willReturn(optionalList);
        assertThatThrownBy( () -> underTest.getSportEventsBySportType(sportEvent.getSport())).isInstanceOf(NoSportEventsFoundBySportException.class)
                .hasMessage("Could not retrieve events with sport type: "+sportEvent.getSport());

    }

    @Test
    void getSportEventsByStatusAndSportType() {
        SportEvent sportEvent=new SportEvent();
        sportEvent.setSport("football");
        sportEvent.setName("TEST CUP 2022");
        sportEvent.setUtcStartTime(Instant.now().plusSeconds(60 * 60));
        SportEvent sportEvent2=new SportEvent();
        sportEvent2.setSport("football");
        sportEvent2.setName("TEST CUP 2022");
        sportEvent2.setUtcStartTime(Instant.now().plusSeconds(60 * 60));
        Optional<List<SportEvent>> optionalList=Optional.of(List.of(sportEvent,sportEvent2));

        given(sportEventRepository.findEventsByStatusAndSportType(sportEvent.getStatus().name() ,sportEvent.getSport())).willReturn(optionalList);

        List<SportEvent> result=underTest.getSportEventsByStatusAndSportType(sportEvent.getStatus() ,sportEvent.getSport());

        assertThat(result.size()).isSameAs(2);
        assertThat(result.get(0)).isSameAs(sportEvent);
    }

    @Test
    void getSportEventsByStatusAndSportWithOptionalEmpty() {
        SportEvent sportEvent=new SportEvent();
        sportEvent.setSport("football");
        sportEvent.setName("TEST CUP 2022");
        sportEvent.setUtcStartTime(Instant.now().plusSeconds(60 * 60));

        Optional<List<SportEvent>> optionalList=Optional.empty();
        given(sportEventRepository.findEventsByStatusAndSportType(sportEvent.getStatus().name(),sportEvent.getSport())).willReturn(optionalList);


        assertThatThrownBy( () -> underTest.getSportEventsByStatusAndSportType(sportEvent.getStatus(),sportEvent.getSport()))
                .isInstanceOf(NoSportEventsFoundBySportAndStatusException.class)
                .hasMessage("Could not retrieve events with sport type: "+sportEvent.getSport()+" and status: "+sportEvent.getStatus());

    }

    @Test
    void testUpdateSportEventStatusToActive() {
        SportEvent sportEvent=new SportEvent();
        sportEvent.setId(1L);
        sportEvent.setSport("football");
        sportEvent.setName("TEST CUP 2022");
        sportEvent.setUtcStartTime(Instant.now().plusSeconds(60 * 60));
        Optional<SportEvent> optionalSportEvent=Optional.of(sportEvent);
        given(sportEventRepository.findEventById(sportEvent.getId())).willReturn(optionalSportEvent);
        underTest.updateSportEventStatus(sportEvent.getId(),SportEventStatus.ACTIVE);
        ArgumentCaptor<SportEvent> sportEventArgumentCaptor=ArgumentCaptor.forClass(SportEvent.class);
        verify(sportEventRepository).save(sportEventArgumentCaptor.capture());
        SportEvent capturedSportEvent=sportEventArgumentCaptor.getValue();
        assertThat(capturedSportEvent.getStatus()).isEqualTo(SportEventStatus.ACTIVE);

    }


    @Test
    void testUpdateSportEventStatusToActiveWhenInPast() {
        SportEvent sportEvent=new SportEvent();
        sportEvent.setId(1L);
        sportEvent.setSport("football");
        sportEvent.setName("TEST CUP 2022");
        sportEvent.setUtcStartTime(Instant.now().minusSeconds(60 * 60));
        Optional<SportEvent> optionalSportEvent=Optional.of(sportEvent);
        given(sportEventRepository.findEventById(sportEvent.getId())).willReturn(optionalSportEvent);

        assertThatThrownBy(()-> underTest.updateSportEventStatus(sportEvent.getId(), SportEventStatus.ACTIVE))
                .isInstanceOf(SportStatusChangeException.class)
                .hasMessage("Can't change from INACTIVE to ACTIVE. Start time has already passed (utcStartTime:"+sportEvent.getUtcStartTime()+")");
        verify(sportEventRepository,never()).save(any());
    }

    @Test
    void testUpdateSportEventStatusFromFinished() {
        SportEvent sportEvent=new SportEvent();
        sportEvent.setId(1L);
        sportEvent.setSport("football");
        sportEvent.setName("TEST CUP 2022");
        sportEvent.setStatus(SportEventStatus.FINISHED);
        sportEvent.setUtcStartTime(Instant.now().minusSeconds(60 * 60));
        Optional<SportEvent> optionalSportEvent=Optional.of(sportEvent);
        given(sportEventRepository.findEventById(sportEvent.getId())).willReturn(optionalSportEvent);

        assertThatThrownBy(()-> underTest.updateSportEventStatus(sportEvent.getId(), SportEventStatus.ACTIVE))
                .isInstanceOf(SportStatusChangeException.class)
                .hasMessage("Can't change from FINISHED to ACTIVE");

        assertThatThrownBy(()-> underTest.updateSportEventStatus(sportEvent.getId(), SportEventStatus.INACTIVE))
                .isInstanceOf(SportStatusChangeException.class)
                .hasMessage("Can't change from FINISHED to INACTIVE");
        verify(sportEventRepository,never()).save(any());

    }
}