package com.example.mykakeibo

import android.content.Context
import androidx.room.*
import java.time.LocalDate

internal class LocalDateConverter {
    /**
     * LocalDate　→　フォーマット文字列
     * フォーマットはyyyy-MM-dd(デフォルト)
     * */
    @TypeConverter
    fun fromLocalDate(localDate: LocalDate): String {
        return localDate.toString()
    }

    /**
     * フォーマット文字列　→　LocalDate
     * フォーマットはyyyy-MM-dd(デフォルト)
     * */
    @TypeConverter
    fun toLocalDate(stringDate: String): LocalDate {
        return LocalDate.parse(stringDate)
    }
}

@Entity(tableName = "spends") // usersというテーブルのデータベースであることを表す
data class SpendData(
    @PrimaryKey(autoGenerate = true) val uid: Int, // 主キー ユニークID // 必ず主キーをつける必要がある
    val dateMillis:LocalDate, //日付
    val purpose: String, // 名目
    val spender: String, // 払った人
    val money: Int, //金額
    val category:String //出費のカテゴリ
)

@Dao
interface SpendDao {
    @Query("SELECT * FROM spends")
    fun getAll(): List<SpendData> // 全件取得のSQLをかく。この時にソートや抽出などもできる。

    @Query("SELECT * FROM spends WHERE uid IN (:uniqueIds)")
    fun loadAllByIds(uniqueIds: IntArray): List<SpendData>

    @Query("SELECT * FROM spends WHERE purpose LIKE :find_purpose LIMIT 1")
    fun findByPurpose(find_purpose: String): SpendData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(spend: SpendData)

    @Insert
    fun insertAll(vararg spends: SpendData)

    @Delete
    fun delete(spend: SpendData)

    @Query("DELETE FROM spends")
    fun deleteAll()
}

//DB
@Database(entities = arrayOf(SpendData::class), version = 1)
@TypeConverters(LocalDateConverter::class)
abstract class SpendDatabase : RoomDatabase() {
    abstract fun spendDao(): SpendDao

    companion object{
        private var INSTANCE: SpendDatabase ? = null
        fun getInstance(context: Context): SpendDatabase {
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context.applicationContext,SpendDatabase::class.java,"SpendDatabase.db").build()
            }
            return INSTANCE!!
        }
    }
}