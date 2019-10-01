package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zahoor.activities.R;
import com.example.zahoor.model.Booking;
import com.example.zahoor.model.Transport;

import java.util.ArrayList;

public class TransportAdapter extends RecyclerView.Adapter<TransportAdapter.ViewHolder>  {
    private ArrayList<Transport> transports;
    ItemClicked activity;

    public interface ItemClicked{
        void onItemClicked(int index);
    }
    //creating CustomTourAdapter
    public TransportAdapter(Context context, ArrayList<Transport> transport){
        transports = transport;
        activity = (ItemClicked) context;
    }
    //viewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView registration,vehicleName,transportType,pickUp,dropUp,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            registration = itemView.findViewById(R.id.registration);
            vehicleName = itemView.findViewById(R.id.vehicleName);
            transportType = itemView.findViewById(R.id.transportType);
            pickUp = itemView.findViewById(R.id.pickUp);
            dropUp = itemView.findViewById(R.id.dropUp);
            price = itemView.findViewById(R.id.price);

            //setting onClickListener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClicked(transports.indexOf((Transport) v.getTag()));
                }
            });
        }
    }
    @NonNull
    @Override
    public TransportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.transport_list_view_layout,viewGroup,false);
        return new TransportAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TransportAdapter.ViewHolder viewHolder, int i) {
        viewHolder.itemView.setTag(transports.get(i));
        //adding values to views
        viewHolder.registration.setText(Integer.toString(transports.get(i).getProduct_id()));
        viewHolder.vehicleName.setText(transports.get(i).getVehicleName());
        viewHolder.transportType.setText(transports.get(i).getType());
        viewHolder.pickUp.setText(transports.get(i).getPickup());
        viewHolder.dropUp.setText(transports.get(i).getDrop());
        viewHolder.price.setText(Integer.toString(transports.get(i).getPrice()));

    }

    @Override
    public int getItemCount() {
        return transports.size();
    }
}
