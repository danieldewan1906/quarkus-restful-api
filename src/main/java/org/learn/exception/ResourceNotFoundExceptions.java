package org.learn.exception;

import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

public class ResourceNotFoundExceptions extends RuntimeException {

    public ResourceNotFoundExceptions(String message) {
        super(message);
    }

    public ResourceNotFoundExceptions(String message, Throwable cause) {
        super(message, cause);
    }

}
