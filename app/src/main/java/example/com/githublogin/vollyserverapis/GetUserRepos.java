package example.com.githublogin.vollyserverapis;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import example.com.githublogin.pojos.UserRepoDetails;
import example.com.githublogin.utils.JSONUtils;


public class GetUserRepos extends BaseTask<JSONArray> {

    public String TAG = "UsrRegstrtinSrvceClas";

    public GetUserRepos(int method, String url, Response.ErrorListener listener, String requestTag, AppRequestListener requestListener) {
        super(method, url, listener, requestTag, requestListener);
        headers = new HashMap<String, String>();

    }

    @Override
    public void processData() {
        sendMessage();
    }


    List<UserRepoDetails> userRepoDetais;


    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        statusCode = response.statusCode;
        String responseString = new String(response.data);

        Log.d(TAG, "response:" + responseString);
        TypeToken<List<UserRepoDetails>> token = new TypeToken<List<UserRepoDetails>>() {
        };
        JSONArray jsonArray = JSONUtils.getJSONArray(responseString);
        userRepoDetais = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            UserRepoDetails userRepoDetail;
            try {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                userRepoDetail = new Gson().fromJson(jsonObject.toString(), UserRepoDetails.class);
                userRepoDetail.setIs_private(jsonObject.getBoolean("private"));
                userRepoDetais.add(userRepoDetail);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return Response.success(
                JSONUtils.getJSONArray(responseString),
                getCacheEntry());

    }

    @Override
    protected void deliverResponse(JSONArray response) {

        RequestPoolManager.getInstance().executeRequest(this);
    }


    public List<UserRepoDetails> getDataObject() {
        return userRepoDetais;
    }


    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }
}
