package com.dicoding.submission.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submission.data.response.DetailUserResponse
import com.dicoding.submission.data.retrofit.ApiService.ApiConfig
import com.dicoding.submission.ui.DetailActivity.Companion.TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModelDetailActivity : ViewModel() {
    private var _userItem = MutableLiveData<DetailUserResponse>()
    val userItem: LiveData<DetailUserResponse> = _userItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUserDetails(user: String) {
        val client = ApiConfig.getApiService().getDetail(user)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.body() != null && response.isSuccessful) {
                    _isLoading.value = false
                    _userItem.value = response.body()
                } else {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }
}

