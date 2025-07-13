package org.singhav.sample.handler;

import lombok.extern.slf4j.Slf4j;
import org.singhav.sample.event.PatientDischargeEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationServiceHandler {

    @Async
    @EventListener
    public void notifyPatients(PatientDischargeEvent event) {
        log.info("Notification Service: Sending discharge notification for patient {} using {}", event.getPatientName(), Thread.currentThread());
    }
}
