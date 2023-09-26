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
import com.dicoding.submission.databinding.FragmentFollowerBinding
import com.dicoding.submission.model.ModelFollowerFragment

class FollowerFragment : Fragment() {

    companion object {
        const val ARG_USERNAME = "arg_username"
    }

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: ModelFollowerFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowerBinding.inflate(
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
        binding.rvFollowerFragment.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvFollowerFragment.addItemDecoration(itemDecoration)

        viewModel = ViewModelProvider(requireActivity()).get(ModelFollowerFragment::class.java)

        viewModel.getUserFollower(username.toString())

        viewModel.followers.observe(requireActivity(), Observer {
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
        binding.rvFollowerFragment.adapter = adapter
    }


}