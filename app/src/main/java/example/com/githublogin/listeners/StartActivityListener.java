package example.com.githublogin.listeners;

import android.view.View;

import example.com.githublogin.pojos.UserRepoDetails;

//Interface to  use from Adapter to Activity for sending data
public interface StartActivityListener {
    public void startActivityMethod(UserRepoDetails userRepoDetails, View v);
}
