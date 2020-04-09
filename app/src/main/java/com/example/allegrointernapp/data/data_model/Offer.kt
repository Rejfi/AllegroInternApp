package com.example.allegrointernapp.data.data_model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Offer(
    val description: String,
    val id: String,
    val name: String,
    val price: @RawValue Price,
    val thumbnailUrl: String) : Parcelable