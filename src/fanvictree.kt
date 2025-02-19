

val p: (Int) -> Int = {a: Int -> a.and(a+1)}

class FinvicTree(_array: IntArray) {
    var array: IntArray=IntArray(_array.size) {0}
    init {
        for (i in 0.._array.size-1) {
            for (u in p(i)..i)
                array[i] += _array[u]
        }
    }

    fun sum(left: Int, right: Int): Int {
        var l=left-1
        var r=right
        var ls=0
        var rs=0
        while (l>=0) {
            ls+=array[l]
            l=p(l)-1
        }
        while (r>=0) {
            rs+=array[r]
            r=p(r)-1
        }
        return rs-ls
    }

    fun set(i: Int, value: Int) {
        var l=i
        while (l<array.size) {
            array[l]+=value
            l=l.or(l+1)
        }
    }

}
