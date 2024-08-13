package br.com.api.youspeaking.youspeaking.configurations.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(2)
public class RequestFilter extends GenericFilterBean {

    @Value("${you-speaking.devmode}")
    private boolean isModoDesenvolvedorLocalhost;

    @Autowired
    private HttpServletRequest request;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        ContentCachingRequestWrapper requestWrapper   = new ContentCachingRequestWrapper(httpServletRequest);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(httpServletResponse);

        String requestBody = getStringValue(requestWrapper.getContentAsByteArray(), request.getCharacterEncoding());

        long tempoInicio = System.nanoTime();
        chain.doFilter(requestWrapper, responseWrapper);
        long tempoFim = System.nanoTime();

        String responseBody = getStringValue(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8.toString());

        printarDetalhesServico(httpServletRequest, httpServletResponse, requestBody, responseBody, tempoInicio, tempoFim);

        requestBody = getStringValue(requestWrapper.getContentAsByteArray(), request.getCharacterEncoding());

        responseWrapper.copyBodyToResponse();
    }

    private void printarDetalhesServico(HttpServletRequest request, HttpServletResponse response, String requestBody, String responseBody, long tempoInicio, long tempoFim){
        StringBuilder sbParametros = new StringBuilder();
        request.getParameterMap().forEach((key, value)->{
            for(String valor : value){
                sbParametros.append(key).append("=").append(valor).append("&");
            }
        });
        if(sbParametros.length() != 0)
            sbParametros.deleteCharAt(sbParametros.length() - 1);

        StringBuilder sb = new StringBuilder();

        sb.append("******************************************************************************************************************************************").append("\n");
        sb.append("Servico -> " + request.getRequestURI() ).append("\n");
        sb.append("Parâmetros   -> " + sbParametros).append("\n");
        sb.append("Body         -> " + requestBody).append("\n");
        sb.append("Response Status      -> " + response.getStatus()).append("\n");
        sb.append("Response Body        -> " + responseBody).append("\n");
        sb.append("==========================================================================================================================================").append("\n");

        System.out.println(sb);
    }

    private String getStringValue(byte[] contentAsByteArray, String characterEncoding) {
        try {
            return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
