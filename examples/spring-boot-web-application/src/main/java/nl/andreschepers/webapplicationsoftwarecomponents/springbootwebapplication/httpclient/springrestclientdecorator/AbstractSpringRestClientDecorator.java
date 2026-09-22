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
import java.net.URI;
import java.util.function.Function;
import java.util.function.Supplier;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

public abstract class AbstractSpringRestClientDecorator implements ISpringRestClientDecorator {

  private final RestClient restClient;
  private final CircuitBreaker circuitBreaker;
  private final RateLimiter rateLimiter;
  private final MediaType requestMediaType;
  private final MediaType responseMediaType;

  public AbstractSpringRestClientDecorator(
      RestClient restClient,
      CircuitBreaker circuitBreaker,
      RateLimiter rateLimiter,
      MediaType requestMediaType,
      MediaType responseMediaType) {
    this.restClient = restClient;
    this.circuitBreaker = circuitBreaker;
    this.rateLimiter = rateLimiter;
    this.requestMediaType = requestMediaType;
    this.responseMediaType = responseMediaType;
  }

  @Override
  public <T> ResponseEntity<T> makeGetRequest(
      URI uri,
      Class<T> responseType,
      RestClient.ResponseSpec.ErrorHandler errorHandler4XX,
      RestClient.ResponseSpec.ErrorHandler errorHandler5XX) {
    return wrapAndExecuteRequest(
        () ->
            restClient
                .get()
                .uri(uri)
                .accept(responseMediaType)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, errorHandler4XX)
                .onStatus(HttpStatusCode::is5xxServerError, errorHandler5XX)
                .toEntity(responseType));
  }

  @Override
  public <T, R> ResponseEntity<T> makePostRequest(
      URI uri,
      R body,
      Class<T> responseType,
      RestClient.ResponseSpec.ErrorHandler errorHandler4XX,
      RestClient.ResponseSpec.ErrorHandler errorHandler5XX) {
    return wrapAndExecuteRequest(
        (requestBody) ->
            restClient
                .post()
                .uri(uri)
                .contentType(requestMediaType)
                .body(requestBody)
                .accept(responseMediaType)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, errorHandler4XX)
                .onStatus(HttpStatusCode::is5xxServerError, errorHandler5XX)
                .toEntity(responseType),
        body);
  }

  @Override
  public <T, R> ResponseEntity<T> makePutRequest(
      URI uri,
      R body,
      Class<T> responseType,
      RestClient.ResponseSpec.ErrorHandler errorHandler4XX,
      RestClient.ResponseSpec.ErrorHandler errorHandler5XX) {
    return wrapAndExecuteRequest(
        (requestBody) ->
            restClient
                .put()
                .uri(uri)
                .contentType(requestMediaType)
                .body(requestBody)
                .accept(responseMediaType)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, errorHandler4XX)
                .onStatus(HttpStatusCode::is5xxServerError, errorHandler5XX)
                .toEntity(responseType),
        body);
  }

  private <T, R> R wrapAndExecuteRequest(Function<T, R> requestFunction, T request) {
    return RateLimiter.decorateFunction(
            rateLimiter, CircuitBreaker.decorateFunction(circuitBreaker, requestFunction))
        .apply(request);
  }

  private <R> R wrapAndExecuteRequest(Supplier<R> requestFunction) {
    return RateLimiter.decorateSupplier(
            rateLimiter, CircuitBreaker.decorateSupplier(circuitBreaker, requestFunction))
        .get();
  }
}
