package org.singhav.sample.service;

import lombok.RequiredArgsConstructor;
import org.singhav.sample.event.PatientDischargeEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientDischargeService {
    private final ApplicationEventPublisher eventPublisher;

    public String dischargePatient(String patientId, String patientName) {
        eventPublisher.publishEvent(new PatientDischargeEvent(this, patientId, patientName));
        return "Patient " + patientName + " with ID " + patientId + " discharged successfully!";
    }
}
