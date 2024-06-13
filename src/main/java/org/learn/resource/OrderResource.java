package org.learn.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.learn.core.WebResponse;
import org.learn.model.orders.OrderDto;
import org.learn.model.orders.OrderRequestDto;
import org.learn.service.OrderService;

@Path("/api/orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    private OrderService orderService;
    @Inject
    private JsonWebToken jwt;

    @POST
    @Path("/checkout")
    @RolesAllowed({"user"})
    public WebResponse<OrderDto> checkout(@Valid @RequestBody OrderRequestDto request) {
        OrderDto orderDto = orderService.checkout(jwt.getSubject(), request);
        return new WebResponse<>(200, "OK", orderDto);
    }
}
