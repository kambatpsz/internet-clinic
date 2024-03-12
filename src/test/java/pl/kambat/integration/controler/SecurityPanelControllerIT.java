package pl.kambat.integration.controler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import pl.kambat.integration.configuration.AbstractIT;

class SecurityPanelControllerIT extends AbstractIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void doctorPanelPage() {
        String url = String.format("http://localhost:%s/internet-clinic/doctor", port);
        String renderedPage = this.restTemplate.getForObject(url, String.class);
        Assertions.assertThat(renderedPage).contains("<title>Please sign in</title>");
    }

    @Test
    void patientPanelPage() {
        String url = String.format("http://localhost:%s/internet-clinic/patient", port);
        String renderedPage = this.restTemplate.getForObject(url, String.class);
        Assertions.assertThat(renderedPage).contains("<title>Please sign in</title>");
    }
}