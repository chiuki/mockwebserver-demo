package com.sqisland.mockwebserver_demo;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import okhttp3.mockwebserver.MockResponse;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class MainActivityTest {
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
  public void followers() {
    mockWebServerRule.server.enqueue(new MockResponse().setBody(
        "{ \"login\" : \"octocat\", \"followers\" : 1500 }"));

    activityRule.launchActivity(null);

    onView(withId(R.id.followers))
        .check(matches(withText("octocat: 1500")));
  }
}