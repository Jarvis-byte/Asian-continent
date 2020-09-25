package com.example.recylerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.viewHolder> implements Filterable {
    public ImageView flags;
    //Initialize Variables;
    private List<Asia>dataList;
    private List<Asia> listItemListFull;
    private Context context;
    private RoomDb database;
    private ImageLoader _ImageLoader;
    public MainAdapter(List<Asia> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
        listItemListFull= new ArrayList<Asia>(dataList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_main,parent,false);
        return new MainAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, int position) {
final Asia data=dataList.get(position);
database=RoomDb.getInstance(context);
holder.textName.setText(data.getName());
holder.textCapital.setText("Capital\t"+data.getCapital());
holder.textRegion.setText("Region\t"+data.getRegion());
holder.textSubRegion.setText("Sub-Region\t"+data.getSubregion());
holder.textPopulation.setText("Population\t"+data.getPopulation());
holder.textBorders.setText("Borders\t\t"+data.getBorgers());
holder.textLanguages.setText("Languages\t"+data.getLanguages());
Log.i("Flags",data.getFlagurl());
        GlideApp.with(context).load(data.getFlagurl()).
                apply(RequestOptions.centerInsideTransform()).into(flags);


holder.delete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        //Initialize delet
       Asia d=dataList.get(holder.getAdapterPosition());
       database.mainDao().delete(d);
       int position =holder.getAdapterPosition();
       dataList.remove(position);
       notifyItemRemoved(position);
       notifyItemRangeChanged(position,dataList.size());


    }
});
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView textName,textCapital,textRegion,textSubRegion,textPopulation,textBorders,textLanguages;
       public ImageView delete;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            textName=itemView.findViewById(R.id.textName);
            textCapital=itemView.findViewById(R.id.textCapital);
            textRegion=itemView.findViewById(R.id.textRegion);
            textSubRegion=itemView.findViewById(R.id.textSubRegion);
            textPopulation=itemView.findViewById(R.id.textPopulation);
            textBorders=itemView.findViewById(R.id.textBorders);
            textLanguages=itemView.findViewById(R.id.textLanguages);
            flags=(ImageView) itemView.findViewById(R.id.flags);
            delete=itemView.findViewById(R.id.delete);

        }

    }

    private Filter exampleFilter =new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Asia> filteredListx=new ArrayList<>();
            if (constraint==null||constraint.length()==0)
            {
                filteredListx.addAll(listItemListFull);
            }
            else
            {
                String filterPattern=constraint.toString().toLowerCase().trim();//trim =empty space remover
                for (Asia item:listItemListFull)
                {
                    if (item.getName().toLowerCase().startsWith(filterPattern))
                    {
                        filteredListx.add(item);
                    }

                }
            }
            FilterResults results=new FilterResults();
            results.values=filteredListx;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dataList.clear();
            dataList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}