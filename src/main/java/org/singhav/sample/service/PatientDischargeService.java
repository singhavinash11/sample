package org.singhav.sample.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.singhav.sample.event.PatientDischargeEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientDischargeService {
    private final ApplicationEventPublisher eventPublisher;

    public String dischargePatient(String patientId, String patientName) {
        log.info("patient discharge process initiated for {} using {}", patientName, Thread.currentThread());
        eventPublisher.publishEvent(new PatientDischargeEvent(this, patientId, patientName));
        log.info("patient discharge process completed for {} using {}", patientName, Thread.currentThread());
        return "Patient " + patientName + " with ID " + patientId + " discharged successfully!";
    }
}
