package com.example.psychologycareapps.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ModelEmotion (
    var emotion :String? = null,
    var emotionImage :Int = 0
) : Parcelable