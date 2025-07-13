package org.singhav.sample.handler;

import lombok.extern.slf4j.Slf4j;
import org.singhav.sample.event.PatientDischargeEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MedicalRecordsServiceHandler {

    @Async
    @EventListener
    public void updatePatientHistory(PatientDischargeEvent event) {
        log.info("Medical Records Service: Updating records for patient {} using {}", event.getPatientId(), Thread.currentThread());
    }
}
