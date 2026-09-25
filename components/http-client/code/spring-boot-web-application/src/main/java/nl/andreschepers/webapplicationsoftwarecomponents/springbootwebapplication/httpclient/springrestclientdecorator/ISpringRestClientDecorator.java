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

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

public interface ISpringRestClientDecorator {

  <T> ResponseEntity<T> makeGetRequest(
      URI uri,
      Class<T> responseType,
      RestClient.ResponseSpec.ErrorHandler errorHandler4XX,
      RestClient.ResponseSpec.ErrorHandler errorHandler5XX);

  <T, R> ResponseEntity<T> makePostRequest(
      URI uri,
      R body,
      Class<T> responseType,
      RestClient.ResponseSpec.ErrorHandler errorHandler4XX,
      RestClient.ResponseSpec.ErrorHandler errorHandler5XX);

  <T, R> ResponseEntity<T> makePutRequest(
      URI uri,
      R body,
      Class<T> responseType,
      RestClient.ResponseSpec.ErrorHandler errorHandler4XX,
      RestClient.ResponseSpec.ErrorHandler errorHandler5XX);
}
