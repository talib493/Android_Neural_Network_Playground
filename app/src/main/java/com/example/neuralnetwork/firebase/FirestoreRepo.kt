package com.example.neuralnetwork.firebase

import androidx.compose.runtime.retain.retain
import com.example.neuralnetwork.dataClasses.FirebaseModelData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions

class FirestoreRepo {
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
    private val uid: String
        get() = auth.currentUser?.uid
            ?: throw IllegalStateException("User not logged in")


    fun addModel(
        model : FirebaseModelData,
        onSuccess:()->Unit,
        onFailure:(String)-> Unit,
    ) {
        firestore.collection("users")
                .document(uid)
                .collection("models")
                .document(model.modelId)
                .set(model).addOnSuccessListener {
                    onSuccess()
                }.addOnFailureListener {
                    onFailure(it.message ?: "Unknown Error")
                }
    }

    fun getModel(
        onSuccess: (List<FirebaseModelData>) -> Unit,
        onFailure: (String) -> Unit
    ){
        firestore
            .collection("users")
            .document(uid)
            .collection("models")
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .get().addOnSuccessListener { result->
                val models =result.toObjects(FirebaseModelData::class.java)
                onSuccess(models)
            }.addOnFailureListener {
                onFailure(it.message?:"Unknown Error")
            }
    }
    fun deleteModel(
        modelId: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ){
        firestore
            .collection("users")
            .document(uid)
            .collection("models")
            .document(modelId)
            .delete().addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener {
                onFailure(it.message?:"Unknown Error")
            }

    }
    fun getModelWithId(
        id: String,
        onSuccess: (FirebaseModelData) -> Unit,
        onFailure: (String) -> Unit)
    {
         firestore
            .collection("users")
            .document(uid)
            .collection("models")
            .document(id)
            .get()
            .addOnSuccessListener { data->
                val entity = data.toObject(FirebaseModelData::class.java)
                if (entity != null){
                    onSuccess(entity)
                }
                else{
                    onFailure("Failed to parse data")
                }

            }.addOnFailureListener {
                onFailure(it.message?:"Unknown Error")
            }
    }
    fun checkExist(
        model: FirebaseModelData,
        Exist: (Boolean)-> Unit
    ){
        firestore.collection("users")
            .document(uid)
            .collection("models")
            .whereEqualTo("modelName", model.modelName)
            .limit(1)
            .get().addOnSuccessListener {snapshots ->
                if (!snapshots.isEmpty){
                    Exist(true)
                }else{
                    Exist(false)
                }
            }
    }
    fun updateModel(
        model : FirebaseModelData,
        onSuccess:()->Unit,
        onFailure:(String)-> Unit,
    ) {
        firestore.collection("users")
            .document(uid)
            .collection("models")
            .document(model.modelId)
            .set(model, SetOptions.merge())
            .addOnSuccessListener{
                onSuccess()
            }.addOnFailureListener {
                onFailure(it.message?:"Unknown Error")
            }
    }


}
