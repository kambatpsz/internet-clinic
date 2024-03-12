package pl.kambat.api.controller.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kambat.api.dto.DoctorAvailabilityRequestDTO;
import pl.kambat.api.dto.mapper.DoctorAvailabilityRequestMapper;
import pl.kambat.business.DoctorAvailabilityRequestService;
import pl.kambat.business.DoctorService;
import pl.kambat.domain.Doctor;
import pl.kambat.domain.DoctorAvailabilityRequest;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(DoctorAvailabilityRestController.API_DOCTOR_AVAILABILITY)
@RequiredArgsConstructor
public class DoctorAvailabilityRestController {

    public static final String API_DOCTOR_AVAILABILITY = "/api/doctor-availability";
    public static final String DOCTOR_ID = "/{doctorId}";
    public static final String UPDATE_DOCTOR_AVAILABILITY_ID = "/update/{doctorAvailabilityId}";
    public static final String DELETE_DOCTOR_AVAILABILITY_ID = "/delete/{doctorAvailabilityId}";
    public static final String DOCTOR_ID_RESULT = "/%s";

    private final DoctorService doctorService;
    private final DoctorAvailabilityRequestService doctorAvailabilityRequestService;
    private final DoctorAvailabilityRequestMapper doctorAvailabilityRequestMapper;


    @GetMapping(value = DOCTOR_ID)
    public List<DoctorAvailabilityRequestDTO> getDoctorAvailabilityRequestList(@PathVariable Integer doctorId) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        return doctorAvailabilityRequestService.findAllDoctorAvailabilityRequestByDoctor(doctor).stream()
                .map(doctorAvailabilityRequestMapper::mapToRequest)
                .toList();
    }

    @PostMapping
    public ResponseEntity<DoctorAvailabilityRequestDTO> addDoctorAvailabilityRequest(
            @Valid @RequestBody DoctorAvailabilityRequestDTO doctorAvailabilityRequestDTO
    ) {
        DoctorAvailabilityRequest newDoctorAvailabilityRequest =
                DoctorAvailabilityRequest.builder()
                        .doctorId(doctorAvailabilityRequestDTO.getDoctorId())
                        .date(doctorAvailabilityRequestDTO.getDate())
                        .hourFrom(doctorAvailabilityRequestDTO.getHourFrom())
                        .reserved(Boolean.FALSE)
                        .build();
        DoctorAvailabilityRequest doctorAvailabilityRequest =
                doctorAvailabilityRequestService.saveNewDoctorAvailabilityRequest(newDoctorAvailabilityRequest);

        return ResponseEntity
                .created(URI.create(API_DOCTOR_AVAILABILITY + DOCTOR_ID_RESULT.formatted(
                        doctorAvailabilityRequest.getDoctorId())))
                .build();
    }

    @PutMapping(value = UPDATE_DOCTOR_AVAILABILITY_ID)
    public ResponseEntity<?> updateDoctorAvailabilityRequest(
            @PathVariable("doctorAvailabilityId") Integer doctorAvailabilityId,
            @Valid @RequestBody DoctorAvailabilityRequestDTO doctorAvailabilityRequestDTO
    ) {
        DoctorAvailabilityRequest newDoctorAvailabilityRequest =
                DoctorAvailabilityRequest.builder()
                        .doctorAvailabilityId(doctorAvailabilityId)
                        .doctorId(doctorAvailabilityRequestDTO.getDoctorId())
                        .date(doctorAvailabilityRequestDTO.getDate())
                        .hourFrom(doctorAvailabilityRequestDTO.getHourFrom())
                        .reserved(Boolean.FALSE)
                        .build();


        doctorAvailabilityRequestService.updateDoctorAvailabilityRequest(newDoctorAvailabilityRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(DELETE_DOCTOR_AVAILABILITY_ID)
    public ResponseEntity<?> deleteDoctorAvailabilityRequest(@PathVariable Integer doctorAvailabilityId) {
        doctorAvailabilityRequestService.deleteDoctorAvailabilityRequest(doctorAvailabilityId);
        return ResponseEntity.ok().build();
    }

}
