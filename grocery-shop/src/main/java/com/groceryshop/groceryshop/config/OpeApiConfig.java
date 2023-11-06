package com.groceryshop.groceryshop.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Grocery Shop",
                        email = "iivaylov00@gmail.com"
                ),

                description = "OpenApi documentation for Grocery Shop",
                title = "OpenApi specification - Grocery Shop",
                version = "1.0"
        ),

        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                )

        }
)
public class OpeApiConfig {
}
