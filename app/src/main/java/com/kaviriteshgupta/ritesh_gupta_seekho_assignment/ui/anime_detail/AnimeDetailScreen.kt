package com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.anime_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.data.network.NetworkResult
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.models.AnimeData
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.models.Character
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.common.CentralizedCircleImage
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.common.CentralizedLandscapeImage
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.common.ErrorScreen
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.common.LoadingScreen
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.common.YoutubePlayer
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.theme.Cyan
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.theme.GreyBg
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.theme.GreyTextColor
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.theme.LabelColor
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.ui.theme.White

@Composable
fun AnimeDetailScreen(
    viewModel: AnimeDetailViewModel = hiltViewModel(),
    animeId: String,
    paddingValues: PaddingValues,
    onNavigateUp: () -> Unit
) {
    val animeDetail by viewModel.animeDetail.collectAsState(NetworkResult.Loading)

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchAnimeDetail(animeId)
    }

    when (animeDetail) {
        is NetworkResult.Loading -> {
            LoadingScreen()
        }

        is NetworkResult.Success -> {
            val animeDetail = (animeDetail as NetworkResult.Success).data
            SuccessScreen(
                anime = animeDetail,
                onNavigateUp = onNavigateUp,
                paddingValues = paddingValues
            )
        }

        is NetworkResult.Error -> {
            val errorMessage = (animeDetail as NetworkResult.Error).exception.message
            ErrorScreen(errMessage = errorMessage)
        }
    }

}


@Composable
fun CharacterCard(
    character: Character,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(120.dp)
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = GreyBg),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            CentralizedCircleImage(
                character.images.jpg.imageUrl, character.name, modifier = Modifier.align(
                    Alignment.CenterHorizontally
                )
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = character.name.trim() ?: "N/A",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                maxLines = 2,
                minLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
        }
    }
}


@Composable
fun SuccessScreen(anime: AnimeData, paddingValues: PaddingValues, onNavigateUp: () -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        item {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onNavigateUp() },
                tint = White
            )
        }

        item {
            val hasTrailer = !anime.trailer?.youtubeId.isNullOrBlank()
            if (hasTrailer) {
                YoutubePlayer(
                    videoId = anime.trailer.youtubeId,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            } else if (!anime.trailer?.images?.largeImageUrl.isNullOrBlank()) {
                CentralizedLandscapeImage(
                    imageUrl = anime.trailer.images.largeImageUrl, title = anime.title
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 10f)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "No trailer or poster available",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        item {
            Text(
                text = anime.title ?: anime.title ?: "No Title",
                style = MaterialTheme.typography.headlineMedium,
                color = Cyan,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
            )
        }


        item {
            DetailItem(label = "Synopsis", value = anime.synopsis ?: "Not available.")
        }

        item {
            if (!anime.genres.isNullOrEmpty()) {
                DetailTags(label = "Genres", tags = anime.genres.mapNotNull { it.name })
            } else {
                DetailItem(label = "Genres", value = "Not available.")
            }
        }

        item {
            if (!anime.castList.isNullOrEmpty()) {
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text(
                        text = "Main Casts",
                        style = MaterialTheme.typography.titleMedium,
                        color = White,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(items = anime.castList!!, key = { it.name }) { characterRole ->
                            CharacterCard(character = characterRole)
                        }
                    }
                }
            }
        }

        item {
            DetailItem(label = "Episodes", value = anime.episodes?.toString() ?: "N/A")
        }

        item {
            DetailItem(label = "Content Rating", value = anime.rating ?: "N/A")
        }

        item {
            DetailItem(label = "Score", value = "${anime.score} by ${anime.scoredBy} Users")
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}



@Composable
fun DetailItem(label: String, value: String) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = White
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = GreyTextColor
        )
    }
}

@Composable
fun DetailTags(label: String, tags: List<String>) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = White
        )
        Spacer(modifier = Modifier.height(4.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(tags) { tag ->
                SuggestionChip(
                    onClick = {

                    },
                    label = {
                        Text(
                            tag,
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = LabelColor.copy(alpha = .2f),
                        labelColor = LabelColor
                    )
                )
            }
        }
    }
}