package com.example.ourstory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create__acc.*
import kotlin.collections.HashMap

class CreateAccActivity : AppCompatActivity() {

    companion object{
        private const val RC_SIGN_IN = 120
    }

    private lateinit var mAuth : FirebaseAuth
    private lateinit var refUsers : DatabaseReference
    private var firebaseUserID: String = ""
    private lateinit var googleSignInClient : GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create__acc)

        mAuth = FirebaseAuth.getInstance()

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        register_button.setOnClickListener {
            registerUser()
        }

        login_button.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        google_button.setOnClickListener {
            signIn()
        }
    }

    private fun registerUser() {
        val firstName = first_name.text.toString()
        val lastName = last_name.text.toString()
        val emailRegis = email.text.toString()
        val passwordRegis = password.text.toString()
        val retype = confirm_password.text.toString()

        if(firstName.isEmpty()){
            first_name.error = "Please enter name"
            first_name.requestFocus()
            return
        }

        if(lastName.isEmpty()){
            last_name.error = "Please enter name"
            last_name.requestFocus()
            return
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
            email.error = "Please enter valid email"
            email.requestFocus()
            return

        }

        if(passwordRegis.isEmpty()){
            password.error = "Please enter password"
            password.requestFocus()
            return

        }

        if(retype.isEmpty() || passwordRegis != retype){
            confirm_password.error = "Incorrect re-type password"
            confirm_password.requestFocus()
            return

        }

        if(!emailRegis.isEmpty() && !passwordRegis.isEmpty() && !firstName.isEmpty() && !retype.isEmpty() && !lastName.isEmpty()){
            mAuth.createUserWithEmailAndPassword(emailRegis,passwordRegis)
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful){
                        firebaseUserID = mAuth.currentUser!!.uid
                        refUsers = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUserID)

                        val userHashMap = HashMap<String, Any>()
                        userHashMap["uid"] = firebaseUserID
                        userHashMap["firstname"] = firstName
                        userHashMap["lastname"] = lastName
                        userHashMap["profile"] = "https://firebasestorage.googleapis.com/v0/b/our-story-13e4c.appspot.com/o/profile.png?alt=media&token=d0af2b17-0694-470e-a6b0-cb14ef54c2b7"
                        userHashMap["cover"] = "https://firebasestorage.googleapis.com/v0/b/our-story-13e4c.appspot.com/o/cover.jpg?alt=media&token=e72663f1-68bb-4f41-b214-e0eb547d3dc2"
                        userHashMap["status"] = "offline"
                        userHashMap["search"] = firstName.toLowerCase()

                        refUsers.updateChildren(userHashMap)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful){
                                    val intent = Intent(this@CreateAccActivity, NavigationActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                    finish()
                                }
                            }
                    }
                    else{
                        Toast.makeText(this@CreateAccActivity, "Error Message: " + task.exception!!.message.toString(),Toast.LENGTH_LONG).show()
                    }
                }
        }else{
            Toast.makeText(this,"Please fill up the Credentials : |", Toast.LENGTH_LONG).show()
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, CreateAccActivity.RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == CreateAccActivity.RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if(task.isSuccessful){
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("CreateAccActivity", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("CreateAccActivity", "Google sign in failed", e)
                }
            }else{
                Log.w("CreateAccActivity", exception.toString())
            }

        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("LoginActivity", "signInWithCredential:success")
                    firebaseUserID = mAuth.currentUser!!.uid
                    refUsers = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUserID)

                    val userHashMap = HashMap<String, Any>()
                    userHashMap["uid"] = firebaseUserID
                    userHashMap["firstname"] = mAuth.currentUser!!.displayName.toString()
                    userHashMap["lastname"] = ""
                    userHashMap["profile"] = mAuth.currentUser!!.photoUrl.toString()
                    userHashMap["cover"] = "https://firebasestorage.googleapis.com/v0/b/our-story-13e4c.appspot.com/o/cover.jpg?alt=media&token=e72663f1-68bb-4f41-b214-e0eb547d3dc2"
                    userHashMap["status"] = "offline"
                    userHashMap["search"] = mAuth.currentUser!!.displayName.toString().toLowerCase()

                    refUsers.updateChildren(userHashMap)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val intent = Intent(this@CreateAccActivity, NavigationActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                    finish()
                                }
                            }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("CreateAccActivity", "signInWithCredential:failure", task.exception)
                }
            }
    }

}