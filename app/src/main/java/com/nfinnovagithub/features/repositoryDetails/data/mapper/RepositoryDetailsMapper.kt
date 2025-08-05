package com.nfinnovagithub.features.repositoryDetails.data.mapper

import com.nfinnovagithub.features.repositoryDetails.data.dto.RepositoryTagDto
import com.nfinnovagithub.features.repositoryDetails.data.dto.RepositoryDetailsDto
import com.nfinnovagithub.features.repositoryDetails.domain.RepositoryTag
import com.nfinnovagithub.features.repositoryDetails.domain.RepositoryDetails

fun RepositoryDetailsDto.toRepositoryDetails(): RepositoryDetails = RepositoryDetails(
    userName = ownerDto.name,
    repoName = repoName,
    userPhoto = ownerDto.userPhoto,
    forks = forks,
    watchers = watchers,
    tags = emptyList()
)

fun RepositoryTagDto.toTag(): RepositoryTag = RepositoryTag(
    name = name,
    sha = commit.sha
)