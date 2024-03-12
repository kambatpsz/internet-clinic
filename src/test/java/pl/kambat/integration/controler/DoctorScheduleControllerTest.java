package pl.kambat.integration.controler;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.kambat.api.controller.DoctorScheduleController;
import pl.kambat.api.dto.DoctorAvailabilityDTO;
import pl.kambat.api.dto.DoctorDTO;
import pl.kambat.api.dto.mapper.DoctorAvailabilityMapper;
import pl.kambat.api.dto.mapper.DoctorMapper;
import pl.kambat.business.DoctorAvailabilityService;
import pl.kambat.business.DoctorService;
import pl.kambat.domain.Doctor;
import pl.kambat.domain.DoctorAvailability;
import pl.kambat.util.DataFixtures;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DoctorScheduleController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class DoctorScheduleControllerTest {

    private MockMvc mockMvc;
    @MockBean
    private DoctorService doctorService;
    @MockBean
    private DoctorMapper doctorMapper;
    @MockBean
    private DoctorAvailabilityService doctorAvailabilityService;
    @MockBean
    private DoctorAvailabilityMapper doctorAvailabilityMapper;

    @Test
    void getDoctorSchedule() throws Exception {
        //given

        Doctor doctor = DataFixtures.someDoctor1();
        when(doctorService.getLoggedDoctor()).thenReturn(doctor);
        when(doctorAvailabilityService.findAllDoctorAvailabilityByDoctor(any())).thenReturn(List.of());
        when(doctorAvailabilityMapper.map(any(DoctorAvailability.class))).thenReturn(DataFixtures.someDoctorAvailabilityDTO1());

        //when, then
        mockMvc.perform(get("/doctor/schedule"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("availableHourS"))
                .andExpect(model().attributeExists("availableMinuteS"))
                .andExpect(model().attributeExists("quantityHourS"))
                .andExpect(model().attributeExists("availableDate"))
                .andExpect(model().attributeExists("doctorAvailabilityDTOs"))
                .andExpect(view().name("doctor/doctor_schedule"));
    }

    @Test
    void postDoctorSchedule() throws Exception {
        // given
        String currentDate = LocalDate.now().toString();
        String availableHour = "8";
        String availableMinute = "0";
        String quantityHour = "2";

        // when, then
        mockMvc.perform(post("/doctor/schedule")
                        .param("availableDate", currentDate)
                        .param("availableHour", availableHour)
                        .param("availableMinute", availableMinute)
                        .param("quantityHour", quantityHour))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("availableHourS"))
                .andExpect(model().attributeExists("availableMinuteS"))
                .andExpect(model().attributeExists("quantityHourS"))
                .andExpect(model().attributeExists("availableDate"))
                .andExpect(model().attributeExists("doctorAvailabilityDTOs"))
                .andExpect(view().name("doctor/doctor_schedule"));
    }

    @Test
    void doctorScheduleAppointment() throws Exception {
        // given
        String availableDoctor = "Mirek Mirkowski 12365987";

        Doctor doctor = DataFixtures.someDoctor1();
        DoctorDTO doctorDTO = DataFixtures.someDoctorDTO1();

        when(doctorService.getDoctorByPwzNumber("12365987")).thenReturn(doctor);
        when(doctorMapper.map(doctor)).thenReturn(doctorDTO);
        when(doctorAvailabilityService.findAllDoctorAvailabilityByDoctor(doctor))
                .thenReturn(List.of(DataFixtures.someDoctorAvailability1()));
        when(doctorAvailabilityMapper.map(any(DoctorAvailability.class)))
                .thenReturn(DataFixtures.someDoctorAvailabilityDTO1());

        // when, then
        mockMvc.perform(post("/patient/doctor/schedule")
                        .param("availableDoctor", availableDoctor))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("doctorDTO"))
                .andExpect(model().attributeExists("doctorAvailabilityDTOs"))
                .andExpect(view().name("patient/doctor_schedule"));

    }
}
