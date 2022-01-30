package com.aleksandrs.gurcenko.EnlabsTakeHomeTask.service;

import com.aleksandrs.gurcenko.EnlabsTakeHomeTask.model.SportEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
public class Utilities {


    /**
     * Builds a URI for newly created sportEvent
     */
    public URI createURIForEvent(SportEvent sportEvent){
        return  URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/sport_events/sport_event").query("id={id}").buildAndExpand(sportEvent.getId()).toUriString());
    }
}
