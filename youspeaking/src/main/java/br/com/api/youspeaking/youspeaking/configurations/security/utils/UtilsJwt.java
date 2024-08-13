package br.com.api.youspeaking.youspeaking.configurations.security.utils;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.xml.bind.DatatypeConverter;

public class UtilsJwt {
    
    public static final Long    JWT_TOKEN_VALIDITY              = 5L * 60L * 60L;

    public  final static String INT_JWT_YOU_SPEAKING            = "5";
    public  final static String INT_JWT_APP_MODULE              = "6";
    
    private final static String INT_JWT_COD_PAR_AUTH_ISSUER     = "INT_JWT_AUTH_ISSUER";
    private final static String INT_JWT_COD_PAR_AUTH_SUBJECT    = "INT_JWT_AUTH_SUBJECT";
    private final static String INT_JWT_COD_PAR_AUTH_EXPIRATION = "INT_JWT_AUTH_EXPIRATION";
    private final static String INT_JWT_COD_PAR_AUTH_ALGORITHM  = "INT_JWT_AUTH_ALGORITHM";
    private final static String INT_JWT_COD_PAR_AUTH_SECRET_KEY = "INT_JWT_AUTH_SECRET_KEY";
    private final static String INT_JWT_COD_PAR_AUTH_URL        = "INT_JWT_AUTH_URL";
    private final static String INT_JWT_COD_PAR_AUTH_REDIRECT   = "INT_JWT_AUTH_ALLOW_REDIRECT";

    private static ObjectNode getJWTParams() throws Exception {
    	return generaObjectNodeJwtParams();
    }
    
    private static ObjectNode generaObjectNodeJwtParams() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
    	ObjectNode map = mapper.createObjectNode();
    	
		map.put(INT_JWT_COD_PAR_AUTH_ISSUER,     "youSpeakingApp");
		map.put(INT_JWT_COD_PAR_AUTH_SUBJECT,    "jwt-auth");
		map.put(INT_JWT_COD_PAR_AUTH_EXPIRATION, "");
		map.put(INT_JWT_COD_PAR_AUTH_ALGORITHM,  "HS256");
		map.put(INT_JWT_COD_PAR_AUTH_SECRET_KEY, "a239824573331ee372214593ce2f3b23871452d141cdc13922faaade8fc21cf11c883cfe111f06bae287123b5132a8e0deafc02cf73acfebb9771f9cf261f3fd");
		map.put(INT_JWT_COD_PAR_AUTH_URL,        "");
		map.put(INT_JWT_COD_PAR_AUTH_REDIRECT,   "YES");
    	
    	return map;
    }   
    
    public static String generateJWTToken(String userId) throws Exception {

    	ObjectNode configMap = getJWTParams();    	
    	
		//The JWT signature algorithm we will be using to sign the token
    	SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		//We will sign our JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(configMap.get(INT_JWT_COD_PAR_AUTH_SECRET_KEY).toString());
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		//Let's set the JWT Claims
		 String data =  ("{ \"codIdeCli\":  \""+userId+"\" }");

		JwtBuilder builder = Jwts.builder().setId("1L")
				.setIssuedAt(now)
				.setSubject (configMap.get(INT_JWT_COD_PAR_AUTH_SUBJECT).toString())
				.setIssuer  (configMap.get(INT_JWT_COD_PAR_AUTH_ISSUER).toString())
				.claim      ("data", data)
				.signWith   (signatureAlgorithm, signingKey);
		
		
		String ttlMillisStr = configMap.get(INT_JWT_COD_PAR_AUTH_EXPIRATION).toString(); 
		long ttlMillis = (!ttlMillisStr.replaceAll("^[0-9]", "").equals("") ? new Long(ttlMillisStr.replaceAll("^[0-9]", "")) : -1);
		
		//if it has been specified, let's add the expiration
		if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
		
		//Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();
	}   
    
    public static String generateJWTToken(String codNumProjeto, String dataJson) throws Exception {
		ObjectNode configMap = getJWTParams();  
	
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(configMap.get(INT_JWT_COD_PAR_AUTH_SECRET_KEY).toString());
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
	
		JwtBuilder builder = Jwts.builder().setId("1L")
			.setIssuedAt(now)
			.setSubject (configMap.get(INT_JWT_COD_PAR_AUTH_SUBJECT).toString())
			.setIssuer  (configMap.get(INT_JWT_COD_PAR_AUTH_ISSUER).toString())
			.claim      ("data", dataJson)
			.signWith   (signatureAlgorithm, signingKey)
		    .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000));
				
		return builder.compact();
    }     

    
    public static Claims decodeJWTToken(String token) throws Exception {
    	ObjectNode configMap = getJWTParams();
    	
    	Claims claims = null;
    	try {
        	claims = Jwts.parser().setSigningKey(configMap.get(INT_JWT_COD_PAR_AUTH_SECRET_KEY).toString()).parseClaimsJws(token).getBody();    	
    	
    	} catch (ExpiredJwtException e) {
    		e.printStackTrace();
    		//throw new MsgExcept("Ocorreu um erro na tentativa de recuperar informações ao sistema. Contate o Administrador (JWT Expired Error).");
    	} catch (Exception e) {
    		e.printStackTrace();
    		//throw new MsgExcept("Ocorreu um erro na tentativa de recuperar informações ao sistema. Contate o Administrador (JWT Error).");
    	}
    	return claims;
	}
    
    public static String getDataFromDecodeJWTToken(String token) throws Exception {
    	Claims claims = decodeJWTToken(token);
    	String data   = "";
    	if (claims != null) {
    		data          = (String) claims.get("data");
    	}
    	return data;    	
    }
    
    public static String generateJWTToken(String codNum, ObjectNode mapValores) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"").append("ambiente").             append("\"").append(":").append("\"").append(mapValores.get("ambiente")).             append("\"").append(",");
		sb.append("\"").append("login").                append("\"").append(":").append("\"").append(mapValores.get("login")).                append("\"").append(",");
		sb.append("\"").append("email").                append("\"").append(":").append("\"").append(mapValores.get("email")).                append("\"").append(",");
		sb.append("\"").append("origem").               append("\"").append(":").append("\"").append(mapValores.get("origem")).               append("\"").append(",");
		sb.append("}");
		return generateJWTToken(codNum, sb.toString());
    } 
}
