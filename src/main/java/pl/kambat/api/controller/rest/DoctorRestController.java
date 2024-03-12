package pl.kambat.api.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.kambat.api.dto.DoctorDTO;
import pl.kambat.api.dto.mapper.DoctorMapper;
import pl.kambat.api.dto.mapper.DoctorNilMapper;
import pl.kambat.business.DoctorNilService;
import pl.kambat.business.DoctorService;
import pl.kambat.domain.Doctor;
import pl.kambat.domain.exception.NotDataFoundException;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(DoctorRestController.API_DOCTOR)
@RequiredArgsConstructor
public class DoctorRestController {

    public static final String API_DOCTOR = "/api/doctor";
    public static final String DOCTOR_ID = "/{doctorId}";
    public static final String DOCTOR_CHECK_IN_NIL ="/{doctorId}/nil";

    private final DoctorService doctorService;
    private final DoctorNilService doctorNilService;
    private final DoctorMapper doctorMapper;
    private final DoctorNilMapper doctorNilMapper;


    @GetMapping
    public List<DoctorDTO> doctorList() {
        return doctorService.findAllDoctors().stream()
                .map(doctorMapper::map)
                .toList();
    }

    @GetMapping(value = DOCTOR_ID)
    public DoctorDTO showDoctorDetails(@PathVariable Integer doctorId) {
        return doctorMapper.map(doctorService.getDoctorById(doctorId));
    }

    @GetMapping(value = DOCTOR_CHECK_IN_NIL)
    public String checkDoctorInNil(@PathVariable Integer doctorId) {
        Doctor doctorById = doctorService.getDoctorById(doctorId);
        Doctor doctorFromNil = doctorNilMapper.map(
                doctorNilService.getDoctorByPwzNumber(doctorById.getPwzNumber())
        );

            if(doctorFromNil != null && doctorService.verifyDoctor(doctorById, doctorFromNil)){
                doctorService.changeDoctorVerifiedStatusById(doctorById.getDoctorId(), Boolean.TRUE);
                return "Doctor %s %s has been properly verified with NIL"
                        .formatted(doctorById.getName(), doctorById.getSurname());
            }

        return "Unsuccessful verification of Doctor(%s %s) in NIL"
                .formatted(doctorById.getName(),doctorById.getSurname());
    }
}