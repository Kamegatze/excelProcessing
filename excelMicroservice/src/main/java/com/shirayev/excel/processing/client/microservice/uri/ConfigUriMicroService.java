package com.shirayev.excel.processing.client.microservice.uri;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "microservice")
public class ConfigUriMicroService {

    private Map<String, String> statistics;

}
