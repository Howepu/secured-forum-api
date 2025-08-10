package com.titov.securedforumapi.dto.response;

public record ErrorResponse(String description, int status, String exceptionName, String message) {
}
