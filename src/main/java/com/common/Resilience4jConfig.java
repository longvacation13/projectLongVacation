package com.common;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class Resilience4jConfig {

    private final CircuitBreakerRegistry circuitBreakerRegistry;

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        return CircuitBreakerRegistry.ofDefaults();
    }

    @Bean
    public CircuitBreaker defaultCircuitBreaker() {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)  // COUNT_BASED
                .slidingWindowSize(100)
                .failureRateThreshold(50F)  // 50% 실패시 OPEN
                .slowCallDurationThreshold(Duration.ofSeconds(30))  // 느린응답으로 판단하는 시간 설정
                .slowCallRateThreshold(50F) // 50% 느릴때 OPEN
                .minimumNumberOfCalls(10)   // 최소 동작을 위한 요청수 (10개의 요청을 가지고 판단한다는 의미)
                .waitDurationInOpenState(Duration.ofSeconds(10))     // OPEN에서 HALF-OPEN 전환 시간
                .automaticTransitionFromOpenToHalfOpenEnabled(true) // waitduration 이후 전환함 (자동전환)
                .permittedNumberOfCallsInHalfOpenState(10)  // half-open에서 허용되는 호출 수
                .build();
          return circuitBreakerRegistry.circuitBreaker("defaultCircuitBreaker", circuitBreakerConfig);
    }
}
