package com.cu.sci.lambdaserver.utils.exception;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Objects;

@Builder(builderClassName = "RestErrorResponseBuilder")
@Data
public class RestErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private String error;
    private String message;
    private String path;

    public ResponseEntity<RestErrorResponse> entity() {
        return ResponseEntity.status(status).headers(HttpHeaders.EMPTY).body(this);
    }

    public String json() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(this);
    }

    public static class RestErrorResponseBuilder {
        public RestErrorResponseBuilder exception(ResponseStatusException exception) {
            HttpStatusCode status = exception.getStatusCode();
            this.status = status.value();

            if (!Objects.requireNonNull(exception.getReason()).isBlank()) {
                this.message = exception.getReason();
            }

            if (status.isError()) {
                this.error = exception.getReason();
            }
            return this;
        }
    }
}
