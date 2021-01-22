package com.hongbog.fileio

import java.io.*

class FileUtil {
    fun readTextFile(fullPath: String): String {
        val file = File(fullPath)
        if (!file.exists()) return ""

        val reader = FileReader(file)
        val buffer = BufferedReader(reader)

        var temp = ""
        val result = StringBuffer()

        while(true) {
            temp = buffer.readLine()
            if (temp == null) break;
            else result.append(buffer)
        }
        buffer.close()
        return result.toString()
    }

    fun writeTextFile(directory: String, filename: String, content: String) {
        val dir = File(directory)
        if (!dir.exists()) dir.mkdirs()

        val writer = FileWriter(directory + "/" + filename)
        val buffer = BufferedWriter(writer)

        buffer.write(content)
        buffer.close()
    }
}