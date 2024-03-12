package pl.kambat.integration.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import pl.kambat.business.PatientService;
import pl.kambat.domain.Member;
import pl.kambat.integration.support.AuthenticationTestSupport;
import pl.kambat.integration.support.ControllerTestSupport;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;


public abstract class RestAssuredIntegrationTestBase
        extends AbstractIT
        implements ControllerTestSupport, AuthenticationTestSupport {

    protected static WireMockServer wireMockServer;

    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private PatientService patientService;
    private String jSessionIdValue;

    @BeforeAll
    static void beforeAll() {
        wireMockServer = new WireMockServer(
                wireMockConfig()
                        .port(9999)
                        .extensions(new ResponseTemplateTransformer(false))
        );
        wireMockServer.start();
    }
    @AfterAll
    static void afterAll() {
        wireMockServer.stop();
    }

    @Test
    void contextLoaded() {
        assertThat(true).isTrue();
    }

    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @BeforeEach
    void beforeEach() {
        patientService.deleteAllPatient();
        CreateRestApiTestUser();
        jSessionIdValue = login("REST_API_TEST", "REST_API_TEST")
                .and()
                .cookie("JSESSIONID")
                .header(HttpHeaders.LOCATION, "http://localhost:%s%s/".formatted(port, basePath))
                .extract()
                .cookie("JSESSIONID");
    }

    private void CreateRestApiTestUser() {
        //Create REST_API TEST USER
        Member restApiTestUser = Member.builder()
                .userType("REST_API")
                .userName("REST_API")
                .userSurname("REST_API")
                .userEmail("REST_API@gmail.com")
                .userLogin("REST_API_TEST")
                .userPassword("REST_API_TEST")
                .userPesel("99999999999")
                .userPhoneNumber("+00 000 000 000")
                .userAddressCountry("")
                .userAddressCity("")
                .userAddressPostalCode("")
                .userAddressStreet("")
                .build();

        patientService.createPatient(restApiTestUser);
    }

    @AfterEach
    void afterEach() {
        logout()
                .and()
                .cookie("JSESSIONID", "");
        jSessionIdValue = null;
        wireMockServer.resetAll();
    }

    public RequestSpecification requestSpecification() {
        return restAssuredBase()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .cookie("JSESSIONID", jSessionIdValue);
    }

    public RequestSpecification requestSpecificationNoAuthentication() {
        return restAssuredBase();
    }


    private RequestSpecification restAssuredBase() {
        return RestAssured
                .given()
                .config(getConfig())
                .basePath(basePath)
                .port(port);
    }

    private RestAssuredConfig getConfig() {
        return RestAssuredConfig
                .config()
                .objectMapperConfig(new ObjectMapperConfig()
                        .jackson2ObjectMapperFactory((type, s) -> objectMapper));
    }
}
