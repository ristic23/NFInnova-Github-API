package com.nfinnovagithub.core.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nfinnovagithub.R
import com.nfinnovagithub.ui.theme.NFInnovaGithubTheme

@Composable
fun EmptyState(
    modifier: Modifier = Modifier,
    message: String,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier
                .testTag("Empty icon")
                .size(60.dp),
            painter = painterResource(R.drawable.ic_empty),
            tint = MaterialTheme.colorScheme.error,
            contentDescription = null,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = message,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Preview
@Composable
private fun EmptyStatePreview() {
    NFInnovaGithubTheme {
        EmptyState(
            modifier = Modifier
                .fillMaxSize(),
            message = "This user has no public repositories."
        )
    }
}