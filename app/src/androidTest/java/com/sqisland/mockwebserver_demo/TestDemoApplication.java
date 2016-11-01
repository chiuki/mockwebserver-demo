package com.sqisland.mockwebserver_demo;

import io.appflate.restmock.RESTMockServer;

public class TestDemoApplication extends DemoApplication {
  @Override
  public String getBaseUrl() {
    return RESTMockServer.getUrl();
  }
}