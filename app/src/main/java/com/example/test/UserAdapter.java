package com.example.test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    List<TodoListItem> items;

    public UserAdapter(List<TodoListItem> items) {
        this.items = items;
    }


    public void updateList(List<TodoListItem>list){
        items=list;
        notifyDataSetChanged();
    }
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final UserAdapter.ViewHolder holder, int position) {
        holder.title.setText(items.get(position).getTitle());
        holder.desc.setText(items.get(position).getDesc());


        holder.check.setOnCheckedChangeListener(null);

        if(items.get(position).isChecked()){
            holder.check.setChecked(true);
        }
        else{
            holder.check.setChecked(false);
        }
        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Lint warning : don't use "final int position"
                int currentPosition = holder.getAdapterPosition();
                if (isChecked) {
                    TodoListItem finalItemBefore = items.get(currentPosition);
                    TodoListItem finalItemAfter = new TodoListItem(finalItemBefore.getTitle(),finalItemBefore.getDesc(),true
                    );
                    items.remove(finalItemBefore);
                    items.add(finalItemAfter);
                    notifyDataSetChanged();
                } else {
                    items.get(currentPosition).setChecked(false);
                    // no need to update the whole list since only one item has changed
                    notifyItemChanged(currentPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView desc;
        public CheckBox check;
        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            desc= itemView.findViewById(R.id.desc);
            check=itemView.findViewById(R.id.check);
        }
    }

}