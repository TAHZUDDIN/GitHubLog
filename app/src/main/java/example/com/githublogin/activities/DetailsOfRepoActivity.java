package example.com.githublogin.activities;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;

import example.com.githublogin.R;
import example.com.githublogin.fragments.FragmentParticularRepoDetail;
import example.com.githublogin.pojos.UserRepoDetails;

public class DetailsOfRepoActivity extends BaseActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    FragmentParticularRepoDetail fragmentviewPagerCommits, fragmentviewPagerBranches;
    ArrayList<String> typesOfFragments;
    private CustomPagerAdapter customPagerAdapter;
    UserRepoDetails userRepoDetails;
    String titleToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_of_repo);



        userRepoDetails = (UserRepoDetails) getIntent().getSerializableExtra(USER_REPO_DETAILS);

        typesOfFragments = new ArrayList<String>(Arrays.asList("Commits", "Branches"));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        titleToolbar =((UserRepoDetails)getIntent().getSerializableExtra(USER_REPO_DETAILS)).getName();
        getSupportActionBar().setTitle(titleToolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        customPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager());
        assert viewPager != null;
        viewPager.setOffscreenPageLimit(2);

        viewPager.setAdapter(customPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }


    class CustomPagerAdapter extends FragmentStatePagerAdapter {

        FragmentManager fragMan;

        public CustomPagerAdapter(FragmentManager fm) {
            super(fm);
            fragMan = fm;
        }


        public FragmentManager getFragmentManager() {
            return fragMan;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {

                case 0:

                    Log.d("CustomPagerAdapter", position + "");
                    fragmentviewPagerCommits = FragmentParticularRepoDetail.newInstance(typesOfFragments.get(position), userRepoDetails);
                    return fragmentviewPagerCommits;

                case 1:
                    Log.d("CustomPagerAdapter", position + "");
                    fragmentviewPagerBranches = FragmentParticularRepoDetail.newInstance(typesOfFragments.get(position), userRepoDetails);
                    return fragmentviewPagerBranches;

                default:
                    return null;
            }


        }


        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {

        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Commits";
                case 1:
                    return "Branches";

            }
            return "";
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
