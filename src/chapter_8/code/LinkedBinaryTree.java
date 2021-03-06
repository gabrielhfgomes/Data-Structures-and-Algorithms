package chapter_8.code;

import chapter_6.code.stack.ArrayStack;
import chapter_6.code.stack.Stack;
import chapter_7.code.positional_list.Position;

/*
 * Created by jjmacagnan on 27/05/2017.
 */
public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {

    protected static class Node<E> implements Position<E> {
        private E element;
        private Node<E> parent;
        private Node<E> left;
        private Node<E> right;

        public Node(E e, Node<E> above, Node<E> leftChild, Node<E> rightChild) {
            element = e;
            parent = above;
            left = leftChild;
            right = rightChild;
        }

        @Override
        public E getElement() throws IllegalStateException {
            return element;
        }

        public Node<E> getParent() {
            return parent;
        }

        public Node<E> getLeft() {
            return left;
        }

        public Node<E> getRight() {
            return right;
        }

        public void setElement(E e) {
            this.element = e;
        }

        public void setParent(Node<E> parentNode) {
            this.parent = parentNode;
        }

        public void setLeft(Node<E> leftChild) {
            this.left = leftChild;
        }

        public void setRight(Node<E> rightChild) {
            this.right = rightChild;
        }
    }

    protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
        return new Node<>(e, parent, left, right);
    }

    protected Node<E> root = null;
    private int size = 0;

    public LinkedBinaryTree() {
    }

    protected Node<E> validate(Position<E> p) throws IllegalStateException {
        if (!(p instanceof Node))
            throw new IllegalStateException("not valide position type");
        Node<E> node = (Node<E>) p;
        if ((node.getParent() == node))
            throw new IllegalStateException("p is no longer in the tree");
        return node;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Position<E> root() {
        return root;
    }

    @Override
    public Position<E> parent(Position<E> p) throws IllegalStateException {
        Node<E> node = validate(p);
        return node.getParent();
    }

    @Override
    public Position<E> left(Position<E> p) throws IllegalStateException {
        Node<E> node = validate(p);
        return node.getLeft();
    }

    @Override
    public Position<E> right(Position<E> p) throws IllegalStateException {
        Node<E> node = validate(p);
        return node.getRight();
    }

    public Position<E> addRoot(E e) throws IllegalStateException {
        if (!isEmpty())
            throw new IllegalStateException("tree is not empty");
        root = createNode(e, null, null, null);
        size = 1;
        return root;
    }

    public Position<E> addLeft(Position<E> p, E e) throws IllegalStateException {
        Node<E> parent = validate(p);
        if (parent.getLeft() != null)
            throw new IllegalStateException("p already has a left child");
        Node<E> child = createNode(e, parent, null, null);
        parent.setLeft(child);
        size++;
        return child;
    }

    public Position<E> addRight(Position<E> p, E e) throws IllegalStateException {
        Node<E> parent = validate(p);
        if (parent.getRight() != null)
            throw new IllegalStateException("p already has a right child");
        Node<E> child = createNode(e, parent, null, null);
        parent.setRight(child);
        size++;
        return child;
    }

    public E set(Position<E> p, E e) throws IllegalStateException {
        Node<E> node = validate(p);
        E temp = node.getElement();
        node.setElement(e);
        return temp;
    }


    public static <E> int countLeftLeaves(BinaryTree<E> binaryTree, Position<E> position) {
        int count = 0;

        if (binaryTree.isEmpty() || binaryTree.size() == 1) {
            return count;
        }

        Position<E> parent = binaryTree.parent(position);

        if (binaryTree.isExternal(position) && binaryTree.left(parent) == position) {
            return count + 1;
        } else {
            if (binaryTree.left(position) != null) {
                count += countLeftLeaves(binaryTree, binaryTree.left(position));
            }

            if (binaryTree.right(position) != null) {
                count += countLeftLeaves(binaryTree, binaryTree.right(position));
            }
        }

        return count;
    }

    public int countLeftLeavesAlt(Position<E> p) {
        int count = 0;

        for(Position<E> c : children(p)) {
            if(c == left(p) && isExternal(c)) {
                count += 1;
            }
            count += countLeftLeavesAlt(c);
        }

        return count;
    }

    public int pathLength(Position<E> p) {
        int count = 0;

        for (Position<E> c : children(p)) {
            count += depth(c);
            count += pathLength(c);
        }

        return count;
    }

    public int pathLengthInternal(Position<E> p) {
        int count = 0;

        for (Position<E> c : children(p)) {
            if (isInternal(c)) {
                count += depth(c);
            }
            count += pathLengthInternal(c);
        }

        return count;
    }

    public int pathLengthExternal(Position<E> p) {
        int count = 0;

        for (Position<E> c : children(p)) {
            if (isExternal(c)) {
                count += depth(c);
            }
            count += pathLengthExternal(c);
        }

        return count;
    }

    public void balanceFactor(Position<E> p) {
        int hLeft = 0;
        int hRight = 0;

        if (isInternal(p)) {
            if (left(p) != null)
                hLeft = height(left(p));

            if (right(p) != null)
                hRight = height(right(p));

            System.out.println("Elemento: " + p.getElement());

            System.out.println("Balance factor: " + (hLeft - hRight));
        }

        if (left(p) != null) {
            balanceFactor(left(p));
        }

        if (right(p) != null) {
            balanceFactor(right(p));
        }
    }

    public void balanceFactorGabriel(Position<E> p) {
        int hleft = 0;
        int hright = 0;

        if (p != null) {
            if (left(p) != null && isInternal(p)) {
                hleft = height(left(p));
            }

            if (right(p) != null && isInternal(p)) {
                hright = height(right(p));
            }

            if (isInternal(p)) {
                System.out.println(p.getElement() + " - " + (hleft - hright));
            }

            if (left(p) != null) {
                balanceFactorGabriel(left(p));
            }

            if (right(p) != null) {
                balanceFactorGabriel(right(p));
            }
        }
    }


    public <E> boolean isIsomorphic(Position<E> p, Position<E> q) {

        if (p == null && q == null) {
            return true;
        }

        if (p == null || q == null) {
            return false;
        }

        if (p.getElement() != q.getElement()) {
            return false;
        }

        Node<E> n1 = (Node<E>) p;
        Node<E> n2 = (Node<E>) q;

        /*Existem dois casos possíveis para que n1 e n2 sejam isomórficos
            Caso 1: as subveres enraizadas nesses nós não foram viradas.

          Essas duas sub-estruturas devem ser isomórficas.
            Caso 2: as subveres enraizadas nesses nós foram viradas*/
        return (isIsomorphic(n1.left, n2.left) && isIsomorphic(n1.right, n2.right))
                || (isIsomorphic(n1.left, n2.right) && isIsomorphic(n1.right, n2.left));
    }


    @Override
    public int depth(Position<E> p) throws IllegalStateException {
        if (isRoot(p))
            return 0;
        else
            return 1 + depth(parent(p));
    }

    public int height(Position<E> p) {
        int h = 0;
        for (Position<E> c : children(p))
            h = Math.max(h, 1 + height(c));

        return h;
    }

    public int pruneSubtree(Position<E> p) {
        int r = 0;
        if (!isRoot(p)) {
            if (p != null) {
                if (p == left(parent(p))) {
                    validate(parent(p)).setLeft(null);
                    size--;
                } else {
                    validate(parent(p)).setRight(null);
                    size--;
                }
                for (Position<E> c : children(validate(p))) {
                    r += 1 + pruneSubtree(left(validate(c))) + pruneSubtree(right(validate(c)));

                    if (c == left(parent(validate(c)))) {
                        validate(c).parent.setLeft(null);
                    } else {
                        validate(c).parent.setRight(null);
                    }
                }
                validate(p).setParent(null);
            }
        }
        size = size - r;
        return r;
    }

    public void swap(Position<E> p, Position<E> q) {
        set(p, set(q, p.getElement()));
    }

    public LinkedBinaryTree<E> clone2(LinkedBinaryTree<E> T, Position<E> p) {
        LinkedBinaryTree T1 = new LinkedBinaryTree();
        T1.addRoot(p);
        LinkedBinaryTree T2 = new LinkedBinaryTree();
        T1.attach(p, T, T2);

        return T1;
    }

    public void cloneAddLeftAddRight(LinkedBinaryTree<E> other, Position<E> p, Position<E> q) {

        if (isRoot(p)) {
            other.addRoot(p.getElement());
            q = other.root;
        }

        if (p != null) {
            for (Position<E> c : children(p)) {
                if (c != null) {
                    if (c == left(parent(c))) {
                        other.addLeft(q, c.getElement());

                        cloneAddLeftAddRight(other, c, left(q));
                    }

                    if (c == right(parent(c))) {
                        other.addRight(q, c.getElement());

                        cloneAddLeftAddRight(other, c, right(q));
                    }
                }

            }
        }


    }

    public void printElementAndHeightPosition(Position<E> p) {

        if (validate(p) != null) {
            System.out.println("Elemento: " + p.getElement());
            System.out.println("Altura: " + height(p));
            System.out.println("--------------");
        }

        if (left(p) != null) {
            printElementAndHeightPosition(validate(left(p)));
        }

        if (right(p) != null) {
            printElementAndHeightPosition(validate(right(p)));
        }
    }

    public void depthN(Position<E> p, int n) {
        if (isRoot(p)) {
            System.out.println("Elemento: " + p.getElement() + " depth: " + " = " + 0);
        }
        for (Position<E> c : children(p)) {
            n = depth(c);
            depthN(c, n + 1);
            System.out.println("Elemento: " + c.getElement() + " depth: " + " = " + n);
        }
    }

    public Position<E> preorderNext(Position<E> v) {

        if (isInternal(v)) {
            return left(v);
        } else {
            Node<E> p = validate(parent(v));

            if (v == left(parent(p))) {
                return right(p);
            } else {
                while (!(v == p.left)) {
                    v = p;
                    p = p.parent;
                }
                return right(p);
            }
        }
    }

    public Position<E> inorderNext(Position<E> v) {
        if (isInternal(v) && right(v) != null) {
            v = right(v);

            while (!isExternal(v) && left(v) != null) {
                v = left(v);
            }
            return v;
        } else {
            Node<E> p = validate(parent(v));

            if (v == p.left) {
                return p;
            } else {
                while (!(v == p.left)) {
                    v = p;
                    p = p.parent;
                }
                return p;
            }
        }
    }

    public Position<E> postorderNext(Position<E> v) {
        Node<E> p;
        if (isInternal(v) && parent(v) != null) {
            p = validate(parent(v));

            if (v == p.right) {
                return p;
            } else {
                if (right(v) != null) {
                    v = right(p);
                    while (!isExternal(v) && left(v) != null) {
                        v = left(v);
                    }
                } else
                    v = parent(v);

                return v;
            }
        } else if (parent(v) != null) {
            p = validate(parent(v));
            if (v == p.left && right(p) != null) {
                return right(p);
            } else
                return p;
        }

        return null;
    }

    public void inorderOn() {
        if (root == null) {
            return;
        }

        //keep the nodes in the path that are waiting to be visited
        Stack<Node> stack = new ArrayStack<>();
        Node node = root;

        //first node to be visited will be the left one
        while (node != null) {
            stack.push(node);
            node = node.left;
        }

        // traverse the tree
        while (stack.size() > 0) {

            // visit the top node
            node = stack.pop();
            System.out.print(node.getElement() + " ");
            if (node.right != null) {
                node = node.right;

                // the next node to be visited is the left most
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
            }
        }
    }


    /**
     * Attaches trees t1 and t2, respectively, as the left and right subtree of the
     * leaf Position p. As a side effect, t1 and t2 are set to empty trees.
     *
     * @param p  a leaf of the tree
     * @param t1 an independent tree whose structure becomes the left child of p
     * @param t2 an independent tree whose structure becomes the right child of p
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     * @throws IllegalArgumentException if p is not a leaf
     */
    public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2) throws IllegalStateException {
        Node<E> node = validate(p);
        if (isInternal(p))
            throw new IllegalStateException("p must be a leaf");
        size += t1.size() + t2.size();

        if (!t1.isEmpty()) { // attach t1 as left subtree of node
            t1.root.setParent(node);
            node.setLeft(t1.root);
            t1.root = null;
            t1.size = 0;
        }

        if (!t2.isEmpty()) {  // attach t2 as right subtree of node
            t2.root.setParent(node);
            node.setRight(t2.root);
            t2.root = null;
            t2.size = 0;
        }
    }


    public E remove(Position<E> p) throws IllegalStateException {
        Node<E> node = validate(p);
        if (numChildren(p) == 2)
            throw new IllegalStateException("p has two children");
        Node<E> child = (node.getLeft() != null ? node.getLeft() : node.getRight());
        if (child != null)
            child.setParent(node.getParent());
        if (node == root)
            root = child;
        else {
            Node<E> parent = node.getParent();
            if (node == parent.getLeft())
                parent.setLeft(child);
            else
                parent.setRight(child);
        }
        size--;
        E temp = node.getElement();
        node.setElement(null);
        node.setLeft(null);
        node.setRight(null);
        node.setParent(node);
        return temp;
    }

    public static <E> void parenthesize(Tree<E> T, Position<E> p) {
        System.out.print(p.getElement( ));
        if (T.isInternal(p)) {
            boolean firstTime = true;
            for (Position<E> c : T.children(p)) {
                System.out.print( (firstTime ? " (" : ", ") ); // determine proper punctuation
                firstTime = false; // any future passes will get comma
                parenthesize(T, c); // recur on child
            }
            System.out.print(")");
        }
    }
}
