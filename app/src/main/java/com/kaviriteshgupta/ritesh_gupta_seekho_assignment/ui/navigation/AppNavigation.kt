package com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.anime_detail.AnimeDetailScreen
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.anime_list.AnimeListScreen

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    paddingValues: PaddingValues
) {
    NavHost(navController = navController, startDestination = NavRoutes.ANIME_LIST_SCREEN) {
        composable(NavRoutes.ANIME_LIST_SCREEN) {
            AnimeListScreen(paddingValues = paddingValues) {
                navController.navigate(NavRoutes.animeDetailRoute(it))
            }
        }

        composable(
            route = NavRoutes.ANIME_DETAIL_SCREEN,
            arguments = listOf(navArgument("animeId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val animeId = backStackEntry.arguments?.getString("animeId")
            if (animeId != null) {
                AnimeDetailScreen(animeId = animeId, paddingValues = paddingValues) {
                    navController.navigateUp()
                }
            }
        }
    }

}