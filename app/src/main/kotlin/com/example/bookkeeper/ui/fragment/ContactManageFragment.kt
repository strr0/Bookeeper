package com.example.bookkeeper.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkeeper.ui.activity.MainActivity
import com.example.bookkeeper.R
import com.example.bookkeeper.databinding.FragmentContactManageBinding
import com.example.bookkeeper.ui.adapter.ContactManageAdapter
import com.example.bookkeeper.ui.viewmodel.ContactManageViewModel
import kotlinx.coroutines.launch

class ContactManageFragment : Fragment() {

    private var _binding: FragmentContactManageBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val contactManageViewModel: ContactManageViewModel by viewModels { ContactManageViewModel.Factory }

        _binding = FragmentContactManageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = ContactManageAdapter(requireContext())
        binding.contactManageList.adapter = adapter
        adapter.setOnChevronClickListener { record ->
            val bundle = Bundle().apply {
                putString("id", record.id.toString())
                putString("name", record.name.orEmpty())
                putString("phone", record.phone.orEmpty())
                putString("balance", record.balance?.toString().orEmpty())
                putString("remarks", record.remarks.orEmpty())
            }
            findNavController(binding.root).navigate(R.id.navigation_contact_manage_edit, bundle)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                contactManageViewModel.accounts.collect { records ->
                    adapter.setRecords(records)
                    adapter.notifyDataSetChanged()
                    binding.contactManageEmpty.visibility = if (records.isEmpty()) View.VISIBLE else View.GONE
                    binding.contactManageList.visibility = if (records.isEmpty()) View.GONE else View.VISIBLE
                }
            }
        }

        binding.contactManageAdd.setOnClickListener { v: View ->
            findNavController(v).navigate(R.id.navigation_contact_manage_edit)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
}
