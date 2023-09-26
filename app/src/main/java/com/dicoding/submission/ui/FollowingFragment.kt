package com.dicoding.submission.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission.data.response.ItemsItem
import com.dicoding.submission.databinding.FragmentFollowingBinding
import com.dicoding.submission.model.ModelFollowingFragment


class FollowingFragment : Fragment() {

    companion object {
        const val ARG_USERNAME = "arg_username"
    }

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: ModelFollowingFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString(ARG_USERNAME)
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollowingFragment.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvFollowingFragment.addItemDecoration(itemDecoration)

        viewModel = ViewModelProvider(requireActivity()).get(ModelFollowingFragment::class.java)

        viewModel.getUserFollowing(username.toString())

        viewModel.following.observe(requireActivity(), Observer {
            setUserAdapter(it)
        })

        viewModel.isLoading.observe(requireActivity(), Observer {
            isLoading(it)
        })


    }

    private fun isLoading(it: Boolean?) {
        if (it == true) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }

    }

    private fun setUserAdapter(listUser: List<ItemsItem>) {
        val adapter = UserAdapter(listUser)
        binding.rvFollowingFragment.adapter = adapter
    }


}