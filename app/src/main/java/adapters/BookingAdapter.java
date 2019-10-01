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
import com.example.zahoor.model.Booking;


import java.util.ArrayList;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {
        private ArrayList<Booking> bookings;
        ItemClicked activity;

        public interface ItemClicked{
            void onItemClicked(int index);
        }
        //creating CustomTourAdapter
        public BookingAdapter(Context context, ArrayList<Booking> booking){
            bookings = booking;
            activity = ( ItemClicked) context;
        }
        //viewHolder class
        public class ViewHolder extends RecyclerView.ViewHolder{
            TextView bookId,customerId,productId,bookType,quantity,total,payStatus, createdAt;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                bookId = itemView.findViewById(R.id.bookingId);
                customerId = itemView.findViewById(R.id.customerId);
                productId = itemView.findViewById(R.id.productId);
                bookType = itemView.findViewById(R.id.bookingType);
                total = itemView.findViewById(R.id.total);
                quantity = itemView.findViewById(R.id.quantity);
                payStatus = itemView.findViewById(R.id.payStatus);
                createdAt = itemView.findViewById(R.id.createdAt);

                //setting onClickListener
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.onItemClicked(bookings.indexOf((Booking) v.getTag()));
                    }
                });
            }
        }
        @NonNull
        @Override
        public BookingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.booking_list_view_layout,viewGroup,false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.itemView.setTag(bookings.get(i));
            //adding values to views
            viewHolder.bookId.setText(Integer.toString(bookings.get(i).getId()));
            viewHolder.customerId.setText(Integer.toString(bookings.get(i).getCustomer_id()));
            viewHolder.productId.setText(Integer.toString(bookings.get(i).getProduct_id()));
            viewHolder.bookType.setText(bookings.get(i).getType());
            viewHolder.quantity.setText(Integer.toString(bookings.get(i).getQuantity()));
            viewHolder.total.setText(Integer.toString(bookings.get(i).getTotal()));
            viewHolder.payStatus.setText(bookings.get(i).getPayStatus());
            viewHolder.createdAt.setText(bookings.get(i).getCreatedAt());
        }

        @Override
        public int getItemCount() {
            return bookings.size();
        }
    }


