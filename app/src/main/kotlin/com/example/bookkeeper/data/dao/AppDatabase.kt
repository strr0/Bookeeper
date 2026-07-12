package com.example.bookkeeper.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bookkeeper.data.converter.BigDecimalConverter
import com.example.bookkeeper.data.converter.DateConverter
import com.example.bookkeeper.data.model.*

@Database(
    entities = [AmsAccount::class, AmsAccountDetail::class, BmsBill::class, BmsBillDetail::class,
        DmsDigit::class, DmsZodiac::class, LmsLottery::class, LmsLotteryDetail::class,
        RmsRule::class, RmsRuleRel::class, UmsUser::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    BigDecimalConverter::class,
    DateConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun amsAccountDao(): AmsAccountDao?
    abstract fun bmsBillDao(): BmsBillDao?
    abstract fun dmsDigitDao(): DmsDigitDao?
    abstract fun lmsLotteryDao(): LmsLotteryDao?
    abstract fun rmsRuleDao(): RmsRuleDao?
    abstract fun umsUserDao(): UmsUserDao?

    companion object {
        private const val DATABASE_FILE_PATH = "database/bookkeeper.db"
        private const val DATABASE_NAME = "bookkeeper.db"

        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, DATABASE_NAME
                    )
                    .createFromAsset(DATABASE_FILE_PATH)
                    .build()
                }
            }
            return INSTANCE!!
        }
    }
}
