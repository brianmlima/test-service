package org.bml;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
public class TestTestService {


    @LocalServerPort
    private int port;

    @Value("${local.management.port}")
    private int mgt;

    @Autowired
    private TestRestTemplate testRestTemplate;


    @Test
    public void shouldReturn200WhenSendingRequestToHello() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<String> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.port + "/hello", String.class);
        assertThat(entity.getStatusCode(), is(HttpStatus.OK));
        assertThat(entity.getBody(), is(TestService.HELLO_RESPONSE_STRING));
    }

    @Test
    public void shouldReturn200WhenSendingRequestToEcho() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<String> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.port + "/hello", String.class);
        assertThat(entity.getStatusCode(), is(HttpStatus.OK));
        assertThat(entity.getBody(), is(TestService.HELLO_RESPONSE_STRING));
    }





}
