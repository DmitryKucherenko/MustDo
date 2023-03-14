package com.life.software.mustdo.domain.model

data class Task(
    val id: Int = 0,
    val taskInfo: String = "",
    val date: Long = 0,
    val done: Boolean = false
)