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

package nl.andreschepers.webapplicationsoftwarecomponents.springbootwebapplication.httpclient.factory;

import java.util.Arrays;
import org.apache.hc.core5.http.ssl.TLS;

public enum TLSVersion {
  TLS_1_3(TLS.V_1_3);

  private final TLS apacheTlsVersion;

  TLSVersion(final TLS apacheTlsVersion) {
    this.apacheTlsVersion = apacheTlsVersion;
  }

  public static TLS[] getApacheTlsVersions(TLSVersion... tlsVersions) {
    return Arrays.stream(tlsVersions)
        .map(tlsVersion -> tlsVersion.apacheTlsVersion)
        .toList()
        .toArray(new TLS[0]);
  }
}
