package org.bml;


import com.thedeanda.lorem.LoremIpsum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static org.bml.TestService.ECHO_MAPPING;
import static org.bml.TestService.ECHO_STRING_PARAM;
import static org.bml.TestService.HELLO_MAPPING;
import static org.bml.TestService.HELLO_RESPONSE_STRING;
import static org.bml.TestService.VERSION_MAPPING;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.http.HttpStatus.OK;

@TestPropertySource(properties = {"management.port=0",})
public class TestTestService extends TestingBase {

    private static final String TEST_VERSION_STRING = "1.0.0";

    static {
        System.setProperty(TestService.VERSION_SYSTEM_PROP_NAME, TEST_VERSION_STRING);
    }

    @Value("${local.management.port}")
    private int mgt;

    @Test
    public void shouldReturn200WhenSendingRequestToHello() throws Exception {
        final ResponseEntity<String> entity = template(HELLO_MAPPING, String.class);
        assertThat(entity.getStatusCode(), is(OK));
        assertThat(entity.getBody(), is(HELLO_RESPONSE_STRING));
    }

    @Test
    public void shouldReturn200WhenSendingRequestToVersion() throws Exception {
        final ResponseEntity<String> entity = template(VERSION_MAPPING, String.class);
        assertThat(entity.getStatusCode(), is(OK));
        assertThat(entity.getBody(), is(TEST_VERSION_STRING));
    }

    @Test
    public void shouldReturn200WhenSendingRequestToEcho() throws Exception {
        final String content = LoremIpsum.getInstance().getParagraphs(1, 1);
        final ResponseEntity<String> entity = template(
                ECHO_MAPPING,
                ECHO_STRING_PARAM, content,
                String.class
        );
        assertThat(entity.getStatusCode(), is(OK));
        assertThat(entity.getBody(), is(content));
    }


}
