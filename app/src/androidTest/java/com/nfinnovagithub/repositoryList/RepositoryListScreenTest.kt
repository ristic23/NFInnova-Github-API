package com.nfinnovagithub.repositoryList

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.performClick
import com.nfinnovagithub.features.repositoryList.domain.UserRepository
import com.nfinnovagithub.features.repositoryList.presentation.RepositoryItem
import junit.framework.TestCase.assertTrue
import com.nfinnovagithub.ui.theme.NFInnovaGithubTheme
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.nfinnovagithub.features.repositoryList.presentation.RepositoryListScreen
import com.nfinnovagithub.features.repositoryList.presentation.RepositoryListState
import org.junit.Rule
import org.junit.Test

class RepositoryListScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun testRepositoryListUi_repositoryLoaded() {
        composeRule.setContent {
            NFInnovaGithubTheme {
                RepositoryListScreen(
                    state = RepositoryListState.Loading,
                    username = "octocat",
                    onRepositoryClick = {}
                )
            }
        }

        composeRule.onNodeWithText("Loading repositories…").assertExists()
        composeRule.onNodeWithTag("Circular Loader").assertExists()
        composeRule.onNodeWithText("octocat repository").assertExists()
        composeRule.onNodeWithText("Repo Name 1").assertDoesNotExist()
    }

    @Test
    fun testRepositoryListUi_repositoryEmpty() {
        composeRule.setContent {
            NFInnovaGithubTheme {
                RepositoryListScreen(
                    state = RepositoryListState.Empty,
                    username = "octocat",
                    onRepositoryClick = {}
                )
            }
        }

        composeRule.onNodeWithText("octocat repository").assertExists()
        composeRule.onNodeWithTag("Empty icon").assertExists()
        composeRule.onNodeWithText("This user has no public repositories.").assertExists()
        composeRule.onNodeWithText("Repo Name 1").assertDoesNotExist()
    }

    @Test
    fun testRepositoryListUi_repositoryError() {
        composeRule.setContent {
            NFInnovaGithubTheme {
                RepositoryListScreen(
                    state = RepositoryListState.Error(error = "Error occurred"),
                    username = "octocat",
                    onRepositoryClick = {}
                )
            }
        }

        composeRule.onNodeWithText("octocat repository").assertExists()
        composeRule.onNodeWithTag("Error icon").assertExists()
        composeRule.onNodeWithText("Error occurred").assertExists()
        composeRule.onNodeWithText("Repo Name 1").assertDoesNotExist()
    }

    @Test
    fun testRepositoryListUi_repositoryErrorDefaultMessage() {
        composeRule.setContent {
            NFInnovaGithubTheme {
                RepositoryListScreen(
                    state = RepositoryListState.Error(error = null),
                    username = "octocat",
                    onRepositoryClick = {}
                )
            }
        }

        composeRule.onNodeWithText("octocat repository").assertExists()
        composeRule.onNodeWithTag("Error icon").assertExists()
        composeRule.onNodeWithText("Something went wrong, check internet connection, and try again").assertExists()
        composeRule.onNodeWithText("Repo Name 1").assertDoesNotExist()
    }

    @Test
    fun testRepositoryListUi_repositorySuccess() {
        composeRule.setContent {
            NFInnovaGithubTheme {
                RepositoryListScreen(
                    state = RepositoryListState.Success(
                        repos = listOf(
                            UserRepository(id = 1, repoName = "Repo Name 1", openIssuesNumber = 1),
                            UserRepository(id = 2, repoName = "Repo Name 33", openIssuesNumber = 2),
                            UserRepository(id = 3, repoName = "Repo Name 44", openIssuesNumber = 5),
                        )
                    ),
                    username = "octocat",
                    onRepositoryClick = {}
                )
            }
        }

        composeRule.onNodeWithText("octocat repository").assertExists()
        composeRule.onNodeWithText("Repo Name 1").assertExists()
        composeRule.onNodeWithText("Repo Name 44").assertExists()
        composeRule.onNodeWithText("1 opened issue").assertExists()
        composeRule.onNodeWithText("5 opened issues").assertExists()
        composeRule.onNodeWithTag("Circular Loader").assertDoesNotExist()
    }

    @Test
    fun testRepositoryListUi_itemClicked() {
        val clicked = mutableStateOf(false)

        composeRule.setContent {
            RepositoryItem(
                repo = UserRepository(1, "Repo Name 1", 1),
                onRepositoryClick = { clicked.value = true }
            )
        }

        composeRule.onNodeWithTag("Repository Item").performClick()
        composeRule.onNodeWithText("Repo Name 1").assertExists()
        composeRule.onNodeWithText("1 opened issue").assertExists()
        assertTrue(clicked.value)
    }

}