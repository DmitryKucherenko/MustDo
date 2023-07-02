package com.life.software.mustdo.domain.model


data class Task(
    val id: Int = 0,
    val taskInfo: String = "",
    val addDate: Long = 0,
    val alarmDate: Long = 0,
    val alarmActive:Boolean = false,
    val done: Boolean = false
)