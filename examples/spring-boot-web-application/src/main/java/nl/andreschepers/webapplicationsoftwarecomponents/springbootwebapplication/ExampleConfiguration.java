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

package nl.andreschepers.webapplicationsoftwarecomponents.springbootwebapplication;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import nl.andreschepers.webapplicationsoftwarecomponents.springbootwebapplication.httpclient.springrestclientdecorator.ApacheHttpClientJsonRequestJsonResponseTypedSpringRestClientDecoratorImplementation;
import nl.andreschepers.webapplicationsoftwarecomponents.springbootwebapplication.httpclient.springrestclientdecorator.ISpringRestClientDecorator;
import nl.andreschepers.webapplicationsoftwarecomponents.springbootwebapplication.httpclient.factory.SpringRestClientFactory;
import nl.andreschepers.webapplicationsoftwarecomponents.springbootwebapplication.httpclient.factory.TLSVersion;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExampleConfiguration {

  @Bean("send-email-http-client")
  public ISpringRestClientDecorator apacheHttpClient() {
    var clientManagerPairDto = createRestClientPoolManagerPair();
    return new ApacheHttpClientJsonRequestJsonResponseTypedSpringRestClientDecoratorImplementation(
        clientManagerPairDto.restClient(),
        clientManagerPairDto.manager(),
        CircuitBreaker.of("Circuit breakertje", CircuitBreakerConfig.ofDefaults()),
        RateLimiter.of("RateLimiter breakertje", RateLimiterConfig.ofDefaults()));
  }

  private SpringRestClientFactory.HttpClientAndApachePoolingConnManagerDto
      createRestClientPoolManagerPair() {
    return SpringRestClientFactory.createWithApachePoolingConnManager(
        new SpringRestClientFactory.ConfigurationDto(
            2000,
            2000,
            2000,
            200,
            50,
            3000,
            NoopHostnameVerifier.INSTANCE,
            null,
            TLSVersion.TLS_1_3));
  }
}
