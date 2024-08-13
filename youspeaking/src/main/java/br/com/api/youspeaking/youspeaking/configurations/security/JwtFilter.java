package br.com.api.youspeaking.youspeaking.configurations.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.api.youspeaking.youspeaking.configurations.security.exception.JwtFilterException;
import br.com.api.youspeaking.youspeaking.configurations.security.utils.UtilsJwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(1)
public class JwtFilter extends OncePerRequestFilter  {

    @Value("${you-speaking.devmode}")
    private boolean isDevMode;

    @Value("${you-speaking.devmode.token}")
    private String tokenDevmode;

     @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
      throws ServletException {
        String path = request.getRequestURI();
        boolean isShouldNotFilter = path.contains("/actuator");
        return isShouldNotFilter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        try {
            String authorization = requestWrapper.getHeader("Authorization");
            if (authorization == null || authorization.isEmpty()) {
                if(isDevMode){
                    authorization = tokenDevmode;
                }
            }
            ObjectNode mapaJwtToken = convertJwtIObjectNode(authorization);
            requestWrapper.setAttribute("jwtToken",     authorization);
            requestWrapper.setAttribute("jwtEmailUser", mapaJwtToken.get("jwtEmail"));
            requestWrapper.setAttribute("jwtLoginUser", mapaJwtToken.get("jwtLoginUsuario"));
            filterChain.doFilter(requestWrapper, responseWrapper);
        
        } catch (Exception e) {
            responseWrapper.setStatus(HttpStatus.FORBIDDEN.value());
        }
        responseWrapper.copyBodyToResponse();
    }


    private ObjectNode convertJwtIObjectNode(String authorization) throws Exception {
        ObjectNode mapaJwtToken = null;
        if (authorization == null && authorization.isEmpty()) {
            throw new JwtFilterException("O header 'Authorization' não está preenchido com o token JWT.");
        }
        String jsonData = UtilsJwt.getDataFromDecodeJWTToken(authorization);
        if (jsonData == null || jsonData.equals("") || jsonData.isEmpty() || jsonData.equals("{}") || jsonData.equals("[]")) {
            throw new JwtFilterException("O header 'Authorization' está com um Token JWT inválido.");
        } else {
            ObjectMapper mapper = new ObjectMapper();
            mapaJwtToken = mapper.createObjectNode();
            ObjectNode jsonMap = new ObjectMapper().readValue(jsonData, ObjectNode.class);
            jsonMap.put("jwtLoginUser", jsonMap.get("login").toString());
            jsonMap.put("jwtEmail",     jsonMap.get("email").toString());
            jsonMap.put("jwtOrigem",    jsonMap.get("origem").toString());
        }
        return mapaJwtToken;
    }
}
