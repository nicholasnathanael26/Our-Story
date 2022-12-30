package com.example.ourstory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ourstory.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_edit_profile.image_profile
import kotlinx.android.synthetic.main.activity_profile.*

class EditProfileActivity : AppCompatActivity() {
    
    private lateinit var mAuth : FirebaseAuth
    private lateinit var refUsers : DatabaseReference
    private var firebaseUserID: String = ""
    var firebaseUser : FirebaseUser? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        supportActionBar?.title = "Profile"
        firebaseUser = FirebaseAuth.getInstance().currentUser
        refUsers = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)



        //display username and profile picture
        refUsers!!.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val user: Users? = snapshot.getValue(Users::class.java)
                    val firstname : String? = user!!.getFirstName()
                    val lastname : String? = user!!.getLastName()

                    edit_firstname.setText(firstname)
                    edit_lastname.setText(lastname)
                    Picasso.get().load(user.getProfile()).placeholder(R.drawable.profile).into(image_profile)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        mAuth = FirebaseAuth.getInstance()

        button_save.setOnClickListener {
            updateProfile()
        }
    }
    private fun updateProfile(){
        val firstname = edit_firstname.text.toString()
        val lastname = edit_lastname.text.toString()

        firebaseUserID = mAuth.currentUser!!.uid
        refUsers = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUserID)

        val userHashMap = HashMap<String, Any>()
        userHashMap["firstname"] = firstname
        userHashMap["lastname"] = lastname

        refUsers.updateChildren(userHashMap)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        val intent = Intent(this@EditProfileActivity, ProfileActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }
                }

    }
}