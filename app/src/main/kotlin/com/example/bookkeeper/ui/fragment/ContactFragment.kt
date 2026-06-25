package com.example.bookkeeper.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
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
            Toast.makeText(activity, record.name, Toast.LENGTH_LONG).show()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                contactViewModel.accounts.collect { records ->
                    adapter.setRecords(records)
                    adapter.notifyDataSetChanged()
                    binding.contactEmpty.visibility = if (records.isEmpty()) View.VISIBLE else View.GONE
                    binding.contactList.visibility = if (records.isEmpty()) View.GONE else View.VISIBLE
                }
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
