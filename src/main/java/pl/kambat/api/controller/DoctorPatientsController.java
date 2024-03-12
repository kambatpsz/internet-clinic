package pl.kambat.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.kambat.api.dto.DoctorDTO;
import pl.kambat.business.PatientService;
import pl.kambat.api.dto.PatientDTO;
import pl.kambat.api.dto.PatientVisitDTO;
import pl.kambat.api.dto.mapper.DoctorMapper;
import pl.kambat.api.dto.mapper.PatientMapper;
import pl.kambat.api.dto.mapper.PatientVisitMapper;
import pl.kambat.business.DoctorService;
import pl.kambat.business.PatientVisitService;

import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DoctorPatientsController {
    private static final String DOCTOR_PATIENTS = "/doctor/patients";
    private static final String PATIENT_DETAILS = "/doctor/patient/details";

    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;
    private final PatientService patientService;
    private final PatientMapper patientMapper;
    private final PatientVisitService patientVisitService;
    private final PatientVisitMapper patientVisitMapper;

    @GetMapping(value = DOCTOR_PATIENTS)
    public String doctorPatientsPage(Model model) {
        DoctorDTO doctorDTO = doctorMapper.map(doctorService.getLoggedDoctor());
        List<PatientVisitDTO> patientsVisitDTO = patientVisitService.findVisitsByDoctorId(doctorMapper.map(doctorDTO))
                .stream()
                .map(patientVisitMapper::map)
                .toList();
        var doctorPatients = patientsVisitDTO.stream()
                .map(PatientVisitDTO::getPatient)
                .distinct()
                .map(patientMapper::map)
                .toList();

        model.addAttribute("doctorPatientsDTOs", doctorPatients);
        return "doctor/my_patients";
    }

    @PostMapping(value = PATIENT_DETAILS)
    public String patientDetails(
            @ModelAttribute("patientDTO") PatientDTO patientDTO,
            ModelMap model
    ) {
        var patient = patientService.findPatientsByPesel(patientDTO.getPesel());
        var patientVisitDTOs = patientVisitService.findVisitsByPatientId(patient).stream()
                .map(patientVisitMapper::map)
                .sorted(Comparator.comparing(PatientVisitDTO::getDateTimeVisit))
                .toList();

        model.addAttribute("patientDTO", patientDTO);
        model.addAttribute("patientVisitsDTOs", patientVisitDTOs);

        return "doctor/patient_details";
    }
}
