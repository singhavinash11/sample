package org.singhav.sample.event;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.context.ApplicationEvent;

@Value
@EqualsAndHashCode(callSuper = true)
public class PatientDischargeEvent extends ApplicationEvent {
    String patientId;
    String patientName;

    public PatientDischargeEvent(Object source, String patientId, String patientName) {
        super(source);
        this.patientId = patientId;
        this.patientName = patientName;
    }
}
