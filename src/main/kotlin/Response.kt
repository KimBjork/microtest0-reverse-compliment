import java.io.File

data class HandlerOutput(val startTime: Long,
                         val runtime: Long,
                         val coldStart: Boolean)

class Response {

    fun createString(startTime : Long, runtime : Long):String {
        return "{\"startTime\": ${startTime}, \"runtime\": ${runtime}, \"coldStart\": ${isColdStart()}}"
    }

    fun createJson(startTime: Long, runtime : Long) : HandlerOutput{
        return HandlerOutput(startTime, runtime, isColdStart())
    }

    private fun isColdStart(): Boolean{
        val fileName = "/tmp/out.txt"
        val file = File(fileName)

        if(file.exists()){
            return false;
        }
        File(fileName).createNewFile()
        return true;
    }
}