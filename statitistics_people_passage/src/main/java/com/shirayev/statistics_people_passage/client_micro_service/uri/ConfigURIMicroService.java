package com.shirayev.statistics_people_passage.client_micro_service.uri;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "microservice")
public class ConfigURIMicroService {

    private Map<String, String> excelProcessing;

}
