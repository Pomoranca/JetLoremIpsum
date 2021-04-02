package com.app.jetloremipsum.result


import com.google.gson.annotations.SerializedName

data class ResultItem(
    @SerializedName("body")
    var body: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("userId")
    var userId: Int?
)