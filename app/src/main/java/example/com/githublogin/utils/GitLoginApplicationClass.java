package example.com.githublogin.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import example.com.githublogin.extras.Constants;


public class GitLoginApplicationClass extends Application implements Constants {


    private static Context context;
    public static SharedPreferences commonSharedPreference;

    @Override
    public void onCreate() {
        super.onCreate();
        GitLoginApplicationClass.context = getApplicationContext();
        commonSharedPreference = context.getSharedPreferences(Constants.COMMON_SHARED_PREFERENCE, Context.MODE_PRIVATE);

    }

    public String getAccessToken() {
        String token;
        token = GitLoginApplicationClass.getCommonSharedPreference().getString(Constants.ACCESS_TOKEN, null);
        return token;
    }

    public static Context getContext() {
        return context;
    }

    public static SharedPreferences getCommonSharedPreference() {
        return commonSharedPreference;
    }

}
