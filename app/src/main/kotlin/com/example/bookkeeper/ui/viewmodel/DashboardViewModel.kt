package com.example.bookkeeper.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookkeeper.data.repository.DashboardRepository
import com.example.bookkeeper.data.vo.DmsDigitVo
import com.example.bookkeeper.data.vo.DmsZodiacVo
import com.example.bookkeeper.data.vo.LmsLotteryDetailVo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest

class DashboardViewModel(private val dashboardRepository: DashboardRepository) : ViewModel() {

    var defaultDigits = emptyList<DmsDigitVo>()
    var defaultZodiacs = emptyList<DmsZodiacVo>()

    val selectedDate = MutableStateFlow(System.currentTimeMillis())
    val selectedArea = MutableStateFlow("hk")

    @OptIn(ExperimentalCoroutinesApi::class)
    val lotteryDetails: Flow<List<LmsLotteryDetailVo>> = combine(selectedDate, selectedArea) { date, area -> Pair(date, area) }
        .flatMapLatest { (date, area) -> dashboardRepository.listLotteryDetails(date, area) }

    @OptIn(ExperimentalCoroutinesApi::class)
    val digits: Flow<List<DmsDigitVo>> = combine(selectedDate, selectedArea) { date, area -> Pair(date, area) }
        .flatMapLatest { (date, area) -> dashboardRepository.listDigits(date, area) }

    @OptIn(ExperimentalCoroutinesApi::class)
    val zodiacs: Flow<List<DmsZodiacVo>> = combine(selectedDate, selectedArea) { date, area -> Pair(date, area) }
        .flatMapLatest { (date, area) -> dashboardRepository.listZodiacs(date, area) }

    suspend fun loadDefaultDigits() {
        if (defaultDigits.isEmpty()) {
            defaultDigits = dashboardRepository.listAllDigits()
        }
    }

    suspend fun loadDefaultZodiacs() {
        if (defaultZodiacs.isEmpty()) {
            defaultZodiacs = dashboardRepository.listAllZodiacs()
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]!!
                DashboardViewModel(DashboardRepository(application))
            }
        }
    }
}