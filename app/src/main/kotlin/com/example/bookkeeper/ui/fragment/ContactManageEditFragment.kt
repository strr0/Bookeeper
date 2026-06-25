package com.example.bookkeeper.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkeeper.ui.activity.MainActivity
import com.example.bookkeeper.R
import com.example.bookkeeper.databinding.FragmentContactManageEditBinding
import com.example.bookkeeper.data.model.AmsAccount
import com.example.bookkeeper.ui.common.AvatarUi
import com.example.bookkeeper.ui.adapter.ContactManageRuleAdapter
import com.example.bookkeeper.ui.viewmodel.ContactManageViewModel
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.*

class ContactManageEditFragment : Fragment() {

    private var _binding: FragmentContactManageEditBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val contactManageViewModel: ContactManageViewModel by viewModels { ContactManageViewModel.Factory }

        _binding = FragmentContactManageEditBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val avatarText = binding.contactManageEditAvatar
        val idText = binding.contactManageEditId
        val nameText = binding.contactManageEditName
        val phoneText = binding.contactManageEditPhone
        val balanceText = binding.contactManageEditBalance
        val remarksText = binding.contactManageEditRemarks

        val bundle = arguments
        if (bundle != null) {
            val name = bundle.getString("name").orEmpty()
            idText.setText(bundle.getString("id"))
            nameText.setText(name)
            phoneText.setText(bundle.getString("phone"))
            balanceText.setText(bundle.getString("balance"))
            remarksText.setText(bundle.getString("remarks"))
            AvatarUi.bindAvatar(requireContext(), avatarText, name)
            binding.contactManageDelete.visibility = View.VISIBLE
        } else {
            AvatarUi.bindAvatar(requireContext(), avatarText, null)
            binding.contactManageDelete.visibility = View.GONE
        }

        nameText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(s: Editable?) {
                AvatarUi.bindAvatar(requireContext(), avatarText, s?.toString())
            }
        })

        // 保存
        binding.contactManageSave.setOnClickListener { v: View ->
            val id = idText.text.toString()
            val name = nameText.text.toString().trim()
            val phone = phoneText.text.toString().trim()
            val balance = balanceText.text.toString().trim()
            val remarks = remarksText.text.toString().trim()
            if (name.isEmpty()) {
                Toast.makeText(activity, getString(R.string.contact_name_empty_tip), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val record = AmsAccount()
            record.name = name
            record.phone = phone
            record.balance = if (balance.isNotBlank()) BigDecimal(balance) else BigDecimal.ZERO
            record.remarks = remarks
            record.updateTime = Date()
            viewLifecycleOwner.lifecycleScope.launch {
                if (id.isEmpty()) {
                    contactManageViewModel.save(record)
                } else {
                    record.id = id.toInt()
                    contactManageViewModel.update(record)
                }
            }
            Toast.makeText(activity, getString(R.string.contact_save_success), Toast.LENGTH_LONG).show()
            findNavController(v).popBackStack()
        }

        // 删除
        binding.contactManageDelete.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setMessage(R.string.contact_delete_confirm)
                .setPositiveButton(R.string.contact_delete) { _, _ ->
                    val id = idText.text.toString()
                    if (id.isNotEmpty()) {
                        val record = AmsAccount()
                        record.id = id.toInt()
                        viewLifecycleOwner.lifecycleScope.launch {
                            contactManageViewModel.remove(record)
                        }
                        Toast.makeText(activity, getString(R.string.contact_delete_success), Toast.LENGTH_LONG).show()
                        findNavController(it).popBackStack()
                    }
                }
                .setNegativeButton(R.string.back, null)
                .show()
        }

        val id = idText.text.toString()
        if (id.isNotBlank()) {
            // 更多配置
            binding.contactManageMoreConfigToggle.visibility = View.VISIBLE
            // 展开/收起
            var moreConfigExpanded = false
            binding.contactManageMoreConfigToggle.setOnClickListener {
                moreConfigExpanded = !moreConfigExpanded
                binding.contactManageMoreConfigCard.visibility = if (moreConfigExpanded) View.VISIBLE else View.GONE
                binding.contactManageMoreConfigToggle.icon = if (moreConfigExpanded)
                    getDrawable(requireContext(), R.drawable.ic_chevron_down) else getDrawable(requireContext(), R.drawable.ic_chevron_right)
            }
            // 规则
            val adapter = ContactManageRuleAdapter(requireContext())
            binding.contactManageRuleList.adapter = adapter
            adapter.setOnSaveClickListener { rule, value ->
                // TODO: 保存规则逻辑
                Toast.makeText(activity, getString(R.string.contact_save_success), Toast.LENGTH_LONG).show()
            }
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    contactManageViewModel.listAccRules(id.toInt()).collect { records ->
                        adapter.setRecords(records)
                        adapter.notifyDataSetChanged()
                    }
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
