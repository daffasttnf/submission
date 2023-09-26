package com.dicoding.submission.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.submission.R
import com.dicoding.submission.data.response.DetailUserResponse
import com.dicoding.submission.databinding.ActivityDetailBinding
import com.dicoding.submission.model.ModelDetailActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: ModelDetailActivity

    companion object {
        const val EXTRA_USER = "user_name"
        const val TAG = "DetailActivity"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(EXTRA_USER)

        supportActionBar?.title = name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this).get(ModelDetailActivity::class.java)
        viewModel.getUserDetails(name.toString())

        viewModel.userItem.observe(this, Observer {
            setDataUser(it)
        })

        viewModel.isLoading.observe(this, Observer {
            isLoading(it)
        })

        val sectionsPagerAdapter = SectionPagerAdapter(this, name)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f


    }

    private fun setDataUser(userData: DetailUserResponse) {
        binding.apply {
            Glide.with(applicationContext)
                .load(userData.avatarUrl)
                .circleCrop()
                .error(R.drawable.baseline_account_circle_24)
                .into(imgProfileUser)
            tvUserFullName.text = userData.name
            tvUsername.text = userData.login
            tvUserFollowersNumber.text = userData.followers.toString()
            tvUserFollowingsNumber.text = userData.following.toString()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    private fun isLoading(status: Boolean) {
        if (status) {
            binding.progressBarDetail.visibility = View.VISIBLE
        } else {
            binding.progressBarDetail.visibility = View.INVISIBLE
        }
    }


}