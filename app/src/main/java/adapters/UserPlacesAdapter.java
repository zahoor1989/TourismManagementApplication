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
import com.example.zahoor.model.TourPlace;
import com.example.zahoor.model.User;

import java.util.ArrayList;

public class UserPlacesAdapter extends RecyclerView.Adapter<UserPlacesAdapter.ViewHolder>
{
    private ArrayList<TourPlace> tPlaces;
    ItemClicked activity;
    private RecyclerView.ViewHolder viewHolder;
    //    private int i;
    //crating interface for onclick
    public interface ItemClicked{
        void onItemClicked(int index);
    }

    //creating CustomTourAdapter
    public UserPlacesAdapter(Context context, ArrayList<TourPlace> tourPlaces){
        tPlaces = tourPlaces;
        activity = ( ItemClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgView;
        TextView placeName, placeAdd;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.placeImage);
            placeName = itemView.findViewById(R.id.placeName);
            placeAdd = itemView.findViewById(R.id.placeAdd);

            //setting onClickListener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClicked(tPlaces.indexOf((User) v.getTag()));
                }
            });
        }
    }
    @NonNull
    @Override
    public UserPlacesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_list_view_layout,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vHolder, int i)
    {
        vHolder.itemView.setTag(tPlaces.get(i));
        Context context = vHolder.itemView.getContext();
        //name of name of image
        String imageName = Integer.toString(tPlaces.get(i).getId());
        int image_id = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        if(image_id>0){
            vHolder.imgView.setImageResource(image_id);
        }else{
            vHolder.imgView.setImageResource(R.drawable.image);
        }
        //adding name to view
        vHolder.placeName.setText(tPlaces.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return tPlaces.size();
    }
}