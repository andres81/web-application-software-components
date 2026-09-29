package nl.andreschepers.webapplicationsoftwarecomponents.springbootwebapplication.httpclient.monitoring;

import io.opentelemetry.api.GlobalOpenTelemetry;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;

public class OTLPApachePoolMetrics {

  /**
   * See <a
   * href="https://opentelemetry.io/docs/specs/semconv/general/metrics/#instrument-units">OTLP
   * docs</a> for more information on units, i.e. "{connection}" for example.
   *
   * @param manager
   */
  public static void configureOTLPApachePoolMetrics(
      PoolingHttpClientConnectionManager manager,
      String instrumentationScopeName,
      String gaugeBuilderNamePrefix) {
    var meter = GlobalOpenTelemetry.getOrNoop().getMeter(instrumentationScopeName);

    meter
        .gaugeBuilder(gaugeBuilderNamePrefix + ".maxTotal")
        .setUnit("{connection}")
        .setDescription("Max number of connections")
        .buildWithCallback(
            (observer) -> {
              observer.record(manager.getTotalStats().getMax());
            });

    meter
        .gaugeBuilder(gaugeBuilderNamePrefix + ".available")
        .setUnit("{connection}")
        .setDescription("Number of available connections")
        .buildWithCallback(
            (observer) -> {
              observer.record(manager.getTotalStats().getAvailable());
            });

    meter
        .gaugeBuilder(gaugeBuilderNamePrefix + ".pending")
        .setUnit("{request}")
        .setDescription("Number of pending connections")
        .buildWithCallback(
            (observer) -> {
              observer.record(manager.getTotalStats().getPending());
            });

    meter
        .gaugeBuilder(gaugeBuilderNamePrefix + ".leased")
        .setUnit("{connection}")
        .setDescription("Number of leased connections")
        .buildWithCallback(
            (observer) -> {
              observer.record(manager.getTotalStats().getLeased());
            });
  }
}
