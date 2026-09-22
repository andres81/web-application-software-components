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

package nl.andreschepers.webapplicationsoftwarecomponents.springbootwebapplication.httpclient.springrestclientdecorator;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.Getter;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Getter
public class ApacheHttpClientJsonRequestJsonResponseTypedSpringRestClientDecoratorImplementation
    extends AbstractSpringRestClientDecorator {

  private final PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;

  public ApacheHttpClientJsonRequestJsonResponseTypedSpringRestClientDecoratorImplementation(
      RestClient restClient,
      PoolingHttpClientConnectionManager poolingHttpClientConnectionManager,
      CircuitBreaker circuitBreaker,
      RateLimiter rateLimiter) {
    super(
        restClient,
        circuitBreaker,
        rateLimiter,
        MediaType.APPLICATION_JSON,
        MediaType.APPLICATION_JSON);
    this.poolingHttpClientConnectionManager = poolingHttpClientConnectionManager;
  }
}
