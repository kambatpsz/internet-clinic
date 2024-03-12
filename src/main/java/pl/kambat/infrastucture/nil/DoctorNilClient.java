package pl.kambat.infrastucture.nil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pl.kambat.business.dao.DoctorNilDAO;
import pl.kambat.domain.DoctorNIL;

import java.util.List;
import java.util.Optional;


@Slf4j
@Component
@AllArgsConstructor
public class DoctorNilClient implements DoctorNilDAO {

    private final WebClient webClient;

    @Override
    public Optional<DoctorNIL> getDoctorFromNilByPwzNumber(String pwzNumber) {
        try {
            var result = webClient
                    .get()
                    .uri(uriBuilder -> uriBuilder.path("/doctors")
                            .queryParam("pwzNumber", pwzNumber)
                            .build())
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<DoctorNIL>>() {
                    })
                    .block();

            if (result != null && !result.isEmpty()) {
                return Optional.ofNullable(result.get(0));
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return Optional.empty();
        }
    }
}