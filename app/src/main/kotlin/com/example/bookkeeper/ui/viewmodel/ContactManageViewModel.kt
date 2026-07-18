package com.example.bookkeeper.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookkeeper.data.model.AmsAccount
import com.example.bookkeeper.data.repository.ContactRepository
import com.example.bookkeeper.data.vo.RmsRuleVo
import kotlinx.coroutines.flow.Flow

class ContactManageViewModel(private val contactRepository: ContactRepository) : ViewModel() {

    val accounts: Flow<List<AmsAccount>> = contactRepository.listAllAccounts()

    fun listAccRules(accId: Long): Flow<List<RmsRuleVo>> {
        return contactRepository.listAccRules(accId)
    }

    suspend fun save(record: AmsAccount) {
        contactRepository.saveAccount(record)
    }

    suspend fun update(record: AmsAccount) {
        contactRepository.updateAccount(record)
    }

    suspend fun remove(record: AmsAccount) {
        contactRepository.removeAccount(record)
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]!!
                ContactManageViewModel(ContactRepository(application))
            }
        }
    }
}