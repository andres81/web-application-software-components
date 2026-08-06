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

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.config.TlsConfig;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.*;
import org.apache.hc.core5.http.ssl.TLS;
import org.apache.hc.core5.pool.PoolConcurrencyPolicy;
import org.apache.hc.core5.pool.PoolReusePolicy;
import org.apache.hc.core5.util.Timeout;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

public final class SpringRestClientFactory {

  private SpringRestClientFactory() {}

  public static HttpClientAndApachePoolingConnManagerDto createWithApachePoolingConnManager(
      WithApachePoolingConnManagerGivenExistingSSLContextConfigurationDto configuration) {

    PoolingHttpClientConnectionManager manager =
        PoolingHttpClientConnectionManagerBuilder.create()
            .setTlsSocketStrategy(
                ClientTlsStrategyBuilder.create()
                    .setSslContext(configuration.sslContext())
                    .setHostnameVerifier(configuration.hostnameVerifier())
                    .setTlsVersions(configuration.tlsVersions())
                    .buildClassic())
            .setDefaultTlsConfig(
                TlsConfig.custom()
                    .setHandshakeTimeout(
                        Timeout.ofMilliseconds(configuration.tlsHandshakeTimeout()))
                    .build())
            .setDefaultConnectionConfig(
                ConnectionConfig.custom()
                    .setConnectTimeout(Timeout.ofMilliseconds(configuration.connectionTimeoutMs()))
                    .setSocketTimeout(Timeout.ofMilliseconds(configuration.socketTimeoutMs()))
                    .build())
            .setMaxConnPerRoute(configuration.maxConnPerRoute())
            .setMaxConnTotal(configuration.maxConnTotal())
            .setPoolConcurrencyPolicy(PoolConcurrencyPolicy.STRICT)
            .setConnPoolPolicy(PoolReusePolicy.LIFO)
            .build();

    var requestConfig =
        RequestConfig.custom()
            .setConnectionRequestTimeout(
                Timeout.ofMilliseconds(configuration.connectionRequestTimeoutMs()))
            .build();

    var client =
        HttpClients.custom()
            .setDefaultRequestConfig(requestConfig)
            .setConnectionManager(manager)
            .build();

    var factory = new HttpComponentsClientHttpRequestFactory(client);
    var bufferedFactory = new BufferingClientHttpRequestFactory(factory);
    var restClient = RestClient.builder().requestFactory(bufferedFactory).build();
    return new HttpClientAndApachePoolingConnManagerDto(restClient, manager);
  }

  public record WithApachePoolingConnManagerGivenExistingSSLContextConfigurationDto(
      int connectionTimeoutMs,
      int socketTimeoutMs,
      int connectionRequestTimeoutMs,
      int maxConnTotal,
      int maxConnPerRoute,
      int tlsHandshakeTimeout,
      HostnameVerifier hostnameVerifier,
      SSLContext sslContext,
      TLS... tlsVersions) {}

  public record HttpClientAndApachePoolingConnManagerDto(
      RestClient restClient, PoolingHttpClientConnectionManager manager) {}
}
