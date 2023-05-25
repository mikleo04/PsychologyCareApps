package com.example.psychologycareapps.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.ArrayList

@Parcelize
class ModelBarchart(
    var title: String? = null,
    var dataChart: ArrayList<Pair<String, Float>> = arrayListOf()

): Parcelable