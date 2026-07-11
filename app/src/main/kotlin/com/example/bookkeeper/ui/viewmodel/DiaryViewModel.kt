package com.example.bookkeeper.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookkeeper.data.model.AmsAccount
import com.example.bookkeeper.data.repository.DiaryRepository
import com.example.bookkeeper.data.vo.BmsBillDetailVo
import com.example.bookkeeper.data.vo.BmsBillVo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class DiaryViewModel(private val diaryRepository: DiaryRepository) : ViewModel() {

    val accounts: Flow<List<AmsAccount>> = diaryRepository.listAllAccounts()

    val selectedDate = MutableStateFlow(System.currentTimeMillis())
    val selectedAccount = MutableStateFlow(0)
    val selectedArea = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val bills: Flow<List<BmsBillVo>> = combine(selectedDate, selectedAccount, selectedArea) { date, account, area ->
        Triple(date, account, area)
    }.flatMapLatest { (date, account, area) ->
        diaryRepository.listBills(date, account, area)
    }

    fun listBillDetails(billId: Int): Flow<List<BmsBillDetailVo>> = diaryRepository.listBillDetails(billId)

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]!!
                DiaryViewModel(DiaryRepository(application))
            }
        }
    }
}