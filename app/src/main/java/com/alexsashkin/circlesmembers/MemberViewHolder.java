package com.alexsashkin.circlesmembers;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexsashkin.circle_manager.MemberModel;
import com.alexsashkin.circlesmembers.databinding.LayoutMemberItemBinding;

public class MemberViewHolder extends RecyclerView.ViewHolder {

    private LayoutMemberItemBinding layout;

    MemberViewHolder(@NonNull LayoutMemberItemBinding layout) {
        super(layout.getRoot());
        this.layout = layout;
    }

    void bind(MemberAdapterListener listener, MemberModel memberModel) {
        layout.setItem(memberModel);
        layout.getRoot().setOnClickListener(v -> {
            if (listener != null) {
                listener.onSelectMember(memberModel);
            }
        });
    }
}
