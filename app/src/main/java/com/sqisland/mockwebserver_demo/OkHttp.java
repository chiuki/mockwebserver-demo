package com.sqisland.mockwebserver_demo;


import okhttp3.OkHttpClient;

public abstract class OkHttp {
  private static OkHttpClient instance = null;

  public static OkHttpClient getInstance() {
    if (instance == null) {
      instance = new OkHttpClient();
    }
    return instance;
  }
}