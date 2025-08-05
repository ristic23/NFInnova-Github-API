package com.nfinnovagithub.features.repositoryList.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRepositoryDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("open_issues_count") val openIssueCount: Int
)
