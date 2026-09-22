/*
 * Copyright 2026 André Schepers
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.andreschepers.webapplicationsoftwarecomponents.springbootwebapplication.httpclient;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import org.springframework.http.MediaType;

public class ApacheHttpClientJsonRequestJsonResponseTypedSpringRestClientImplementation
    extends AbstractSpringRestClientFacade {

  public ApacheHttpClientJsonRequestJsonResponseTypedSpringRestClientImplementation(
      SpringRestClientFactory.WithApachePoolingConnManagerGivenExistingSSLContextConfigurationDto
          configDto,
      CircuitBreakerConfig circuitBreakerConfig,
      RateLimiterConfig rateLimiterConfig,
      MediaType requestMediaType,
      MediaType responseMediaType) {
    super(
        SpringRestClientFactory.createWithApachePoolingConnManager(configDto),
        CircuitBreaker.of("circuit brekertje", circuitBreakerConfig),
        RateLimiter.of("rate limietje", rateLimiterConfig),
        requestMediaType,
        responseMediaType);
  }
}
