package pl.kambat.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.kambat.business.PatientService;
import pl.kambat.api.dto.PatientDTO;
import pl.kambat.api.dto.mapper.PatientMapper;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PatientPanelController {

    private static final String PATIENT = "/patient";

    private final PatientService patientService;
    private final PatientMapper patientMapper;

    @GetMapping(value = PATIENT)
    public String patientPanelPage(Model model) {
        PatientDTO patientDTO = patientMapper.map(patientService.getLoggedPatient());
        model.addAttribute("patientName", patientDTO.getName());
        model.addAttribute("patientSurname", patientDTO.getSurname());
        return "patient/patient_panel";
    }
}
