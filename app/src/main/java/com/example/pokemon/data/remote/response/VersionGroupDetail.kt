package com.example.pokemon.data.remote.response

data class VersionGroupDetail(
    val level_learned_at: Int,
    val move_learn_method: MoveLearnMethod,
    val order: Any,
    val version_group: VersionGroup
)