package com.example.psychologycareapps.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ModelRecommendation (
    var albumImage : Int = 0,
    var judulMusik : String? = null,
    var penyanyi : String? = null
) : Parcelable