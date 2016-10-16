package com.sqisland.mockwebserver_demo;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.internal.tls.SslClient;

public class TestDemoApplication extends DemoApplication {
  private String baseUrl;

  @Override
  public String getBaseUrl() {
    return baseUrl;
  }

  public void setBaseUrl(String url) {
    baseUrl = url;
  }

  @Override
  public SSLSocketFactory getSSLSocketFactory() {
    return SslClient.localhost().socketFactory;
  }

  @Override
  public X509TrustManager getX509TrustManager() {
    return SslClient.localhost().trustManager;
  }
}