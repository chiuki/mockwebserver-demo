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
  private static final String OCTOCAT_BODY = "{ \"login\" : \"octocat\", \"followers\" : 1500 }";
  private static final String JESSE_BODY = "{ \"login\" : \"swankjesse\", \"followers\" : 2400 }";
  private static final String CHIUKI_BODY = "{ \"login\" : \"chiuki\", \"followers\" : 1000 }";

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
    mockWebServerRule.server.enqueue(new MockResponse().setBody(OCTOCAT_BODY));
    mockWebServerRule.server.enqueue(new MockResponse().setBody(JESSE_BODY));
    mockWebServerRule.server.enqueue(new MockResponse().setBody(CHIUKI_BODY));

    activityRule.launchActivity(null);

    onView(withId(R.id.followers_1))
        .check(matches(withText("octocat: 1500")));
    onView(withId(R.id.followers_2))
        .check(matches(withText("swankjesse: 2400")));
    onView(withId(R.id.followers_3))
        .check(matches(withText("chiuki: 1000")));
  }
}