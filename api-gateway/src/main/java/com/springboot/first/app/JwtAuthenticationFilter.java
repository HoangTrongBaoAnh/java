package com.springboot.first.app;

import java.util.List;
import java.util.function.Predicate;

import org.apache.http.annotation.Obsolete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import com.springboot.first.app.exception.JwtTokenMalformedException;
import com.springboot.first.app.exception.JwtTokenMissingException;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GatewayFilter{
	
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();

		final List<String> apiEndpoints = List.of("/signin", "/signup");

		Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
				.noneMatch(uri -> r.getURI().getPath().contains(uri));

		if (isApiSecured.test(request)) {
			if (!request.getHeaders().containsKey("Authorization")) {
				ServerHttpResponse response = exchange.getResponse();
				response.setStatusCode(HttpStatus.UNAUTHORIZED);

				return response.setComplete();
			}

			List<String> token = request.getHeaders().getOrEmpty("Authorization");
			String oldtoken = token.get(0);
			oldtoken = oldtoken.replace("Bearer ", "");
			if(!jwtUtil.validateJwtToken(oldtoken)) {
				ServerHttpResponse response = exchange.getResponse();
				response.setStatusCode(HttpStatus.UNAUTHORIZED);

				return response.setComplete();
			}
			
//			try {
//				jwtUtil.validateToken(oldtoken);
//			} catch (JwtTokenMalformedException | JwtTokenMissingException e) {
//				// e.printStackTrace();
//
//				ServerHttpResponse response = exchange.getResponse();
//				response.setStatusCode(HttpStatus.BAD_REQUEST);
//
//				return response.setComplete();
//			}
//
//			Claims claims = jwtUtil.getClaims(token);
//			exchange.getRequest().mutate().header("id", String.valueOf(claims.get("id"))).build();
		}

		return chain.filter(exchange);
	}
	
	
//	private boolean isTokenValid(ServerWebExchange exchange) {
//        try {
//            HttpHeaders httpHeaders = exchange.getRequest().getHeaders();
//            List<String> tokens = httpHeaders.get("Authorization");
//
//            if (!isEmpty(tokens)) {
//                String oldToken = tokens.get(0);
//                oldToken = oldToken.replace("Bearer ", "");
//
//                Claims claims = Jwts.parser()
//                        .setSigningKey(getSystemEnvProperty(APP_SECRET_KEY, null))
//                        .parseClaimsJws(oldToken)
//                        .getBody();
//
//                return claims.getExpiration().after(new Date(System.currentTimeMillis()));
//            }
//        } catch (Exception ex) {
//            log.error("Error parsing request token: {}, {}", ex.getClass().getName(), ex.getMessage());
//        }
//
//        return false;
//    }
}
	

