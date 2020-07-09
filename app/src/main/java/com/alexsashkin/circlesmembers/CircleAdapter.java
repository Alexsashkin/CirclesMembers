package com.alexsashkin.circlesmembers;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.alexsashkin.circle_manager.CircleModel;

import java.util.List;

public class CircleAdapter extends RecyclerView.Adapter<CircleViewHolder> {

    private CircleAdapterListener listener;
    private List<CircleModel> circlesList;

    public void setListener(CircleAdapterListener listener) {
        this.listener = listener;
    }

    public void setCirclesList(List<CircleModel> circlesList) {
        this.circlesList = circlesList;
        notifyDataSetChanged();
    }

    public void addCircle(CircleModel circleModel) {
        circlesList.add(circleModel);
        notifyDataSetChanged();
    }

    public CircleAdapter(List<CircleModel> circlesList) {
        this.circlesList = circlesList;
    }

    public CircleAdapter(List<CircleModel> circlesList, CircleAdapterListener listener) {
        this.circlesList = circlesList;
        setListener(listener);
    }


    @NonNull
    @Override
    public CircleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CircleViewHolder(
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.layout_circle_item,
                        parent,
                        false));
    }

    @Override
    public void onBindViewHolder(@NonNull CircleViewHolder holder, int position) {
        if (circlesList.size() > 0) {
            holder.bind(listener, circlesList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return circlesList == null ? 0 : circlesList.size();
    }
}
