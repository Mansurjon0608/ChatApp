package adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.realtimechatapp.databinding.ItemUsersBinding
import com.squareup.picasso.Picasso
import models.User


class UserAdapter(val list: List<User>, val onCLick: OnCLick) :RecyclerView.Adapter<UserAdapter.Vh>(){
    inner class Vh(var itemRv:ItemUsersBinding): RecyclerView.ViewHolder(itemRv.root){

        fun onBind(user: User, position: Int){
            itemRv.txtUsername.text = user.displayName

            Picasso.get().load(user.photoUrl).into(itemRv.profileImage)

            itemRv.root.setOnClickListener {
                    onCLick.onCLick(user)
            }
        }
    }

    inner class PhoneVh( var itemRv: ItemUsersBinding):RecyclerView.ViewHolder(itemRv.root){

        fun onBind(user: User, position: Int){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    interface OnCLick{
        fun onCLick(user: User)
    }
}
