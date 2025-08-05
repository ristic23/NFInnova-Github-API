package com.nfinnovagithub.features.repositoryDetails.domain

data class RepositoryDetails(
    val userName: String,
    val repoName: String,
    val userPhoto: String,
    val forks: Int,
    val watchers: Int,
    val tags: List<RepositoryTag>
)

data class RepositoryTag(
    val name: String,
    val sha: String,
)
