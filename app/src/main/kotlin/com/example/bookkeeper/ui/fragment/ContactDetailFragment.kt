package com.example.bookkeeper.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.bookkeeper.databinding.FragmentContactDetailBinding
import com.example.bookkeeper.ui.activity.MainActivity
import com.example.bookkeeper.ui.adapter.ContactDetailAdapter
import com.example.bookkeeper.ui.viewmodel.ContactViewModel
import kotlinx.coroutines.launch

class ContactDetailFragment : Fragment() {

    private var _binding: FragmentContactDetailBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val contactViewModel: ContactViewModel by viewModels { ContactViewModel.Factory }

        _binding = FragmentContactDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val bundle = arguments
        val accId = bundle!!.getLong("accId")

        val adapter = ContactDetailAdapter(requireContext())
        binding.contactDetailList.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                contactViewModel.listAccountDetails(accId).collect { records ->
                    adapter.setRecords(records)
                    adapter.notifyDataSetChanged()
                    binding.contactDetailEmpty.visibility = if (records.isEmpty()) View.VISIBLE else View.GONE
                    binding.contactDetailList.visibility = if (records.isEmpty()) View.GONE else View.VISIBLE
                }
            }
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        val activity : MainActivity = requireActivity() as MainActivity
        // 隐藏导航栏
        activity.hideNavView()
    }

    override fun onStop() {
        super.onStop()
        val activity : MainActivity = requireActivity() as MainActivity
        // 显示导航栏
        activity.showNavView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}