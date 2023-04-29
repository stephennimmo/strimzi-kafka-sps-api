package io.strimzi.kafka.sps;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.strimzi.kafka.sps.dvm.system.SystemService;
import io.strimzi.kafka.sps.exception.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class ThrowableMapperTest {

    @InjectMock
    SystemService systemService;

    @Test
    public void throwUnexpectedRuntimeExceptionInCustomerService() {
        Mockito.when(systemService.findAll()).thenThrow(new RuntimeException("Completely Unexpected"));
        ErrorResponse errorResponse = given()
                .when()
                .get("/systems")
                .then()
                .statusCode(500)
                .extract().as(ErrorResponse.class);
        assertThat(errorResponse.errors()).contains(new ErrorResponse.ErrorMessage(null, "An unexpected error has occurred. Please contact support."));
    }

}
