package org.learn.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import org.learn.core.WebResponse;
import org.learn.model.user.UserDto;
import org.learn.service.UserService;

@Path("/api/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    private UserService userService;

    @GET
    @Path("/current")
    @RolesAllowed({"user"})
    public WebResponse<UserDto> getUserByEmail(@Context SecurityContext sc) {
        String email = sc.getUserPrincipal().getName();
        UserDto userDto = userService.getUserByEmail(email);
        return new WebResponse<>(200, "OK", userDto);
    }
}
