package si.ak93.todoapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Anže Kožar on 31.5.2018.
 * nzkozar@gmail.com
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    List<TodoItem> dataset;
    private OnItemClickListener onItemClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View mView;
        TextView nameText;

        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            nameText=itemView.findViewById(R.id.itemName);
        }
    }

    public RecyclerViewAdapter(List<TodoItem> items){
        dataset=items;
        onItemClickListener=null;
    }

    public RecyclerViewAdapter(List<TodoItem> item, OnItemClickListener listener){
        dataset=item;
        onItemClickListener=listener;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapter.ViewHolder holder, final int position) {
        TodoItem item = dataset.get(position);

        holder.nameText.setText(item.name);

        if(onItemClickListener!=null){
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
