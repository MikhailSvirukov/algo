import kotlin.math.max

// TODO определить, что не так с удалением, потому что коректно только удаление листа


class Node(a: Int) {
    var value: Int?=a
    var left:Node?=null
    var right:Node?=null
    var height=1

    fun getBalance(): Int {
        height= max((right?.height ?: 0), (left?.height ?: 0))+1
        return (right?.value ?: 0) - (left?.value ?: 0)
    }
}


class AVLTree() {
    var root:Node?=null
    //correct
    fun add(a: Int, node: Node?) {
        if (root==null) {
            root= Node(a)
            return
        }
        if (a>=(node?.value ?: Int.MIN_VALUE)) {
            if (node?.right == null) {
                node?.right = Node(a)
                return
            }else {
                add(a, node.right)
                node.right?.getBalance()
            }
        } else {
            if (node?.left == null) {
                node?.left = Node(a)
                return
            }else {
                add(a, node.left)
                node.left?.getBalance()
            }
        }
        if (node==root)
            root?.getBalance()
    }

    //correct
    fun find(a: Int, node: Node?): Node? {
        if (node?.value == a)
            return node
        if ((node?.value ?: Int.MIN_VALUE)<a) {
            if (node?.right == null)
                return null
            return find(a, node.right)
        } else {
            if (node?.left == null)
                return null
            return find(a, node.left)
        }
    }

    //correct
    //предполагается, что ищется предок ТОЛЬКО существующего узла
     fun findParent(target: Int?, node: Node?): Node? {
        //случай с корнем
        if (target==node?.value)
            return root
        if (target != null) {
            if (target<(node?.value ?: Int.MAX_VALUE))
                return if (node?.left?.value==target) node else node?.left?.let { findParent(target, it) }
            else
                return if (node?.right?.value==target) node else node?.right?.let { findParent(target, it) }
        }
        return null
    }

    //correct
    private fun findUpperBorder(node: Node?): Node? {
        val t=node?.right
        if (t?.left!=null)
            return findUpperBorder(t.left)
        else
            return t
    }

    private fun changeDeletion(node: Node?, basic: Node?) {
        var t=node?.right
        if (t?.left!=null)
            t=findUpperBorder(t.left)
        basic?.value=t?.value
        var w=findParent(t?.value, root)
        w?.left=null
        w?.getBalance()
    }

    //будем счиатать, что удаляется ТОЛЬКО существующий узел
    fun remove(a: Int, node: Node?) {
        if (node?.value==a) {
            var t = findParent(node.value, root)
            if (node.right == null && node.left == null) {
                if (t?.left?.value == a)
                    t.left = null
                else
                    t?.right = null
            } else if (node.right == null) {
                if (t?.left?.value == a)
                    t.left = t.left?.left
                else
                    t?.right = t?.left?.left
            } else
                changeDeletion(node.right, node)
            return
        } else if ((node?.value ?: Int.MIN_VALUE)>a) {
            remove(a, node?.left)
            node?.left?.getBalance()
        } else {
            remove(a, node?.right)
            node?.right?.getBalance()
        }
        if (node==root)
            root?.getBalance()
    }
}

fun printPaths(tree: AVLTree) {
    var stack=ArrayDeque<Node?>()
    stack.addLast(tree.root)
    while (stack.isNotEmpty()) {
        var r=stack.removeFirst()
        print("${r?.value} ")
        if (r?.right==null && r?.left==null) {
            println()
        } else {
            stack.addLast(r.left)
            stack.addLast(r.right)
        }

    }
}

fun main() {
    var avl=AVLTree()
    readln().split(" ").map { avl.add(it.toInt(), avl.root) }
    avl.remove(4, avl.root)
    println(avl.findParent(5, avl.root)?.value)
}