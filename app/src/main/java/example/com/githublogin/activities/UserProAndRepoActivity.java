package example.com.githublogin.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.UUID;

import example.com.githublogin.R;
import example.com.githublogin.adapters.ReposAdapter;
import example.com.githublogin.apiobjects.AllUser;
import example.com.githublogin.extras.Constants;
import example.com.githublogin.listeners.StartActivityListener;
import example.com.githublogin.pojos.ParametersForAccessToken;
import example.com.githublogin.pojos.UserDetail;
import example.com.githublogin.pojos.UserRepoDetails;
import example.com.githublogin.utils.GitLoginApplicationClass;
import example.com.githublogin.utils.UtilityClass;
import example.com.githublogin.vollyserverapis.AppRequestListener;
import example.com.githublogin.vollyserverapis.BaseTask;
import example.com.githublogin.vollyserverapis.GetAccessToken;
import example.com.githublogin.vollyserverapis.GetUserDetails;
import example.com.githublogin.vollyserverapis.GetUserRepos;



public class UserProAndRepoActivity extends BaseActivity implements AppRequestListener, Constants, View.OnClickListener, StartActivityListener {
    WebView loginWeb;
    String uuid;
    String code, state;
    String acess_token;
    UserDetail userDetails;
    Gson gson;
    ImageView circularImage;
    TextView textViewName, textviewFollowers, textViewFollowing;
    List<UserRepoDetails> userRepoDetais;
    RecyclerView recyclerViewRepos;
    LinearLayoutManager linearLayoutManager;
    ReposAdapter reposAdapter;
    View appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = new Gson();
        setContentView(R.layout.activity_login_and_sign_up);
        loginWeb = (WebView) findViewById(R.id.web_view);
        circularImage = (ImageView) findViewById(R.id.id_image);
        textViewName = (TextView) findViewById(R.id.id_name);
        textviewFollowers = (TextView) findViewById(R.id.id_textview_followers);
        textViewFollowing = (TextView) findViewById(R.id.id_textview_following);
        appBarLayout = findViewById(R.id.appBarLayout);
        recyclerViewRepos = (RecyclerView) findViewById(R.id.id_recyclerView);
        setLoadingVariables();
        loginWeb.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // Here put your code
                Log.d("UserProAndRepoActivity", url);
                code = (Uri.parse(url)).getQueryParameter("code");
                state = (Uri.parse(url)).getQueryParameter("state");
                Log.d("Code and Stae", code + ", " + state);
                GitLoginApplicationClass.getCommonSharedPreference().edit().putString(Constants.STATE, state)
                        .putString(Constants.CODE, code).commit();

                if (code != null && state != null && code.length() > 0 && state.length() > 0)
                    getAcessTokenAPI();
                return false; //Allow WebView to load url
            }
        });

        uuid = UUID.randomUUID().toString();
        Log.d("UserProAndRepoActivity", uuid);
        if (!GitLoginApplicationClass.getCommonSharedPreference().getBoolean(LOGGEDIN_OR_NOT, false)) {
            loginWeb.loadUrl("https://github.com/login/oauth/authorize?client_id=" + getResources().getString(R.string.github_client_id)
                    + "&redirect_uri=https://github.com&scope=repo&state=" + uuid + "&allow_signup=false");
        } else
        {
            acess_token = GitLoginApplicationClass.getCommonSharedPreference().getString(Constants.ACCESS_TOKEN, null);
            getUserDetailAPI();
        }
    }


    // Getting the Required fields as an Object to call a POST API
    public ParametersForAccessToken getParametersForAccessTokens() {
        ParametersForAccessToken pfat = new ParametersForAccessToken();
        pfat.setClient_id(UtilityClass.ClientID);
        pfat.setClient_secret(UtilityClass.client_secret);
        pfat.setRedirect_uri(UtilityClass.redirect_uri);
        pfat.setCode(code);
        pfat.setState(state);
        return pfat;

    }


    // OnClickListener of Views
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                break;
        }

    }




    // Methods of the Volley
    @Override
    public <T> void onRequestStarted(BaseTask<T> request) {

        Log.d("REQUEST STARTED", "=======REQUEST STARTED");
        loginWeb.setVisibility(View.GONE);
        showLoadingScreen();

    }

    @Override
    public <T> void onRequestCompleted(BaseTask<T> request) {


        if (request.getRequestTag().equalsIgnoreCase(ID_GET_ACCESS_TOKEN)) {
            if (((GetAccessToken) request).getAccessTokenString() != null) {
                Log.d("REQUEST COMPLETED", "=======REQUEST COMPLETED ACCESS TOKEN");
                acess_token = ((GetAccessToken) request).getAccessTokenString();
                GitLoginApplicationClass.getCommonSharedPreference().edit().putString(Constants.ACCESS_TOKEN, acess_token).commit();
                GitLoginApplicationClass.getCommonSharedPreference().edit().putBoolean(LOGGEDIN_OR_NOT, true).apply();
                Log.d("Access Token", " in LoginClass=== " + acess_token);
                getUserDetailAPI();

            }

        }

        if (request.getRequestTag().equalsIgnoreCase(ID_GET_USER_DETAILS)) {
            if (((GetUserDetails) request).getDataObject() != null) {
                Log.d("REQUEST COMPLETED", "=======REQUEST COMPLETED USER DETAILS");
                userDetails = ((GetUserDetails) request).getDataObject();

                // Call User Repo API
                getUserRepoAPI();

                if (userDetails.getName() != null)
                    textViewName.setText(userDetails.getName());
                else
                    textViewName.setText(userDetails.getLogin());

                textviewFollowers.setText(userDetails.getFollowers());
                textViewFollowing.setText(userDetails.getFollowing());


                Picasso.with(UserProAndRepoActivity.this)
                        .load(userDetails.getAvatar_url())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(circularImage);

                showMainView();
                appBarLayout.setVisibility(View.VISIBLE);
                GitLoginApplicationClass.getCommonSharedPreference().edit().putString(Constants.USER_DETAILS, gson.toJson(userDetails)).commit();


            }
        }

        if (request.getRequestTag().equalsIgnoreCase(ID_GET_USER_REPOS)) {
            if (((GetUserRepos) request).getDataObject() != null) {
                Log.d("REQUEST COMPLETED", "=======REQUEST COMPLETED LIST OF REPO");
                userRepoDetais = ((GetUserRepos) request).getDataObject();
                initAdapterAndCall();

            }
        }


    }

    @Override
    public <T> void onRequestFailed(BaseTask<T> request) {

        Log.d("REQUEST FAILED", "=======REQUEST FAILED");

    }


    // Init AND Call Adapter
    public void initAdapterAndCall() {

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewRepos.setLayoutManager(linearLayoutManager);
        reposAdapter = new ReposAdapter(userRepoDetais);
        reposAdapter.setStartActivityListener(this);
        recyclerViewRepos.setAdapter(reposAdapter);

    }


    // Interface method to start an Activity on List Clicked
    @Override
    public void startActivityMethod(UserRepoDetails userRepoDetails, View v) {
        Intent i = new Intent(UserProAndRepoActivity.this, DetailsOfRepoActivity.class);
        i.putExtra(USER_REPO_DETAILS, userRepoDetails);

        if (Build.VERSION.SDK_INT >= 21)
        {
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(this, v, "profile");
            startActivity(i,options.toBundle());
        }
        else
        {
            startActivity(i);
        }


    }


    // API calls

    public void getAcessTokenAPI() {
        String url = "https://github.com/login/oauth/access_token";
        AllUser.getInstance().getAcessTokenPOST(url, UserProAndRepoActivity.this, UserProAndRepoActivity.this, getParametersForAccessTokens());
    }


    public void getUserDetailAPI() {
        String url = "https://api.github.com/user?access_token=" + acess_token;
        AllUser.getInstance().getUserDetailGET(url, UserProAndRepoActivity.this, UserProAndRepoActivity.this);
    }


    public void getUserRepoAPI() {
        Log.d("getUserRepoAPI", "=======getUserRepoAPI");
        String url = "https://api.github.com/users/" + userDetails.getLogin() + "/subscriptions?access_token=" + acess_token;
        AllUser.getInstance().getUserReposGET(url, this, UserProAndRepoActivity.this);
    }


}
