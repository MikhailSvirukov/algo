import jdk.incubator.vector.Vector


class Knot(a: Int?=null, par:Knot?=null) {
    var value=a
    var left:Knot?=null
    var right:Knot?=null
    var parent:Knot?=par
    var leaf=true
    fun add(key: Int) {
        var t:Knot?=this
        if (t?.value==null) {
            t?.value = key
            return
        }
        while (true) {
            if ((t?.value ?: 0)<=key) {
                if (t?.left==null) {
                    t?.left = Knot(key, t)
                    t?.leaf=false
                    break
                } else
                    t=t.left
            } else {
                if (t?.right == null) {
                    t?.right = Knot(key, t)
                    t?.leaf=false
                    break
                } else
                    t = t.right
            }
        }

    }

    fun find(key: Int): Boolean {
        var t:Knot?=this
        while (t!=null) {
            println(t.value)
            if (t.value==key)
                return true
            t = if ((t.value ?: 0)<=key) t.left else t.right
        }
        return false
    }

    fun remove(key: Int):Boolean {
        var t:Knot?=this
        while (t!=null) {
            println(t.value)
            if (t.value==key)
                break
            t = if ((t.value ?: 0)<=key) t.left else t.right
        }
        if (t==null)
            return false
        if (t.leaf) {
            if ((t.parent?.value ?: 0)<key)
                t.parent?.left=null
            else
                t.parent?.right=null
        } else if (t.right==null) {
            if ((t.parent?.value ?: 0)<key)
                t.parent?.left=t.left
            else
                t.parent?.right=t.left
        } else {
            var r=t.right
            while (r?.right!=null)
                r=r.right
            r?.parent?.right=null
            t.value=r?.value
        }
        return true
    }

    fun printLeafs() {
        var t=this
        var stack= java.util.Vector<Knot>()
        while (true) {
            if (t.leaf)
                print("${t.value} ")
            if (t.left!=null)
                stack.addFirst(t.left)
            if (t.right!=null)
                stack.addFirst(t.right)
            if (stack.isEmpty())
                break
            t=stack.removeFirst()
        }
    }


}

fun main() {
    var tree=Knot()
    readln().split(" ").map { tree.add(it.toInt()) }
    tree.printLeafs()

}
