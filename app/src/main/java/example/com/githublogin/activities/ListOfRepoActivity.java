package example.com.githublogin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.List;

import example.com.githublogin.R;
import example.com.githublogin.adapters.ReposAdapter;
import example.com.githublogin.apiobjects.AllUser;
import example.com.githublogin.extras.Constants;
import example.com.githublogin.listeners.StartActivityListener;
import example.com.githublogin.pojos.UserDetail;
import example.com.githublogin.pojos.UserRepoDetails;
import example.com.githublogin.vollyserverapis.AppRequestListener;
import example.com.githublogin.vollyserverapis.BaseTask;
import example.com.githublogin.vollyserverapis.GetUserRepos;

public class ListOfRepoActivity extends BaseActivity implements AppRequestListener,Constants,StartActivityListener {

    UserDetail userDetail;
    List<UserRepoDetails> userRepoDetais;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerViewRepos;
    ReposAdapter reposAdapter;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_repo);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setToolbarTitle(toolbar,"Repositories");
        recyclerViewRepos = (RecyclerView)findViewById(R.id.id_recyclerView);

        if(getIntent().getSerializableExtra(USER_DETAILS)!= null)
            userDetail = (UserDetail)getIntent().getSerializableExtra(USER_DETAILS);

        setLoadingVariables();
        showLoadingScreen();

        getUserRepoAPI();
    }

    @Override
    public <T> void onRequestStarted(BaseTask<T> request)
    {
        Log.d("REQUEST STARTED","=======REQUEST STARTED LIST OF REPO");
    }

    @Override
    public <T> void onRequestCompleted(BaseTask<T> request)
    {

        if (request.getRequestTag().equalsIgnoreCase(ID_GET_USER_REPOS))
        {
            if (((GetUserRepos)request).getDataObject() != null)
            {
                showMainView();
                Log.d("REQUEST COMPLETED","=======REQUEST COMPLETED LIST OF REPO");
                userRepoDetais = ((GetUserRepos)request).getDataObject();
                initAdapterAndCall();

            }
        }



    }

    @Override
    public <T> void onRequestFailed(BaseTask<T> request) {
        Log.d("REQUEST FAILED","=======REQUEST FAILED LIST OF REPO");

    }



    public void getUserRepoAPI()
    {
        Log.d("getUserRepoAPI","=======getUserRepoAPI");
        String url = "https://api.github.com/users/"+userDetail.getLogin()+"/subscriptions?access_token="+getIntent().getStringExtra(ACCESS_TOKEN);
        AllUser.getInstance().getUserReposGET(url, this, ListOfRepoActivity.this);
    }

    public void initAdapterAndCall()
    {

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewRepos.setLayoutManager(linearLayoutManager);
        reposAdapter = new ReposAdapter(userRepoDetais);
        reposAdapter.setStartActivityListener(this);
        recyclerViewRepos.setAdapter(reposAdapter);

    }

    @Override
    public void startActivityMethod(UserRepoDetails userRepoDetails, View v)
    {
        Intent i = new Intent(ListOfRepoActivity.this,DetailsOfRepoActivity.class);
        i.putExtra(USER_REPO_DETAILS, userRepoDetails);
        startActivity(i);

    }
}
