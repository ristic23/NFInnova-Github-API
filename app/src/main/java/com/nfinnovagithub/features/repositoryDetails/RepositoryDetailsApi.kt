package com.nfinnovagithub.features.repositoryDetails

import com.nfinnovagithub.features.repositoryDetails.data.dto.RepositoryDetailsDto
import com.nfinnovagithub.features.repositoryDetails.data.dto.RepositoryTagDto
import retrofit2.http.GET
import retrofit2.http.Path

interface RepositoryDetailsApi {

    @GET("repos/{owner}/{repo}")
    suspend fun getRepository(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): RepositoryDetailsDto

    @GET("repos/{owner}/{repo}/tags")
    suspend fun getRepositoryTags(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): List<RepositoryTagDto>
}