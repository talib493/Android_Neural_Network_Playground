package com.example.neuralnetwork.firebase;

import com.google.firebase.auth.FirebaseAuth

public class FirebaseAuthRepo {
    val auth = FirebaseAuth.getInstance()

    fun createUser(email: String,
                   password:String ,
                   onSuccess:()-> Unit,
                   onFailure:(String)-> Unit
    )
    {
        auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener {
            onFailure(it.message?:"Unknown Error")
        }
    }
    fun logInUser(email: String,
                   password: String,
                   onSuccess: () -> Unit,
                   onFailure: (String) -> Unit)
    {
        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener {
            onFailure(it.message?:"Unknown Error")
        }

    }
    fun logOut(){
        auth.signOut()
    }
    fun currentUser()= auth.currentUser
}
