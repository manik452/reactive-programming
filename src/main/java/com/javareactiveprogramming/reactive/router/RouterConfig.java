package com.javareactiveprogramming.reactive.router;

import com.javareactiveprogramming.reactive.handler.CustomerHandler;
import com.javareactiveprogramming.reactive.handler.CustomerStreamHandler;
import com.javareactiveprogramming.reactive.model.Customer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Autowired
    private CustomerHandler customerHandler;
    @Autowired
    private CustomerStreamHandler customerStreamHandler;

    @Bean
    @RouterOperations(value = {
            @RouterOperation(
                    path = "/router/customers",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.GET,
                    beanClass = CustomerHandler.class,
                    beanMethod = "loadCustomers",
                    operation = @Operation(operationId = "loadCustomers",
                            responses = @ApiResponse(
                                    responseCode = "200",
                                    description = "Operation Successful",
                                    content = @Content(
                                            schema = @Schema(
                                                    implementation = Customer.class
                                            )
                                    )
                            )
                    )
            ),
            @RouterOperation(
                    path = "/router/customer/{input}",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.GET,
                    beanClass = CustomerHandler.class,
                    beanMethod = "loadCustomerById",
                    operation = @Operation(operationId = "loadCustomerById",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Operation Successful",
                                            content = @Content(
                                                    schema = @Schema(
                                                            implementation = Customer.class
                                                    )
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Operation not Successful"
                                    )
                            },
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "input")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/router/saveCustomer",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.POST,
                    beanClass = CustomerHandler.class,
                    beanMethod = "saveCustomer",
                    operation = @Operation(operationId = "saveCustomer",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Operation Successful",
                                            content = @Content(
                                                    schema = @Schema(
                                                            implementation = String.class
                                                    )
                                            )
                                    )
                            }/*,
                            requestBody = @RequestBody(
                                    content = @Content(
                                            schema = @Schema(
                                                    implementation = Customer.class
                                            )
                                    )
                            )*/
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .GET("/router/coustomers", customerHandler::loadCustomers)
                .GET("/router/coustomer/{input}", customerHandler::loadCustomerById)
                .POST("/router/saveCustomer", customerHandler::saveCustomer)
                .GET("/router/coustomersstream", customerStreamHandler::loadCustomersStream)
                .build();
    }
}
