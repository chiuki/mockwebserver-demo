package com.sqisland.mockwebserver_demo;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

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

  @Test
  public void followers() throws IOException {
    MockWebServer server = new MockWebServer();
    server.start();

    TestDemoApplication app = (TestDemoApplication)
        InstrumentationRegistry.getTargetContext().getApplicationContext();
    app.setBaseUrl(server.url("/").toString());

    server.enqueue(new MockResponse().setBody("{ \"login\" : \"octocat\", \"followers\" : 1500 }"));

    activityRule.launchActivity(null);

    onView(withId(R.id.followers))
        .check(matches(withText("octocat: 1500")));

    server.shutdown();
  }
}