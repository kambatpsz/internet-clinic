package pl.kambat.integration.controler;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.kambat.api.controller.DoctorPanelController;
import pl.kambat.api.dto.DoctorDTO;
import pl.kambat.api.dto.mapper.DoctorMapper;
import pl.kambat.business.DoctorService;
import pl.kambat.domain.Doctor;
import pl.kambat.util.DataFixtures;

@WebMvcTest(DoctorPanelController.class)
@AutoConfigureMockMvc(addFilters = false)
class DoctorPanelControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DoctorService doctorService;
    @MockBean
    private DoctorMapper doctorMapper;

    @Test
    public void testDoctorPanelPage() throws Exception {

        //given
        Doctor mockDoctor = DataFixtures.someDoctor1();
        DoctorDTO mockDoctorDTO = DataFixtures.someDoctorDTO1();

        // when, then
        Mockito.when(doctorService.getLoggedDoctor()).thenReturn(mockDoctor);
        Mockito.when(doctorMapper.map(mockDoctor)).thenReturn(mockDoctorDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/doctor"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("doctorName", "Mirek"))
                .andExpect(MockMvcResultMatchers.model().attribute("doctorSurname", "Mirkowski"))
                .andExpect(MockMvcResultMatchers.view().name("doctor/doctor_panel"));
    }


}