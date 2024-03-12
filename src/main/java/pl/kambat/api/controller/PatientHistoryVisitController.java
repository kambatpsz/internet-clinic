package pl.kambat.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import pl.kambat.api.dto.PatientVisitDTO;
import pl.kambat.business.PatientService;
import pl.kambat.api.dto.PatientDTO;
import pl.kambat.api.dto.mapper.PatientMapper;
import pl.kambat.api.dto.mapper.PatientVisitMapper;
import pl.kambat.business.PatientVisitService;

import java.util.Comparator;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PatientHistoryVisitController {
    private static final String PATIENT_VISITS_HISTORY = "/patient/visit/history";

    private final PatientService patientService;
    private final PatientMapper patientMapper;
    private final PatientVisitService patientVisitService;
    private final PatientVisitMapper patientVisitMapper;

    @GetMapping(value = PATIENT_VISITS_HISTORY)
    public String PatientDetails(
            ModelMap model
    ) {
        PatientDTO patientDTO = patientMapper.map(patientService.getLoggedPatient());
        var patient = patientService.findPatientsByPesel(patientDTO.getPesel());
        var patientVisitDTOs = patientVisitService.findVisitsByPatientId(patient).stream()
                .map(patientVisitMapper::map)
                .sorted(Comparator.comparing(PatientVisitDTO::getDateTimeVisit))
                .toList();

        model.addAttribute("patientDTO", patientDTO);
        model.addAttribute("patientVisitsDTOs", patientVisitDTOs);

        return "patient/visit_history";
    }

}
