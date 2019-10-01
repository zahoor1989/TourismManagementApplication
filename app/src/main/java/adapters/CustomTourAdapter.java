package adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.zahoor.activities.R;
import com.example.zahoor.model.TourPlace;

import java.util.ArrayList;

public class CustomTourAdapter extends RecyclerView.Adapter<CustomTourAdapter.ViewHolder>
{
    private ArrayList<TourPlace> tourPlaces;
    ItemClicked activity;
    //private RecyclerView.ViewHolder viewHolder;
//    private int i;
    //crating interface for onclick
    public interface ItemClicked{
     void onItemClicked(int index);
    }

    //creating CustomTourAdapter
    public CustomTourAdapter(Context context, ArrayList<TourPlace> tp){
    tourPlaces = tp;
    activity = ( ItemClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgView;
        TextView placeName, placeAdd;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView = (ImageView) itemView.findViewById(R.id.placeImage);
            placeName = itemView.findViewById(R.id.placeName);
            placeAdd = itemView.findViewById(R.id.placeAdd);

            //setting onClickListener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClicked(tourPlaces.indexOf((TourPlace) v.getTag()));
                }
            });
        }
    }
    @NonNull
    @Override
    public CustomTourAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_view_layout,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.itemView.setTag(tourPlaces.get(i));
        //Context context = viewHolder.itemView.getContext();
        //name of name of image
        String imageName = tourPlaces.get(i).getImage();
        System.out.println(imageName.toString());
        if(!imageName.isEmpty()){
            viewHolder.imgView.setImageBitmap(StringToBitMap(imageName));
        }else{
            viewHolder.imgView.setImageResource(R.drawable.image);
        }
        viewHolder.placeName.setText(tourPlaces.get(i).getName());
        viewHolder.placeAdd.setText(tourPlaces.get(i).getAdd());
    }

    @Override
    public int getItemCount() {
        return tourPlaces.size();
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}