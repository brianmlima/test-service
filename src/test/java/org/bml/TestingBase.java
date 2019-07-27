package org.bml;

import com.google.common.collect.ImmutableList;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class TestingBase {

    @LocalServerPort
    private int port;

    @Value("${local.management.port}")
    private int mgt;

    @Autowired
    private TestRestTemplate restTemplate;

    protected String localhost(final String path) {
        return "http://localhost:" + this.port + path;
    }

    protected <T> ResponseEntity<T> template(final String path, Class<T> clazz) {
        return restTemplate.getForEntity(localhost(path), clazz);
    }


    protected <T> ResponseEntity<T> template(final String path, Class<T> clazz,MediaType mediaType) {

        HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(mediaType);
        headers.setAccept(ImmutableList.of(mediaType));
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(new LinkedMultiValueMap<>(), headers);
        return restTemplate.getForEntity(localhost(path), clazz, request);
    }


    protected <T> ResponseEntity<T> template(final String path, final String paramKey, final String paramValue, final Class<T> clazz) {
        return template(
                path,
                ImmutableList.of(new BasicNameValuePair(paramKey, paramValue)),
                clazz
        );
    }

    protected <T> ResponseEntity<T> template(final String path, final List<NameValuePair> params, final Class<T> clazz) {
        return restTemplate.getForEntity(
                String.format(
                        "%s?%s",
                        localhost(path),
                        URLEncodedUtils.format(params, StandardCharsets.UTF_8)
                ),
                clazz);
    }


}
