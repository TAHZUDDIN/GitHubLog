package example.com.githublogin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import example.com.githublogin.R;
import example.com.githublogin.extras.Constants;
import example.com.githublogin.utils.GitLoginApplicationClass;

public class LoginActivity extends AppCompatActivity implements Constants {


    CardView loginCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginCardView = (CardView) findViewById(R.id.id_cardView_Login);


        // Checking the boolean value to check whether user is logged in or not
        if (!GitLoginApplicationClass.getCommonSharedPreference().getBoolean(LOGGEDIN_OR_NOT, false)) {

            loginCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivityMethod();
                }
            });

        } else
        {
            startActivityMethod();
        }

    }


    public void startActivityMethod() {
        Intent i = new Intent(LoginActivity.this, UserProAndRepoActivity.class);
        startActivity(i);
        finish();

    }
}
