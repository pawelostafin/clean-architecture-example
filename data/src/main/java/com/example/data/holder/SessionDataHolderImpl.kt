package com.example.data.holder

import android.content.SharedPreferences
import com.example.domain.model.UserId

class SessionDataHolderImpl(
    private val sharedPreferences: SharedPreferences
) : SessionDataHolder {

    override fun setUserId(userId: UserId?) {
        val stringValue = userId?.value?.toString()
        sharedPreferences.edit()
            .putString(KEY_USER_ID, stringValue)
            .apply()
    }

    override fun getUserId(): UserId? {
        val savedValue = sharedPreferences.getString(KEY_USER_ID, null)
        val rawLongValue = savedValue?.toLongOrNull()
        return rawLongValue?.let { UserId(it) }
    }

    companion object {
        private const val KEY_USER_ID = "KEY_USER_ID"
    }

}