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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkeeper.ui.activity.MainActivity
import com.example.bookkeeper.databinding.FragmentRuleManageBinding
import com.example.bookkeeper.ui.adapter.RuleManageAdapter
import com.example.bookkeeper.ui.viewmodel.RuleManageViewModel
import kotlinx.coroutines.launch

class RuleManageFragment : Fragment() {

    private var _binding: FragmentRuleManageBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val ruleManageViewModel: RuleManageViewModel by viewModels { RuleManageViewModel.Factory }

        _binding = FragmentRuleManageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = RuleManageAdapter(requireContext())
        binding.ruleManageList.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                ruleManageViewModel.rules.collect { records ->
                    adapter.setRecords(records)
                    adapter.notifyDataSetChanged()
                    binding.ruleManageEmpty.visibility = if (records.isEmpty()) View.VISIBLE else View.GONE
                    binding.ruleManageList.visibility = if (records.isEmpty()) View.GONE else View.VISIBLE
                }
            }
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
