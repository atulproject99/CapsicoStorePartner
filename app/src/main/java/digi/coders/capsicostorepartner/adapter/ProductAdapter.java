package digi.coders.capsicostorepartner.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import digi.coders.capsicostorepartner.R;
import digi.coders.capsicostorepartner.activity.ProductFullDetailActivity;
import digi.coders.capsicostorepartner.databinding.ProductLayoutBinding;
import digi.coders.capsicostorepartner.helper.Constraints;
import digi.coders.capsicostorepartner.model.Product;

public class ProductAdapter  extends RecyclerView.Adapter<ProductAdapter.MyHolder> {

    private Context ctx;
    private List<Product> list;

    public ProductAdapter(List<Product> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ctx=parent.getContext();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout,parent,false);
        return new MyHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Product product=list.get(position);
        holder.binding.productName.setText(product.getName());
        holder.binding.productPrice.setText(product.getSellPrice());
        holder.binding.productTitle.setText(product.getTitle());
        holder.binding.productPrice.setText("₹"+product.getSellPrice());
        Picasso.get().load(Constraints.BASE_URL+Constraints.MASTER_PRODUCT+product.getIcon()).into(holder.binding.productImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductFullDetailActivity.product=product;
                ctx.startActivity(new Intent(ctx, ProductFullDetailActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        ProductLayoutBinding binding;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            binding=ProductLayoutBinding.bind(itemView);
        }
    }

}
