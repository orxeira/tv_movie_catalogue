package com.orxeira.data.source

interface LocationDataSource {
    suspend fun findLastRegion(): String?
}