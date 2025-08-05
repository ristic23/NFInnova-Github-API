package com.nfinnovagithub.features.repositoryList.data

import com.nfinnovagithub.features.repositoryList.data.dto.UserRepositoryDto
import retrofit2.http.GET
import retrofit2.http.Path

interface RepositoryListApi {

    @GET("users/{username}/repos")
    suspend fun getUserRepos(
        @Path("username") username: String
    ): List<UserRepositoryDto>

}