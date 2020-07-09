package com.alexsashkin.circlesmembers;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.alexsashkin.circle_manager.MemberModel;

import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberViewHolder> {

    private MemberAdapterListener listener;
    private List<MemberModel> membersList;

    public void setListener(MemberAdapterListener listener) {
        this.listener = listener;
    }

    public void setMembersList(List<MemberModel> membersList) {
        this.membersList = membersList;

        notifyDataSetChanged();
    }

    public void addMember(MemberModel circle) {
        membersList.add(circle);
        notifyDataSetChanged();
    }

    public MemberAdapter(List<MemberModel> membersList, MemberAdapterListener listener) {
        this.membersList = membersList;
        setListener(listener);
    }


    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MemberViewHolder(
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.layout_member_item,
                        parent,
                        false));
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        if (membersList.size() > 0) {
            holder.bind(listener, membersList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return membersList == null ? 0 : membersList.size();
    }
}
