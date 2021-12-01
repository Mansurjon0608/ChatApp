package com.example.realtimechatapp

import adapters.MessageAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.realtimechatapp.databinding.ActivityMessagesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import models.Messages
import models.User

class MessagesActivity : AppCompatActivity() {

    lateinit var binding: ActivityMessagesBinding
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var auth: FirebaseAuth
    lateinit var referenceMessage: DatabaseReference
    lateinit var messageAdapter: MessageAdapter
    lateinit var user: User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessagesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        user = intent.getSerializableExtra("keyUser") as User



        firebaseDatabase = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        referenceMessage = firebaseDatabase.getReference("message")


        Picasso.get().load(user.photoUrl).into(binding.profileImage)
        binding.txtUsername.text = user.displayName

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, UsersActivity::class.java))
        }


        binding.editMessage.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.btnSend.setImageResource(R.drawable.ic_baseline_unsend_24)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.btnSend.setImageResource(R.drawable.ic_baseline_send_24)
            }
        })


        binding.btnSend.setOnClickListener {
            if (binding.editMessage.text.toString().trim().isNotEmpty()) {
                val key = referenceMessage.push().key
                val message =
                    Messages(key, binding.editMessage.text.toString().trim(), auth.uid, user.uid)
                referenceMessage.child(key!!).setValue(message)
                Toast.makeText(this, "Sent", Toast.LENGTH_SHORT).show()
                binding.editMessage.text.clear()
                binding.btnSend.setImageResource(R.drawable.ic_baseline_unsend_24)
            }else{
                Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show()
                binding.editMessage.text.clear()
                binding.btnSend.setImageResource(R.drawable.ic_baseline_unsend_24)
            }

        }

        referenceMessage.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listM = ArrayList<Messages>()
                val children = snapshot.children

                for (child in children) {
                    val value = child.getValue(Messages::class.java)
                    if (value != null) {
                        if ((value.fromUid==auth.uid && value.toUid==user.uid) || (value.toUid==auth.uid && value.fromUid == user.uid))
                        listM.add(value)
                    }


                }

                messageAdapter = MessageAdapter(listM, auth.uid!!)
                binding.rv.adapter = messageAdapter
                binding.rv.scrollToPosition(listM.size - 1)

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }
}