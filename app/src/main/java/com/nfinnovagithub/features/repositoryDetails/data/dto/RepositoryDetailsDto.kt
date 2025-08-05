package com.nfinnovagithub.features.repositoryDetails.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepositoryDetailsDto(
    @SerialName("owner") val ownerDto: OwnerDto,
    @SerialName("name") val repoName: String,
    @SerialName("forks_count") val forks: Int,
    @SerialName("watchers_count") val watchers: Int,
)

@Serializable
data class OwnerDto(
    @SerialName("login") val name: String,
    @SerialName("avatar_url") val userPhoto: String,
)

@Serializable
data class RepositoryTagDto(
    @SerialName("name") val name: String,
    @SerialName("commit") val commit: RepositoryTagCommitDto,
)

@Serializable
data class RepositoryTagCommitDto(
    @SerialName("sha") val sha: String,
)
