
val pow:(Int) -> Int = {a: Int ->
    var res=1
    var i=1
    while (i<=a) {
        res *= 2
        i++
    }
    res
}

//разреженная таблица для минимума (аналогично работает для максимума и GCD)
class SparseTree(numbers: IntArray) {
    var log=32 -numbers.size.countLeadingZeroBits()
    var array=Array(numbers.size) { IntArray(log) { Int.MAX_VALUE } }
    init {
        for (i in numbers.indices)
            array[i][0]=numbers[i]
        for (k in 1..<log) {
            for (i in numbers.indices) {
                array[i][k]=min(array[i][k-1], array[min(i+pow(k-1), numbers.size-1)][k-1])
            }
        }
    }

    fun segment_min(left: Int, right: Int): Int {
        if (left==right)
            return array[left][0]
        val k=31-(right-left).countLeadingZeroBits()
        return min(array[left][k], array[right-pow(k)][k])
    }

}




fun main() {
    val line= readln().split(" ").map { it.toInt() }.toIntArray()
    var w=SparseTree(line)
    for (i in 0..<line.size) {
        for (u in 0..w.log-1) {
            print("${w.array[i][u]} ")
        }
        println()
    }
    println(w.segment_min(0, line.size-1))

}