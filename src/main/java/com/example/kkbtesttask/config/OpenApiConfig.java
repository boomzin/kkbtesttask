package com.example.kkbtesttask.config;

import com.example.kkbtesttask.model.Task;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "REST API documentation",
                version = "1.0",
                description = "Competency testing project from Kuban Kredit Bank",
                contact = @Contact(url = "https://github.com/boomzin", name = "Ernest Aidinov", email = "ernest.aidinov@gmail.com")
        )
)
public class OpenApiConfig {

    @Bean
    public OpenApiCustomiser schemaCustomiser() {
        ResolvedSchema resolvedSchema = ModelConverters.getInstance()
                .resolveAsResolvedSchema(new AnnotatedType(Task.class));
        return openApi -> openApi
                .schema(resolvedSchema.schema.getName(), resolvedSchema.schema);
    }
}
