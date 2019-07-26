package org.bml;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@Controller
public class TestService {


    public static void main(final String[] args) {
        SpringApplication.run(TestService.class, args);
    }


    public static final String HELLO_RESPONSE_STRING="hi";
    public static final String HELLO_MAPPING="/hello";

    public static final String ECHO_MAPPING="/echo";

    public static final String ECHO_STRING_PARAM="string";


    @GetMapping(HELLO_MAPPING)
    @ResponseBody
    public String hello() {
        return HELLO_RESPONSE_STRING;
    }

    @GetMapping(ECHO_MAPPING)
    @ResponseBody
    public String echo(@NotNull @NotEmpty @RequestParam(name = ECHO_STRING_PARAM) final String string) {
        return string;
    }

}
