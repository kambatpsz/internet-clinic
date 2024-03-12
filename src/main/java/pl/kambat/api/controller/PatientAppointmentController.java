package pl.kambat.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.kambat.api.dto.PatientVisitDTO;
import pl.kambat.api.dto.mapper.DoctorMapper;
import pl.kambat.business.DoctorService;
import pl.kambat.business.PatientService;
import pl.kambat.business.PatientVisitService;
import pl.kambat.domain.DoctorAvailability;
import pl.kambat.domain.PatientVisit;
import pl.kambat.api.dto.DoctorAvailabilityDTO;
import pl.kambat.api.dto.mapper.DoctorAvailabilityMapper;
import pl.kambat.api.dto.mapper.PatientVisitMapper;
import pl.kambat.business.DoctorAvailabilityService;
import pl.kambat.domain.Doctor;
import pl.kambat.domain.Patient;
import pl.kambat.domain.exception.ProcessingDataException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class PatientAppointmentController {
    private static final String PATIENT_APPOINTMENT = "/patient/appointment";
    private static final String PATIENT_APPOINTMENT_ADD = "/patient/appointment/add";
    private static final String PATIENT_APPOINTMENT_DELETE = "/patient/appointment/delete";

    private final DoctorAvailabilityService doctorAvailabilityService;
    private final DoctorAvailabilityMapper doctorAvailabilityMapper;
    private final DoctorMapper doctorMapper;
    private final DoctorService doctorService;
    private final PatientVisitService patientVisitService;
    private final PatientVisitMapper patientVisitMapper;
    private final PatientService patientService;

    @GetMapping(value = PATIENT_APPOINTMENT)
    public ModelAndView doctorSchedule(
    ) {
        Patient patient = patientService.getLoggedPatient();
        List<PatientVisitDTO> patientVisitDTOs = patientVisitService.findVisitsByPatientId(patient)
                .stream()
                .filter(pV -> pV.getVisitStatus().equals(Boolean.TRUE))
                .map(patientVisitMapper::map)
                .toList();

        Map<String, ?> model = Map.of(
                "availableDoctorS", getAvailableDoctorDTO(),
                "patientVisitDTOs", patientVisitDTOs
        );
        return new ModelAndView("patient/patient_appointment", model);
    }

    private List<String> getAvailableDoctorDTO() {

        return doctorAvailabilityService.findDoctorsWithActiveAppointments().stream()
                .map(doctorMapper::map)
                .map(doctor -> doctor.getName() + " " + doctor.getSurname() + " " + doctor.getPwzNumber())
                .toList();
    }

    @PostMapping(value = PATIENT_APPOINTMENT_ADD)
    public ModelAndView addPatientAppointment(
            @ModelAttribute("doctorAvailabilityDTO") DoctorAvailabilityDTO doctorAvailabilityDTO,
            @RequestParam("pwzNumber") String pwzNumber
    ) {
        Doctor doctor = doctorService.getDoctorByPwzNumber(pwzNumber);
        Patient patient = patientService.getLoggedPatient();

        doctorAvailabilityDTO.setDoctor(doctor);
        doctorAvailabilityDTO.setReserved(Boolean.TRUE);
        doctorAvailabilityService.changeStatusDoctorAvailability(doctorAvailabilityMapper.map(doctorAvailabilityDTO));

        PatientVisit patientVisit = PatientVisit.builder()
                .dateTimeVisit(LocalDateTime.of(doctorAvailabilityDTO.getDate(), doctorAvailabilityDTO.getHourFrom()))
                .visitStatus(Boolean.TRUE)
                .patient(patient)
                .doctor(doctor)
                .build();
        patientVisitService.save(patientVisit);

        return doctorSchedule();
    }

    @PostMapping(value = PATIENT_APPOINTMENT_DELETE)
    public ModelAndView deletePatientAppointment(
            @RequestParam("patientVisitId") Integer patientVisitId
    ) {

        if (patientVisitId == null) {
            throw new ProcessingDataException("Missing or invalid patientVisitId");
        }

        PatientVisit patientVisit = patientVisitService.findPatientVisitById(patientVisitId);

        PatientVisit patientVisitWithNewStatus = patientVisit
                .withVisitStatus(Boolean.FALSE)
                .withDateOfCancellation(LocalDateTime.now().withNano(0))
                .withVisitNote("Appointment cancelled by patient [" + LocalDateTime.now().withNano(0) + "]");

        patientVisitService.save(patientVisitWithNewStatus);

        var doctorAvailability =
                doctorAvailabilityService.findDoctorAvailabilityByDate(
                        patientVisit.getDoctor(),
                        patientVisit.getDateTimeVisit()
                );


        if (doctorAvailability != null) {
            DoctorAvailability doctorAvailabilityWithNewStatus = doctorAvailability.withReserved(Boolean.FALSE);
            doctorAvailabilityService.changeStatusDoctorAvailability(doctorAvailabilityWithNewStatus);
        }

        return doctorSchedule();
    }
}
