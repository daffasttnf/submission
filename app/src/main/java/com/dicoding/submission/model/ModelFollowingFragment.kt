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

class ModelFollowingFragment : ViewModel() {
    private val _following = MutableLiveData<List<ItemsItem>>()
    val following: LiveData<List<ItemsItem>> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUserFollowing(username: String) {
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    _isLoading.value = false
                    _following.value = response.body()
                } else {
                    _isLoading.value = false
                    Log.e("FollowingFragment", response.message())
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e("FollowingFragment", t.message.toString())
            }

        })
    }

}