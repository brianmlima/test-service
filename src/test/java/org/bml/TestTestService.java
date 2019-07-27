package org.bml;


import com.thedeanda.lorem.LoremIpsum;
import lombok.extern.slf4j.Slf4j;
import org.bml.response.version.VersionContent;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@TestPropertySource(properties = {"management.port=0",})
public class TestTestService extends TestingBase {

    @Value("${local.management.port}")
    private int mgt;

    @Autowired
    private VersionContent masterVersion;

    @Test
    public void shouldReturn200WhenSendingRequestToHello() throws Exception {
        final ResponseEntity<String> entity = template(HELLO_MAPPING, String.class);
        assertThat(entity.getStatusCode(), is(OK));
        assertThat(entity.getBody(), is(HELLO_RESPONSE_STRING));
    }

    @Test
    public void shouldReturn200WhenSendingRequestToVersionJson() throws Exception {
        final ResponseEntity<VersionContent> entity = template(VERSION_MAPPING + ".json", VersionContent.class);
        assertThat(entity.getStatusCode(), is(OK));
        assertThat(entity.getBody(), samePropertyValuesAs(masterVersion));
    }

    @Test
    public void shouldReturn200WhenSendingRequestToVersionYaml() throws Exception {
        //,TestServiceConfig.MEDIA_TYPE_YAML
        final ResponseEntity<VersionContent> entity = template(VERSION_MAPPING + ".yaml", VersionContent.class);
        assertThat(entity.getStatusCode(), is(OK));
        assertThat(entity.getBody(), samePropertyValuesAs(masterVersion));
    }

    @Test
    public void shouldReturn200WhenSendingRequestToVersionJSON() throws Exception {
        final ResponseEntity<VersionContent> entity = template(VERSION_MAPPING + ".json", VersionContent.class);
        assertThat(entity.getStatusCode(), is(OK));
        assertThat(entity.getBody(), samePropertyValuesAs(masterVersion));

    }

    @Test
    public void shouldReturn200WhenSendingRequestToVersionDefaultsToJSON() throws Exception {
        final ResponseEntity<VersionContent> entity = template(VERSION_MAPPING, VersionContent.class);
        assertThat(entity.getStatusCode(), is(OK));
        assertThat(entity.getBody(), samePropertyValuesAs(masterVersion));
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
