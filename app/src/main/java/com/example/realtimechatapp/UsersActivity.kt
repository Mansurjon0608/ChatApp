package com.example.realtimechatapp

import adapters.UserAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.realtimechatapp.databinding.ActivityUsersBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import models.User

class UsersActivity : AppCompatActivity() {
    lateinit var binding:ActivityUsersBinding

    lateinit var firebaseDatabase:FirebaseDatabase
    lateinit var reference: DatabaseReference
    lateinit var auth:FirebaseAuth
    lateinit var userAdapter: UserAdapter
    lateinit var list:ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("user")



        val user = User (auth.currentUser?.displayName, auth.currentUser?.photoUrl.toString(), auth.uid)

        binding.username.text = auth.currentUser?.displayName
        Picasso.get().load(user.photoUrl).into(binding.profileImage)
        /*binding.profileImage.setImageURI(auth.currentUser?.photoUrl)*/

        reference.child(auth.uid!!).setValue(user)


        reference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               list = ArrayList()
                val childred = snapshot.children
                 for (child in childred){
                     val value = child.getValue(User::class.java)
                     if (value!=null){
                         if (value.uid!=auth.uid)
                         list.add(value)
                     }
                 }

                val userAdapter = UserAdapter(list, object : UserAdapter.OnCLick{
                    override fun onCLick(user: User) {
                        val intent = Intent(this@UsersActivity, MessagesActivity::class.java)
                        intent.putExtra("keyUser", user)
                        startActivity(intent)

                    }

                })
                binding.rvUsers.adapter = userAdapter
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }
}