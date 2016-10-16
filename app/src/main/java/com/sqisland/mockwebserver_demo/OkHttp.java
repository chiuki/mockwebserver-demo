package com.sqisland.mockwebserver_demo;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;

public abstract class OkHttp {
  private static OkHttpClient instance = null;

  public static OkHttpClient getInstance() {
    if (instance == null) {
      instance = new OkHttpClient.Builder()
          .certificatePinner(createCertificatePinner())
          .build();
    }
    return instance;
  }

  private static CertificatePinner createCertificatePinner() {
    String hostname = "api.github.com";
    return new CertificatePinner.Builder()
            .add(hostname, "sha256/6wJsqVDF8K19zxfLxV5DGRneLyzso9adVdUN/exDacw")
            .add(hostname, "sha256/k2v657xBsOVe1PQRwOsHsw3bsGT2VzIqz5K+59sNQws=")
            .add(hostname, "sha256/WoiWRyIOVNa9ihaBciRSC7XHjliYS9VwUGOIud4PB18=")
            .build();
  }
}