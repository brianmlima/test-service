package org.bml;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.bml.response.VersionContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
@Controller
public class TestService {


    public static void main(final String[] args) {
        SpringApplication.run(TestService.class, args);
    }


    public static final String HELLO_RESPONSE_STRING = "hi";
    public static final String HELLO_MAPPING = "/hello";

    public static final String ECHO_MAPPING = "/echo";
    public static final String ECHO_STRING_PARAM = "string";


    public static final String VERSION_MAPPING = "/version";
    public static final String VERSION_SYSTEM_PROP_NAME = "app_version";


    @Autowired
    @Qualifier("jsonObjectMapper")
    private ObjectMapper jsonObjectMapper;

    @GetMapping(HELLO_MAPPING)
    @ResponseBody
    public String hello() {
        return HELLO_RESPONSE_STRING;
    }
    @GetMapping(VERSION_MAPPING)
    public ResponseEntity<VersionContent> version() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("version.json")) {
            VersionContent versionContent = jsonObjectMapper.readValue(inputStream, VersionContent.class);
            return ResponseEntity.ok(versionContent);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(VersionContent.builder().error(e.getMessage()).build());
        }
    }

    @GetMapping(ECHO_MAPPING)
    @ResponseBody
    public String echo(@NotNull @NotEmpty @RequestParam(name = ECHO_STRING_PARAM) final String string) {
        return string;
    }

}
