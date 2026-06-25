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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkeeper.R
import com.example.bookkeeper.databinding.FragmentDiaryBinding
import com.example.bookkeeper.ui.adapter.DiaryAdapter
import com.example.bookkeeper.ui.viewmodel.DiaryViewModel
import com.example.bookkeeper.util.DateUtil
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.launch

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

        binding.diaryDateBtn.text = DateUtil.formatDate()

        binding.diaryDateBtn.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(R.string.common_date_select)
                .setSelection(DateUtil.getAndroidTimestamp(binding.diaryDateBtn.text.toString(), DateUtil.dateFormatter))
                .build()
            datePicker.show(childFragmentManager, "date_picker")
            datePicker.addOnPositiveButtonClickListener { selection ->
                val timestamp = DateUtil.getSystemTimestamp(selection)
                binding.diaryDateBtn.text = DateUtil.formatDate(DateUtil.getDate(timestamp))
                diaryViewModel.selectedDate.value = timestamp
            }
        }

        val adapter = DiaryAdapter(requireContext())
        binding.diaryList.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                diaryViewModel.bills.collect { records ->
                    adapter.setRecords(records)
                    adapter.notifyDataSetChanged()
                    binding.diaryEmpty.visibility = if (records.isEmpty()) View.VISIBLE else View.GONE
                    binding.diaryList.visibility = if (records.isEmpty()) View.GONE else View.VISIBLE
                }
            }
        }

        binding.diaryAdd.setOnClickListener { v: View ->
            findNavController(v).navigate(R.id.navigation_diary_edit)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}