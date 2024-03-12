package pl.kambat.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.kambat.api.dto.DoctorDTO;
import pl.kambat.api.dto.mapper.DoctorMapper;
import pl.kambat.business.DoctorService;

@Controller
@RequiredArgsConstructor
public class DoctorPanelController {

    private static final String DOCTOR = "/doctor";

    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;

    @GetMapping(value = DOCTOR)
    public String doctorPanelPage(Model model) {
        DoctorDTO doctorDTO = doctorMapper.map(doctorService.getLoggedDoctor());
        model.addAttribute("doctorName", doctorDTO.getName());
        model.addAttribute("doctorSurname", doctorDTO.getSurname());
        return "doctor/doctor_panel";
    }
}
