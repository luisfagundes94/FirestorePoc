package com.luisfelipe.firebasetp3.presentation.form

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.luisfelipe.firebasetp3.R
import com.luisfelipe.firebasetp3.domain.model.User
import com.luisfelipe.firebasetp3.presentation.users.UsersActivity
import com.luisfelipe.firebasetp3.utils.toast
import kotlinx.android.synthetic.main.activity_form.*

class FormActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(FormViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        onClearFieldsBtnClick()
        onSaveUserBtnClick()
        onVizualizeUsersBtnClick()
        initObservers()
    }

    private fun onClearFieldsBtnClick() {
        btn_clear_fields.setOnClickListener {
            name.text?.clear()
            mobile_phone.text?.clear()
            password.text?.clear()
            phone.text?.clear()
            mobile_phone.text?.clear()
            cpf.text?.clear()
            city.text?.clear()
        }
    }

    private fun onSaveUserBtnClick() {
        btn_save_user.setOnClickListener {
            val user = User(
                name = name.text.toString(),
                password = password.text.toString(),
                phone = phone.text.toString(),
                mobilePhone = mobile_phone.text.toString(),
                cpf = cpf.text.toString(),
                city = city.text.toString(),
                email = mobile_phone.text.toString()
            )
            viewModel.checkIfFieldsAreEmpty(user)
        }
    }

    private fun onVizualizeUsersBtnClick() {
        btn_navigate_to_users_activity.setOnClickListener {
            navigateToUsersActivity()
        }
    }

    private fun initObservers() {
        viewModel.apply {
            emptyFields().observe(this@FormActivity, Observer {
                displayEmptyFieldsToast()
            })
            notEmptyFields().observe(this@FormActivity, Observer { user ->
                viewModel.insertUserToDatabase(user)
            })
            onInsertUserToDatabaseFailure().observe(this@FormActivity, Observer {
                displayOnInsertUserToDatabaseFailureMsg()
            })
            onInsertUserToDatabaseSuccess().observe(this@FormActivity, Observer {
                displayOnInsertUserToDatabaseSuccessMsg()
            })
        }
    }

    private fun navigateToUsersActivity() =
        startActivity(UsersActivity.getIntent(this))

    private fun displayOnInsertUserToDatabaseSuccessMsg() =
        toast("Usuário salvo com sucesso!")

    private fun displayOnInsertUserToDatabaseFailureMsg() =
        toast("Erro ao salvar usuário")

    private fun displayEmptyFieldsToast() =
        toast("Por favor preencha todos os campos!")

}
