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
import com.example.bookkeeper.R
import com.example.bookkeeper.databinding.FragmentDashboardBinding
import com.example.bookkeeper.ui.adapter.DigitAdapter
import com.example.bookkeeper.ui.adapter.LotteryAdapter
import com.example.bookkeeper.ui.adapter.ZodiacAdapter
import com.example.bookkeeper.ui.viewmodel.DashboardViewModel
import com.example.bookkeeper.util.DateUtil
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private var isHk = true

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel: DashboardViewModel by viewModels { DashboardViewModel.Factory }

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.dashboardDateBtn.text = DateUtil.formatDate()

        binding.dashboardDateBtn.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(R.string.common_date_select)
                .setSelection(DateUtil.getAndroidTimestamp(binding.dashboardDateBtn.text.toString(), DateUtil.dateFormatter))
                .build()
            datePicker.show(childFragmentManager, "date_picker")
            datePicker.addOnPositiveButtonClickListener { selection ->
                val timestamp = DateUtil.getSystemTimestamp(selection)
                binding.dashboardDateBtn.text = DateUtil.formatDate(DateUtil.getDate(timestamp))
                dashboardViewModel.selectedDate.value = timestamp
            }
        }

        binding.dashboardAreaChip.setOnClickListener {
            isHk = !isHk
            binding.dashboardAreaChip.setText(if (isHk) R.string.common_area_hk else R.string.common_area_mo)
            dashboardViewModel.selectedArea.value = if (isHk) "hk" else "mo"
        }

        val lotteryAdapter = LotteryAdapter(requireContext())
        binding.lotteryList.adapter = lotteryAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                dashboardViewModel.lotteryDetails.collect { records ->
                    lotteryAdapter.updateRecords(records)
                    lotteryAdapter.notifyDataSetChanged()
                }
            }
        }

        val digitAdapter = DigitAdapter(requireContext(), emptyList())
        binding.digitList.adapter = digitAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            dashboardViewModel.loadDefaultDigits()
            digitAdapter.setRecords(dashboardViewModel.defaultDigits)
            digitAdapter.notifyDataSetChanged()
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                dashboardViewModel.digits.collect { records ->
                    digitAdapter.updateRecords(records)
                    digitAdapter.notifyDataSetChanged()
                }
            }
        }

        val zodiacAdapter = ZodiacAdapter(requireContext(), emptyList())
        binding.zodiacList.adapter = zodiacAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            dashboardViewModel.loadDefaultZodiacs()
            zodiacAdapter.setRecords(dashboardViewModel.defaultZodiacs)
            zodiacAdapter.notifyDataSetChanged()
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                dashboardViewModel.zodiacs.collect { records ->
                    zodiacAdapter.updateRecords(records)
                    zodiacAdapter.notifyDataSetChanged()
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