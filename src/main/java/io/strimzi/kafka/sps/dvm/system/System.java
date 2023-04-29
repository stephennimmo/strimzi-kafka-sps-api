package io.strimzi.kafka.sps.dvm.system;

import jakarta.validation.constraints.NotBlank;

public record System(
        @NotBlank(message = "{System.name.required}")
        String name
) {}
