package com.jfas.marvelapi.service.impl;

import com.jfas.marvelapi.exception.ApiErrorException;
import com.jfas.marvelapi.service.HttpClientService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class RestTemplateService implements HttpClientService {

    private final RestTemplate restTemplate;

    public RestTemplateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public <T> T doGet(String endpoint, Map<String, String> queryParams, Class<T> responseType) {
        String finalUrl = buildFinalUrl(endpoint, queryParams);

        HttpEntity httpEntity = new HttpEntity<>(this.getHeaders());
        ResponseEntity<T> response = restTemplate.exchange(finalUrl, HttpMethod.GET, httpEntity, responseType);

        if (response.getStatusCode().value() != HttpStatus.OK.value()) {
            String message = String.format("Error consumiendo endpoint [%s - %s], código de respuesta es: %s",
                    HttpMethod.GET, endpoint, response.getStatusCode());
            throw new ApiErrorException(message);
        }

        return response.getBody();
    }

    private static String buildFinalUrl(String endpoint, Map<String, String> queryParams) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(endpoint);

        if (queryParams != null) {
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
        }

        return builder.build().toUriString();
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Override
    public <T, R> T doPost(String endpoint, Map<String, String> queryParams, Class<T> responseType, R bodyRequest) {
        String finalUrl = buildFinalUrl(endpoint, queryParams);

        HttpEntity httpEntity = new HttpEntity<>(bodyRequest, this.getHeaders());
        ResponseEntity<T> response = restTemplate.exchange(finalUrl, HttpMethod.POST, httpEntity, responseType);

        if (response.getStatusCode().value() != HttpStatus.OK.value() ||
                response.getStatusCode().value() != HttpStatus.CREATED.value()) {
            String message = String.format("Error consumiendo endpoint [%s - %s], código de respuesta es: %s",
                    HttpMethod.POST, endpoint, response.getStatusCode());
            throw new ApiErrorException(message);
        }

        return response.getBody();
    }

    @Override
    public <T, R> T doPut(String endpoint, Map<String, String> queryParams, Class<T> responseType, R bodyRequest) {
        String finalUrl = buildFinalUrl(endpoint, queryParams);

        HttpEntity httpEntity = new HttpEntity<>(bodyRequest, this.getHeaders());
        ResponseEntity<T> response = restTemplate.exchange(finalUrl, HttpMethod.PUT, httpEntity, responseType);

        if (response.getStatusCode().value() != HttpStatus.OK.value()) {
            String message = String.format("Error consumiendo endpoint [%s - %s], código de respuesta es: %s",
                    HttpMethod.PUT, endpoint, response.getStatusCode());
            throw new ApiErrorException(message);
        }

        return response.getBody();
    }

    @Override
    public <T> T doDelete(String endpoint, Map<String, String> queryParams, Class<T> responseType) {
        String finalUrl = buildFinalUrl(endpoint, queryParams);

        HttpEntity httpEntity = new HttpEntity<>(this.getHeaders());
        ResponseEntity<T> response = restTemplate.exchange(finalUrl, HttpMethod.DELETE, httpEntity, responseType);

        if (response.getStatusCode().value() != HttpStatus.OK.value()) {
            String message = String.format("Error consumiendo endpoint [%s - %s], código de respuesta es: %s",
                    HttpMethod.DELETE, endpoint, response.getStatusCode());
            throw new ApiErrorException(message);
        }

        return response.getBody();
    }
}
