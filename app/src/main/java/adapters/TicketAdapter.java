package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zahoor.activities.R;
import com.example.zahoor.model.Ticket;

import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder>{
private ArrayList<Ticket> tickets;
        ItemClicked activity;

public interface ItemClicked{
    void onItemClicked(int index);
}
    //creating CustomTourAdapter
    public TicketAdapter(Context context, ArrayList<Ticket> ticket){
        tickets = ticket;
        activity = (ItemClicked) context;
    }
//viewHolder class
public class ViewHolder extends RecyclerView.ViewHolder{
    TextView ticketId,ticketName,ticketPrice,travelDate,ticketValidity;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        ticketId = itemView.findViewById(R.id.ticketId);
        ticketName = itemView.findViewById(R.id.ticketName);
        ticketPrice = itemView.findViewById(R.id.ticketPrice);
        travelDate = itemView.findViewById(R.id.travelDate);
        ticketValidity = itemView.findViewById(R.id.ticketValidity);

        //setting onClickListener
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onItemClicked(tickets.indexOf((Ticket) v.getTag()));
            }
        });
    }
}
    @NonNull
    @Override
    public TicketAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ticket_list_view_layout,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketAdapter.ViewHolder viewHolder, int i) {
        viewHolder.itemView.setTag(tickets.get(i));
        //adding values to views
        viewHolder.ticketId.setText(Integer.toString(tickets.get(i).getId()));
        viewHolder.ticketName.setText(tickets.get(i).getName());
        viewHolder.ticketPrice.setText(tickets.get(i).getPrice());
        viewHolder.travelDate.setText(tickets.get(i).gettDate());
        viewHolder.ticketValidity.setText(tickets.get(i).getValidity());

    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }
}

