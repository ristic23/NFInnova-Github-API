package com.nfinnovagithub.navigation

object Routes {
    const val REPOSITORIES = "repositories"
    const val REPOSITORY = "repository"
    fun detailsWithParameters(owner: String, repo: String) = "$REPOSITORY/$owner/$repo"
}