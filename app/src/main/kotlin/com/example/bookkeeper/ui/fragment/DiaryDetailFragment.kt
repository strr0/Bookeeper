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
import com.example.bookkeeper.databinding.FragmentDiaryDetailBinding
import com.example.bookkeeper.ui.activity.MainActivity
import com.example.bookkeeper.ui.adapter.DiaryDetailAdapter
import com.example.bookkeeper.ui.viewmodel.DashboardViewModel
import com.example.bookkeeper.ui.viewmodel.DiaryViewModel
import kotlinx.coroutines.launch

class DiaryDetailFragment : Fragment() {

    private var _binding: FragmentDiaryDetailBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val diaryViewModel: DiaryViewModel by viewModels { DiaryViewModel.Factory }
        val dashboardViewModel: DashboardViewModel by viewModels { DashboardViewModel.Factory }

        _binding = FragmentDiaryDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val bundle = arguments
        val billId = bundle!!.getLong("billId")

        viewLifecycleOwner.lifecycleScope.launch {
            dashboardViewModel.loadDefaultDigits()
            dashboardViewModel.loadDefaultZodiacs()
            val adapter = DiaryDetailAdapter(requireContext(), dashboardViewModel.defaultDigits, dashboardViewModel.defaultZodiacs)
            binding.diaryDetailList.adapter = adapter
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                diaryViewModel.listBillDetails(billId).collect { records ->
                    adapter.setRecords(records)
                    adapter.notifyDataSetChanged()
                    binding.diaryDetailEmpty.visibility = if (records.isEmpty()) View.VISIBLE else View.GONE
                    binding.diaryDetailList.visibility = if (records.isEmpty()) View.GONE else View.VISIBLE
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