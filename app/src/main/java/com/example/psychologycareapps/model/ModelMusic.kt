package com.example.psychologycareapps.model

class ModelMusic(mImageResourceID: Int, private val mAudioResourceID: Int, private val titleMusic: String) {

    private var mImageResourceID = NO_IMAGE_PROVIDED

    init {
        this.mImageResourceID = mImageResourceID
    }

    fun getTitleMusic(): String {
        return titleMusic
    }

    fun getmAudioResourceID(): Int {
        return mAudioResourceID
    }

    fun getmImageResourceID(): Int {
        return mImageResourceID
    }

    fun hasImage(): Boolean {
        return mImageResourceID != NO_IMAGE_PROVIDED
    }

    companion object {
        private const val NO_IMAGE_PROVIDED = -1
    }

}