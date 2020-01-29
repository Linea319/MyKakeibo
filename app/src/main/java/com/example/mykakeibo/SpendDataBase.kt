package com.example.mykakeibo

import androidx.room.*

@Entity(tableName = "spends") // usersというテーブルのデータベースであることを表す
data class SpendData(
    @PrimaryKey val uid: Int, // 主キー ユニークID // 必ず主キーをつける必要がある
    val dateMillis:Long, //日付(ミリ秒)
    val purpose: String, // 名目
    val spender: String, // 払った人
    val money: Float, //金額
    val category:String //出費のカテゴリ
)

@Dao
interface SpendDao {
    @Query("SELECT * FROM spends")
    fun getAll(): List<SpendData> // 全件取得のSQLをかく。この時にソートや抽出などもできる。

    @Query("SELECT * FROM spends WHERE uid IN (:uniqueIds)")
    fun loadAllByIds(uniqueIds: IntArray): List<SpendData>// 追加処理　コンフリクトが起こった時は置き換える

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): SpendData

    @Insert
    fun insertAll(vararg spends: SpendData)

    @Delete
    fun delete(spend: SpendData)
}

@Database(entities = arrayOf(SpendData::class), version = 1)
abstract class SpendDatabase : RoomDatabase() {
    abstract fun spendDao(): SpendDao
}