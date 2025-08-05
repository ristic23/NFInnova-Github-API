package com.nfinnovagithub.features.repositoryList.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nfinnovagithub.R
import com.nfinnovagithub.core.presentation.EmptyState
import com.nfinnovagithub.core.presentation.ErrorState
import com.nfinnovagithub.core.presentation.LoadingState
import com.nfinnovagithub.features.repositoryList.domain.UserRepository
import com.nfinnovagithub.ui.theme.NFInnovaGithubTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun RepositoryListWrapper(
    viewModel: RepositoryListViewModel = koinViewModel(),
    username: String = "octocat",
    onRepositoryClick: (String, String) -> Unit,
) {
    val state by viewModel.repoListScreenState.collectAsStateWithLifecycle(RepositoryListState.Loading)

    LaunchedEffect(Unit) {
        // Improvement - here we can add state for username and input field
        viewModel.fetchRepos(username = username)
    }

    RepositoryListScreen(
        state = state,
        username = username,
        onRepositoryClick = {
            onRepositoryClick(username, it)
        },
    )
}

@Composable
private fun RepositoryListScreen(
    state: RepositoryListState,
    username: String,
    onRepositoryClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(R.string.repo, username),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
        when (state) {
            is RepositoryListState.Empty -> EmptyState(
                modifier = Modifier
                    .fillMaxSize(),
                message = stringResource(R.string.emptyRepositoriesResult)
            )

            is RepositoryListState.Error -> ErrorState(
                modifier = Modifier
                    .fillMaxSize(),
                title = stringResource(R.string.error),
                message = state.error ?: stringResource(R.string.defaultErrorMsg)
            )

            is RepositoryListState.Loading -> LoadingState(
                modifier = Modifier
                    .fillMaxSize(),
                message = stringResource(R.string.loadingRepositories)
            )

            is RepositoryListState.Success -> RepositoryList(
                repos = state.repos,
                onRepositoryClick = onRepositoryClick
            )
        }
    }
}

@Composable
private fun RepositoryList(
    modifier: Modifier = Modifier,
    repos: List<UserRepository>,
    onRepositoryClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = 8.dp)
    ) {
        items(
            count = repos.size,
            key = { repos[it].id }
        ) {
            RepositoryItem(
                repo = repos[it],
                onRepositoryClick = onRepositoryClick,
            )
        }
    }
}

@Composable
private fun RepositoryItem(
    modifier: Modifier = Modifier,
    repo: UserRepository,
    onRepositoryClick: (String) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onRepositoryClick(repo.repoName) }
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
        ),
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = repo.repoName,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(
                    id = if (repo.openIssuesNumber == 1)
                        R.string.openedIssue
                    else
                        R.string.openedIssues,
                    repo.openIssuesNumber
                ),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Preview
@Composable
private fun RepositoryListPreview() {
    val state = RepositoryListState.Success(
        listOf(
            UserRepository(id = 1, repoName = "Repo Name", openIssuesNumber = 1),
            UserRepository(id = 2, repoName = "Repo Name", openIssuesNumber = 2),
            UserRepository(id = 3, repoName = "Repo Name", openIssuesNumber = 44),
        )
    )
    NFInnovaGithubTheme {
        RepositoryListScreen(
            state = state,
            username = "octocat",
            onRepositoryClick = {}
        )
    }
}