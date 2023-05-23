package com.example.psychologycareapps.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ModelQuestionPanas(
    var question : String? = null,
    var answer : String? = null
) : Parcelable