package com.kunal.memento.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BottomsheetRequestData(
    val title: String,
    val buttonText: String,
    val hintText: String
) : Parcelable
