package org.learn.resource;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.learn.core.WebResponse;
import org.learn.model.product.ProductDto;
import org.learn.model.product.ProductRequestDto;
import org.learn.model.user.UserDto;
import org.learn.service.ProductService;
import org.learn.service.UserService;

import java.util.List;

@Path("/api/products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    private ProductService productService;

    @Inject
    private UserService userService;

    @Inject
    private JsonWebToken jwt;

    @POST
    @RolesAllowed({"user"})
    public WebResponse<ProductDto> createProduct(@Valid ProductRequestDto request) {
        ProductDto productDto = productService.createProduct(jwt.getSubject(), request);
        return new WebResponse<>(200, "OK", productDto);
    }

    @GET
    @RolesAllowed({"admin", "user"})
    public WebResponse<ProductDto> getProductDetail(@QueryParam("code") String code) {
        userService.getCurrentUser(jwt.getRawToken());
        ProductDto productDto = productService.getProductDetail(code);
        return new WebResponse<>(200, "OK", productDto);
    }

    @GET
    @Path("/merchant")
    @RolesAllowed({"user"})
    public WebResponse<List<ProductDto>> getProductByMerchant() {
        String email = jwt.getSubject();
        List<ProductDto> productDtos = productService.getProductByMerchant(email);
        return new WebResponse<>(200, "OK", productDtos);
    }
}
