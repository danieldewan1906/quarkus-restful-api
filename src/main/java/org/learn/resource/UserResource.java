package org.learn.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.learn.core.WebResponse;
import org.learn.model.merchant.MerchantDto;
import org.learn.model.merchant.MerchantRequestDto;
import org.learn.model.user.UserDto;
import org.learn.model.user.UserRequestDto;
import org.learn.service.UserService;

@Path("/api/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    private UserService userService;

    @Inject
    JsonWebToken token;

    @GET
    @Path("/current")
    @RolesAllowed({"user"})
    public WebResponse<UserDto> getUserByEmail() {
        UserDto userDto = userService.getCurrentUser(token.getRawToken());
        return new WebResponse<>(200, "OK", userDto);
    }

    @PUT
    @Path("/edit")
    @RolesAllowed({"user"})
    public WebResponse<Boolean> updateUser(@Context SecurityContext sc, @Valid UserRequestDto request) {
        String email = sc.getUserPrincipal().getName();
        userService.updateUser(email, request);
        return new WebResponse<>(200, "OK", true);
    }

    @PUT
    @Path("/merchant")
    @RolesAllowed({"user"})
    public WebResponse<MerchantDto> createMerchant(@Valid MerchantRequestDto request) {
        MerchantDto merchant = userService.createMerchant(token.getSubject(), request);
        return new WebResponse<>(200, "OK", merchant);
    }
}
