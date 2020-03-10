package com.luisfelipe.firebasetp3.utils

import com.luisfelipe.firebasetp3.domain.model.User

interface OnItemClickListener {
    fun onItemClick(user: User)
}