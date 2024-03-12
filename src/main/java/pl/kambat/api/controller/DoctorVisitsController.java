package pl.kambat.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.kambat.api.dto.DoctorDTO;
import pl.kambat.api.dto.PatientVisitDTO;
import pl.kambat.api.dto.mapper.DoctorMapper;
import pl.kambat.api.dto.mapper.PatientVisitMapper;
import pl.kambat.business.DoctorService;
import pl.kambat.business.PatientVisitService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DoctorVisitsController {
    private static final String DOCTOR_VISITS = "/doctor/visits";
    private static final String DOCTOR_VISITS_OPENING = "/doctor/visit/open";
    private static final String DOCTOR_VISITS_SAVE = "/doctor/visit/save";

    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;
    private final PatientVisitService patientVisitService;
    private final PatientVisitMapper patientVisitMapper;

    @GetMapping(value = DOCTOR_VISITS)
    public String doctorVisitsPage(Model model) {
        DoctorDTO doctorDTO = doctorMapper.map(doctorService.getLoggedDoctor());
        List<PatientVisitDTO> patientVisitDTO = patientVisitService.findActiveDoctorVisits(doctorDTO.getDoctorId())
                .stream()
                .map(patientVisitMapper::map)
                .toList();

        model.addAttribute("patientVisitDTOs", patientVisitDTO);
        return "doctor/doctor_visits";
    }

    @PostMapping(value = DOCTOR_VISITS_OPENING)
    public String patientVisitOpening(
            @RequestParam("patientVisitId") Integer patientVisitID,
            ModelMap model
    ) {
        var patientVisitDTO = patientVisitMapper.map(patientVisitService.findPatientVisitById(patientVisitID));
        model.addAttribute("patientVisitDTO", patientVisitDTO);
        return "doctor/visit_open";
    }

    @PostMapping(value = DOCTOR_VISITS_SAVE)
    public String patientVisitUpdate(
            @ModelAttribute("patientVisitDTO") PatientVisitDTO patientVisitDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "error";
        }
        var existingPatientVisitDTO = patientVisitMapper.map(patientVisitService
                .findPatientVisitById(patientVisitDTO.getPatientVisitId()));

        existingPatientVisitDTO.setVisitNote(patientVisitDTO.getVisitNote());
        existingPatientVisitDTO.setVisitStatus(Boolean.FALSE);
        patientVisitService.save(patientVisitMapper.map(existingPatientVisitDTO));

        return "doctor/visit_save";
    }
}
