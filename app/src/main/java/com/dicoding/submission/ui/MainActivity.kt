package com.dicoding.submission.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission.R
import com.dicoding.submission.data.response.ItemsItem
import com.dicoding.submission.databinding.ActivityMainBinding
import com.dicoding.submission.model.MainViewModel
import com.dicoding.submission.model.ModelMainActivity

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var viewModel: ModelMainActivity
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            MainViewModel::class.java
        )

        mainViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            updateTheme(isDarkModeActive)
        }

        supportActionBar?.hide()
        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        viewModel = ViewModelProvider(this).get(ModelMainActivity::class.java)

        with(binding) {
            searchBar.inflateMenu(R.menu.option_menu)
            searchBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu2 -> {
                        startActivity(Intent(this@MainActivity,ThemeSettingActivity::class.java))
                        true
                    }
                    else -> false
                }
            }
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    if (searchView.text?.isEmpty() == true) {
                        Toast.makeText(
                            this@MainActivity,
                            "Masukkan Username terlebih dahulu",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        searchBar.text = searchView.text
                        searchView.hide()
                        viewModel.getUser(searchBar.text.toString())
                    }

                    false
                }
        }


        viewModel.user.observe(this, Observer {
            setUserAdapter(it)
        })

        viewModel.isLoading.observe(this, Observer {
            isLoading(it)
        })
    }


    private fun setUserAdapter(listUser: List<ItemsItem>) {
        if (listUser.isEmpty()) {
            Toast.makeText(this@MainActivity, "Username Tidak ada", Toast.LENGTH_SHORT).show()
        } else {
            val adapter = UserAdapter(listUser)
            binding.rvUser.adapter = adapter
        }
    }


    private fun isLoading(status: Boolean) {
        if (status) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun updateTheme(isDarkModeActive: Boolean) {
        if (isDarkModeActive) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}