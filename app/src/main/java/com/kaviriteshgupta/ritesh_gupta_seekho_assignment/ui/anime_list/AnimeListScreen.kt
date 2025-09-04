package com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.anime_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.models.AnimeData
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.common.CentralizedPortraitImage
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.common.EmptyScreen
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.common.ErrorScreen
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.common.LoadingScreen
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.theme.Black
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.theme.Cyan
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.theme.GreyBg
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.theme.GreyTextColor
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.theme.White


@Composable
fun AnimeListScreen(
    viewModel: AnimeListViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    onNavigateToDetail: (animeId: String) -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is AnimeListUiState.Loading -> {
            LoadingScreen()
        }

        is AnimeListUiState.Success -> {
            val animeList = (uiState as AnimeListUiState.Success).anime
            if (animeList.isEmpty()) {
                EmptyScreen("No anime found")
            } else {
                SuccessScreen(animeList = animeList, paddingValues = paddingValues) { animeId ->
                    onNavigateToDetail(animeId)
                }
            }
        }

        is AnimeListUiState.Error -> {
            val errorMessage = (uiState as AnimeListUiState.Error).message
            ErrorScreen(errMessage = errorMessage)
        }
    }

}

@Composable
fun SuccessScreen(
    animeList: List<AnimeData>,
    paddingValues: PaddingValues,
    onNavigateToDetail: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Black)
    ) {

        Text(
            text = "Anime Universe",
            style = MaterialTheme.typography.headlineLarge,
            color = Cyan,
            modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 18.dp)
        )

        LazyColumn(modifier = Modifier.padding(8.dp)) {
            items(
                items = animeList,
                key = { anime -> anime.animeId }
            ) { anime ->
                AnimeListItem(
                    anime = anime,
                    modifier = Modifier,
                    onItemClick = { onNavigateToDetail(anime.animeId.toString()) }
                )
            }
        }
    }
}

@Composable
fun AnimeListItem(
    anime: AnimeData,
    modifier: Modifier,
    onItemClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick)
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = GreyBg,
            contentColor = White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            CentralizedPortraitImage(anime.images?.jpg?.imageUrl)

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                if (!anime.title.isNullOrEmpty()) {
                    Text(
                        text = anime.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (anime.episodes != null) {
                    Text(
                        text = "${anime.episodes} Episodes",
                        style = MaterialTheme.typography.bodyMedium,
                        color = GreyTextColor
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                if (!anime.rating.isNullOrEmpty()) {
                    Text(
                        text = "Rated: ${anime.rating}",
                        style = MaterialTheme.typography.bodySmall,
                        color = GreyTextColor
                    )
                }
            }
        }
    }
}