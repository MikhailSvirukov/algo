fun IntArray.swap(a: Int, b:Int) {
    val t=this[a]
    this[a]=this[b]
    this[b]=t
}

class Heap(ints: IntArray) {
    private var array=ints
    init {
        for (i in array.indices)
            elementUp(i)

    }

    private fun elementUp(index: Int): Boolean {
        var t=index
        while (t>0 && array[t]>array[(t-1)/2]) {
            array.swap(t, (t-1)/2)
            t=(t-1)/2
        }
        return t == index
    }

    private fun elementDown(index: Int) {
        if (2*index+1<array.size && array[index]<array[2*index+1]) {
            array.swap(index, 2*index+1)
            elementDown(2 * index + 1)
        }
        if (2*index+2<array.size && array[index]<array[2*index+2]){
            array.swap(index, 2*index+2)
            elementDown(2 * index + 2)
        }
    }

    fun set(index: Int, value: Int) {
        array[index]=value
        if (elementUp(index))
            elementDown(index)
    }

    fun add(value: Int) {
        array=array.plus(value)
        elementUp(array.size-1)
    }

    fun getMax(): Int {
        return array[0]
    }

    fun extractMax(): Int {
        var res=array.first()
        this.set(0, array.last())
        array=array.dropLast(1).toIntArray()
        return res
    }

}

fun heap_sort(array: IntArray) {
    val obj = Heap(array)
    for (i in array.indices)
        array[i]=obj.extractMax()
}
