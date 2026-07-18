package com.example.bookkeeper.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation.findNavController
import com.example.bookkeeper.R
import com.example.bookkeeper.data.model.AmsAccount
import com.example.bookkeeper.databinding.FragmentDiaryBinding
import com.example.bookkeeper.ui.adapter.CommonSpinnerAdapter
import com.example.bookkeeper.ui.adapter.DiaryAdapter
import com.example.bookkeeper.ui.common.AppConstants
import com.example.bookkeeper.ui.viewmodel.DiaryViewModel
import com.example.bookkeeper.util.DateUtil
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.launch
import java.util.Date

class DiaryFragment : Fragment() {

    private var _binding: FragmentDiaryBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val diaryViewModel: DiaryViewModel by viewModels { DiaryViewModel.Factory }

        _binding = FragmentDiaryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.diaryDateBtn.text = DateUtil.formatDate(Date(diaryViewModel.selectedDate.value))

        binding.diaryDateBtn.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(R.string.common_date_select)
                .setSelection(DateUtil.getAndroidTimestamp(diaryViewModel.selectedDate.value))
                .build()
            datePicker.show(childFragmentManager, "date_picker")
            datePicker.addOnPositiveButtonClickListener { selection ->
                val timestamp = DateUtil.getSystemTimestamp(selection)
                binding.diaryDateBtn.text = DateUtil.formatDate(DateUtil.getDate(timestamp))
                diaryViewModel.selectedDate.value = timestamp
            }
        }

        val accountAdapter = CommonSpinnerAdapter<AmsAccount>(requireContext(), { it.id }, { it.name }, emptyList())
        binding.diaryAccount.setAdapter(accountAdapter)
        binding.diaryAccount.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            diaryViewModel.selectedAccount.value = accountAdapter.getItem(position).id
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                diaryViewModel.accounts.collect { records ->
                    accountAdapter.setRecords(records)
                    accountAdapter.notifyDataSetChanged()
                }
            }
        }

        val areaAdapter = CommonSpinnerAdapter<AppConstants.Area>(requireContext(), { it.id }, { it.text }, AppConstants.Area.entries)
        binding.diaryArea.setAdapter(areaAdapter)
        binding.diaryArea.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            diaryViewModel.selectedArea.value = areaAdapter.getItem(position).code
        }

        val diaryAdapter = DiaryAdapter(requireContext())
        binding.diaryList.adapter = diaryAdapter
        diaryAdapter.setOnChevronClickListener { billId ->
            val bundle = Bundle().apply {
                putLong("billId", billId)
            }
            findNavController(binding.root).navigate(R.id.navigation_diary_detail, bundle)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                diaryViewModel.bills.collect { records ->
                    diaryAdapter.setRecords(records)
                    diaryAdapter.notifyDataSetChanged()
                    binding.diaryEmpty.visibility = if (records.isEmpty()) View.VISIBLE else View.GONE
                    binding.diaryList.visibility = if (records.isEmpty()) View.GONE else View.VISIBLE
                }
            }
        }

        binding.diaryAdd.setOnClickListener { v: View ->
            findNavController(v).navigate(R.id.navigation_diary_edit)
        }

        binding.diarySwipeRefresh.setOnRefreshListener {
            diaryViewModel.selectedAccount.value = 0
            diaryViewModel.selectedArea.value = ""
            binding.diaryAccount.setText("", false)
            binding.diaryArea.setText("", false)
            binding.diarySwipeRefresh.isRefreshing = false
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}