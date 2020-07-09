package com.alexsashkin.circlesmembers;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexsashkin.circle_manager.CircleModel;
import com.alexsashkin.circlesmembers.databinding.LayoutCircleItemBinding;

public class CircleViewHolder extends RecyclerView.ViewHolder {

    private LayoutCircleItemBinding layout;

    CircleViewHolder(@NonNull LayoutCircleItemBinding layout) {
        super(layout.getRoot());
        this.layout = layout;
    }

    void bind(CircleAdapterListener listener, CircleModel circleModel) {
        layout.setItem(circleModel);
        layout.getRoot().setOnClickListener(v -> {
            if (listener != null) {
                listener.onSelectCircle(circleModel);
            }
        });
    }
}
