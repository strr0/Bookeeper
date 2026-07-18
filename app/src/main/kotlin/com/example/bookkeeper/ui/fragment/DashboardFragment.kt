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
import com.example.bookkeeper.ui.component.LotteryEditDialog
import com.example.bookkeeper.ui.viewmodel.DashboardViewModel
import com.example.bookkeeper.util.DateUtil
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.launch
import java.util.Date

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

        binding.dashboardDateBtn.text = DateUtil.formatDate(Date(dashboardViewModel.selectedDate.value))

        binding.dashboardDateBtn.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(R.string.common_date_select)
                .setSelection(DateUtil.getAndroidTimestamp(dashboardViewModel.selectedDate.value))
                .build()
            datePicker.show(childFragmentManager, "date_picker")
            datePicker.addOnPositiveButtonClickListener { selection ->
                val timestamp = DateUtil.getSystemTimestamp(selection)
                binding.dashboardDateBtn.text = DateUtil.formatDate(DateUtil.getDate(timestamp))
                dashboardViewModel.selectedDate.value = timestamp
            }
        }

        isHk = "hk".equals(dashboardViewModel.selectedArea.value)
        binding.dashboardAreaChip.setText(if (isHk) R.string.common_area_hk else R.string.common_area_mo)

        binding.dashboardAreaChip.setOnClickListener {
            isHk = !isHk
            binding.dashboardAreaChip.setText(if (isHk) R.string.common_area_hk else R.string.common_area_mo)
            dashboardViewModel.selectedArea.value = if (isHk) "hk" else "mo"
        }

        val lotteryClick = { _: View ->
            LotteryEditDialog.show(requireContext()) { list ->
                viewLifecycleOwner.lifecycleScope.launch {
                    dashboardViewModel.saveLotteryDetail(list)
                }
            }
            true
        }
        val lotteryAdapter = LotteryAdapter(requireContext())
        binding.lotteryList.adapter = lotteryAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                dashboardViewModel.lotteryDetails.collect { records ->
                    lotteryAdapter.updateRecords(records)
                    lotteryAdapter.notifyDataSetChanged()
                    if(records.isNotEmpty()) {
                        binding.textLottery.setOnLongClickListener(null)
                    } else {
                        binding.textLottery.setOnLongClickListener(lotteryClick)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            dashboardViewModel.loadDefaultDigits()
            val digitAdapter = DigitAdapter(requireContext(), dashboardViewModel.defaultDigits)
            binding.digitList.adapter = digitAdapter
            binding.digitListEmpty.visibility = View.GONE
            binding.digitList.visibility = View.VISIBLE
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                dashboardViewModel.digits.collect { records ->
                    digitAdapter.updateRecords(records)
                    digitAdapter.notifyDataSetChanged()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            dashboardViewModel.loadDefaultZodiacs()
            val zodiacAdapter = ZodiacAdapter(requireContext(), dashboardViewModel.defaultZodiacs)
            binding.zodiacList.adapter = zodiacAdapter
            binding.zodiacListEmpty.visibility = View.GONE
            binding.zodiacList.visibility = View.VISIBLE
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                dashboardViewModel.zodiacs.collect { records ->
                    zodiacAdapter.updateRecords(records)
                    zodiacAdapter.notifyDataSetChanged()
                }
            }
        }

        binding.dashboardSwipeRefresh.setOnRefreshListener {
            binding.dashboardSwipeRefresh.isRefreshing = false
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}