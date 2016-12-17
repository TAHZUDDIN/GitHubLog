package example.com.githublogin.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.com.githublogin.R;
import example.com.githublogin.listeners.StartActivityListener;
import example.com.githublogin.pojos.BranchInfo;
import example.com.githublogin.pojos.RepoCommits;


public class RepoCommitsEtcAdapter extends RecyclerView.Adapter<RepoCommitsEtcAdapter.ViewHolder> {


    List<RepoCommits> listRepoCommitsDetails;
    List<BranchInfo> listRepoBranches;
    boolean dataCommitsBoolean = false;
    boolean branchesBoolean = false;


    public StartActivityListener startActivityListener;


    public void setStartActivityListener(StartActivityListener startActivityListener) {
        this.startActivityListener = startActivityListener;
    }


    public RepoCommitsEtcAdapter() {

    }

    public void setDataCommits(List<RepoCommits> repoCommits) {
        dataCommitsBoolean = true;
        this.listRepoCommitsDetails = new ArrayList<>();
        this.listRepoCommitsDetails = repoCommits;

    }


    public void setDataBranches(List<BranchInfo> repoBranches) {
        branchesBoolean = true;
        this.listRepoBranches = new ArrayList<>();
        this.listRepoBranches = repoBranches;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflating_listrepo_layout, parent, false); //Inflating the layout
        ViewHolder vhItem = new ViewHolder(v); //Creating ViewHolder and passing the object of type view
        return vhItem;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (dataCommitsBoolean) {

            RepoCommits repoCommits = listRepoCommitsDetails.get(position);
            String message = ((RepoCommits.Commit) repoCommits.getCommit()).getMessage();
            String sha = repoCommits.getSha();
            String name = repoCommits.getCommit().getCommitter().getName();

            String date = repoCommits.getCommit().getCommitter().getDate();
            date = date.substring(0, 10);

            sha = sha.substring(0, 10);
            holder.name.setText(message);
            holder.description.setText(sha);
            holder.language.setText(name);
            holder.star.setText(date);

        } else if (branchesBoolean) {
            BranchInfo branchInfo = listRepoBranches.get(position);
            String name = branchInfo.getName();
            String sha = branchInfo.getCommit().getSha();
            String url = branchInfo.getCommit().getUrl();


            sha = sha.substring(0, 10);
            holder.name.setText(name);
            holder.description.setText(sha);
            holder.language.setText(url);
            holder.star.setVisibility(View.GONE);

        }

    }

    @Override
    public int getItemCount() {
        if (dataCommitsBoolean)
            return listRepoCommitsDetails.size();
        else
            return listRepoBranches.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, description, language, star;
        View parentView;


        public ViewHolder(View itemView) {
            super(itemView);
            parentView = (View) itemView.findViewById(R.id.id_LinLay_parent_inflatingRepo);

            name = (TextView) itemView.findViewById(R.id.id_textview_name);
            description = (TextView) itemView.findViewById(R.id.id_textview_description);
            language = (TextView) itemView.findViewById(R.id.id_textview_language);
            star = (TextView) itemView.findViewById(R.id.id_textview_star);

        }
    }


}