package com.sqisland.mockwebserver_demo;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public abstract class OkHttp {
  private static OkHttpClient instance = null;

  public static OkHttpClient getInstance() {
    if (instance == null) {
      instance = new OkHttpClient.Builder()
          .readTimeout(1, TimeUnit.SECONDS)
          .connectTimeout(1, TimeUnit.SECONDS)
          .build();
    }
    return instance;
  }
}