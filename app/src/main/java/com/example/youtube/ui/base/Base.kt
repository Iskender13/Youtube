package com.example.youtube.ui.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.example.youtube.data.service.Resource
import com.example.youtube.utils.showToast

abstract class Base:AppCompatActivity() {
    fun <T> LiveData<Resource<T>>.stateHandler(
        success: (data: T) -> Unit,
        state: ((res: Resource<T>) -> Unit) ?= null,
    ){
        observe(this@Base){res->
            state?.invoke(res)
            when(res){
                is Resource.Error -> {
                    this@Base.showToast(res.message!!)
                }
                is Resource.Loading -> {}
                is Resource.Success -> {
                    if (res.data != null){
                        success(res.data)
                    }
                }
            }
        }
    }
}