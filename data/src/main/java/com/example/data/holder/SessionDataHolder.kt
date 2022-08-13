package com.example.data.holder

import com.example.domain.model.UserId

interface SessionDataHolder {

    fun setUserId(userId: UserId?)

    fun getUserId(): UserId?

}