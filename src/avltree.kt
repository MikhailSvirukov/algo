import kotlin.math.max

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

    fun find(a: Int?, node: Node?): Node? {
        if (node?.value == a)
            return node
        if ((node?.value ?: Int.MIN_VALUE)<(a ?: Int.MAX_VALUE)) {
            if (node?.right == null)
                return null
            return find(a, node.right)
        } else {
            if (node?.left == null)
                return null
            return find(a, node.left)
        }
    }

     fun findParent(target: Int?, node: Node?): Node? {
        if (find(target, root)==null)
            return null
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

    fun findUpperBorder(node: Node?): Node? {
        val t=node?.right
        if (t?.left!=null)
            return findUpperBorder(t.left)
        else
            return t
    }

    private fun changeDeletion(node: Node?, basic: Node?, parent: Node?) {
        if (node?.left==null) {
            if (basic==parent) {
                node?.left=root?.left
                root=node
            } else if (parent?.left==basic)
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

    fun remove(a: Int, node: Node?) {
        if (find(a, root)==null)
            return
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
        node?.right=temp?.left
        temp?.left=node
        if (parent==root) {
            root = temp
            node?.getBalance()
            return
        }
        if (parent?.right == node)
            parent?.right = temp
        else
            parent?.left = temp
        node?.getBalance()
        temp?.getBalance()
        parent?.getBalance()

    }

    private fun rotateRight(node: Node?, parent: Node?) {
        val temp=node?.left
        node?.left=temp?.right
        temp?.right=node
        if (parent==root) {
            root = temp
            node?.getBalance()
            return
        }
        if (parent?.right==node)
            parent?.right=temp
        else
            parent?.left=temp
        node?.getBalance()
        temp?.getBalance()
        parent?.getBalance()
    }

    private fun checkHeight(node: Node?) {
        var parent=findParent(node?.value, root)
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



fun main() {
    var avl=AVLTree()
    readln().split(" ").map { avl.add(it.toInt(), avl.root) }
}