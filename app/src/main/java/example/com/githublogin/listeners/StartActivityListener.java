package example.com.githublogin.listeners;

import android.view.View;

import example.com.githublogin.pojos.UserRepoDetails;


public interface StartActivityListener {
    public void startActivityMethod(UserRepoDetails userRepoDetails, View v);
}
