package com.nokhrin.config;

import com.nokhrin.service.WordService;
import com.nokhrin.service.WordServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;

import org.springframework.remoting.support.RemoteExporter;

@Configuration
public class HessianServerConfig {

    @Bean(name = "/GWService")
    public RemoteExporter hessianServiceExporter() {

        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(new WordServiceImpl());
        exporter.setServiceInterface(WordService.class);
        return exporter;
    }
}
