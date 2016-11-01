package com.sqisland.mockwebserver_demo;

import android.app.Application;
import android.content.Context;

import io.appflate.restmock.android.RESTMockTestRunner;

public class CustomTestRunner extends RESTMockTestRunner {
  @Override
  public Application newApplication(
      ClassLoader cl, String className, Context context)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    return super.newApplication(cl, TestDemoApplication.class.getName(), context);
  }
}