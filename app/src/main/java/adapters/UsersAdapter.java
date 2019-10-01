package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zahoor.activities.R;
import com.example.zahoor.model.User;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    private ArrayList<User> users;
    ItemClicked activity;

    public interface ItemClicked{
        void onItemClicked(int index);
    }
    //creating CustomTourAdapter
    public UsersAdapter(Context context, ArrayList<User> user){
        users = user;
        activity = ( ItemClicked) context;
    }
    //viewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView userId,userName, userEmail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userId = itemView.findViewById(R.id.userId);
            userName = itemView.findViewById(R.id.userName);
            userEmail= itemView.findViewById(R.id.userEmail);

            //setting onClickListener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClicked(users.indexOf((User) v.getTag()));
                }
            });
        }
    }
    @NonNull
    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_list_view_layout,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.itemView.setTag(users.get(i));
        //adding values to views
        viewHolder.userId.setText(Integer.toString(users.get(i).getId()));
        viewHolder.userName.setText(users.get(i).getName());
        viewHolder.userEmail.setText(users.get(i).getEmail());

    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
