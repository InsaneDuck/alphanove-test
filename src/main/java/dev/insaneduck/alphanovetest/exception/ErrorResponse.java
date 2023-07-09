package dev.insaneduck.alphanovetest.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@AllArgsConstructor
@Data
@Builder
public class ErrorResponse {
    private HttpStatus httpStatus;
    private String errorMessage;
}

