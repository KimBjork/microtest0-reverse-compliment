import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.OutputStream

class Main {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val startTime = System.currentTimeMillis()

            val rev = ReverseComplement()
            rev.runBenchmark("/var/task/fasta-1000000")

            val endTime = System.currentTimeMillis()
            val runtime = endTime - startTime
            val res = Response().createString(startTime, runtime)
            println(res)
        }
    }

    fun handler(output: OutputStream): Unit {
        val startTime = System.currentTimeMillis()
        val mapper = jacksonObjectMapper()
        var envVar: String = System.getenv("LAMBDA_TASK_ROOT") ?: "default_value"
        println(envVar)

        val rev = ReverseComplement()
        rev.runBenchmark("$envVar/fasta-1000000")

        val endTime = System.currentTimeMillis()
        val runtime = endTime - startTime
        val res = Response().createJson(startTime, runtime)
        println(res)

        mapper.writeValue(output, res)
    }
}
