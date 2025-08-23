package org.singhav.sample.controller;

import lombok.RequiredArgsConstructor;
import org.singhav.sample.model.patient.PatientDischargeRequest;
import org.singhav.sample.service.PatientDischargeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/discharge/api")
public class DischargeProcessController {
    private final PatientDischargeService patientDischargeService;

    @PostMapping("/process")
    public String dischargePatient(@RequestBody PatientDischargeRequest request) {
        return patientDischargeService.dischargePatient(request.patientId(), request.patientName());
    }
}
