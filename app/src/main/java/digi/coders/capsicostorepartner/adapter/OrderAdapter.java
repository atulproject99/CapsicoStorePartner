package digi.coders.capsicostorepartner.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import digi.coders.capsicostorepartner.R;
import digi.coders.capsicostorepartner.activity.OrderDetailsActivity;
import digi.coders.capsicostorepartner.activity.OrderHistoryActivity;
import digi.coders.capsicostorepartner.activity.OrderStatusActivity;
import digi.coders.capsicostorepartner.activity.OrderSummaryActivity;
import digi.coders.capsicostorepartner.activity.TrackOrderActivity;
import digi.coders.capsicostorepartner.activity.TrackRiderActivity;
import digi.coders.capsicostorepartner.databinding.CancelReasonLayoutBinding;
import digi.coders.capsicostorepartner.databinding.DeliveredOrderLayoutBinding;
import digi.coders.capsicostorepartner.databinding.NewOrdersLayoutBinding;
import digi.coders.capsicostorepartner.databinding.ProcessingOrderLayoutBinding;
import digi.coders.capsicostorepartner.fragment.PreparingOrderFragment;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyHolder> {
    private int status;
    private Context ctx;

    public OrderAdapter(int status) {
        this.status = status;
    }

    @NonNull

    @Override
    public MyHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        ctx=parent.getContext();
        View view=null;
        if(status==1)
        {
            //new order
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.new_orders_layout, parent,false);
        }
        else if(status==2)
        {
            //processorder
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.processing_order_layout, parent,false);
        }
        else
        {
            //delivered order
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.delivered_order_layout, parent,false);

        }
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  MyHolder holder, int position) {
        if(status==1)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ctx.startActivity(new Intent(ctx, OrderDetailsActivity.class));
                }
            });

        }
        else if(status==2) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ctx.startActivity(new Intent(ctx, OrderStatusActivity.class));
                }
            });
        }
        else
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   ctx.startActivity(new Intent(ctx, OrderHistoryActivity.class));
                }
            });

        }



        //handle all button
        //new order
        if(status==1) {
            holder.binding1.acceptOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ctx, "Order Accepted", Toast.LENGTH_SHORT).show();

                }
            });

            holder.binding1.rejectOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder aler = new AlertDialog.Builder(ctx);
                    AlertDialog alertDialog = aler.create();
                    View view = LayoutInflater.from(ctx).inflate(R.layout.cancel_reason_layout, null);
                    CancelReasonLayoutBinding binding = CancelReasonLayoutBinding.bind(view);
                    binding.close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                    binding.submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Toast.makeText(ctx, "Successfulky post reason", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.setView(view);
                    alertDialog.show();

                }
            });

        }
        //preparing order

        else if(status==2) {
            holder.binding2.markAsDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ctx, "Food prepared", Toast.LENGTH_SHORT).show();
                }
            });
            holder.binding2.trackRider.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ctx.startActivity(new Intent(ctx, TrackRiderActivity.class));
                }
            });

        }
        //delivered
        else {
            holder.binding3.viewOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ctx.startActivity(new Intent(ctx, OrderSummaryActivity.class));
                }
            });
            holder.binding3.trackOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ctx.startActivity(new Intent(ctx, TrackOrderActivity.class));
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        if(status==1)
        {
            //new order
            return 5;
        }
        else if(status==2)
        {
            //processorder
            return 5;
        }
        else
        {
            //delivered order
            return 5;
        }
    }


    public class MyHolder extends RecyclerView.ViewHolder
    {
        NewOrdersLayoutBinding binding1;
        ProcessingOrderLayoutBinding binding2;
        DeliveredOrderLayoutBinding binding3;
        public MyHolder(@NonNull  View itemView) {
            super(itemView);
            if(status==1)
            {
                binding1=NewOrdersLayoutBinding.bind(itemView);
            }
            else if(status==2)
            {
                binding2=ProcessingOrderLayoutBinding.bind(itemView);

            }
            else
            {

                binding3=DeliveredOrderLayoutBinding.bind(itemView);
            }


        }

    }
}
