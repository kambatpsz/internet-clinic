package pl.kambat.integration.support;

import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;
import pl.kambat.api.controller.rest.DoctorAvailabilityRestController;
import pl.kambat.api.dto.DoctorAvailabilityRequestDTO;

import java.util.List;

public interface DoctorAvailabilityRestTestSupport {

    RequestSpecification requestSpecification();

    default List<DoctorAvailabilityRequestDTO> getDoctorAvailabilityList(Integer doctorId) {
        return requestSpecification()
                .get(DoctorAvailabilityRestController.API_DOCTOR_AVAILABILITY + DoctorAvailabilityRestController.DOCTOR_ID, doctorId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList(".", DoctorAvailabilityRequestDTO.class);
    }

    default void addDoctorAvailabilityRequest(DoctorAvailabilityRequestDTO requestDTO) {
        requestSpecification()
                .body(requestDTO)
                .post(DoctorAvailabilityRestController.API_DOCTOR_AVAILABILITY)
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    default void updateDoctorAvailabilityRequest(Integer doctorAvailabilityId, DoctorAvailabilityRequestDTO requestDTO) {
        requestSpecification()
                .body(requestDTO)
                .put(DoctorAvailabilityRestController.API_DOCTOR_AVAILABILITY +
                                DoctorAvailabilityRestController.UPDATE_DOCTOR_AVAILABILITY_ID,
                        doctorAvailabilityId)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    default void deleteDoctorAvailabilityRequest(Integer doctorAvailabilityId) {
        requestSpecification()
                .delete(DoctorAvailabilityRestController.API_DOCTOR_AVAILABILITY +
                                DoctorAvailabilityRestController.DELETE_DOCTOR_AVAILABILITY_ID,
                        doctorAvailabilityId)
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
