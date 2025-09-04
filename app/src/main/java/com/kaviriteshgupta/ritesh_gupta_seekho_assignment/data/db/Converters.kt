package com.kaviriteshgupta.ritesh_gupta_seekho_assignment.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.models.AnimeData
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.models.Character
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.models.Genre
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.models.PosterImage
import com.kaviriteshgupta.ritesh_gupta_seekho_assignment.domain.models.Trailer

class Converters {

    private val gson = Gson()


    @TypeConverter
    fun fromAnimeDataList(animeDataList: List<AnimeData>?): String? {
        if (animeDataList == null) {
            return null
        }
        return gson.toJson(animeDataList)
    }

    @TypeConverter
    fun toAnimeDataList(animeDataListString: String?): List<AnimeData>? {
        if (animeDataListString == null) {
            return null
        }
        val listType = object : TypeToken<List<AnimeData>>() {}.type
        return gson.fromJson(animeDataListString, listType)
    }

    @TypeConverter
    fun fromPosterImage(posterImage: PosterImage?): String? {
        if (posterImage == null) {
            return null
        }
        return gson.toJson(posterImage)
    }

    @TypeConverter
    fun toPosterImage(posterImageString: String?): PosterImage? {
        if (posterImageString == null) {
            return null
        }
        val listType = object : TypeToken<PosterImage>() {}.type
        return gson.fromJson(posterImageString, listType)
    }

    @TypeConverter
    fun fromTrailer(trailer: Trailer?): String? {
        if (trailer == null) {
            return null
        }
        return gson.toJson(trailer)
    }

    @TypeConverter
    fun toTrailer(trailerString: String?): Trailer? {
        if (trailerString == null) {
            return null
        }
        val listType = object : TypeToken<Trailer>() {}.type
        return gson.fromJson(trailerString, listType)
    }


    @TypeConverter
    fun fromGenreList(genreList: List<Genre>?): String? {
        if (genreList == null) {
            return null
        }
        return gson.toJson(genreList)
    }

    @TypeConverter
    fun toGenreList(genreListString: String?): List<Genre>? {
        if (genreListString == null) {
            return null
        }
        val listType = object : TypeToken<List<Genre>>() {}.type
        return gson.fromJson(genreListString, listType)
    }

    @TypeConverter
    fun fromCharacterList(characterList: List<Character>?): String? {
        if (characterList == null) {
            return null
        }
        return gson.toJson(characterList)
    }

    @TypeConverter
    fun toCharacterList(characterListString: String?): List<Character>? {
        if (characterListString == null) {
            return null
        }
        val listType = object : TypeToken<List<Character>>() {}.type
        return gson.fromJson(characterListString, listType)
    }

}
