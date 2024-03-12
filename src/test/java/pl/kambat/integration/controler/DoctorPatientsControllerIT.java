package pl.kambat.integration.controler;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.kambat.api.controller.DoctorPatientsController;
import pl.kambat.api.dto.DoctorDTO;
import pl.kambat.api.dto.PatientDTO;
import pl.kambat.api.dto.PatientVisitDTO;
import pl.kambat.api.dto.mapper.DoctorMapper;
import pl.kambat.api.dto.mapper.PatientMapper;
import pl.kambat.api.dto.mapper.PatientVisitMapper;
import pl.kambat.business.DoctorService;
import pl.kambat.business.PatientService;
import pl.kambat.business.PatientVisitService;
import pl.kambat.domain.Doctor;
import pl.kambat.domain.Patient;
import pl.kambat.domain.PatientVisit;
import pl.kambat.util.DataFixtures;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DoctorPatientsController.class)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(locations = "classpath:application-test.yml")
class DoctorPatientsControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DoctorService doctorService;

    @MockBean
    private PatientService patientService;

    @MockBean
    private DoctorMapper doctorMapper;

    @MockBean
    private PatientVisitService patientVisitService;

    @MockBean
    private PatientVisitMapper patientVisitMapper;

    @MockBean
    private PatientMapper patientMapper;

    @Test
    void doctorPatientsPage() throws Exception {
        //given
        DoctorDTO doctorDTO = DataFixtures.someDoctorDTO1();
        Doctor doctor = DataFixtures.someDoctor1();
        PatientVisitDTO patientVisitDTO = DataFixtures.somePatientVisitDTO1();
        PatientVisit patientVisit = DataFixtures.somePatientVisit1();
        PatientDTO patientDTO = DataFixtures.somePatientDTO1();


        // when, then
        Mockito.when(doctorService.getLoggedDoctor()).thenReturn(doctor);
        Mockito.when(doctorMapper.map(doctor)).thenReturn(doctorDTO);
        Mockito.when(patientVisitService.findVisitsByDoctorId(doctor))
                .thenReturn(List.of(patientVisit));
        Mockito.when(patientVisitMapper.map(patientVisitDTO))
                .thenReturn(patientVisit);
        Mockito.when(patientMapper.map(patientVisitDTO.getPatient()))
                .thenReturn(patientDTO);


        mockMvc.perform(MockMvcRequestBuilders.get("/doctor/patients"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("doctorPatientsDTOs"))
                .andExpect(view().name("doctor/my_patients"));

    }

    @Test
    void patientDetails() throws Exception {
        //given
        PatientDTO patientDTO = DataFixtures.somePatientDTO1();
        Patient patient = DataFixtures.somePatient1();
        PatientVisit patientVisit = DataFixtures.somePatientVisit1();
        PatientVisitDTO patientVisitDTO = DataFixtures.somePatientVisitDTO1();
        // when, then
        Mockito.when(patientService.findPatientsByPesel(patientDTO.getPesel()))
                .thenReturn(patient);

        Mockito.when(patientVisitService.findVisitsByPatientId(patient))
                .thenReturn(List.of(patientVisit));

        Mockito.when(patientVisitMapper.map(patientVisit))
                .thenReturn(patientVisitDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/doctor/patient/details")
                        .flashAttr("patientDTO", patientDTO)
                        .flashAttr("patientVisitsDTOs", List.of(patientVisitDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("patientDTO", patientDTO))
                .andExpect(MockMvcResultMatchers.model().attribute("patientVisitsDTOs", List.of(patientVisitDTO)))
                .andExpect(MockMvcResultMatchers.view().name("doctor/patient_details"));
    }
}