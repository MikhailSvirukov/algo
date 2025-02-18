
val two_pow:(Int) -> Int = {a: Int ->
    var res=1
    while (res<a)
        res*=2
    res
}

class segment_node(_sum: Int, _max:Int, _min:Int) {
    var sum=_sum
    var max=_max
    var min = _min
}

class segmentTree() {
    var array= Array<segment_node>(0,  {segment_node(0,0,0)})
    var size=0

    constructor(_array: IntArray) : this() {
        size=two_pow.invoke(_array.size)*2-1
        array =IntArray(size)
        _array.copyInto(array, array.size/2)
        var i=size-1
        while (i>0) {
            array[(i-1)/2]=array[i]+array[i-1]
            i-=2
        }
    }

    fun set(i: Int, value: Int) {
        var t=i+(size+1)/2
        this.array[t]=value
        while (t>0) {
            if (t%2==0)
                array[(t-1)/2]=array[t]+array[t-1]
            else
                array[(t-1)/2]=array[t]+array[t+1]
            t=(t-1)/2
        }
    }

    fun sum(left: Int, right: Int): Int {
        var l=left+(size-1)/2
        var r=right+(size-1)/2
        var count=0
        while (l<=r) {
            if (l%2==0)
                count+=array[l]
            l/=2
            if (r%2!=0)
                count+=array[r]
            r=r/2-1
        }
        return count
    }
}

fun main() {
    var s="4 7 8 5 6 2 1 4 7 8".split(" ").map { it.toInt() }.toIntArray()
    var w=segmentTree(s)
    w.array.map{ print("$it ") }
    w.set(5, 11)
    w.set(7, -1)
    println()
    w.array.map{ print("$it ") }
    println()
    println(w.sum(4, 4))
}