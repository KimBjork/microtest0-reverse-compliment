import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.OutputStreamWriter

/*
 * The Computer Language Benchmarks Game
 * http://benchmarksgame.alioth.debian.org/
 * implemented in Kotlin by Patrik Schwermer
 */

class ReverseComplement {
    private var ins = null
    private var sequence: MutableList<Char> = mutableListOf()
    private val transMap: HashMap<Char, Char> = hashMapOf()

    init{
        val nucleotides = "AaCcGgTtUuMmRrWwSsYyKkVvHhDdBbNn"
        val complements = "TTGGCCAAAAKKYYWWSSRRMMBBDDHHVVNN"

        for(i in 0 until nucleotides.length){
            transMap[nucleotides[i]] = complements[i]
        }
    }

    private fun solve(bufferedReader: BufferedReader){
        var prevSeqName = ""
        bufferedReader.useLines { lines ->
            lines.forEach { line ->
                if(line[0] == '>'){
                    if(prevSeqName != ""){
                        write(prevSeqName)
                    }
                    prevSeqName = line
                } else {
                    line.forEach { char ->
                        val complement: Char? = transMap[char]
                        if(complement != null) sequence.add(complement)
                    }
                }
            }
        }

        write(prevSeqName)
        bufferedReader.close()
    }

    private fun write(sequenceName: String){
        val fileName = "/tmp/res-${System.currentTimeMillis()}"
        File(fileName).createNewFile()
        val file = File(fileName)
        val writer = BufferedWriter(OutputStreamWriter(file.outputStream()))

        writer.append("$sequenceName\n")

        var counter = 0
        for(i in sequence.size-1 downTo 0){
            counter++
            writer.append(sequence[i])
            if(counter%60==0 && counter != 1){
                writer.append('\n')
                //Flush every 100 lines to avoid overfull buffer causing untimely print
                if(counter%100 == 0) writer.flush()
            }
        }
        writer.append('\n')

        sequence = mutableListOf()
        writer.close()
        file.delete()
    }

    fun runBenchmark(fileName: String){
        //ins = this.javaClass.classLoader.getResourceAsStream("$fileName")
        val file = File(fileName)
        solve(file.bufferedReader())
    }
}