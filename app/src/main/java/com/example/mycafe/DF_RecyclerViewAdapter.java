package com.example.mycafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class DF_RecyclerViewAdapter extends RecyclerView.Adapter<DF_RecyclerViewAdapter.MyViewHolder>{
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<DonutFlavorModel> donutFlavorModels;

    int donutNum = 0;
    public DF_RecyclerViewAdapter(Context context, ArrayList<DonutFlavorModel> donutFlavorModels,
                                  RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.donutFlavorModels = donutFlavorModels;
        this.recyclerViewInterface = recyclerViewInterface;
    }
    @NonNull
    @Override
    public DF_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate our view
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);

        return new DF_RecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull DF_RecyclerViewAdapter.MyViewHolder holder, int position) {
    //
        holder.txtName.setText(donutFlavorModels.get(position).getDonutName());
        holder.txtPrice.setText(donutFlavorModels.get(position).getDonutPrice());
        holder.imageView.setImageResource(donutFlavorModels.get(position).getImage());
        holder.txtCount.setText(String.valueOf(OrdersBase.getInstance().getDonutNum(position)));}

    @Override
    public int getItemCount() {
        return donutFlavorModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //kinda like onCreate

        ImageView imageView;
        TextView txtName, txtPrice;
        Button btnIncDonut, btnDecDonut;
        EditText txtCount;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            //imageView = itemView.findViewById(R.id.imageView);
            imageView = itemView.findViewById(R.id.imageView);
            txtName = itemView.findViewById(R.id.textView9);
            txtPrice = itemView.findViewById(R.id.textView10);
            btnIncDonut = itemView.findViewById(R.id.btnIncDonut);
            btnDecDonut = itemView.findViewById(R.id.btnDecDonut);
            txtCount = itemView.findViewById(R.id.txtCount);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface!=null){
                        int position = getAbsoluteAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(position);
                        }

                    }
                }
            });
            btnIncDonut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();
                    recyclerViewInterface.onPlusClick(position);
                    OrdersBase.getInstance().addDonutNum(position);
                    txtCount.setText(String.valueOf(OrdersBase.getInstance().getDonutNum(position)));
                }
            });
            btnDecDonut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();
                    recyclerViewInterface.onMinClick(position);
                    OrdersBase.getInstance().remDonutNum(position);
                    txtCount.setText(String.valueOf(OrdersBase.getInstance().getDonutNum(position)));
                }
            });
        }
    }
}
