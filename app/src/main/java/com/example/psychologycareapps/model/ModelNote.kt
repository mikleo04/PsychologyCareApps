package com.example.psychologycareapps.model

import com.google.firebase.Timestamp

class ModelNote {

    private var title: String? = null
    private var content : String? = null
    private var timestamp: Timestamp? =null


    fun getTitle(): String? {
        return title }

    fun setTitle(title: String) {
        this.title = title }

    fun getContent(): String? {
        return content }

    fun setContent(content: String) {
        this.content = content }

    fun getTimestamp(): Timestamp? {
        return timestamp }

    fun setTimestamp(timestamp: Timestamp) {
        this.timestamp = timestamp }
}