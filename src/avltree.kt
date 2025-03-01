import kotlin.math.max

// TODO определить, что не так с удалением, потому что коректно только удаление листа


class Node(a: Int) {
    var value: Int?=a
    var left:Node?=null
    var right:Node?=null
    var height=1

    fun getBalance(): Int {
        height= max((right?.height ?: 0), (left?.height ?: 0))+1
        return (right?.height ?: 0) - (left?.height ?: 0)
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
                checkHeight(node.right)
            }
        } else {
            if (node?.left == null) {
                node?.left = Node(a)
                return
            }else {
                add(a, node.left)
                checkHeight(node.left)
            }
        }
        if (node==root)
            checkHeight(root)
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
        checkHeight(node?.left)
    }

    //будем счиатать, что удаляется ТОЛЬКО существующий узел
    fun remove(a: Int, node: Node?) {
        if (node?.value == a) {
            if (node.right==null)
                root=node.left
            else {
                changeDeletion(node.right, root, root)
                checkHeight(root)
            }
            return
        }
        if (node?.right != null) {
            if (node.right?.value == a) {
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
            checkHeight(node?.left)
        } else {
            remove(a, node?.right)
            checkHeight(node?.right)
        }
        if (node==root)
            checkHeight(root)
    }

    private fun rotateLeft(node: Node?, parent: Node?) {
        val temp=node?.right
        println(node?.right)
        node?.right=temp?.left
        temp?.left=node
        if (parent?.right==node)
            parent?.right=temp
        else
            parent?.left=temp
        node?.getBalance()
        temp?.getBalance()
        parent?.getBalance()

    }
    private fun rotateRight(node: Node?, parent: Node?) {
        val temp=node?.left
        node?.left=temp?.right
        temp?.right=node
        if (parent?.right==node)
            parent?.right=temp
        else
            parent?.left=temp
        node?.getBalance()
        temp?.getBalance()
        parent?.getBalance()
    }

    fun checkHeight(node: Node?) {
        var parent=findParent(node?.value, root)
        if (parent==node)
            parent=this.root
        if (node?.getBalance()==2) {
            println(node.right?.value)

            if ((node.right?.getBalance() ?: Int.MIN_VALUE)>-1)
                rotateLeft(node, parent)
            else {
                rotateRight(node.right, node)
                rotateLeft(node, parent)
            }
        } else if (node?.getBalance()==-2) {
            if ((node.left?.getBalance() ?: Int.MIN_VALUE)<1)
                rotateRight(node, parent)
            else {
                rotateLeft(node.left, node)
                rotateRight(node, parent)
            }
        }
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
    //avl.remove(8, avl.root)
    println("++++++++++++++++++++++")
    println(avl.find(14, avl.root)?.height)
    println(avl.find(13, avl.root)?.height)
    println(avl.find(18, avl.root)?.height)
    println(avl.find(19, avl.root)?.height)
    println(avl.find(16, avl.root)?.height)
    println(avl.find(15, avl.root)?.height)
    println("---------------------------------")
    println(avl.findParent(14, avl.root)?.value)
    println(avl.findParent(13, avl.root)?.value)
    println(avl.findParent(18, avl.root)?.value)
    println(avl.findParent(19, avl.root)?.value)
    println(avl.findParent(16, avl.root)?.value)
    println(avl.findParent(15, avl.root)?.value)
}