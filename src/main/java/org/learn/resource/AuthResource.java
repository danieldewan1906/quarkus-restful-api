package org.learn.resource;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import org.learn.core.WebResponse;
import org.learn.model.auth.LoginRequest;
import org.learn.model.auth.LoginResponse;
import org.learn.model.auth.RegisterRequest;
import org.learn.model.auth.RegisterResponse;
import org.learn.service.AuthService;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    private AuthService authService;

    @POST
    @Path("/register")
    @PermitAll
    public WebResponse<RegisterResponse> registerUser(RegisterRequest request) {
        RegisterResponse response = authService.registerUser(request);
        return new WebResponse<>(200, "OK", response);
    }

    @POST
    @Path("/login")
    @PermitAll
    public WebResponse<LoginResponse> loginUser(LoginRequest request) {
        LoginResponse response = authService.loginUser(request);
        return new WebResponse<>(200, "OK", response);
    }

    @DELETE
    @Path("/logout")
    @RolesAllowed({"admin", "user"})
    public WebResponse<Void> logoutUser(@Context SecurityContext sc) {
        authService.logoutUser(sc);
        return new WebResponse<>(200, "Logout Success", null);
    }
}
