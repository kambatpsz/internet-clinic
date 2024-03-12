package pl.kambat.integration.controler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.kambat.api.controller.PatientPanelController;
import pl.kambat.api.dto.PatientDTO;
import pl.kambat.api.dto.mapper.PatientMapper;
import pl.kambat.business.PatientService;
import pl.kambat.domain.Patient;
import pl.kambat.util.DataFixtures;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientPanelController.class)
@AutoConfigureMockMvc(addFilters = false)
class PatientPanelControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @MockBean
    private PatientMapper patientMapper;


    @Test
    void patientPanelPage() throws Exception {
        // given
        PatientDTO patientDTO = DataFixtures.somePatientDTO1();
        Patient patient = DataFixtures.somePatient1();

        when(patientService.getLoggedPatient()).thenReturn(patient);
        when(patientMapper.map(patient)).thenReturn(patientDTO);

        // when, then
        mockMvc.perform(get("/patient"))
                .andExpect(status().isOk())
                .andExpect(view().name("patient/patient_panel"))
                .andExpect(model().attributeExists("patientName"))
                .andExpect(model().attribute("patientName", patientDTO.getName()))
                .andExpect(model().attributeExists("patientSurname"))
                .andExpect(model().attribute("patientSurname", patientDTO.getSurname()));
    }

}