package com.sqisland.mockwebserver_demo;

import android.app.Application;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

public class DemoApplication extends Application {
  public String getBaseUrl() {
    return "https://api.github.com";
  }

  public SSLSocketFactory getSSLSocketFactory() {
    return null;  // Use system default
  }

  public X509TrustManager getX509TrustManager() {
    return null;  // Use system default
  }
}