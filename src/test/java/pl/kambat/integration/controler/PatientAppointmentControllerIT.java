package pl.kambat.integration.controler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import pl.kambat.api.controller.PatientAppointmentController;
import pl.kambat.api.dto.DoctorAvailabilityDTO;
import pl.kambat.api.dto.DoctorDTO;
import pl.kambat.api.dto.PatientVisitDTO;
import pl.kambat.api.dto.mapper.DoctorAvailabilityMapper;
import pl.kambat.api.dto.mapper.DoctorMapper;
import pl.kambat.api.dto.mapper.PatientMapper;
import pl.kambat.api.dto.mapper.PatientVisitMapper;
import pl.kambat.business.DoctorAvailabilityService;
import pl.kambat.business.DoctorService;
import pl.kambat.business.PatientService;
import pl.kambat.business.PatientVisitService;
import pl.kambat.domain.Doctor;
import pl.kambat.domain.Patient;
import pl.kambat.domain.PatientVisit;
import pl.kambat.util.DataFixtures;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientAppointmentController.class)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(locations = "classpath:application-test.yml")
class PatientAppointmentControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DoctorAvailabilityService doctorAvailabilityService;

    @MockBean
    private DoctorAvailabilityMapper doctorAvailabilityMapper;

    @MockBean
    private DoctorMapper doctorMapper;

    @MockBean
    private PatientMapper patientMapper;

    @MockBean
    private DoctorService doctorService;

    @MockBean
    private PatientVisitService patientVisitService;

    @MockBean
    private PatientVisitMapper patientVisitMapper;

    @MockBean
    private PatientService patientService;

    @Test
    void doctorSchedule() throws Exception {
        //given
        Patient patient = DataFixtures.somePatient1();
        PatientVisit patientVisit = DataFixtures.somePatientVisit1();
        PatientVisitDTO patientVisitDTO = DataFixtures.somePatientVisitDTO1();
        Doctor doctor = DataFixtures.someDoctor1();
        DoctorDTO doctorDTO = DataFixtures.someDoctorDTO1();
        List<String> availableDoctorDTOs = List.of(
                doctor.getName() + " " +
                        doctor.getSurname() + " " +
                        doctor.getPwzNumber());

        // when, then
        when(patientService.getLoggedPatient()).thenReturn(patient);
        when(patientVisitService.findVisitsByPatientId(patient)).thenReturn(List.of(patientVisit));
        when(patientVisitMapper.map(patientVisit)).thenReturn(patientVisitDTO);
        when(doctorAvailabilityService.findDoctorsWithActiveAppointments()).thenReturn(List.of(doctor));
        when(doctorMapper.map(doctor)).thenReturn(doctorDTO);

        mockMvc.perform(get("/patient/appointment")
                        .flashAttr("availableDoctorS", availableDoctorDTOs)
                        .flashAttr("patientVisitDTOs", List.of(patientVisitDTO)))
                .andExpect(status().isOk())
                .andExpect(view().name("patient/patient_appointment"))
                .andExpect(model().attributeExists("availableDoctorS"))
                .andExpect(model().attribute("availableDoctorS", availableDoctorDTOs))
                .andExpect(model().attribute("patientVisitDTOs", List.of(patientVisitDTO)))
                .andExpect(model().size(2));

    }

    @Test
    void addPatientAppointment() throws Exception {
        // given
        Doctor doctor = DataFixtures.someDoctor1();
        Patient patient = DataFixtures.somePatient1();
        DoctorAvailabilityDTO doctorAvailabilityDTO = DataFixtures.someDoctorAvailabilityDTO1();

        when(doctorService.getDoctorByPwzNumber(any())).thenReturn(doctor);
        when(patientService.getLoggedPatient()).thenReturn(patient);

        // when, then
        mockMvc.perform(post("/patient/appointment/add")
                        .flashAttr("doctorAvailabilityDTO", doctorAvailabilityDTO)
                        .param("pwzNumber", doctor.getPwzNumber()))
                .andExpect(status().isOk())
                .andExpect(view().name("patient/patient_appointment"))
                .andExpect(model().attributeExists("availableDoctorS"))
                .andExpect(model().attributeExists("patientVisitDTOs"))
                .andExpect(model().attributeExists("doctorAvailabilityDTO"))
                .andExpect(model().size(3));
    }

    @Test
    void deletePatientAppointment() throws Exception {
        // given
        int patientVisitId = 1;
        PatientVisit patientVisit = DataFixtures.somePatientVisit1();

        //when, then
        when(patientVisitService.findPatientVisitById(patientVisitId)).thenReturn(patientVisit);

        mockMvc.perform(post("/patient/appointment/delete")
                        .param("patientVisitId", String.valueOf(patientVisitId)))
                .andExpect(status().isOk())
                .andExpect(view().name("patient/patient_appointment"))
                .andExpect(model().attributeExists("availableDoctorS"))
                .andExpect(model().attributeExists("patientVisitDTOs"))
                .andExpect(model().size(2));
    }
}