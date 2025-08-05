package com.nfinnovagithub.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nfinnovagithub.features.repositoryDetails.presentation.RepositoryDetailsWrapper
import com.nfinnovagithub.features.repositoryList.presentation.RepositoryListWrapper

@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.REPOSITORIES
    ) {
        composable(route = Routes.REPOSITORIES) {
            RepositoryListWrapper(
                onRepositoryClick = { owner, repo ->
                    navController.navigate(Routes.detailsWithParameters(owner, repo))
                },
            )
        }
        composable(
            route = "${Routes.REPOSITORY}/{owner}/{repo}",
            arguments = listOf(
                navArgument("owner") { type = NavType.StringType },
                navArgument("repo") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val owner = backStackEntry.arguments?.getString("owner") ?: ""
            val repo = backStackEntry.arguments?.getString("repo") ?: ""

            RepositoryDetailsWrapper(
                owner = owner,
                repo = repo
            )
        }
    }
}