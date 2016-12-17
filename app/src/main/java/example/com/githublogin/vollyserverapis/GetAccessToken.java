package example.com.githublogin.vollyserverapis;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import example.com.githublogin.pojos.ParametersForAccessToken;


public class GetAccessToken extends BaseTask<JSONObject> {

    HttpEntity entity;
    String access_token;

    public GetAccessToken(int method, String url, Response.ErrorListener listener, String requestTag, AppRequestListener requestListener, ParametersForAccessToken object) {
        super(method, url, listener, requestTag, requestListener);
        headers = new HashMap<String, String>();
        String jsonObject = new Gson().toJson(object);
        try {
            entity = new StringEntity(jsonObject);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void processData() {
        sendMessage();
    }


    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        statusCode = response.statusCode;
        String responseString = new String(response.data);
        Log.d("GetAccessToken", " " + responseString);

        access_token = getAccessToken(responseString);
        Log.d("Token====", " " + access_token);
        JSONObject jsonObject = new JSONObject();
        return Response.success
                (
                        jsonObject,
                        getCacheEntry()
                );
    }

    private String getAccessToken(String responseString) {
        String token = responseString.split("&")[0];
        token = token.split("=")[1];
        return token;
    }


    public String getAccessTokenString() {
        return access_token;
    }


    @Override
    protected void deliverResponse(JSONObject response) {
        this.setResponse(response);
        RequestPoolManager.getInstance().executeRequest(this);
    }


    @Override
    public String getBodyContentType() {
        return "application/json";
    }


    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }


    @Override
    public byte[] getBody() throws AuthFailureError {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            entity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }


}
