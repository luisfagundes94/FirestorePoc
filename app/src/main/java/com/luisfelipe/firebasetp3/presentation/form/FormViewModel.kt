package com.luisfelipe.firebasetp3.presentation.form

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.luisfelipe.firebasetp3.domain.model.User

class FormViewModel : ViewModel() {

    private val emptyFields = MutableLiveData<Unit>()
    private val notEmptyFields = MutableLiveData<User>()
    private val onInsertUserToDatabaseSuccess = MutableLiveData<Unit>()
    private val onInsertUserToDatabaseFailure = MutableLiveData<Unit>()

    private val firebaseDatabase = FirebaseFirestore.getInstance()

    fun emptyFields() = emptyFields
    fun notEmptyFields() = notEmptyFields
    fun onInsertUserToDatabaseSuccess() = onInsertUserToDatabaseSuccess
    fun onInsertUserToDatabaseFailure() = onInsertUserToDatabaseFailure

    fun checkIfFieldsAreEmpty(user: User) {
        logUser(user)
        if (user.city.isNotEmpty() && user.cpf.isNotEmpty() && user.mobilePhone.isNotEmpty()
            && user.name.isNotEmpty() && user.phone.isNotEmpty() && user.password.isNotEmpty()
            && user.email.isNotEmpty()
        ) notEmptyFields.postValue(user)
        else emptyFields.postValue(Unit)
    }

    fun insertUserToDatabase(user: User) {
        firebaseDatabase.firestoreSettings = createFirebaseDatabaseSettings()
        firebaseDatabase.collection("users")
            .add(user)
            .addOnSuccessListener {
                onInsertUserToDatabaseSuccess.postValue(Unit)
            }
            .addOnFailureListener {
                onInsertUserToDatabaseFailure.postValue(Unit)
            }
    }

    private fun createFirebaseDatabaseSettings() =
        FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()

    private fun logUser(user: User) {
        Log.d("user", user.toString())
    }
}