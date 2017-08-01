package com.sqisland.mockwebserver_demo;

import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import io.appflate.restmock.RESTMockServer;
import io.appflate.restmock.RequestsVerifier;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static io.appflate.restmock.utils.RequestMatchers.pathEndsWith;
import static io.appflate.restmock.utils.RequestMatchers.pathStartsWith;

public class MainActivityTest {
  @Rule
  public ActivityTestRule<MainActivity> activityRule
      = new ActivityTestRule<>(MainActivity.class, true, false);

  @Rule
  public OkHttpIdlingResourceRule okHttpIdlingResourceRule = new OkHttpIdlingResourceRule();

  @Before
  public void reset() {
    RESTMockServer.reset();
  }

  @Test
  public void followers() {
    RESTMockServer.whenGET(pathEndsWith("octocat"))
        .thenReturnFile("users/octocat.json");

    activityRule.launchActivity(null);

    onView(withId(R.id.followers))
        .check(matches(withText("octocat: 1500")));

    RequestsVerifier.verifyGET(pathStartsWith("/users/octocat")).invoked();
  }

  @Test
  public void status404() {
    RESTMockServer.whenGET(pathEndsWith("octocat"))
        .thenReturnEmpty(404);

    activityRule.launchActivity(null);

    onView(withId(R.id.followers))
        .check(matches(withText("404")));
  }
}