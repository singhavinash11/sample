package org.singhav.sample.handler;

import lombok.extern.slf4j.Slf4j;
import org.singhav.sample.event.PatientDischargeEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HousekeepingServiceHandler {

    @Async
    @EventListener
    public void cleanAndAssign(PatientDischargeEvent event) {
        log.info("Housekeeping Service: Preparing room for next patient after discharge of {} using {}", event.getPatientName(), Thread.currentThread());
    }
}
