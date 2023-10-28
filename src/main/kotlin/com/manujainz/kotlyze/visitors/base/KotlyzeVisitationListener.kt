package com.manujainz.kotlyze.visitors.base


interface KotlyzeVisitationListener {

    fun onLineVisit(
        content: String,
        lineNo: Int,
        fileName: String
    )
}