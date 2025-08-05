package com.nfinnovagithub.features.repositoryList.data.mapper

import com.nfinnovagithub.features.repositoryList.data.dto.UserRepositoryDto
import com.nfinnovagithub.features.repositoryList.domain.UserRepository

fun UserRepositoryDto.toUserRepository(): UserRepository = UserRepository(
    id = id,
    repoName = name,
    openIssuesNumber = openIssueCount
)