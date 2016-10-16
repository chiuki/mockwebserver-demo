package com.sqisland.mockwebserver_demo;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

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
  public void followers() throws IOException, InterruptedException {
    Dispatcher dispatcher = new Dispatcher() {
      @Override
      public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
        String path = request.getPath();
        String[] parts = path.split("/");
        String username = parts[parts.length-1];
        return new MockResponse().setBody(
            "{ \"login\" : \"" + username + "\", \"followers\" : " + username.length() + " }"
        );
      }
    };
    mockWebServerRule.server.setDispatcher(dispatcher);

    activityRule.launchActivity(null);

    onView(withId(R.id.followers_1))
        .check(matches(withText("octocat: 7")));
    onView(withId(R.id.followers_2))
        .check(matches(withText("swankjesse: 10")));
    onView(withId(R.id.followers_3))
        .check(matches(withText("chiuki: 6")));
  }
}