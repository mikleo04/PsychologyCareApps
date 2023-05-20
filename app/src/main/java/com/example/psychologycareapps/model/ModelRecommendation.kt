package com.example.psychologycareapps.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ModelRecommendation (
    var imageActivity : Int = 0,
    var judulActivity : String? = null,
    var deskripsiActivity : String? = null,
    var turotialActivity : String? = null,
    var isExpandable : Boolean = false
) : Parcelable