package com.luisfelipe.firebasetp3.presentation.users

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.luisfelipe.firebasetp3.R
import com.luisfelipe.firebasetp3.domain.model.User
import com.luisfelipe.firebasetp3.presentation.user_details.UserDetailsActivity
import com.luisfelipe.firebasetp3.utils.OnItemClickListener
import com.luisfelipe.firebasetp3.utils.toast
import kotlinx.android.synthetic.main.activity_users.*

class UsersActivity : AppCompatActivity(), OnItemClickListener {

    companion object {
        fun getIntent(context: Context) =
            Intent(context, UsersActivity::class.java)
    }

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(UsersViewModel::class.java)
    }

    private val usersAdapter by lazy { UserListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        viewModel.getAllUsersFromFirestoreDatabase()
        initObservers()
    }

    private fun initObservers() {
        viewModel.apply {
            noUsersAvailableYet().observe(this@UsersActivity, Observer {
                displayNoUsersAvailableMessage()
            })
            isLoading().observe(this@UsersActivity, Observer {
                displayProgressBar()
            })
            isNotLoading().observe(this@UsersActivity, Observer {
                hideProgressBar()
            })
            onFetchUsersFromDatabaseFailure().observe(this@UsersActivity, Observer { errorMsg ->
                displayErrorMessage(message = errorMsg)
            })
            onFetchUsersFromDatabaseSuccess().observe(this@UsersActivity, Observer { users ->
                setUsersRecyclerView(users = users)
            })
        }
    }

    private fun setUsersRecyclerView(users: List<User>) {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        usersRecyclerView.setHasFixedSize(true)
        usersRecyclerView.layoutManager = layoutManager
        usersRecyclerView.adapter = usersAdapter
        usersAdapter.updateUsers(users)
        usersAdapter.setOnItemClickListener(this)
    }

    private fun displayErrorMessage(message: String) {
        error_message.visibility = View.VISIBLE
        error_message.text = message
    }

    private fun displayNoUsersAvailableMessage() {
        error_message.visibility = View.VISIBLE
        error_message.text = getString(R.string.warning_no_users_available)
    }

    override fun onItemClick(user: User) {
        navigateToUserDetailsActivity(user)
    }

    private fun navigateToUserDetailsActivity(user: User) {
        startActivity(UserDetailsActivity.getIntent(this, user))
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    private fun displayProgressBar() {
        progressBar.visibility = View.VISIBLE
    }
}
