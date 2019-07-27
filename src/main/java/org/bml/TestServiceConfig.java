package org.bml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.collect.ImmutableList;
import org.bml.response.version.VersionContent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
@EnableWebMvc
public class TestServiceConfig implements WebMvcConfigurer {

    public static final MediaType MEDIA_TYPE_YAML = MediaType.valueOf("text/yaml");
    public static final MediaType MEDIA_TYPE_YML = MediaType.valueOf("text/yml");

    @Primary()
    @Bean(name = "jsonObjectMapper")
    public ObjectMapper jsonObjectMapper() {
        return new ObjectMapper();
    }

    @Bean(name = "yamlObjectMapper")
    public ObjectMapper yamlObjectMapper() {
        return new ObjectMapper(new YAMLFactory());
    }


    @Bean
    public VersionContent versionContent(final ObjectMapper jsonObjectMapper)throws IOException{
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("version.json")) {
            VersionContent versionContent = jsonObjectMapper.readValue(inputStream, VersionContent.class);
            return versionContent;
        }
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
                .favorPathExtension(true)
                .favorParameter(false)
                .ignoreAcceptHeader(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType(MediaType.APPLICATION_JSON.getSubtype(),
                        MediaType.APPLICATION_JSON)
                .mediaType(MEDIA_TYPE_YML.getSubtype(), MEDIA_TYPE_YML)
                .mediaType(MEDIA_TYPE_YAML.getSubtype(), MEDIA_TYPE_YAML);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new YamlMessageConverter());
    }








    @Component
    public static class YamlMessageConverter extends MappingJackson2HttpMessageConverter {
        public YamlMessageConverter() {
            //can use overloaded constructor to set supported MediaType
            super(new ObjectMapper(new YAMLFactory()));
            this.setSupportedMediaTypes(ImmutableList.of(MEDIA_TYPE_YML, MEDIA_TYPE_YAML));
        }
    }








}
