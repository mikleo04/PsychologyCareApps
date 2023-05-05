package com.example.psychologycareapps.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ModelMoodmusic (

    var backgroundMoodmusic : Int = 0,
    var moodMusic : String? = null
):Parcelable
