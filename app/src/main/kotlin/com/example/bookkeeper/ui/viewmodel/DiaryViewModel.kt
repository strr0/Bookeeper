package com.example.bookkeeper.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookkeeper.data.model.AmsAccount
import com.example.bookkeeper.data.repository.DiaryRepository
import com.example.bookkeeper.data.vo.BmsBillVo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class DiaryViewModel(diaryRepository: DiaryRepository) : ViewModel() {

    val accounts: Flow<List<AmsAccount>> = diaryRepository.listAllAccounts()

    val selectedDate = MutableStateFlow(System.currentTimeMillis())

    @OptIn(ExperimentalCoroutinesApi::class)
    val bills: Flow<List<BmsBillVo>> = selectedDate.flatMapLatest {
        diaryRepository.listBills(it)
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]!!
                DiaryViewModel(DiaryRepository(application))
            }
        }
    }
}