package com.nfinnovagithub.features.repositoryDetails.consts

import com.nfinnovagithub.features.repositoryDetails.data.dto.OwnerDto
import com.nfinnovagithub.features.repositoryDetails.data.dto.RepositoryDetailsDto
import com.nfinnovagithub.features.repositoryDetails.data.dto.RepositoryTagCommitDto
import com.nfinnovagithub.features.repositoryDetails.data.dto.RepositoryTagDto
import com.nfinnovagithub.features.repositoryDetails.domain.RepositoryDetails
import com.nfinnovagithub.features.repositoryDetails.domain.RepositoryTag

fun repoDto(
    owner: String = "octocat",
    repoName: String = "Hello-World"
) = RepositoryDetailsDto(
    ownerDto = OwnerDto(owner, "commit"),
    repoName = repoName,
    forks = 1,
    watchers = 2
)

fun tagDto(
    name: String = "v1.0.0",
    sha: String = "efda7e4155cd580da52ec0e58c835bc1626ae78cb5f076f67d7114a7537998ba"
) = RepositoryTagDto(
    name = name,
    commit = RepositoryTagCommitDto(sha = sha)
)

fun repoDetails(
    userName: String = "octocat",
    repoName: String = "Hello-World",
    userPhoto: String = "user.png",
    forks: Int = 1,
    watchers: Int = 2,
    tags: List<RepositoryTag> = listOf(),
) = RepositoryDetails(
    userName = userName,
    repoName = repoName,
    userPhoto = userPhoto,
    forks = forks,
    watchers = watchers,
    tags = tags
)

fun repoTag(
    name: String = "v1.0.0",
    sha: String = "efda7e4155cd580da52ec0e58c835bc1626ae78cb5f076f67d7114a7537998ba"
) = RepositoryTag(
    name = name,
    sha = sha
)

