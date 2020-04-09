package com.example.allegrointernapp.data.data_model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Price(
    val amount: @RawValue String,
    val currency: @RawValue String
) : Parcelable