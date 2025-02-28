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

    private fun changeDeletion(node: Node?, basic: Node?, parent: Node?) {
        if (node?.left==null) {
            if (parent?.left==basic)
                parent?.left=node
            else
                parent?.right=node
        } else if (node.left?.left==null){
            basic?.value=node.left?.value
            node.left=null
        } else {
            changeDeletion(node.left, basic, parent)
        }
        node?.left?.getBalance()
    }

    //будем счиатать, что удаляется ТОЛЬКО существующий узел
    fun remove(a: Int, node: Node?) {
        if (node?.value == a) {
            if (node.right==null)
                root=node.left
            else {
                changeDeletion(node.right, root, root)
                root?.getBalance()
            }
            return
        }
        if (node?.right != null) {
            if (node.right?.value == a) {
                //println("********")
                if (node.right?.right==null && node.right?.left==null)
                    node.right=null
                else if (node.right?.right==null)
                    node.right=node.right?.left
                else {
                    changeDeletion(node.right?.right, node.right, node)
                }
                return
            }
        }
        if (node?.left != null) {
            if (node.left?.value == a) {
                if (node.left?.right==null && node.left?.left==null)
                    node.left=null
                else if (node.left?.right==null)
                    node.left=node.left?.left
                else {
                    changeDeletion(node.left?.right, node.left, node)
                }
                return
            }
        }
        if ((node?.value ?: Int.MIN_VALUE) > a) {
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
    avl.remove(8, avl.root)
    println(avl.find(8, avl.root)?.height)
    println(avl.find(5, avl.root)?.height)
    println(avl.find(10, avl.root)?.height)
    println(avl.find(7, avl.root)?.height)
    println(avl.find(6, avl.root)?.height)
    println(avl.find(9, avl.root)?.height)
    println(avl.find(1, avl.root)?.height)
    println(avl.find(2, avl.root)?.height)
    println(avl.find(4, avl.root)?.height)
    println("---------------------------------")
    println(avl.findParent(8, avl.root)?.value)
    println(avl.findParent(5, avl.root)?.value)
    println(avl.findParent(10, avl.root)?.value)
    println(avl.findParent(7, avl.root)?.value)
    println(avl.findParent(6, avl.root)?.value)
    println(avl.findParent(9, avl.root)?.value)
    println(avl.findParent(1, avl.root)?.value)
    println(avl.findParent(2, avl.root)?.value)
    println(avl.findParent(4, avl.root)?.value)
}