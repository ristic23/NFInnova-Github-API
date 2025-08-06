package com.nfinnovagithub.features.repositoryDetails.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.nfinnovagithub.R
import com.nfinnovagithub.core.presentation.ErrorState
import com.nfinnovagithub.core.presentation.LoadingState
import com.nfinnovagithub.features.repositoryDetails.domain.RepositoryDetails
import com.nfinnovagithub.features.repositoryDetails.domain.RepositoryTag
import com.nfinnovagithub.features.repositoryDetails.presentation.RepositoryDetailsState.Error
import com.nfinnovagithub.features.repositoryDetails.presentation.RepositoryDetailsState.Loading
import com.nfinnovagithub.features.repositoryDetails.presentation.RepositoryDetailsState.Success
import com.nfinnovagithub.ui.theme.NFInnovaGithubTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun RepositoryDetailsWrapper(
    viewModel: RepositoryDetailsViewModel = koinViewModel(),
    owner: String,
    repo: String,
) {
    val state by viewModel.repoDetailsScreenState.collectAsStateWithLifecycle(Loading)

    LaunchedEffect(Unit) {
        viewModel.fetch(owner, repo)
    }

    RepositoryDetailsScreen(
        state = state,
    )
}

@Composable
fun RepositoryDetailsScreen(
    state: RepositoryDetailsState,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        when (state) {
            is Error -> ErrorState(
                modifier = Modifier
                    .fillMaxSize(),
                title = stringResource(R.string.error),
                message = state.error ?: stringResource(R.string.defaultErrorMsg)
            )

            is Loading -> LoadingState(
                modifier = Modifier
                    .fillMaxSize(),
                message = stringResource(R.string.loadingRepository)
            )

            is Success -> {
                RepositoryDetails(
                    modifier = Modifier
                        .fillMaxSize(),
                    details = state.repos
                )
            }
        }
    }
}


@Composable
fun RepositoryDetails(
    modifier: Modifier = Modifier,
    details: RepositoryDetails,
) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.onPrimary,
                        shape = RoundedCornerShape(bottomStart = 48.dp, bottomEnd = 32.dp)
                    )
                ,
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .width(140.dp)
                            .height(140.dp)
                            .clip(CircleShape)
                            .testTag("User photo"),
                        contentDescription = "User image",
                        model = details.userPhoto,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = details.userName,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = details.repoName,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                    ) {
                        Text(
                            modifier = Modifier
                                .weight(1f),
                            text = stringResource(id = R.string.forksLabel, details.forks),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Start
                        )
                        Text(
                            modifier = Modifier
                                .weight(1f),
                            text = stringResource(id = R.string.watchersLabel, details.watchers),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }

        if (details.tags.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 48.dp),
                        text = stringResource(R.string.noTagsMsg),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        } else {
            items(details.tags.size) {
                val item = details.tags[it]
                Column {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth(),
                        text = item.name,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth(),
                        text = item.sha,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@Preview
@Composable
private fun RepositoryDetailsPreview() {
    NFInnovaGithubTheme {
        RepositoryDetails(
            modifier = Modifier
                .fillMaxSize(),
            details = RepositoryDetails(
                userName = "User name",
                repoName = "Repo name",
                forks = 2,
                watchers = 2,
                userPhoto = "https://canto-wp-media.s3.amazonaws.com/app/uploads/2019/08/19194138/image-url-3.jpg",
                tags = listOf(
                    RepositoryTag(name = "tag 1", sha = "a421k4n124nkl12"),
                    RepositoryTag(name = "tag 2", sha = "da32t235252weg"),
                    RepositoryTag(name = "tag 3", sha = "da32t235252weg"),
                    RepositoryTag(name = "tag 4", sha = "da32t235252weg"),
                    RepositoryTag(name = "tag 5", sha = "da32t235252weg"),
                )
            )
        )
    }
}