package org.singhav.sample.handler;

import lombok.extern.slf4j.Slf4j;
import org.singhav.sample.event.PatientDischargeEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BillingServiceHandler {

    @Async
    @EventListener
    public void processBill(PatientDischargeEvent event) {
        log.info("Billing Service: Finalizing bill for patient {} using {}", event.getPatientId(), Thread.currentThread());
    }
}
