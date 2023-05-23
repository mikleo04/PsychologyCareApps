package com.example.psychologycareapps.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ModelQuestionPanas(
    var id: String? = null,
    var type:String? = null,
    var question : String? = null,
    var answer : Int = 0
) : Parcelable