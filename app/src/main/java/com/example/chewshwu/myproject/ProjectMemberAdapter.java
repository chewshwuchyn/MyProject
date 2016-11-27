package com.example.chewshwu.myproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import com.bumptech.glide.Glide;

/**
 * Created by CHEW SHWU on 11/27/2016.
 */

public class ProjectMemberAdapter extends RecyclerView.Adapter<ProjectMemberAdapter.ViewHolder>{

    private Context context;
    private List<ProjectMembers> projectMembersList;

    public ProjectMemberAdapter(Context context, List<ProjectMembers> projectMembersList) {
        this.context = context;
        this.projectMembersList = projectMembersList;
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);

        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(projectMembersList.get(position).getName());
        holder.email.setText(projectMembersList.get(position).getEmail());
        holder.position.setText(projectMembersList.get(position).getPosition());
        Glide.with(context).load(projectMembersList.get(position).getImageurl()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return projectMembersList.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{

        public TextView name;
        public TextView email;
        public TextView position;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            email = (TextView) itemView.findViewById(R.id.email);
            position = (TextView) itemView.findViewById(R.id.position);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
