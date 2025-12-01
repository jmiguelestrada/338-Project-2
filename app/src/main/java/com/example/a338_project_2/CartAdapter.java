package com.example.a338_project_2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a338_project_2.Database.entities.Cart;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Cart> items;

    public CartAdapter(List<Cart> items) {
        this.items = items;
    }

    public void setItems(List<Cart> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart item = items.get(position);

        holder.nameTextView.setText(item.getMenuItemName());

        String qtyText = "Qty: " + item.getMenuItemQuantity();
        holder.quantityTextView.setText(qtyText);

        double lineTotal = item.getMenuItemPrice() * item.getMenuItemQuantity();
        holder.priceTextView.setText(
                String.format("$%.2f each | $%.2f", item.getMenuItemPrice(), lineTotal)
        );
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView quantityTextView;
        TextView priceTextView;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView    = itemView.findViewById(R.id.cartItemNameTextView);
            quantityTextView = itemView.findViewById(R.id.cartItemQuantityTextView);
            priceTextView   = itemView.findViewById(R.id.cartItemPriceTextView);
        }
    }
}
