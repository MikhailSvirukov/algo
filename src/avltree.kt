
class Node(a: Int) {
    var value: Int?=a
    var left:Node?=null
    var right:Node?=null
    var height=1

    fun getBalance(): Int {
        return (right?.value ?: 0) - (left?.value ?: 0)
    }
}


class AVLTree() {
    var root:Node?=null

    fun add(a: Int, node: Node?) {
        if (root==null) {
            root = Node(a)
            return
        }
        if (a>=(root?.value ?: Int.MIN_VALUE))
            if (root?.right==null)
                root?.right=Node(a)
            else {
                add(a, root?.right)
                root?.right?.getBalance()
            }
        else
            if (root?.left==null)
                root?.left=Node(a)
            else {
                add(a, root?.left)
                root?.left?.getBalance()
            }
        root?.getBalance()
    }

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

    //предполагается, что ищется предок ТОЛЬКО существующего узла
    private fun findParent(target: Int?, node: Node?): Node? {
        //случай с корнем
        if (target==node?.value)
            return null
        if (target != null) {
            if (target<(node?.value ?: Int.MAX_VALUE))
                return if (node?.left?.value==target) node else node?.left?.let { findParent(target, it) }
            else
                return if (node?.right?.value==target) node else node?.right?.let { findParent(target, it) }
        }
        return null
    }

    private fun findUpperBorder(node: Node?): Node? {
        var t=node?.right
        if (t?.left!=null)
            return findUpperBorder(t.left)
        else
            return t
    }

    private fun changeDeletion(node: Node?, basic: Node?) {
        var t=node?.right
        if (t?.left!=null) {
            findUpperBorder(t.left)
            t.getBalance()
        } else {
            basic?.value=t?.value
            var w=findParent(t?.value, root)
            w?.left=null
            w?.getBalance()
        }

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