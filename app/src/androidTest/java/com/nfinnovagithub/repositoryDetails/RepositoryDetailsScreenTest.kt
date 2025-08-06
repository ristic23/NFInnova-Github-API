package com.nfinnovagithub.repositoryDetails

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.nfinnovagithub.features.repositoryDetails.domain.RepositoryDetails
import com.nfinnovagithub.features.repositoryDetails.domain.RepositoryTag
import com.nfinnovagithub.features.repositoryDetails.presentation.RepositoryDetailsScreen
import com.nfinnovagithub.features.repositoryDetails.presentation.RepositoryDetailsState
import org.junit.Rule
import org.junit.Test

class RepositoryDetailsScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun repositoryDetailsScreen_onRepositoryLoading() {
        composeRule.setContent {
            RepositoryDetailsScreen(
                state = RepositoryDetailsState.Loading
            )
        }

        composeRule.onNodeWithText("Loading repository…").assertExists()
        composeRule.onNodeWithTag("Circular Loader").assertExists()
        composeRule.onNodeWithTag("Error icon").assertDoesNotExist()
    }

    @Test
    fun repositoryDetailsScreen_onRepositoryError() {
        composeRule.setContent {
            RepositoryDetailsScreen(
                state = RepositoryDetailsState.Error(error = "Repo error")
            )
        }

        composeRule.onNodeWithTag("Error icon").assertExists()
        composeRule.onNodeWithText("Repo error").assertExists()
        composeRule.onNodeWithTag("Circular Loader").assertDoesNotExist()
    }

    @Test
    fun repositoryDetailsScreen_onRepositoryErrorDefaultMessage() {
        composeRule.setContent {
            RepositoryDetailsScreen(
                state = RepositoryDetailsState.Error(error = null)
            )
        }

        composeRule.onNodeWithTag("Error icon").assertExists()
        composeRule.onNodeWithText("Something went wrong, check internet connection, and try again").assertExists()
        composeRule.onNodeWithTag("Circular Loader").assertDoesNotExist()
    }

    @Test
    fun repositoryDetailsScreen_onRepositorySuccess() {
        composeRule.setContent {
            RepositoryDetailsScreen(
                state = RepositoryDetailsState.Success(
                    repos = RepositoryDetails(
                        userName = "octocat",
                        repoName = "hello-world",
                        userPhoto = "photo.jpg",
                        forks = 1,
                        watchers = 5,
                        tags = listOf(
                            RepositoryTag(name = "v1.0.2", sha = "4egrg4t342"),
                            RepositoryTag(name = "v1.0.1", sha = "4634gs"),
                            RepositoryTag(name = "v1.0.0", sha = "1412421424"),
                        )
                    )
                )
            )
        }

        composeRule.onNodeWithText("octocat").assertExists()
        composeRule.onNodeWithText("hello-world").assertExists()
        composeRule.onNodeWithTag("User photo").assertExists()
        composeRule.onNodeWithText("Forks: 1").assertExists()
        composeRule.onNodeWithText("Watchers: 5").assertExists()
        composeRule.onNodeWithText("v1.0.2").assertExists()
        composeRule.onNodeWithText("1412421424").assertExists()
        composeRule.onNodeWithTag("Circular Loader").assertDoesNotExist()
    }

    @Test
    fun repositoryDetailsScreen_onRepositorySuccessTagsEmpty() {
        composeRule.setContent {
            RepositoryDetailsScreen(
                state = RepositoryDetailsState.Success(
                    repos = RepositoryDetails(
                        userName = "octocat",
                        repoName = "hello-world",
                        userPhoto = "photo.jpg",
                        forks = 1,
                        watchers = 5,
                        tags = emptyList()
                    )
                )
            )
        }

        composeRule.onNodeWithText("Tags are empty").assertExists()
    }


}