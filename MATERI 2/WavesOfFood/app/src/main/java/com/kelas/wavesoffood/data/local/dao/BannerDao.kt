package com.kelas.wavesoffood.data.local.dao

import androidx.room.*
import com.kelas.wavesoffood.data.model.Banner

@Dao
interface BannerDao {

    @Query("SELECT * FROM banners WHERE isActive = 1 ORDER BY priority DESC, id ASC")
    suspend fun getActiveBanners(): List<Banner>

    @Query("SELECT * FROM banners WHERE id = :id")
    suspend fun getBannerById(id: String): Banner?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBanner(banner: Banner)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBanners(banners: List<Banner>)

    @Delete
    suspend fun deleteBanner(banner: Banner)

    @Query("DELETE FROM banners")
    suspend fun clearBanners()
}
