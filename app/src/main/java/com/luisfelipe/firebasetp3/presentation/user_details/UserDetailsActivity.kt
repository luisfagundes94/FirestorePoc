package com.luisfelipe.firebasetp3.presentation.user_details

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.luisfelipe.firebasetp3.R
import com.luisfelipe.firebasetp3.domain.model.User
import kotlinx.android.synthetic.main.activity_user_details.*

class UserDetailsActivity : AppCompatActivity() {

    companion object {
        private const val USER = "USER"
        fun getIntent(context: Context, user: User) =
            Intent(context, UserDetailsActivity::class.java).apply {
                putExtra(USER, user)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        val user = intent.getParcelableExtra<User>(USER)
        user?.let {
            setUserInfo(it)
        }
    }

    private fun setUserInfo(user: User) {
        name.text = user.name
        email.text = user.email
        mobile_phone.text = user.mobilePhone
        phone.text = user.phone
        city.text = user.city
        cpf.text = user.cpf
    }
}
