package pl.kambat.integration.controler;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import pl.kambat.api.controller.DoctorVisitsController;
import pl.kambat.api.dto.DoctorDTO;
import pl.kambat.api.dto.PatientVisitDTO;
import pl.kambat.api.dto.mapper.DoctorMapper;
import pl.kambat.api.dto.mapper.PatientVisitMapper;
import pl.kambat.business.DoctorService;
import pl.kambat.business.PatientVisitService;
import pl.kambat.domain.Doctor;
import pl.kambat.domain.PatientVisit;
import pl.kambat.util.DataFixtures;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DoctorVisitsController.class)
@AutoConfigureMockMvc(addFilters = false)
class DoctorVisitsControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DoctorService doctorService;
    @MockBean
    private DoctorMapper doctorMapper;
    @MockBean
    private PatientVisitService patientVisitService;
    @MockBean
    private PatientVisitMapper patientVisitMapper;

    @Test
    void doctorVisitsPage() throws Exception {
        //given
        Doctor doctor = DataFixtures.someDoctor1();
        DoctorDTO doctorDTO = DataFixtures.someDoctorDTO1();
        List<PatientVisit> patientVisitList = List.of(DataFixtures.somePatientVisit1());

        when(doctorService.getLoggedDoctor()).thenReturn(doctor);
        when(patientVisitService.findActiveDoctorVisits(doctor.getDoctorId())).thenReturn(patientVisitList);
        when(doctorMapper.map(doctor)).thenReturn(doctorDTO);

        // When, Then
        mockMvc.perform(get("/doctor/visits"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("patientVisitDTOs"))
                .andExpect(view().name("doctor/doctor_visits"));

    }

//    @Test
//    void postPatientVisitOpening() throws Exception {
//        // given
//        PatientVisit patientVisit = DataFixtures.somePatientVisit1().withPatientVisitId(1);
//        PatientVisitDTO patientVisitDTO = DataFixtures.somePatientVisitDTO1();
//        patientVisitDTO.setPatientVisitId(1);
//
//        Mockito.when(patientVisitService.findPatientVisitById(any())).thenReturn(patientVisit);
//        Mockito.when(patientVisitMapper.map(patientVisit)).thenReturn(patientVisitDTO);
//
//
//        // when, then
////        mockMvc.perform(post("/doctor/visits/opening")
////                        .param("patientVisitId", String.valueOf(1)))
//////                .flashAttr("patientVisitDTO", patientVisitDTO))
//////                .andExpect(status().isOk())
////                .andExpect(model().attributeExists("patientVisitId"))
////                .andExpect(model().attributeExists("patientVisitDTO"))
////                .andExpect(view().name("doctor/visit_open"));
//        // when, then
////        mockMvc.perform(post("/doctor/visits/opening")
////                        .param("patientVisitId", String.valueOf(1)))
////                .andExpect(status().isOk())
////                .andExpect(model().attributeExists("patientVisitDTO"))
////                .andExpect(view().name("doctor/visit_open"));
//        ResultActions resultActions = mockMvc.perform(post("/doctor/visits/opening")
//                        .param("patientVisitId", String.valueOf(1)))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("patientVisitDTO"))
//                .andExpect(view().name("doctor/visit_open"));
//
//        // Dodaj informacje do konsoli debugowej
//        MvcResult result = resultActions.andReturn();
//        System.out.println("Model: " + result.getModelAndView().getModel());
//        System.out.println("View: " + result.getModelAndView().getViewName());
//    }

//    @Test
//    void patientVisitUpdate() throws Exception {
//        // given
//        PatientVisit patientVisit = DataFixtures.somePatientVisit1().withPatientVisitId(1);
//        PatientVisitDTO patientVisitDTO = DataFixtures.somePatientVisitDTO1();
//        patientVisitDTO.setPatientVisitId(1);
//
//        when(patientVisitService.findPatientVisitById(patientVisitDTO.getPatientVisitId()))
//                .thenReturn(DataFixtures.somePatientVisit1());
//        when(patientVisitMapper.map(patientVisitDTO)).thenReturn(patientVisit);
//        when(patientVisitMapper.map(patientVisit)).thenReturn(patientVisitDTO);
////        when(patientVisitService.save(patientVisit)).then(any());
//
//
//        // when, then
//        mockMvc.perform(post("/doctor/visits/save")
//                        .param("patientVisitId", String.valueOf(patientVisitDTO.getPatientVisitId()))
//                        .flashAttr("patientVisitDTO", patientVisitDTO))
//                .andExpect(status().isOk())
//                .andExpect(view().name("doctor/visit_save"));
//    }

}