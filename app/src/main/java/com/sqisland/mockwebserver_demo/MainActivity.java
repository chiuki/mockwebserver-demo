package com.sqisland.mockwebserver_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final TextView textView = (TextView) findViewById(R.id.followers);

    DemoApplication app = (DemoApplication) getApplication();
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(app.getBaseUrl())
        .addConverterFactory(MoshiConverterFactory.create())
        .client(OkHttp.getInstance(app.getSSLSocketFactory(), app.getX509TrustManager()))
        .build();

    GitHubService service = retrofit.create(GitHubService.class);
    service.getUser("octocat").enqueue(new Callback<User>() {
      @Override
      public void onResponse(Call<User> call, Response<User> response) {
        if (response.isSuccessful()) {
          User user = response.body();
          textView.setText(getString(R.string.login_followers, user.login, user.followers));
        } else {
          textView.setText(String.valueOf(response.code()));
        }
      }
      @Override
      public void onFailure(Call<User> call, Throwable t) {
        textView.setText(t.getClass().getSimpleName());
      }
    });
  }
}