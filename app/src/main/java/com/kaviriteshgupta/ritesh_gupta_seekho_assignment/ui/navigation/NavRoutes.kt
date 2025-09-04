package com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.navigation

object NavRoutes {

    const val ANIME_LIST_SCREEN = "animeList"
    const val ANIME_DETAIL_SCREEN = "animeDetail/{animeId}"

    fun animeDetailRoute(animeId: String) = "animeDetail/$animeId"

}