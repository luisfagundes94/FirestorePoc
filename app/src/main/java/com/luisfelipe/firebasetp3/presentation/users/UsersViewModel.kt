package com.luisfelipe.firebasetp3.presentation.users

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.luisfelipe.firebasetp3.domain.model.User

class UsersViewModel : ViewModel() {

    private val noUsersAvailableYet = MutableLiveData<Unit>()
    private val onFetchUsersFromDatabaseFailure = MutableLiveData<String>()
    private val onFetchUsersFromDatabaseSuccess = MutableLiveData<List<User>>()

    private val firestoreDatabase = FirebaseFirestore.getInstance()

    fun noUsersAvailableYet() = noUsersAvailableYet
    fun onFetchUsersFromDatabaseFailure() = onFetchUsersFromDatabaseFailure
    fun onFetchUsersFromDatabaseSuccess() = onFetchUsersFromDatabaseSuccess

    fun getAllUsersFromFirestoreDatabase() {
        val users = mutableListOf<User>()

        firestoreDatabase.firestoreSettings = createFirebaseDatabaseSettings()
        firestoreDatabase.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val user = document.toObject(User::class.java)
                    users.add(user)
                }

                onFetchUsersFromDatabaseSuccess.postValue(users)
            }
            .addOnFailureListener {
                onFetchUsersFromDatabaseFailure.postValue(it.message)
            }
            .addOnCompleteListener {
                if (users.isNullOrEmpty()) noUsersAvailableYet.postValue(Unit)
            }
    }

    private fun createFirebaseDatabaseSettings() =
        FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
}