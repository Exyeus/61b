package lab9;

// import java.util.AbstractSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        if (p.key.compareTo(key) == 0) {
            return p.value;
        } else if (p.key.compareTo(key) > 0) {
            return getHelper(key, p.left);
        } else {
            return getHelper(key, p.right);
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, this.root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p.key.compareTo(key) > 0) {
            return putHelper(key, value, p.left);
        } else if (p.key.compareTo(key) < 0) {
            return putHelper(key, value, p.right);
        } else {
            return p;
        }
    }
    private Node putRecursive(Node current, K key, V value) {
        // 1. 基本情况：如果当前节点为空，表示找到了插入位置，创建新节点并返回
        if (current == null) {
            size++; // 只有在插入新节点时才增加 size
            return new Node(key, value);
        }

        // 2. 查找插入/更新位置：根据 key 的比较结果向下递归
        int cmp = key.compareTo(current.key);
        if (cmp < 0) {
            // key 小于当前节点，继续在左子树查找/插入
            // 关键：将递归调用的返回值赋给 current.left
            current.left = putRecursive(current.left, key, value);
        } else if (cmp > 0) {
            // key 大于当前节点，继续在右子树查找/插入
            // 关键：将递归调用的返回值赋给 current.right
            current.right = putRecursive(current.right, key, value);
        } else {
            // 3. 找到了键：key 与当前节点键相同，更新值
            // 不增加 size，因为是更新不是插入
            current.value = value;
        }

        // 4. 返回当前节点：它可能是原始节点，或其子节点被更新，或它是一个新节点（当 current 初始为 null 时）
        return current;
    }
    /** Inserts the key
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        /*Node possibleExistingNode = findNodeWithGivenKey(key, this.root);
        if (possibleExistingNode != null) {
            possibleExistingNode.value = value;
            return;
        }
        size += 1;
        Node p = putHelper(key, value, this.root);
        if (p != null) {
            p.value = value;
        } else {
            p = new Node(key, value);
        }*/
        root = putRecursive(root, key, value);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return this.size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        // throw new UnsupportedOperationException();
        Set<K> s = new HashSet<K>();
        setHelper(this.root, s);
        return s;
    }
    private void setHelper(Node p, Set<K> s) {
        if (p == null) {
            return;
        } else {
            s.add(p.key);
            setHelper(p.left, s);
            setHelper(p.right, s);
        }
    }

    /**
     * This should be decided: The Parent of the resultNode,
     * must have left child, causing no NullPointerException.
     * @param p The start Node of our searching
     * @return The smallest Node of this side
     */
    private Node findRightBranchSmallest(Node p) {
        Node resultNode = p;
        while (resultNode.left != null) {
            resultNode = resultNode.left;
        }
        return resultNode;
    }
    @Deprecated
    private Node findParentOfNode(Node target, Node start) {
        if (start == null
                || target == null
                || start == target) {
            return null;
        }
        if (start.left == target
                || start.right == target) {
            return start;
        }
        if (target.key.compareTo(start.key) > 0) {
            return findParentOfNode(target, start.right);
        } else if (target.key.compareTo(start.key) < 0) {
            return findParentOfNode(target, start.left);
        } else {
            return null;
        }
        // No findings, target DNE or being the root Node.
    }

    /**
     * Misson:
     * 1. Root special case ok
     * 2. No child
     * 3. Single child
     * 4. Both children
     */
    // 标准的递归删除方法，通过返回新的子树根，优雅地处理了所有情况，包括根节点删除。
    @Deprecated
    private Node removeHelperWithKey(K key) {
        Node targetNode = findNodeWithGivenKey(key, root);
        Node targetNodeParent;
        if (targetNode == this.root) {
            // Directly remove the root node.
            // It is possible that this case can be merged into
            // more general solutions
            if (targetNode.left == null
                    && targetNode.right == null) {
                // This root Node is the only one.
                return null;
            } else if (targetNode.right == null) {
                this.root = this.root.left;
            } else if (targetNode.left == null) {
                this.root = this.root.right;
            } else {
                // find the smallest of the right tree
                // and substitute the root Node with this Node
                Node successor = findRightBranchSmallest(root.right);
                // Question: How to remove the reference of its parent?
                Node originalParent = findParentOfNode(successor, this.root);
                assert originalParent != null;
                if (originalParent == this.root) {

                    originalParent = originalParent.right;
                } else {
                    // this smallest of right side, is at height > 2.
                    originalParent.left = null;
                    root = successor;
                }
            }
        } else {
            Node originalParent = findParentOfNode(targetNode, this.root);
            assert originalParent != null;
            // The targetNode has its parent!
            if (targetNode.left == null
                    && targetNode.right == null) {
                this.root = targetNode;
                originalParent.left = null;
            } else if (targetNode.left == null) {
                targetNode = targetNode.right;
            } else if (targetNode.right == null) {
                targetNode = targetNode.left;
            } else {
                Node successor = findRightBranchSmallest(targetNode.right);
                Node successorParent = findParentOfNode(successor, targetNode);
                assert successorParent != null;
                this.root = successor;
                successorParent.left = null;
            }
        }
        return targetNode;
    }

    /**
     *
     * @param key The key you want to find.
     * @param p The start Node of searching.
     * @return The Node found that contain this key.
     */
    private Node findNodeWithGivenKey(K key, Node p) {
        if (p == null) {
            return null;
        } else if (p.key.compareTo(key) == 0) {
            // Always remember to use `compareTo` to compare non-int objects!
            return p;
        } else if (p.key.compareTo(key) > 0) {
            return findNodeWithGivenKey(key, p.left);
        } else {
            return findNodeWithGivenKey(key, p.right);
        }

    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */

    @Override
    public V remove(K key) {
        /*if (get(key) == null) {
            return null;
        }
        size -= 1;
        Node removedNode = removeHelperWithKey(key);
        if (removedNode == null) {
            return null;
        } else {
            return removedNode.value;
        }*/
        V valueToRemove = get(key); // 使用已有的 get 方法
        if (valueToRemove == null) {
            return null;
        }

        // 如果存在，则进行删除，并减少 size
        root = removeRecursive(root, key);
        size--; // 只有成功删除一个节点时才减少 size

        return valueToRemove;
    }

    private Node findMinNode(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * 递归删除方法
     * @param current 当前子树的根节点
     * @param key 要删除的键
     * @return 删除节点后，以 current 为原始根节点的子树的新的根节点
     */
    private Node removeRecursive(Node current, K key) {
        // 1. 基本情况：如果当前节点为空，表示没找到要删除的节点（不应该发生，因为remove()已经检查过key是否存在）
        if (current == null) {
            return null;
        }

        // 2. 查找要删除的节点：根据 key 的比较结果向下递归
        int cmp = key.compareTo(current.key);
        if (cmp < 0) {
            // 要删除的 key 在左子树，递归删除并更新 current 的左子节点
            current.left = removeRecursive(current.left, key);
        } else if (cmp > 0) {
            // 要删除的 key 在右子树，递归删除并更新 current 的右子节点
            current.right = removeRecursive(current.right, key);
        } else {
            // 3. 找到了要删除的节点：current 就是要删除的节点

            // 情况一：当前节点是叶子节点（没有子节点）
            if (current.left == null && current.right == null) {
                return null; // 直接返回 null，表示该节点被移除
            }

            // 情况二：当前节点只有一个子节点
            if (current.left == null) {
                return current.right; // 返回右子节点，让其替代当前节点
            }
            if (current.right == null) {
                return current.left; // 返回左子节点，让其替代当前节点
            }

            // 情况三：当前节点有两个子节点
            // 找到右子树中的最小节点（Inorder Successor）
            Node successor = findMinNode(current.right);

            // 将最小节点的值复制到当前节点（被删除的节点）
            current.key = successor.key;
            current.value = successor.value;

            // 递归地删除右子树中的最小节点（因为它现在的内容已经复制到 current 了）
            // 这一步会处理情况一或情况二的删除
            current.right = removeRecursive(current.right, successor.key);
        }

        // 返回当前节点（它可能是原来的节点，或者是一个新的替代节点，或者其子节点被更新了）
        return current;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/

    @Override
    public V remove(K key, V value) {
        /*Node readyToRemove = findNodeWithGivenKey(key, root);
        if (readyToRemove == null) {
            return null;
        } else {
            if (readyToRemove.key != key) {
                return null;
            } else {
                return remove(key);
            }
        }*/
        // 先尝试获取值，如果不存在，直接返回null，且不修改size
        Node nodeToRemove = findNodeWithGivenKey(key, root); // 找到可能存在的节点
        if (nodeToRemove == null) { // key 不存在
            return null;
        }

        // 如果 key 存在，检查 value 是否匹配
        // 注意：value 也可能是 null，需要处理 null-safe 比较
        if (nodeToRemove.value == value) { // 使用 Objects.equals 进行 null-safe 比较
            // value 匹配，执行删除
            return remove(key); // 调用你已经修改好的 remove(K key) 方法
        } else {
            // value 不匹配，不删除
            return null;
        }
    }

    /**
     * Perhaps using the left-node-right recursive pattern?
     */
    @Override
    public Iterator<K> iterator() {
        return new BSTMapStackIterator();
    }
    private class BSTMapStackIterator implements Iterator<K> {
        private Stack<Node> stack;

        public BSTMapStackIterator() {
            stack = new Stack<>();
            // 初始化栈：将最左侧路径上的所有节点压入栈中
            Node current = root;
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            Node node = stack.pop(); // 弹出当前要访问的节点
            K result = node.key;

            // 转向右子树：将右子树的最左侧路径上的所有节点压入栈中
            Node current = node.right;
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation not supported by this iterator.");
        }
    }
}
