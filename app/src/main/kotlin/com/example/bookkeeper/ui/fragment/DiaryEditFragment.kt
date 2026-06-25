package com.example.bookkeeper.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation.findNavController
import com.example.bookkeeper.ui.activity.MainActivity
import com.example.bookkeeper.R
import com.example.bookkeeper.databinding.FragmentDiaryEditBinding
import com.example.bookkeeper.data.model.AmsAccount
import com.example.bookkeeper.data.model.BmsBill
import com.example.bookkeeper.data.model.BmsBillDetail
import com.example.bookkeeper.ui.adapter.CommonSpinnerAdapter
import com.example.bookkeeper.ui.viewmodel.DiaryViewModel
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch
import java.math.BigDecimal

class DiaryEditFragment : Fragment() {

    private var _binding: FragmentDiaryEditBinding? = null
    private val binding get() = _binding!!

    private var isZodiac = false
    private var isHk = true
    private var displayText = ""

    private val regex: Regex = Regex(("^((\\d+|[鼠牛虎兔龙蛇马羊猴鸡狗猪]+)、)*(\\d+|[鼠牛虎兔龙蛇马羊猴鸡狗猪]+)×\\d+(\\.\\d+)?$"))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val diaryViewModel: DiaryViewModel by viewModels { DiaryViewModel.Factory }

        _binding = FragmentDiaryEditBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (savedInstanceState != null) {
            displayText = savedInstanceState.getString(STATE_DISPLAY, "")
            isZodiac = savedInstanceState.getBoolean(STATE_ZODIAC_MODE, false)
        }
        updateDisplay()
        applyMode(isZodiac)

        binding.diaryChange.setOnClickListener {
            applyMode(!isZodiac)
        }

        binding.diaryAreaChip.setOnClickListener {
            isHk = !isHk
            binding.diaryAreaChip.setText(if (isHk) R.string.common_area_hk else R.string.common_area_mo)
        }

        binding.clearEntity.setOnClickListener {
            if (displayText.isNotEmpty()) {
                displayText = displayText.dropLast(1)
            }
            updateDisplay()
        }

        bindInputButtons(
            binding.digit0, binding.digit1, binding.digit2, binding.digit3,
            binding.digit4, binding.digit5, binding.digit6, binding.digit7,
            binding.digit8, binding.digit9, binding.decPoint,
            binding.separator, binding.multiply,
            binding.zodiacRat, binding.zodiacOx, binding.zodiacTiger,
            binding.zodiacRabbit, binding.zodiacDragon, binding.zodiacSnake,
            binding.zodiacHorse, binding.zodiacGoat, binding.zodiacMonkey,
            binding.zodiacRooster, binding.zodiacDog, binding.zodiacPig,
            binding.zodiacSeparator, binding.zodiacMultiply,
        )

        bindSpinner()

        binding.enter.setOnClickListener { onEnter() }
        binding.zodiacEnter.setOnClickListener { onEnter() }
        binding.confirm.setOnClickListener { onConfirm(it) }
        binding.zodiacConfirm.setOnClickListener { onConfirm(it) }

        return root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_DISPLAY, displayText)
        outState.putBoolean(STATE_ZODIAC_MODE, isZodiac)
    }

    private fun bindInputButtons(vararg buttons: MaterialButton) {
        buttons.forEach { button ->
            button.setOnClickListener { appendInput(button.text.toString()) }
        }
    }

    private fun appendInput(value: String) {
        displayText += value
        updateDisplay()
    }

    private fun updateDisplay() {
        binding.diaryDisplay.text = if (displayText.isEmpty()) "0" else displayText
        binding.diaryDisplayScroll.post {
            binding.diaryDisplayScroll.fullScroll(View.FOCUS_DOWN)
        }
    }

    private fun applyMode(isZodiac: Boolean) {
        this.isZodiac = isZodiac
        binding.keypadNumber.isVisible = !isZodiac
        binding.keypadZodiac.isVisible = isZodiac
        binding.diaryChange.setText(
            if (isZodiac) R.string.diary_change_to_number else R.string.diary_change
        )
    }

    private fun bindSpinner() {
        val diaryViewModel: DiaryViewModel by viewModels { DiaryViewModel.Factory }

        val diarySelect = binding.diarySelect
        val adapter = CommonSpinnerAdapter<AmsAccount>(requireContext(), { it.id }, { it.name })
        diarySelect.setAdapter(adapter)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                diaryViewModel.accounts.collect { records ->
                    adapter.setRecords(records)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun onEnter() {
        if (displayText.isNotEmpty() && !'\n'.equals(displayText.last())) {
            appendInput("\n")
        }
    }

    private fun onConfirm(view: View) {
        if (displayText.isEmpty()) {
            Toast.makeText(activity, getString(R.string.diary_content_empty_tip), Toast.LENGTH_LONG).show()
            return
        }
        val bill: BmsBill = BmsBill()
        var formatText: String = ""
        val billDetailList: List<BmsBillDetail> = ArrayList()
        displayText.split("\n").forEach { input ->
            if (!regex.matches(input)) {
                Toast.makeText(activity, getString(R.string.diary_content_format_tip), Toast.LENGTH_LONG).show()
                return
            }
            val rawBill = input.split("×")
            val amount: BigDecimal = BigDecimal(rawBill[1])
            rawBill[0].split("、").forEach { item ->
                val trimItem = item.trim()
            }
        }
        findNavController(view).popBackStack()
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

    companion object {
        private const val STATE_DISPLAY = "display_text"
        private const val STATE_ZODIAC_MODE = "zodiac_mode"
    }
}
