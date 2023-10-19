package com.manujainz.kotlyze.reporting.model

data class Issue(
    val fileName: String,
    val lineNo: Int,
    val msg: String
)

