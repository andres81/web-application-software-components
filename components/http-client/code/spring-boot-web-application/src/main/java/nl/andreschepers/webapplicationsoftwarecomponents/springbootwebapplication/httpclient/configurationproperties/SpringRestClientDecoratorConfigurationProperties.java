/*
 * Copyright 2026 André Schepers
 *
 * Licensed under the Apache License; Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing; software
 * distributed under the License is distributed on an "AS IS" BASIS;
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND; either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.andreschepers.webapplicationsoftwarecomponents.springbootwebapplication.httpclient.configurationproperties;

import java.util.List;
import javax.net.ssl.SSLContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.andreschepers.webapplicationsoftwarecomponents.springbootwebapplication.httpclient.factory.TLSVersion;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "nl.andreschepers.example.springrestclient.email")
public class SpringRestClientDecoratorConfigurationProperties {

  private final int connectionTimeoutMs;
  private final int socketTimeoutMs;
  private final int connectionRequestTimeoutMs;
  private final int maxTotalConnections;
  private final int maxConnectionsPerRoute;
  private final int tlsHandshakeTimeout;
  private final SSLContext sslContext;
  private final List<TLSVersion> tlsVersions;

  private final CircuitBreakerProperties circuitBreakerProperties;
  private final RateLimiterProperties rateLimiterProperties;

  public record CircuitBreakerProperties(
      int maxAttempts,
      double failureRateThreshold,
      double successRateThreshold,
      double requestVolumeThreshold) {}

  public record RateLimiterProperties(boolean enabled, int limitForPeriod, int period) {}
}
