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
import androidx.navigation.Navigation.findNavController
import com.example.bookkeeper.R
import com.example.bookkeeper.databinding.FragmentContactBinding
import com.example.bookkeeper.ui.adapter.ContactAdapter
import com.example.bookkeeper.ui.viewmodel.ContactViewModel
import kotlinx.coroutines.launch

class ContactFragment : Fragment() {

    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val contactViewModel: ContactViewModel by viewModels { ContactViewModel.Factory }

        _binding = FragmentContactBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = ContactAdapter(requireContext())
        binding.contactList.adapter = adapter
        adapter.setOnChevronClickListener { record ->
            val bundle = Bundle().apply {
                putLong("accId", record.id)
            }
            findNavController(binding.root).navigate(R.id.navigation_contact_detail, bundle)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                contactViewModel.accounts.collect { records ->
                    adapter.setRecords(records)
                    adapter.notifyDataSetChanged()
                    binding.contactEmpty.visibility = if (records.isEmpty()) View.VISIBLE else View.GONE
                    binding.contactList.visibility = if (records.isEmpty()) View.GONE else View.VISIBLE
                    binding.contactSwipeRefresh.isRefreshing = false
                }
            }
        }

        binding.contactSwipeRefresh.setOnRefreshListener {
            binding.contactSwipeRefresh.isRefreshing = false
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
