package com.aleksandrs.gurcenko.EnlabsTakeHomeTask.service;

import com.aleksandrs.gurcenko.EnlabsTakeHomeTask.exception.NoSportEventsFoundBySportAndStatusException;
import com.aleksandrs.gurcenko.EnlabsTakeHomeTask.exception.NoSportEventsFoundBySportException;
import com.aleksandrs.gurcenko.EnlabsTakeHomeTask.exception.NoSportEventsFoundByStatusException;
import com.aleksandrs.gurcenko.EnlabsTakeHomeTask.exception.SportEventWithIdNotFoundException;
import com.aleksandrs.gurcenko.EnlabsTakeHomeTask.model.SportEvent;
import com.aleksandrs.gurcenko.EnlabsTakeHomeTask.model.SportEventStatus;
import com.aleksandrs.gurcenko.EnlabsTakeHomeTask.repository.SportEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SportEventService {

    private final SportEventRepository sportEventRepository;


    public SportEvent addSportEvent(SportEvent sportEvent){
        return sportEventRepository.save(sportEvent);
    }

    public List<SportEvent> getSportEvents(){
        return sportEventRepository.findAll();
    }

    public SportEvent getEventById(Long id){
        return sportEventRepository.findEventById(id)
                .orElseThrow(() -> new SportEventWithIdNotFoundException("Event with id "+id+" not found"));
    }

    public List<SportEvent> getSportEventsByStatus(SportEventStatus status){
        return sportEventRepository.findEventsByStatus(status.name())
                .orElseThrow(()-> new NoSportEventsFoundByStatusException("No events found with status "+status));
    }

    public List<SportEvent> getSportEventsBySportType(String sportType){
        return sportEventRepository.findEventsBySport(sportType.toUpperCase())
                .orElseThrow(()-> new NoSportEventsFoundBySportException("No events found with sport type: "+sportType));
    }


    public List<SportEvent> getSportEventsByStatusAndSportType(SportEventStatus status, String sportType){
        return sportEventRepository.findEventsByStatusAndSportType(status.name(),sportType.toUpperCase())
                .orElseThrow(()-> new NoSportEventsFoundBySportAndStatusException("No events found with sport type: "+sportType+" and status: "+status));
    }

    public SportEvent updateSportEventStatus(Long id, SportEventStatus status){
        SportEvent eventFromDB= getEventById(id);
        eventFromDB.updateStatus(status);
        return sportEventRepository.save(eventFromDB);
    }


}
