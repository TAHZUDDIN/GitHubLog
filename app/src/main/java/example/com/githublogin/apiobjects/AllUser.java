package example.com.githublogin.apiobjects;

import android.content.Context;

import com.android.volley.Request;

import example.com.githublogin.extras.Constants;
import example.com.githublogin.pojos.ParametersForAccessToken;
import example.com.githublogin.vollyserverapis.AppRequestListener;
import example.com.githublogin.vollyserverapis.GetAccessToken;
import example.com.githublogin.vollyserverapis.GetBranchInfo;
import example.com.githublogin.vollyserverapis.GetRepoCommits;
import example.com.githublogin.vollyserverapis.GetUserDetails;
import example.com.githublogin.vollyserverapis.GetUserRepos;


public class AllUser extends BaseObject implements Constants {

    private static AllUser sInstance;

    @Override
    public void clear(Context context) {

    }

    public static AllUser getInstance() {
        if (sInstance == null)
            sInstance = new AllUser();
        return sInstance;
    }

    public void getAcessTokenPOST(String url, AppRequestListener appRequestListener, Context context, ParametersForAccessToken parametersForAccessToken) {
        AppNetworkError appNetworkError = new AppNetworkError();
        GetAccessToken request = new GetAccessToken(Request.Method.POST, url, appNetworkError, Constants.ID_GET_ACCESS_TOKEN, appRequestListener, parametersForAccessToken);
        sendRequest(context, appNetworkError, request, appRequestListener);
    }


    public void getUserDetailGET(String url, AppRequestListener appRequestListener, Context context) {
        AppNetworkError appNetworkError = new AppNetworkError();
        GetUserDetails request = new GetUserDetails(Request.Method.GET, url, appNetworkError, Constants.ID_GET_USER_DETAILS, appRequestListener);
        sendRequest(context, appNetworkError, request, appRequestListener);
    }

    public void getUserReposGET(String url, AppRequestListener appRequestListener, Context context) {
        AppNetworkError appNetworkError = new AppNetworkError();
        GetUserRepos request = new GetUserRepos(Request.Method.GET, url, appNetworkError, Constants.ID_GET_USER_REPOS, appRequestListener);
        sendRequest(context, appNetworkError, request, appRequestListener);
    }

    public void getRepoCommitsGET(String url, AppRequestListener appRequestListener, Context context) {
        AppNetworkError appNetworkError = new AppNetworkError();
        GetRepoCommits request = new GetRepoCommits(Request.Method.GET, url, appNetworkError, Constants.ID_GET_REPO_COMMITS, appRequestListener);
        sendRequest(context, appNetworkError, request, appRequestListener);
    }


    public void getBranchInfoGET(String url, AppRequestListener appRequestListener, Context context) {
        AppNetworkError appNetworkError = new AppNetworkError();
        GetBranchInfo request = new GetBranchInfo(Request.Method.GET, url, appNetworkError, Constants.ID_GET_REPO_BRANCHES, appRequestListener);
        sendRequest(context, appNetworkError, request, appRequestListener);
    }
//
//
//    public void getGoogleLocationForCity(String url, AppRequestListener appRequestListener, Context context)
//    {
//        AppNetworkError appNetworkError = new AppNetworkError();
//        GoogleLocationAPIcallServiceClass request = new  GoogleLocationAPIcallServiceClass(Request.Method.GET, url, appNetworkError, Constants.ID_GOOGLE_LOCATION_APICALL, appRequestListener);
//        sendRequest(context, appNetworkError, request, appRequestListener);
//    }
//
//
//
//
//    public void postDataUserPreference(String url, AppRequestListener appRequestListener, Context context, PostDataUserPreference postDataUserPreference) {
//        AppNetworkError appNetworkError = new AppNetworkError();
//        PostDataUserPrefenceServiceClass request = new PostDataUserPrefenceServiceClass(Request.Method.POST, url, appNetworkError, ID_POST_USER_PREFERENCE, appRequestListener, postDataUserPreference);
//        sendRequest(context, appNetworkError, request, appRequestListener);
//    }


}
