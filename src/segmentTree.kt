
val two_pow:(Int) -> Int = {a: Int ->
    var res=1
    while (res<a)
        res*=2
    res
}

val min: (Int, Int)->Int = {a:Int, b:Int -> if (a<b) a else b}
val max: (Int, Int)->Int = {a:Int, b:Int -> if (a>b) a else b}
val sum: (Int, Int)->Int = {a:Int, b:Int -> a+b}


class Segment_node(_sum: Int, _max:Int, _min:Int) {
    var sum=_sum
    var max=_max
    var min = _min
}

class segmentTree(_array: IntArray) {
    private var size=two_pow.invoke(_array.size)*2-1
    var array= Array(size) { Segment_node(0, 0, 0) }
    init {
        for (i in _array.indices) {
            array[size/2+i].min=_array[i]
            array[size/2+i].max=_array[i]
            array[size/2+i].sum=_array[i]
        }
        var i=size-1
        while (i>0) {
            array[(i-1)/2].min= min(array[i].min, array[i-1].min)
            array[(i-1)/2].max= max(array[i].min, array[i-1].min)
            array[(i-1)/2].sum= sum.invoke(array[i].sum, array[i-1].sum)
            i-=2
        }
    }
    private fun set_sum(i: Int) {
        var t=i
        while (t>0) {
            if (t%2==0)
                array[(t-1)/2].sum=sum.invoke(array[t].sum, array[t-1].sum)
            else
                array[(t-1)/2].sum=sum.invoke(array[t].sum, array[t+1].sum)
            t=(t-1)/2
        }
    }

    private fun set_maxmin(i: Int) {
        var t=i
        while (t>0) {
            if (t%2==0) {
                array[(t - 1) / 2].max = max(array[t].max, array[t - 1].max)
                array[(t - 1) / 2].min = max(array[t].min, array[t - 1].min)
            } else {
                array[(t - 1) / 2].max = max(array[t].max, array[t + 1].max)
                array[(t - 1) / 2].min = max(array[t].min, array[t + 1].min)
            }
            t=(t-1)/2
        }
    }

    fun set(i: Int, value: Int) {
        this.array[i+size/2].sum=value
        set_sum(i+size/2)
        set_maxmin(i+size/2)
    }

    fun sum(left: Int, right: Int): Int {
        var l=left+size/2
        var r=right+size/2
        var count=0
        while (l<=r) {
            if (l%2==0)
                count+=array[l].sum
            l/=2
            if (r%2!=0)
                count+=array[r].sum
            r=r/2-1
        }
        return count
    }
}

fun main() {
    var s="4 7 8 5 6 2 1 4 7 8".split(" ").map { it.toInt() }.toIntArray()
    var w=segmentTree(s)
    w.array.map{ print("${it.sum} ") }
    w.set(5, 11)
    w.set(7, -1)
    println()
    w.array.map{ print("${it.sum} ") }
    println()
    println(w.sum(5, 7))
}