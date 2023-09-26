package com.dicoding.submission.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submission.data.response.GithubResponse
import com.dicoding.submission.data.response.ItemsItem
import com.dicoding.submission.data.retrofit.ApiService.ApiConfig
import com.dicoding.submission.ui.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModelMainActivity : ViewModel() {

    private val _user = MutableLiveData<List<ItemsItem>>()
    val user: LiveData<List<ItemsItem>> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getUser()
    }

    fun getUser(name: String = "Daffa") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(name)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _user.value = response.body()?.items
                } else {
                    Log.e(MainActivity.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(MainActivity.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }


}