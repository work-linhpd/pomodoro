package com.linhpd.pomodoro.services

import java.io.File
import java.io.FileOutputStream
import java.net.URL
import javax.sound.sampled.AudioSystem

interface SoundService {
    fun playNotify()
}

class SoundServiceImpl : SoundService {
    override fun playNotify() {
        val soundResource: URL? = this::class.java.getResource("/sounds/happy-bells-notification.wav")
        if (soundResource != null) {
            val tempFile = File("temp_sound.wav")
            tempFile.deleteOnExit()

            val inputStream = soundResource.openStream()
            val outputStream = FileOutputStream(tempFile)
            inputStream.copyTo(outputStream)
            inputStream.close()
            outputStream.close()


            val soundFile = File(tempFile.path)
            val audioIn = AudioSystem.getAudioInputStream(soundFile)
            val clip = AudioSystem.getClip()
            clip.open(audioIn)
            clip.start()
        } else {
            println("Sound file not found")
        }
    }
}