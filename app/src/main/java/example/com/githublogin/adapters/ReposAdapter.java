package example.com.githublogin.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.com.githublogin.R;
import example.com.githublogin.listeners.StartActivityListener;
import example.com.githublogin.pojos.UserRepoDetails;


public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ViewHolder> implements View.OnClickListener {


    List<UserRepoDetails> listUserRepoDetails;


    public StartActivityListener startActivityListener;


    public void setStartActivityListener(StartActivityListener startActivityListener) {
        this.startActivityListener = startActivityListener;
    }


    public ReposAdapter(List<UserRepoDetails> listUserRepoDetails) {
        this.listUserRepoDetails = new ArrayList<>();
        this.listUserRepoDetails = listUserRepoDetails;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflating_listrepo_layout, parent, false); //Inflating the layout
        ViewHolder vhItem = new ViewHolder(v); //Creating ViewHolder and passing the object of type view
        return vhItem;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        UserRepoDetails userRepoDetails = listUserRepoDetails.get(position);
        holder.name.setText(userRepoDetails.getName());
        if (userRepoDetails.getDescription() != null)
            holder.description.setText(userRepoDetails.getDescription());
        else
            holder.description.setText("No Description");
        holder.language.setText(userRepoDetails.getLanguage());
        if(userRepoDetails.is_private()== true)
            holder.imageViewRepoPubPriv.setImageResource(R.drawable.ic_swap_calls_black_24dp);
        else
            holder.imageViewRepoPubPriv.setImageResource(R.drawable.icon_publicrepo_github);

        holder.starAnd.setVisibility(View.GONE);
        holder.parentView.setTag(userRepoDetails);


    }

    @Override
    public int getItemCount() {
        return listUserRepoDetails.size();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.id_LinLay_parent_inflatingRepo:
                startActivityListener.startActivityMethod(((UserRepoDetails) view.getTag()), view);
                break;
            default:
                break;
        }

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, description, language, star;
        View parentView,starAnd;
        ImageView imageViewRepoPubPriv;


        public ViewHolder(View itemView) {
            super(itemView);
            parentView = (View) itemView.findViewById(R.id.id_LinLay_parent_inflatingRepo);
            parentView.setOnClickListener(ReposAdapter.this);
            name = (TextView) itemView.findViewById(R.id.id_textview_name);
            description = (TextView) itemView.findViewById(R.id.id_textview_description);
            language = (TextView) itemView.findViewById(R.id.id_textview_language);
            imageViewRepoPubPriv =(ImageView) itemView.findViewById(R.id.id_imgvw_repoPubPriv);
            starAnd =  itemView.findViewById(R.id.template);

        }
    }


}
