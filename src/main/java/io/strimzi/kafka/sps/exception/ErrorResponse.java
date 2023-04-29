package io.strimzi.kafka.sps.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public record ErrorResponse(
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String errorId,
        List<ErrorMessage> errors
) {
    public record ErrorMessage(
            @JsonInclude(JsonInclude.Include.NON_NULL)
            String path,
            String message
    ) {
    }
}