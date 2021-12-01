package adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.realtimechatapp.databinding.ItemFromUserBinding
import com.example.realtimechatapp.databinding.ItemToUserBinding
import models.Messages

class MessageAdapter(val list: List<Messages>, var uid:String) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    inner class FromVh(var itemRv:ItemFromUserBinding):RecyclerView.ViewHolder(itemRv.root){

        fun onBind(message: Messages){
            itemRv.txt.text = message.message
            itemRv.txtDate.text = message.date
        }
    }

    inner class ToVh(var itemRv:ItemToUserBinding):RecyclerView.ViewHolder(itemRv.root){
        fun onBind(message: Messages){
            itemRv.txt.text = message.message
            itemRv.txtDate.text = message.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == 1){
            return FromVh(ItemFromUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }else{
            return ToVh(ItemToUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (getItemViewType(position) == 1){
            val fromVh = holder as FromVh
            fromVh.onBind(list[position])
        }else{
            val toVh = holder as ToVh
            toVh.onBind(list[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (list[position].fromUid == uid){
            return 1
        }else{
            return 2
        }
    }

    override fun getItemCount(): Int = list.size
}