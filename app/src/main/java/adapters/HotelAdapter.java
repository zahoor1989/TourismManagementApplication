package adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.zahoor.activities.R;
import com.example.zahoor.model.Hotel;

import java.util.ArrayList;

public class HotelAdapter  extends RecyclerView.Adapter<HotelAdapter.ViewHolder>{
    private ArrayList<Hotel> hotels;
    ItemClicked activity;

    public interface ItemClicked{
        void onItemClicked(int index);
    }
    //creating CustomTourAdapter
    public HotelAdapter(Context context, ArrayList<Hotel> hotel){
        hotels = hotel;
        activity = (HotelAdapter.ItemClicked) context;
    }
    //viewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView hotelName,hotelAddress;
        ImageView hotelImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hotelImage = itemView.findViewById(R.id.hotelImage);
            hotelName = itemView.findViewById(R.id.hotelName);
            hotelAddress = itemView.findViewById(R.id.hotelAddress);

            //setting onClickListener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClicked(hotels.indexOf((Hotel) v.getTag()));
                }
            });
        }
    }
    @NonNull
    @Override
    public HotelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hotels_list_view_layout,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelAdapter.ViewHolder viewHolder, int i) {
        viewHolder.itemView.setTag(hotels.get(i));
        //adding values to views
        viewHolder.hotelName.setText(hotels.get(i).getName());
        //Need to work on image
        //name of name of image
        String imageName = hotels.get(i).getImages();
        System.out.println(imageName.toString());
        if(!imageName.isEmpty()){
            viewHolder.hotelImage.setImageBitmap(StringToBitMap(imageName));
        }else{
            viewHolder.hotelImage.setImageResource(R.drawable.image);
        }
        viewHolder.hotelAddress.setText(hotels.get(i).getAddress());

    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}
