package com.aleksandrs.gurcenko.EnlabsTakeHomeTask.controller;

import com.aleksandrs.gurcenko.EnlabsTakeHomeTask.model.SportEvent;
import com.aleksandrs.gurcenko.EnlabsTakeHomeTask.model.SportEventStatus;
import com.aleksandrs.gurcenko.EnlabsTakeHomeTask.service.SportEventService;
import com.aleksandrs.gurcenko.EnlabsTakeHomeTask.service.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sport_events")
@Slf4j
public class SportEventController {

    private final SportEventService sportEventService;
    private final Utilities utilities;

    @GetMapping("/all")
    public ResponseEntity<List<SportEvent>> getAll(){
            log.info("GET request for all sport events");
            return ResponseEntity
                    .ok()
                    .body(sportEventService.getSportEvents());
    }

    @GetMapping("/by_status")
    public ResponseEntity<List<SportEvent>> getAllByStatus(@RequestParam SportEventStatus status){
        log.info("GET request for events by status ["+status.name()+"]");
        return ResponseEntity
                .ok()
                .body(sportEventService.getSportEventsByStatus(status));
    }

    @GetMapping("/by_sport")
    public ResponseEntity<List<SportEvent>> getAllBySportType(@RequestParam String sport){
        log.info("GET request for events by sport ["+sport+"]");
        return ResponseEntity
                .ok()
                .body(sportEventService.getSportEventsBySportType(sport));
    }

    @GetMapping("/by_status_and_sport")
    public ResponseEntity<List<SportEvent>> getAllBySportTypeAndStatus(@RequestParam SportEventStatus status,@RequestParam String sport){
        log.info("GET request for events by status ["+status+"] and by sport ["+sport+"]");
        return ResponseEntity
                .ok()
                .body(sportEventService.getSportEventsByStatusAndSportType(status,sport));
    }


    @GetMapping("/sport_event")
    public ResponseEntity<SportEvent> getById(@RequestParam Long id){
        log.info("GET request for event with id ["+id+"]");
            return ResponseEntity
                    .ok()
                    .body(sportEventService.getEventById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<SportEvent> add(@RequestBody SportEvent sportEvent) {
        if(sportEvent.getId()!=null){
            sportEvent.setId(null);
        }
        log.info("POST request for event:["+sportEvent+"]");
        SportEvent created = sportEventService.addSportEvent(sportEvent);
        URI uri=utilities.createURIForEvent(created);
        return ResponseEntity
                .created(uri)
                .body(created);
    }

    @PutMapping("/update")
    public ResponseEntity<SportEvent> updateStatus(@RequestParam Long id, @RequestParam SportEventStatus status){
            log.info("Put request for event with id ["+id+"] to update status to ["+status+"]");
            SportEvent updated = sportEventService.updateSportEventStatus(id, status);
            return ResponseEntity
                    .ok()
                    .body(updated);
    }









}
