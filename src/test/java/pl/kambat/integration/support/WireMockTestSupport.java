package pl.kambat.integration.support;

import com.github.tomakehurst.wiremock.WireMockServer;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public interface WireMockTestSupport {
    default void stubForNil(final WireMockServer wireMockServer, final String pwzNumber) {
        wireMockServer.stubFor(get(urlEqualTo("/doctors?pwzNumber=" + pwzNumber))
                .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withBodyFile("wiremock/nilData.json")
                .withTransformerParameters(Map.of("pwzNumber", pwzNumber))
                .withTransformers("response-template")));
    }
}


//http://localhost:3000/doctors?pwzNumber=123456