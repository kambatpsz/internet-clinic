package pl.kambat.integration.support;

import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;
import pl.kambat.api.controller.rest.DoctorRestController;
import pl.kambat.api.dto.DoctorDTO;

import java.util.List;

public interface DoctorControllerRestTestSupport {

    RequestSpecification requestSpecification();

    default List<DoctorDTO> listDoctors() {
        return requestSpecification()
                .get(DoctorRestController.API_DOCTOR)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .jsonPath()
                .getList(".", DoctorDTO.class);
    }

    default DoctorDTO getDoctorById(final Integer doctorId) {

        return requestSpecification()
                .get(DoctorRestController.API_DOCTOR + DoctorRestController.DOCTOR_ID, doctorId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(DoctorDTO.class);
    }
}
