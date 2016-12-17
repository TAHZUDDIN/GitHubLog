package example.com.githublogin.activities;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import example.com.githublogin.R;
import example.com.githublogin.extras.Constants;



public class BaseActivity extends AppCompatActivity implements Constants {

    ProgressDialog progressDialog;
    Activity activity;
    View progressBar;
    TextView nullCaseView;
    private View loadingScreen;

    public DisplayMetrics getDisplayMetrics() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

    public void setToolbarTitle(Toolbar toolbar, String title) {
        toolbar.setTitle(null);
        TextView titletext = (TextView) toolbar.findViewById(R.id.title);
        titletext.setText(title);

    }

    public void showToast(String text) {
        try {
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showProgressDialog(String title, String subTitle) {
        try {
            progressDialog = ProgressDialog.show(this, title, subTitle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideProgressDialog() {
        try {
            progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor() {
        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(int color) {
        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(color);
        }
    }

    public void hidesoftKeyPad(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    //Getting ids of ProgressBar
    public void setLoadingVariables() {
        progressBar =  findViewById(R.id.progress_bar);
        nullCaseView = (TextView) findViewById(R.id.null_case_view);
    }

    // To show ProgressBar when required
    public void showLoadingScreen() {
        progressBar.setVisibility(View.VISIBLE);
        nullCaseView.setVisibility(View.GONE);
    }

    //To show view after successuful API calling
    public void showMainView() {
        progressBar.setVisibility(View.GONE);
    }

    public void showNullCaseView(String text) {
        loadingScreen.setVisibility(View.VISIBLE);
        loadingScreen.setAlpha(1);
        progressBar.setVisibility(View.GONE);
        nullCaseView.setVisibility(View.VISIBLE);
        nullCaseView.setText(text);
    }
}

