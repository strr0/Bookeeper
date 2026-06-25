package com.example.bookkeeper.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookkeeper.data.model.RmsRule
import com.example.bookkeeper.data.repository.RuleRepository
import kotlinx.coroutines.flow.Flow

class RuleManageViewModel(private var ruleRepository: RuleRepository) : ViewModel() {

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]!!
                RuleManageViewModel(RuleRepository(application))
            }
        }
    }

    val rules: Flow<List<RmsRule>> = ruleRepository.listAllRules()
}