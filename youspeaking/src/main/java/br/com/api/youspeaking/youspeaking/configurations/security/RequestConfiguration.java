package br.com.api.youspeaking.youspeaking.configurations.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(3)
public class RequestConfiguration extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper   requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(requestWrapper, responseWrapper);
        String responseBody = getStringValue(responseWrapper.getContentAsByteArray(), responseWrapper.getCharacterEncoding());
        handleRequests(requestWrapper, responseWrapper, responseBody);
        responseWrapper.copyBodyToResponse();
    }

    private void handleRequests(ContentCachingRequestWrapper requestWrapper, ContentCachingResponseWrapper responseWrapper, String responseBody) {
        switch (requestWrapper.getMethod()) {
            case "GET" -> handleRequestGet(responseWrapper, responseBody);
            case "POST" -> handleRequestPost(responseWrapper, responseBody);
            case "PUT" -> handleRequestPut(responseWrapper, responseBody);
            case "DELETE" -> handleRequestDelete(responseWrapper, responseBody);
        }
    }

    private void handleRequestGet(ContentCachingResponseWrapper responseWrapper, String responseBody) {
        if (responseBody == null || responseBody.isEmpty() || responseBody.equals("{}") || responseBody.equals("[]")) {
            responseWrapper.setStatus(HttpStatus.NO_CONTENT.value());
        }
    }

    private void handleRequestPost(ContentCachingResponseWrapper responseWrapper, String responseBody) {
        if (responseBody == null || responseBody.isEmpty() || responseBody.equals("{}") || responseBody.equals("[]")) {
            responseWrapper.setStatus(HttpStatus.CREATED.value());
        }
    }

    private void handleRequestPut(ContentCachingResponseWrapper responseWrapper, String responseBody) {
        if (responseBody == null || responseBody.isEmpty() || responseBody.equals("{}") || responseBody.equals("[]")) {
            responseWrapper.setStatus(HttpStatus.CREATED.value());
        }    
    }

    private void handleRequestDelete(ContentCachingResponseWrapper responseWrapper, String responseBody) {
        if (responseBody == null || responseBody.isEmpty() || responseBody.equals("{}") || responseBody.equals("[]")) {
            responseWrapper.setStatus(HttpStatus.CREATED.value());
        }
    }

    private String getStringValue(byte[] contentAsByteArray, String characterEncoding) {
        try {
            return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
