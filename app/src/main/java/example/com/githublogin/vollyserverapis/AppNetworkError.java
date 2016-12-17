package example.com.githublogin.vollyserverapis;

import com.android.volley.Response;
import com.android.volley.VolleyError;


public class AppNetworkError implements Response.ErrorListener {
    BaseTask<?> task;
    AppRequestListener requestListener;

    public void setRequestListener(BaseTask<?> task,
                                   AppRequestListener requestListener) {
        this.requestListener = requestListener;
        this.task = task;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        System.out.println("Request failed :: " + task.getStatusCode());
        requestListener.onRequestFailed(task);
    }
}
