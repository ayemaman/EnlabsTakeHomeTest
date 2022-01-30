package com.aleksandrs.gurcenko.EnlabsTakeHomeTask.model;

import com.aleksandrs.gurcenko.EnlabsTakeHomeTask.exception.SportStatusChangeException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;


/**
 * SportEvent class contains two methods that can change the value of status variable:
 * setStatus(SportEventStatus status)  and updateStatus(SportEventStatus status).
 * updateStatus(SportEventStatus status) -> to be used for business logic
 * setStatus(SportEventStatus status) -> to be used for setup
 */

@Entity(name="SportEvent")
@Table(name="sport_events")
@NoArgsConstructor
@Getter
@Setter
@Slf4j
@ToString
public class SportEvent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String sport;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SportEventStatus status=SportEventStatus.INACTIVE;

    @Column(nullable = false)
    private Instant utcStartTime;

    public void setSport(String sport){
        this.sport=sport.toUpperCase();
    }

    /**
     * Event status change restrictions:
     *
     * >Can be changed from inactive to active
     *
     * >Can be changed from active to finished
     *
     * >Cannot activate an event if start_time is in the past
     *
     * >Finished could not be changed to any status
     *
     * >Inactive can not be changed to finished
     */

    public void updateStatus(SportEventStatus status){
        switch (this.status){
            case INACTIVE:
                if(status==SportEventStatus.FINISHED){
                    log.error("Can't change from "+ this.status + " to "+status+ " for event: ["+this.id+"] "+name);
                    throw new SportStatusChangeException("Can't change from "+ this.status + " to "+status);
                }else if(status==SportEventStatus.ACTIVE){
                   if(checkStartTimeOK()) {
                       log.info("Status successfully changed for event: ["+this.id+"] "+name+ " from "+this.status+ " to "+status);
                       this.status = status;

                   }else{
                       log.error("Can't change from "+ this.status + " to "+status+ " for event: ["+this.id+"] "+name+ ". Start time has already passed (utcStartTime:"+this.utcStartTime+")");
                       throw new SportStatusChangeException("Can't change from "+ this.status + " to "+status+". Start time has already passed (utcStartTime:"+this.utcStartTime+")");
                   }
                }
                break;
            case ACTIVE:
                if(status==SportEventStatus.INACTIVE){
                    log.error("Can't change from "+ this.status + " to "+status);
                    throw new SportStatusChangeException("Can't change from "+ this.status + " to "+status);
                }
                else if(status==SportEventStatus.FINISHED){
                    log.info("Status successfully changed for event: ["+this.id+"] "+name+ " from "+this.status+ " to "+status);
                    this.status=status;
                }
                break;
            case  FINISHED:
                if(status!=SportEventStatus.FINISHED) {
                    log.error("Can't change from "+ this.status + " to "+status);
                    throw new SportStatusChangeException("Can't change from " + this.status + " to " + status);
                }
        }
    }


    private boolean checkStartTimeOK() {
        return this.utcStartTime.isAfter(Instant.now()) ;
    }


}
