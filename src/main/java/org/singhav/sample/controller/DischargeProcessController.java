package org.singhav.sample.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.singhav.sample.model.PatientDischargeRequest;
import org.singhav.sample.service.PatientDischargeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/discharge/api")
public class DischargeProcessController {
    private final PatientDischargeService patientDischargeService;

    @PostMapping("/process")
    public String dischargePatient(@RequestBody PatientDischargeRequest request) {
        log.info("Received Request on {}", Thread.currentThread());
        return patientDischargeService.dischargePatient(request.patientId(), request.patientName());
    }
}
