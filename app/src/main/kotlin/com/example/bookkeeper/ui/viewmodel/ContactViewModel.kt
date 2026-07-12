package com.example.bookkeeper.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookkeeper.data.model.AmsAccount
import com.example.bookkeeper.data.repository.ContactRepository
import kotlinx.coroutines.flow.Flow

class ContactViewModel(private val contactRepository: ContactRepository) : ViewModel() {

    val accounts: Flow<List<AmsAccount>> = contactRepository.listAllAccounts()

    fun listAccountDetails(accId: Int) = contactRepository.listAccountDetails(accId)

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]!!
                ContactViewModel(ContactRepository(application))
            }
        }
    }
}