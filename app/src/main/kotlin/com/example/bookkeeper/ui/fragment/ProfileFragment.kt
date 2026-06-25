package com.example.bookkeeper.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.example.bookkeeper.R
import com.example.bookkeeper.databinding.FragmentProfileBinding
import com.example.bookkeeper.ui.activity.MainActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        requireActivity().addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, inflater: MenuInflater) {
                inflater.inflate(R.menu.menu_profile, menu)
            }

            override fun onMenuItemSelected(item: MenuItem): Boolean {
                if (item.itemId == R.id.action_more) {
                    val toolbar = requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(
                        androidx.appcompat.R.id.action_bar
                    )
                    val anchorView = toolbar?.findViewById<View>(R.id.action_more) ?: binding.root
                    val popup = PopupMenu(requireContext(), anchorView)
                    popup.menuInflater.inflate(R.menu.menu_profile_more, popup.menu)
                    popup.setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.profile_more_logout -> {
                                AlertDialog.Builder(requireContext())
                                    .setMessage(R.string.profile_more_logout_confirm)
                                    .setPositiveButton(R.string.yes) { _, _ ->
                                        val activity : MainActivity = requireActivity() as MainActivity
                                        activity.logout()
                                    }
                                    .setNegativeButton(R.string.no, null)
                                    .show()
                                true
                            }
                            else -> false
                        }
                    }
                    popup.show()
                    return true
                }
                return false
            }
        }, viewLifecycleOwner)

        binding.contactManage.profileMenuContactChevron.setOnClickListener { v: View ->
            findNavController(v).navigate(R.id.navigation_contact_manage)
        }

        binding.ruleManage.profileMenuRuleChevron.setOnClickListener { v: View ->
            findNavController(v).navigate(R.id.navigation_rule_manage)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
