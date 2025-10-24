package com.example.pokemon.utils

import androidx.compose.ui.graphics.Color
import com.example.pokemon.ui.theme.BugTypeColor
import com.example.pokemon.ui.theme.DarkTypeColor
import com.example.pokemon.ui.theme.DragonTypeColor
import com.example.pokemon.ui.theme.ElectricTypeColor
import com.example.pokemon.ui.theme.FairyTypeColor
import com.example.pokemon.ui.theme.FightingTypeColor
import com.example.pokemon.ui.theme.FireTypeColor
import com.example.pokemon.ui.theme.FlyingTypeColor
import com.example.pokemon.ui.theme.GhostTypeColor
import com.example.pokemon.ui.theme.GrassTypeColor
import com.example.pokemon.ui.theme.GroundTypeColor
import com.example.pokemon.ui.theme.IceTypeColor
import com.example.pokemon.ui.theme.PoisonTypeColor
import com.example.pokemon.ui.theme.PsychicTypeColor
import com.example.pokemon.ui.theme.RockTypeColor
import com.example.pokemon.ui.theme.SteelTypeColor
import com.example.pokemon.ui.theme.UnknownTypeColor
import com.example.pokemon.ui.theme.WaterTypeColor
import java.util.Locale


fun getTypeColor(type:String) : Color{
    val NormalTypeColor = Color.LightGray
    return when(type.lowercase(Locale.ROOT)){
        "fire" -> FireTypeColor
        "water" -> WaterTypeColor
        "poison" -> PoisonTypeColor
        "grass" -> GrassTypeColor
        "flying" -> FlyingTypeColor
        "bug" -> BugTypeColor
        "normal" -> NormalTypeColor
        "electric" -> ElectricTypeColor
        "fighting" -> FightingTypeColor
        "steel" -> SteelTypeColor
        "ice" -> IceTypeColor
        "ghost" -> GhostTypeColor
        "rock" -> RockTypeColor
        "psychic" -> PsychicTypeColor
        "dark" -> DarkTypeColor
        "dragon" -> DragonTypeColor
        "fairy" -> FairyTypeColor
        "ground" -> GroundTypeColor
        else -> UnknownTypeColor
    }
}