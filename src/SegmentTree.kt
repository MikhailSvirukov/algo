
val two_pow:(Int) -> Int = {a: Int ->
    var res=1
    while (res<a)
        res*=2
    res
}

val min: (Int, Int) -> Int = {a:Int, b: Int -> if (a<b) a else b}

class segmentTree(_array: IntArray) {
    var size=two_pow.invoke(_array.size)*2-1
    var array= IntArray(size)

    init {
        size=two_pow.invoke(_array.size)*2-1
        _array.copyInto(array, array.size/2)
        balance()
    }

    fun set(i: Int, value: Int) {
        var t=i+(size+1)/2
        array[t]=value
        balance()
    }

    /*fun sum(left: Int, right: Int): Int {
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
    }*/

    fun min(left: Int, right: Int): Int {
        var l=left+(size-1)/2
        var r=right+(size-1)/2
        var minimum=array[l]
        while (l<=r) {
            if (l%2==0)
                minimum = min.invoke(array[l], minimum)
            l/=2
            if (r%2!=0)
                minimum = min.invoke(array[r], minimum)
            r=r/2-1
        }
        return minimum
    }


    fun balance() {
        var i=size-1
        while (i>0) {
            array[(i-1)/2]=min.invoke(array[i],array[i-1])
            i-=2
        }
    }

    fun inc(left: Int, right: Int, value: Int) {
        var l=left+(size-1)/2
        var r=right+(size-1)/2
        for (i in l..r)
            array[i]+=value
        balance()
    }
}
