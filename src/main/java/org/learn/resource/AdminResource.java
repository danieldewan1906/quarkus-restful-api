package org.learn.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.learn.core.WebResponse;
import org.learn.service.AdminService;

@Path("/api/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminResource {

    @Inject
    private AdminService adminService;

    @PUT
    @Path("{merchantId}")
    @RolesAllowed({"admin"})
    public WebResponse<Boolean> activatedMerchant(@PathParam("merchantId") String merchantId) {
        adminService.activatedMerchant(merchantId);
        return new WebResponse<>(200, "OK", true);
    }
}
