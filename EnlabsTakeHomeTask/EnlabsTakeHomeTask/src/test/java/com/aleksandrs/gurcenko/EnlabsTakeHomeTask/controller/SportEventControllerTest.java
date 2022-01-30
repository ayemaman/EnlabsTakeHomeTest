package com.aleksandrs.gurcenko.EnlabsTakeHomeTask.controller;

import com.aleksandrs.gurcenko.EnlabsTakeHomeTask.exception.*;
import com.aleksandrs.gurcenko.EnlabsTakeHomeTask.model.SportEvent;
import com.aleksandrs.gurcenko.EnlabsTakeHomeTask.model.SportEventStatus;
import com.aleksandrs.gurcenko.EnlabsTakeHomeTask.service.SportEventService;
import com.aleksandrs.gurcenko.EnlabsTakeHomeTask.service.Utilities;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SportEventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SportEventService service;

    @MockBean
    private Utilities utilities;



    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void getAll() throws Exception {
        SportEvent sportEvent=new SportEvent();
        sportEvent.setId(1L);
        sportEvent.setSport("football");
        sportEvent.setName("TEST CUP 2022");
        Instant time=Instant.now().plusSeconds(60 * 60);
        sportEvent.setUtcStartTime(time);

        SportEvent sportEvent2=new SportEvent();
        sportEvent2.setId(2L);
        sportEvent2.setSport("football");
        sportEvent2.setName("TEST CUP 2022");
        sportEvent2.setStatus(SportEventStatus.ACTIVE);
        sportEvent2.setUtcStartTime(time);
        Mockito.when(service.getSportEvents()).thenReturn(List.of(sportEvent,sportEvent2));

        String get_url = "/api/sport_events/all";

        mockMvc.perform(get(get_url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].sport").value("FOOTBALL"))
                .andExpect(jsonPath("$[0].name").value("TEST CUP 2022"))
                .andExpect(jsonPath("$[0].status").value("INACTIVE"))
                .andExpect(jsonPath("$[0].utcStartTime").value(time.toString()))

                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].sport").value("FOOTBALL"))
                .andExpect(jsonPath("$[1].name").value("TEST CUP 2022"))
                .andExpect(jsonPath("$[1].status").value("ACTIVE"))
                .andExpect(jsonPath("$[1].utcStartTime").value(time.toString()));
    }

    @Test
    void getAllByStatus() throws Exception {
        SportEvent sportEvent=new SportEvent();
        sportEvent.setId(1L);
        sportEvent.setSport("football");
        sportEvent.setName("TEST CUP 2022");
        Instant time=Instant.now().plusSeconds(60 * 60);
        sportEvent.setUtcStartTime(time);

        SportEvent sportEvent2=new SportEvent();
        sportEvent2.setId(2L);
        sportEvent2.setSport("football");
        sportEvent2.setName("TEST CUP 2022");
        sportEvent2.setUtcStartTime(time);

        Mockito.when(service.getSportEventsByStatus(sportEvent.getStatus())).thenReturn(List.of(sportEvent,sportEvent2));
        String get_url = "/api/sport_events/by_status";

        mockMvc.perform(get(get_url)
                        .param("status",SportEventStatus.INACTIVE.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].sport").value("FOOTBALL"))
                .andExpect(jsonPath("$[0].name").value("TEST CUP 2022"))
                .andExpect(jsonPath("$[0].status").value("INACTIVE"))
                .andExpect(jsonPath("$[0].utcStartTime").value(time.toString()))

                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].sport").value("FOOTBALL"))
                .andExpect(jsonPath("$[1].name").value("TEST CUP 2022"))
                .andExpect(jsonPath("$[1].status").value("INACTIVE"))
                .andExpect(jsonPath("$[1].utcStartTime").value(time.toString()));
    }

    @Test
    void getAllByStatusNotFound() throws Exception {
        Mockito.when(service.getSportEventsByStatus(SportEventStatus.ACTIVE)).thenThrow(new NoSportEventsFoundByStatusException("test"));
        String get_url = "/api/sport_events/by_status";

        mockMvc.perform(get(get_url)
                        .param("status",SportEventStatus.ACTIVE.name()))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllBySportType() throws Exception {
        SportEvent sportEvent=new SportEvent();
        sportEvent.setId(1L);
        sportEvent.setSport("football");
        sportEvent.setName("TEST CUP 2022");
        Instant time=Instant.now().plusSeconds(60 * 60);
        sportEvent.setUtcStartTime(time);

        SportEvent sportEvent2=new SportEvent();
        sportEvent2.setId(2L);
        sportEvent2.setSport("football");
        sportEvent2.setName("TEST CUP 2022");
        sportEvent2.setUtcStartTime(time);

        Mockito.when(service.getSportEventsBySportType(sportEvent.getSport())).thenReturn(List.of(sportEvent,sportEvent2));
        String get_url = "/api/sport_events/by_sport";

        mockMvc.perform(get(get_url)
                        .param("sport",sportEvent.getSport()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].sport").value("FOOTBALL"))
                .andExpect(jsonPath("$[0].name").value("TEST CUP 2022"))
                .andExpect(jsonPath("$[0].status").value("INACTIVE"))
                .andExpect(jsonPath("$[0].utcStartTime").value(time.toString()))

                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].sport").value("FOOTBALL"))
                .andExpect(jsonPath("$[1].name").value("TEST CUP 2022"))
                .andExpect(jsonPath("$[1].status").value("INACTIVE"))
                .andExpect(jsonPath("$[1].utcStartTime").value(time.toString()));
    }

    @Test
    void getAllBySportTypeNotFound() throws Exception {

        Mockito.when(service.getSportEventsBySportType("football")).thenThrow(new NoSportEventsFoundBySportException("test"));
        String get_url = "/api/sport_events/by_sport";

        mockMvc.perform(get(get_url)
                        .param("sport","football"))
                .andExpect(status().isNoContent());

    }


    @Test
    void getAllBySportTypeAndStatus() throws Exception {
        SportEvent sportEvent=new SportEvent();
        sportEvent.setId(1L);
        sportEvent.setSport("football");
        sportEvent.setName("TEST CUP 2022");
        Instant time=Instant.now().plusSeconds(60 * 60);
        sportEvent.setUtcStartTime(time);

        SportEvent sportEvent2=new SportEvent();
        sportEvent2.setId(2L);
        sportEvent2.setSport("football");
        sportEvent2.setName("TEST CUP 2022");
        sportEvent2.setUtcStartTime(time);

        Mockito.when(service.getSportEventsByStatusAndSportType(sportEvent.getStatus(),sportEvent.getSport())).thenReturn(List.of(sportEvent,sportEvent2));
        String get_url = "/api/sport_events/by_status_and_sport";

        mockMvc.perform(get(get_url)
                        .param("sport",sportEvent.getSport())
                        .param("status", sportEvent.getStatus().name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].sport").value("FOOTBALL"))
                .andExpect(jsonPath("$[0].name").value("TEST CUP 2022"))
                .andExpect(jsonPath("$[0].status").value("INACTIVE"))
                .andExpect(jsonPath("$[0].utcStartTime").value(time.toString()))

                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].sport").value("FOOTBALL"))
                .andExpect(jsonPath("$[1].name").value("TEST CUP 2022"))
                .andExpect(jsonPath("$[1].status").value("INACTIVE"))
                .andExpect(jsonPath("$[1].utcStartTime").value(time.toString()));
    }

    @Test
    void getAllBySportTypeAndStatusNotFound() throws Exception {


        Mockito.when(service.getSportEventsByStatusAndSportType(SportEventStatus.ACTIVE,"football")).thenThrow(new NoSportEventsFoundBySportAndStatusException("test"));
        String get_url = "/api/sport_events/by_status_and_sport";

        mockMvc.perform(get(get_url)
                        .param("sport","football")
                        .param("status", SportEventStatus.ACTIVE.name()))
                .andExpect(status().isNoContent());
    }

    @Test
    void getById() throws Exception {
        SportEvent sportEvent=new SportEvent();
        sportEvent.setId(1L);
        sportEvent.setSport("football");
        sportEvent.setName("TEST CUP 2022");
        Instant time=Instant.now().plusSeconds(60 * 60);
        sportEvent.setUtcStartTime(time);

        Mockito.when(service.getEventById(sportEvent.getId())).thenReturn(sportEvent);
        String get_url = "/api/sport_events/sport_event/";

        mockMvc.perform(get(get_url)
                        .param("id",sportEvent.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.sport").value("FOOTBALL"))
                .andExpect(jsonPath("$.name").value("TEST CUP 2022"))
                .andExpect(jsonPath("$.status").value("INACTIVE"))
                .andExpect(jsonPath("$.utcStartTime").value(time.toString()));

    }

    @Test
    void getByIdNotFound() throws Exception {
        Mockito.when(service.getEventById(1L)).thenThrow(new SportEventWithIdNotFoundException("test"));
        String get_url = "/api/sport_events/sport_event/";

        mockMvc.perform(get(get_url)
                        .param("id","1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void add() throws Exception {
        SportEvent sportEvent=new SportEvent();
        sportEvent.setSport("football");
        sportEvent.setName("TEST CUP 2022");
        Instant time=Instant.now().plusSeconds(60 * 60);
        sportEvent.setUtcStartTime(time);

        SportEvent savedSportEvent=new SportEvent();
        savedSportEvent.setId(1L);
        savedSportEvent.setSport("football");
        savedSportEvent.setName("TEST CUP 2022");
        savedSportEvent.setUtcStartTime(time);
        Mockito.when(service.addSportEvent(any())).thenReturn(savedSportEvent);
        Mockito.when(utilities.createURIForEvent(savedSportEvent)).thenReturn(URI.create(""));
        String post_url = "/api/sport_events/add";
        mockMvc.perform(post(post_url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(sportEvent))
                    )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.sport").value("FOOTBALL"))
                .andExpect(jsonPath("$.name").value("TEST CUP 2022"))
                .andExpect(jsonPath("$.status").value("INACTIVE"))
                .andExpect(jsonPath("$.utcStartTime").value(time.toString()));;

    }

    @Test
    void updateStatus() throws Exception {
        SportEvent sportEvent=new SportEvent();
        sportEvent.setId(1L);
        sportEvent.setSport("football");
        sportEvent.setName("TEST CUP 2022");
        Instant time=Instant.now().plusSeconds(60 * 60);
        sportEvent.setUtcStartTime(time);

        SportEvent updatedSportEvent=new SportEvent();
        updatedSportEvent.setId(1L);
        updatedSportEvent.setSport("football");
        updatedSportEvent.setName("TEST CUP 2022");
        updatedSportEvent.setUtcStartTime(time);
        updatedSportEvent.setStatus(SportEventStatus.ACTIVE);

        Mockito.when(service.updateSportEventStatus(sportEvent.getId(), SportEventStatus.ACTIVE)).thenReturn(updatedSportEvent);
        String put_url = "/api/sport_events/update";
        mockMvc.perform(put(put_url)
                    .contentType("application/json")
                    .param("id",updatedSportEvent.getId().toString())
                    .param("status",SportEventStatus.ACTIVE.name())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.sport").value("FOOTBALL"))
                .andExpect(jsonPath("$.name").value("TEST CUP 2022"))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.utcStartTime").value(time.toString()));
    }

    @Test
    void updateStatusWrong() throws Exception {
        SportEvent sportEvent=new SportEvent();
        sportEvent.setId(1L);
        sportEvent.setSport("football");
        sportEvent.setName("TEST CUP 2022");
        Instant time=Instant.now().plusSeconds(60 * 60);
        sportEvent.setUtcStartTime(time);

        SportEvent updatedSportEvent=new SportEvent();
        updatedSportEvent.setId(1L);
        updatedSportEvent.setSport("football");
        updatedSportEvent.setName("TEST CUP 2022");
        updatedSportEvent.setUtcStartTime(time);
        updatedSportEvent.setStatus(SportEventStatus.ACTIVE);

        Mockito.when(service.updateSportEventStatus(sportEvent.getId(), SportEventStatus.ACTIVE)).thenThrow(new SportStatusChangeException("test"));
        String put_url = "/api/sport_events/update";
        mockMvc.perform(put(put_url)
                        .contentType("application/json")
                        .param("id",updatedSportEvent.getId().toString())
                        .param("status",SportEventStatus.ACTIVE.name())
                )
                .andExpect(status().isForbidden());
    }



}