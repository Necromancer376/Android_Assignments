package com.example.assignment3.Utils

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class DownloadWorker(
    val context: Context,
    var workerParameters: WorkerParameters,
) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {
        return try {
            val content: String = inputData.getString("FILE_CONTENT")!!
            val outputUri = saveToFile(context, content)
            val data = workDataOf("KEY_FILEWORKER_RESP" to outputUri.toString())

            Result.success(data)
        } catch (throwable: Throwable) {
            Log.e("Download","error worker")
            Result.failure()
        }
    }

    private fun saveToFile(context: Context, content: String): Uri {
        val fileName: String = inputData.getString("FILE_NAME")!!
        val outputDir = File(context.filesDir, Constants.OUTPUT_PATH)

        if (!outputDir.exists()) {
            outputDir.mkdir()
        }
        val outputFile = File(outputDir, fileName)
        var output: FileOutputStream? = null

        try {
            output = FileOutputStream(outputFile)
            outputFile.appendText(content)
        }
        finally {
            output?.let {
                try {
                    it.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                    Log.e("Download","error")
                }
            }
        }

        return Uri.fromFile(outputFile)
    }
}