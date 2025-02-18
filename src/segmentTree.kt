
val two_pow:(Int) -> Int = {a: Int ->
    var res=1
    while (res<a)
        res*=2
    res
}

class segmentTree() {
    var array:IntArray = IntArray(0)
    var size=0

    constructor(_array: IntArray) : this() {
        this.size=two_pow.invoke(_array.size)*2-1
        this.array =IntArray(this.size)
        _array.copyInto(array, array.size/2)
        var i=this.size-1
        while (i>0) {
            this.array[(i-1)/2]=this.array[i]+this.array[i-1]
            i-=2
        }
    }

    fun set(i: Int, value: Int) {
        var t=i+(this.size+1)/2
        this.array[t]=value
        while (t>0) {
            if (t%2==0)
                this.array[(t-1)/2]=array[t]+array[t-1]
            else
                this.array[(t-1)/2]=array[t]+array[t+1]
            t=(t-1)/2
        }
    }

    fun sum(left: Int, right: Int): Int {
        var l=left+(size-1)/2
        var r=right+(size-1)/2
        var count=0
        println("$l $r")
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