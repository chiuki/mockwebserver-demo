package com.sqisland.mockwebserver_demo;

public class TestDemoApplication extends DemoApplication {
  private String baseUrl;

  @Override
  public String getBaseUrl() {
    return baseUrl;
  }

  public void setBaseUrl(String url) {
    baseUrl = url;
  }
}