package com.example.appw4

import Entity.Person
import Interface.OnItemClickListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomViewHolder (view: View): RecyclerView.ViewHolder(view){
    var txtFullName: TextView = view.findViewById(R.id.txtPersonNameItem_recycler)
    var txtAddress: TextView = view.findViewById(R.id.txtAddressItem_recycler)
    var txtPhone: TextView = view.findViewById(R.id.txtPhoneItem_recycler)
    var imgPhoto: ImageView = view.findViewById(R.id.imgPhoto_ItemRecycler)

    fun bind (item: Person, clickListener: OnItemClickListener){
        txtFullName.setText(item.FullName().toString())
        txtAddress.setText(item.Address.toString())
        txtPhone.setText(item.Phone.toString())
        imgPhoto.setImageBitmap(item.Photo)

        itemView.setOnClickListener{
            clickListener.onItemClicked(item)
        }
    }
}


class PeopleListAdapter (private var itemList: List<Person>, val itemClickListener: OnItemClickListener): RecyclerView.Adapter<CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_person, parent, false)
        return CustomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        var item = itemList[position]
        holder.bind(item, itemClickListener)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}