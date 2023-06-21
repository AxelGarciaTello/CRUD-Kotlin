package com.example.crud_v1

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(context: Context, dataList: List<DataClass>) : RecyclerView.Adapter<MyViewHolder>() {

    var context: Context = context
    var dataList: List<DataClass> = dataList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //Glide.with(context).load(dataList).get(position).getDataImage()).into(holder.recImage);
        holder.recTitle.setText(dataList.get(position).dataTitle)
        holder.recDesc.setText(dataList.get(position).dataDesc)
        holder.recLang.setText(dataList.get(position).dataLang)

        holder.recCard.setOnClickListener {
            var intent: Intent = Intent(context, DetailActivity::class.java)
            //intent.putExtra("Image",dataList.get(holder.adapterPosition).dataImage)
            intent.putExtra("Description", dataList.get(holder.adapterPosition).dataDesc)
            intent.putExtra("Title", dataList.get(holder.adapterPosition).dataTitle)
            intent.putExtra("Key", dataList.get(holder.adapterPosition).key)
            intent.putExtra("Language", dataList.get(holder.adapterPosition).dataLang)
            context.startActivity(intent)
        }
    }

    fun searchDataList(searchList: ArrayList<DataClass>){
        dataList = searchList;
        notifyDataSetChanged();
    }
}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val recImage: ImageView = itemView.findViewById(R.id.recImage)
    val recCard: CardView = itemView.findViewById(R.id.recCard)
    val recDesc: TextView = itemView.findViewById(R.id.recDesc)
    val recLang: TextView = itemView.findViewById(R.id.recLang)
    val recTitle: TextView = itemView.findViewById(R.id.recTitle)
}
