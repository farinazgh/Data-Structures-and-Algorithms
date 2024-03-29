public class RecursiveInsertDeleteonBST {
    static class Node {

        Node lchild;
        int data;
        Node rchild;
    }

    ;
    Node root;

    RecursiveInsertDeleteonBST() {
        root = null;
    }

    void iInsert(int key) {

        Node t = root;
        Node p;
        Node r = null;

        // root is empty
        if (root == null) {
            p = new Node();
            p.data = key;
            p.lchild = null;
            p.rchild = null;
            root = p;
            return;
        }

        while (t != null) {
            r = t;
            if (key < t.data) {
                t = t.lchild;
            } else if (key > t.data) {
                t = t.rchild;
            } else {
                return;
            }
        }

        // Now t points at NULL and r points at insert location
        p = new Node();
        p.data = key;
        p.lchild = null;
        p.rchild = null;

        if (key < r.data) {
            r.lchild = p;
        } else {
            r.rchild = p;
        }

    }

    void Inorder(Node p) {
        if (p != null) {
            Inorder(p.lchild);
            System.out.println(p.data + ", ");
            Inorder(p.rchild);
        }
    }

    Node iSearch(int key) {
        Node t = root;
        while (t != null) {
            if (key == t.data) {
                return t;
            } else if (key < t.data) {
                t = t.lchild;
            } else {
                t = t.rchild;
            }
        }
        return null;
    }

    Node rInsert(Node p, int key) {
        Node t;
        if (p == null) {
            t = new Node();
            t.data = key;
            t.lchild = null;
            t.rchild = null;
            return t;
        }

        if (key < p.data) {
            p.lchild = rInsert(p.lchild, key);
        } else if (key > p.data) {
            p.rchild = rInsert(p.rchild, key);
        }
        return p;  // key == p.data?
    }

    Node rSearch(Node p, int key) {
        if (p == null) {
            return null;
        }

        if (key == p.data) {
            return p;
        } else if (key < p.data) {
            return rSearch(p.lchild, key);
        } else {
            return rSearch(p.rchild, key);
        }
    }

    Node Delete(Node p, int key) {
        Node q;

        if (p == null) {
            return null;
        }

        if (p.lchild == null && p.rchild == null) {
            if (p == root) {
                root = null;
            }
            return null;
        }

        if (key < p.data) {
            p.lchild = Delete(p.lchild, key);
        } else if (key > p.data) {
            p.rchild = Delete(p.rchild, key);
        } else {
            if (Height(p.lchild) > Height(p.rchild)) {
                q = InPre(p.lchild);
                p.data = q.data;
                p.lchild = Delete(p.lchild, q.data);
            } else {
                q = InSucc(p.rchild);
                p.data = q.data;
                p.rchild = Delete(p.rchild, q.data);
            }
        }
        return p;
    }

    int Height(Node p) {
        int x;
        int y;
        if (p == null) {
            return 0;
        }
        x = Height(p.lchild);
        y = Height(p.rchild);
        return x > y ? x + 1 : y + 1;
    }

    Node InPre(Node p) {
        while (p != null && p.rchild != null) {
            p = p.rchild;
        }
        return p;
    }

    Node InSucc(Node p) {
        while (p != null && p.lchild != null) {
            p = p.lchild;
        }
        return p;
    }

    Node getRoot() {
        return root;
    }

    public static void main(String[] args) {

        RecursiveInsertDeleteonBST bst = new RecursiveInsertDeleteonBST();

        // Iterative insert
        bst.iInsert(10);
        bst.iInsert(5);
        bst.iInsert(20);
        bst.iInsert(8);
        bst.iInsert(30);

        // Inorder traversal
        bst.Inorder(bst.getRoot());
        System.out.println();

        // Iterative search
        Node temp = bst.iSearch(2);
        if (temp != null) {
            System.out.println(temp.data);
        } else {
            System.out.println("Element not found");
        }

        // Recursive search
        temp = bst.rSearch(bst.getRoot(), 20);
        if (temp != null) {
            System.out.println(temp.data);
        } else {
            System.out.println("Element not found");
        }

        // Recursive insert
        bst.rInsert(bst.getRoot(), 50);
        bst.rInsert(bst.getRoot(), 70);
        bst.rInsert(bst.getRoot(), 1);
        bst.Inorder(bst.getRoot());
        System.out.println("\n");

        // Inorder predecessor and inorder successor
        RecursiveInsertDeleteonBST bs = new RecursiveInsertDeleteonBST();
        bs.iInsert(5);
        bs.iInsert(2);
        bs.iInsert(8);
        bs.iInsert(7);
        bs.iInsert(9);
        bs.iInsert(1);

        temp = bs.InPre(bs.getRoot());
        System.out.println("InPre: " + temp.data);

        temp = bs.InSucc(bs.getRoot());
        System.out.println("InSucc: " + temp.data);

        bs.Inorder(bs.getRoot());
        System.out.println();

        // Delete
        bs.Delete(bs.getRoot(), 7);
        bs.Inorder(bs.getRoot());

    }
}