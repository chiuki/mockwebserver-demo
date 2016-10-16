package com.sqisland.mockwebserver_demo;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;

public class MainActivityTest {
  private static final String OCTOCAT_BODY = "{ \"login\" : \"octocat\", \"followers\" : 1500 }";

  @Rule
  public ActivityTestRule<MainActivity> activityRule
      = new ActivityTestRule<>(MainActivity.class, true, false);

  @Rule
  public OkHttpIdlingResourceRule okHttpIdlingResourceRule = new OkHttpIdlingResourceRule();

  @Rule
  public MockWebServerRule mockWebServerRule = new MockWebServerRule();

  @Before
  public void setBaseUrl() {
    TestDemoApplication app = (TestDemoApplication)
        InstrumentationRegistry.getTargetContext().getApplicationContext();
    app.setBaseUrl(mockWebServerRule.server.url("/").toString());
  }

  @Test
  public void followers() throws IOException, InterruptedException {
    mockWebServerRule.server.enqueue(new MockResponse().setBody(OCTOCAT_BODY));

    activityRule.launchActivity(null);

    onView(withId(R.id.followers))
        .check(matches(withText("octocat: 1500")));

    RecordedRequest request = mockWebServerRule.server.takeRequest();
    assertEquals("/users/octocat", request.getPath());
  }

  @Test
  public void status404() throws IOException {
    mockWebServerRule.server.enqueue(new MockResponse().setResponseCode(404));

    activityRule.launchActivity(null);

    onView(withId(R.id.followers))
        .check(matches(withText("404")));
  }

  @Test
  public void malformedJson() throws IOException {
    mockWebServerRule.server.enqueue(new MockResponse().setBody("Jason"));

    activityRule.launchActivity(null);

    onView(withId(R.id.followers))
        .check(matches(withText("IOException")));
  }

  @Test
  public void timeout() throws IOException {
    mockWebServerRule.server.enqueue(
        new MockResponse().setBody(OCTOCAT_BODY).throttleBody(1, 1, TimeUnit.SECONDS));

    activityRule.launchActivity(null);

    onView(withId(R.id.followers))
        .check(matches(withText("SocketTimeoutException")));
  }
}