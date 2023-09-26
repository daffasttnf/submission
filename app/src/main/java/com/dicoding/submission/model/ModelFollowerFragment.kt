package com.dicoding.submission.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submission.data.response.ItemsItem
import com.dicoding.submission.data.retrofit.ApiService.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModelFollowerFragment : ViewModel() {
    private val _followers = MutableLiveData<List<ItemsItem>>()
    val followers: LiveData<List<ItemsItem>> = _followers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUserFollower(username: String) {
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    _isLoading.value = false
                    _followers.value = response.body()
                } else {
                    _isLoading.value = false
                    Log.e("FollowerFragment", response.message())
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e("FollowerFragment", t.message.toString())
            }

        })
    }

}