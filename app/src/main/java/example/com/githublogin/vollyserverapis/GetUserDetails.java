package example.com.githublogin.vollyserverapis;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import example.com.githublogin.pojos.UserDetail;
import example.com.githublogin.utils.JSONUtils;


public class GetUserDetails extends BaseTask<JSONObject> {

    public String TAG = "UsrRegstrtinSrvceClas";

    public GetUserDetails(int method, String url, Response.ErrorListener listener, String requestTag, AppRequestListener requestListener) {
        super(method, url, listener, requestTag, requestListener);
        headers = new HashMap<String, String>();
    }

    @Override
    public void processData() {
        sendMessage();
    }


    UserDetail userDetail;


    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        statusCode = response.statusCode;
        String responseString = new String(response.data);
        Log.i(TAG, "response:" + responseString);
        userDetail = new Gson().fromJson(responseString, UserDetail.class);
        return Response.success(
                JSONUtils.getJSONObject(responseString),
                getCacheEntry());
    }


    public UserDetail getDataObject() {
        return userDetail;
    }


    @Override
    protected void deliverResponse(JSONObject response) {

        this.setResponse(response);
        RequestPoolManager.getInstance().executeRequest(this);

    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }
}