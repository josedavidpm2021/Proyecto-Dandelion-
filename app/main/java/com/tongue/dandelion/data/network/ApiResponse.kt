package com.tongue.dandelion.data.network

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @field:SerializedName("ok") val ok: Boolean,
    @field: SerializedName("success") val success: Success<T>,
    @field: SerializedName("error") val error: Error
) {
}