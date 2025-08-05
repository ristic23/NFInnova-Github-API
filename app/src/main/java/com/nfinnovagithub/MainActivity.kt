package com.nfinnovagithub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nfinnovagithub.features.repositoryList.presentation.RepositoryListWrapper
import com.nfinnovagithub.ui.theme.NFInnovaGithubTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NFInnovaGithubTheme {
                RepositoryListWrapper(
                    onRepositoryClick = {}
                )
            }
        }
    }
}
