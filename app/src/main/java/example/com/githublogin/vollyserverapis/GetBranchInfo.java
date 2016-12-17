package example.com.githublogin.vollyserverapis;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import example.com.githublogin.pojos.BranchInfo;
import example.com.githublogin.utils.JSONUtils;


public class GetBranchInfo extends BaseTask<JSONArray> {

    public String TAG = "GetBranchInfo";

    public GetBranchInfo(int method, String url, Response.ErrorListener listener, String requestTag, AppRequestListener requestListener) {
        super(method, url, listener, requestTag, requestListener);
        headers = new HashMap<String, String>();

    }

    @Override
    public void processData() {
        sendMessage();
    }


    List<BranchInfo> branchInfo;


    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        statusCode = response.statusCode;
        String responseString = new String(response.data);

        Log.d(TAG, "response:" + responseString);
        TypeToken<List<BranchInfo>> token = new TypeToken<List<BranchInfo>>() {
        };
        branchInfo = new Gson().fromJson(responseString, token.getType());

        return Response.success(
                JSONUtils.getJSONArray(responseString),
                getCacheEntry());

    }

    @Override
    protected void deliverResponse(JSONArray response) {

        RequestPoolManager.getInstance().executeRequest(this);
    }


    public List<BranchInfo> getDataObject() {
        return branchInfo;
    }


    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }
}
