package example.com.githublogin.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import example.com.githublogin.R;
import example.com.githublogin.adapters.RepoCommitsEtcAdapter;
import example.com.githublogin.apiobjects.AllUser;
import example.com.githublogin.extras.Constants;
import example.com.githublogin.pojos.BranchInfo;
import example.com.githublogin.pojos.RepoCommits;
import example.com.githublogin.pojos.UserRepoDetails;
import example.com.githublogin.utils.GitLoginApplicationClass;
import example.com.githublogin.vollyserverapis.AppRequestListener;
import example.com.githublogin.vollyserverapis.BaseTask;
import example.com.githublogin.vollyserverapis.GetBranchInfo;
import example.com.githublogin.vollyserverapis.GetRepoCommits;


public class FragmentParticularRepoDetail extends Fragment implements AppRequestListener, Constants {

    String typeOfApiToCall;
    View fragmentView;
    UserRepoDetails userRepoDetails;
    List<RepoCommits> repoCommits;
    List<BranchInfo> branchInfo;


    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerViewRepos;
    RepoCommitsEtcAdapter repoCommitsEtcAdapter;
    View progressBarInFrameLayout;


    public static FragmentParticularRepoDetail newInstance(String tagType, UserRepoDetails userRepoDetails) {
        FragmentParticularRepoDetail fragment = new FragmentParticularRepoDetail();
        Bundle bundle = new Bundle();
        bundle.putString("TAG", tagType);
        bundle.putSerializable("userRepoDetails", userRepoDetails);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        typeOfApiToCall = getArguments().getString("TAG");
        userRepoDetails = (UserRepoDetails) getArguments().getSerializable("userRepoDetails");
        if (typeOfApiToCall.equals("Commits"))
            getRepoCommitsAPI();
        else
            getBranchInfoAPI();

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_particularrepo_layout, container, false);
        recyclerViewRepos = (RecyclerView) fragmentView.findViewById(R.id.id_recyclerView);
        progressBarInFrameLayout = fragmentView.findViewById(R.id.progress_bar);
        progressBarInFrameLayout.setVisibility(View.VISIBLE);

        return fragmentView;
    }


    @Override
    public <T> void onRequestStarted(BaseTask<T> request) {

    }

    @Override
    public <T> void onRequestCompleted(BaseTask<T> request) {


        if (request.getRequestTag().equalsIgnoreCase(ID_GET_REPO_COMMITS)) {
            if (((GetRepoCommits) request).getDataObject() != null) {
                Log.d("REQUEST COMPLETED", "=======REQUEST COMPLETED REPO COMMITS");
                repoCommits = ((GetRepoCommits) request).getDataObject();
                progressBarInFrameLayout.setVisibility(View.GONE);
                initAdapterAndCall();

            }
        } else {
            if (((GetBranchInfo) request).getDataObject() != null) {
                Log.d("REQUEST COMPLETED", "=======REQUEST COMPLETED REPO COMMITS");
                branchInfo = ((GetBranchInfo) request).getDataObject();
                progressBarInFrameLayout.setVisibility(View.GONE);
                initAdapterAndCall();
            }

        }


    }

    @Override
    public <T> void onRequestFailed(BaseTask<T> request) {

    }


    public void initAdapterAndCall() {

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewRepos.setLayoutManager(linearLayoutManager);
        if (typeOfApiToCall.equals("Commits")) {
            repoCommitsEtcAdapter = new RepoCommitsEtcAdapter();
            repoCommitsEtcAdapter.setDataCommits(repoCommits);
        } else {
            repoCommitsEtcAdapter = new RepoCommitsEtcAdapter();
            repoCommitsEtcAdapter.setDataBranches(branchInfo);
        }

        recyclerViewRepos.setAdapter(repoCommitsEtcAdapter);
        repoCommitsEtcAdapter.notifyDataSetChanged();

    }


    public void getRepoCommitsAPI() {
        String accesstoken = GitLoginApplicationClass.getCommonSharedPreference().getString(Constants.ACCESS_TOKEN, "");
        String login = ((UserRepoDetails.Owner) userRepoDetails.getOwner()).getLogin();
        String url = "https://api.github.com/repos/" + login + "/" + userRepoDetails.getName() + "/commits?access_token=" + accesstoken;
        AllUser.getInstance().getRepoCommitsGET(url, FragmentParticularRepoDetail.this, getActivity());
    }


    public void getBranchInfoAPI() {

//        https://api.github.com/repos/TAHZUDDIN/hello-world/branches?access_token=81c8e4ebaa2321e42093612e480e9577ae4837cd
        String accesstoken = GitLoginApplicationClass.getCommonSharedPreference().getString(Constants.ACCESS_TOKEN, "");
        String login = (userRepoDetails.getOwner()).getLogin();
        String url = "https://api.github.com/repos/" + login + "/" + userRepoDetails.getName() + "/branches?access_token=" + accesstoken;
        AllUser.getInstance().getBranchInfoGET(url, FragmentParticularRepoDetail.this, getActivity());
    }


}
