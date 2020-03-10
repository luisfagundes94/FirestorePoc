package com.luisfelipe.firebasetp3.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class User(
    var name: String = "",
    var password: String = "",
    var phone: String = "",
    var mobilePhone: String = "",
    var cpf: String = "",
    var city: String = "",
    var email: String = ""
): Parcelable