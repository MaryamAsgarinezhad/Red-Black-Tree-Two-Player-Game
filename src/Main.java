import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        RBNode.makeNill();
        Scanner scan = new Scanner(System.in);

        Game main = new Game(new RBTree());
        main.PlayGame(scan.nextLine(), scan.nextLine());
    }
}
class Game{
    RBTree tree;
    int p1_negative;
    int p2_negative;

    public Game(RBTree gameBoard) {
        this.tree = gameBoard;
        this.p1_negative = 0;
        this.p2_negative = 0;
    }

    public void PlayGame(String p1, String p2){

        int i = 0, j = 0, k = 0, t = 0 , x = 0;
        while (i < p1.length() || k < p2.length()){

            if(i < p1.length()){
                while (p1.charAt(j) != ','){j++; if(j == p1.length()) break;}
                x = Integer.parseInt(p1.substring(i, j));
                if(!move(1, x, i, k, p1, p2)){
                    System.out.println("0");
                    return;
                }
                j++;
                i = j;
            }
            if(k < p2.length()){
                while (p2.charAt(t) != ','){t++; if(t == p2.length()) break;}
                x = Integer.parseInt(p2.substring(k, t));
                if(!move(2, x, i, k, p1, p2)){
                    System.out.println("0");
                    return;
                }
                t++;
                k = t;
            }
        }

        if(!tree.valid(1, 0, i, k, p1, p2)){
            System.out.println("0");
            return;
        }

        RBNode n = this.tree.minimum(this.tree.getRoot());
        while (n != RBNode.nill){
            if(n.getPlayer() == 1 && !n.isBlack()) p1_negative += n.getValue()*tree.height(n);
            else if(n.getPlayer() == 2 && !n.isBlack()) p2_negative += n.getValue()*tree.height(n);
            //System.out.println(GameBoard.height(n));
            n = this.tree.successor(n);
        }

        if(p1_negative == p2_negative){
            System.out.println("0 " + p1_negative +" "+ p2_negative);
            return;
        }

        if(p1_negative > p2_negative)
            System.out.println("2 " + p1_negative +" "+ p2_negative);
        else
            System.out.println("1 " + p1_negative +" "+ p2_negative);
    }

    public boolean move( int player,int x, int j, int t, String p1, String p2){
        if(!tree.valid( player, x, j, t, p1, p2)) return false;
        RBNode n = tree.search(x, tree.getRoot());
        if(n != RBNode.nill){
            tree.delete(n);
            tree.insert(x + 32, player);
        }
        else tree.insert(x, player);
        return true;
    }
}

class RBNode{
    private RBNode father;
    private RBNode right;
    private RBNode left;
    private boolean black;
    private int value;
    private int player;
    public static RBNode nill = new RBNode(null, null, null, true, 0, 0);

    public static void makeNill(){
        nill.right = nill;
        nill.left =  nill;
        nill.father =nill;
        nill.black = true;
    }

    public RBNode(RBNode father, RBNode right, RBNode left, boolean black, int value, int player) {
        this.father = father;
        this.right = right;
        this.left = left;
        this.black = black;
        this.value = value;
        this.player = player;
    }

    public RBNode getFather() {
        return father;
    }

    public void setFather(RBNode father) {
        this.father = father;
    }

    public RBNode getRight() {
        return right;
    }

    public void setRight(RBNode right) {
        this.right = right;
    }

    public RBNode getLeft() {
        return left;
    }

    public void setLeft(RBNode left) {
        this.left = left;
    }

    public boolean isBlack(){
        return this.black;
    }

    public void setBlack(boolean black) {
        if(this == nill) { return;}
        this.black = black;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }
}

class RBTree{
    private RBNode root;


    RBTree(){
        this(RBNode.nill);
        RBNode.makeNill();
    }

    public RBTree(RBNode root) {
        this.root = root; RBNode.makeNill();
    }

    public RBNode getRoot() {
        return root;
    }

    public void setRoot(RBNode root) {
        this.root = root;
    }

    public RBNode search(int x, RBNode r){
        while(r != RBNode.nill){
            if(r.getValue() == x) return r;
            if(r.getValue() < x) {r = r.getRight();}
            else r = r.getLeft();
        }
        return RBNode.nill;
    }
    public void leftRotate(RBNode x){
        RBNode y = x.getRight();
        x.setRight(y.getLeft());
        if(y.getLeft() != RBNode.nill) y.getLeft().setFather(x);
        y.setFather(x.getFather());
        if(x.getFather() == RBNode.nill) this.root = y;
        else{
            if(x == x.getFather().getLeft()) x.getFather().setLeft(y);
            else x.getFather().setRight(y);
        }
        y.setLeft(x);
        x.setFather(y);
    }
    public void rightRotate(RBNode x){
        RBNode y = x.getLeft();
        x.setLeft(y.getRight());
        if(y.getRight() != RBNode.nill) y.getRight().setFather(x);
        y.setFather(x.getFather());
        if(x.getFather() == RBNode.nill) this.root = y;
        else{
            if(x == x.getFather().getRight()) x.getFather().setRight(y);
            else x.getFather().setLeft(y);
        }
        y.setRight(x);
        x.setFather(y);
    }

    public RBNode minimum(RBNode x){
        if (x == RBNode.nill) return RBNode.nill;
        while(x.getLeft() != RBNode.nill)
            x = x.getLeft();
        return x;
    }

    public RBNode successor(RBNode n){
        RBNode x = n;
        if (x == RBNode.nill) return RBNode.nill;
        if(x.getRight() != RBNode.nill) return minimum(x.getRight());
        RBNode p = x.getFather();
        while(p != RBNode.nill && x == p.getRight()){
            x = p;
            p = p.getFather();
        }
        return p;
    }
    public int height(RBNode x){
        if(x == RBNode.nill) return 0;
        return 1 + Math.max(height(x.getLeft()), height(x.getRight()));
    }
    public void insert(int x, int player){
        RBNode n = new RBNode(RBNode.nill, RBNode.nill, RBNode.nill, false, x, player);
        RBNode prep = RBNode.nill;
        RBNode p = root;
        while (p != RBNode.nill){
            prep = p;
            if(x < p.getValue())
                p = p.getLeft();
            else {
                if(x > p.getValue())
                    p = p.getRight();
                else return;
            }
        }
        n.setFather(prep);
        if(prep == RBNode.nill) this.root = n;
        else {
            if(x < prep.getValue())
                prep.setLeft(n);
            else prep.setRight(n);
        }
        Insertfixup(n);
    }

    public void Insertfixup(RBNode x) {

        x.setBlack(false);
        while (x != root && !x.getFather().isBlack()){
            if(x.getFather().getFather() == RBNode.nill) break;
            if(x.getFather() == x.getFather().getFather().getLeft()){
                RBNode y = x.getFather().getFather().getRight();
                if(!y.isBlack()){
                    x.getFather().setBlack(true);
                    y.setBlack(true);
                    x.getFather().getFather().setBlack(false);
                    x = x.getFather().getFather();
                }
                else{
                    if(x == x.getFather().getRight()){
                        x = x.getFather();
                        this.leftRotate(x);
                    }
                    x.getFather().setBlack(true);
                    x.getFather().getFather().setBlack(false);
                    this.rightRotate(x.getFather().getFather());
                }
            }
            else{
                RBNode y = x.getFather().getFather().getLeft();
                if(!y.isBlack()){
                    x.getFather().setBlack(true);
                    y.setBlack(true);
                    x.getFather().getFather().setBlack(false);
                    x = x.getFather().getFather();
                }
                else{
                    if(x == x.getFather().getLeft()){
                        x = x.getFather();
                        this.rightRotate(x);
                    }
                    x.getFather().setBlack(true);
                    x.getFather().getFather().setBlack(false);
                    this.leftRotate(x.getFather().getFather());
                }
            }
        }
        root.setBlack(true);
    }

    public RBNode delete(RBNode z){
        RBNode y, x;
        if(z == RBNode.nill) return RBNode.nill;
        if(z.getLeft() == RBNode.nill || z.getRight() == RBNode.nill) y = z;
        else y = this.successor(z);

        if(y.getLeft() != RBNode.nill) x = y.getLeft();
        else x = y.getRight();

        x.setFather(y.getFather());

        if (y.getFather() == RBNode.nill) root = x;
        else {
            if(y == y.getFather().getLeft()) y.getFather().setLeft(x);
            else y.getFather().setRight(x);
        }

        if (y != z) {
            z.setValue(y.getValue());
            z.setPlayer(y.getPlayer());
        }
        if (y.isBlack()) fixDeleteColor(x);
        return y;
    }

    public void fixDeleteColor(RBNode x){
        RBNode w;
        while ( x != this.root && x.isBlack()){

            if(x == x.getFather().getLeft()){
                w = x.getFather().getRight();
                if(!w.isBlack()){
                    w.setBlack(true);
                    x.getFather().setBlack(false);
                    leftRotate(x.getFather());
                    w = x.getFather().getRight();
                }
                if(w.getLeft().isBlack() && w.getRight().isBlack()){
                    w.setBlack(false);
                    x = x.getFather();
                }
                else {
                    if(w.getRight().isBlack()){
                        w.getLeft().setBlack(true);
                        w.setBlack(false);
                        rightRotate(w);
                        w = x.getFather().getRight();
                    }
                    w.setBlack(x.getFather().isBlack());
                    x.getFather().setBlack(true);
                    w.getRight().setBlack(true);
                    leftRotate(x.getFather());
                    x = this.root;
                }
            }

            else {
                w = x.getFather().getLeft();
                if(!w.isBlack()){
                    w.setBlack(true);
                    x.getFather().setBlack(false);
                    rightRotate(x.getFather());
                    w = x.getFather().getLeft();
                }
                if(w.getRight().isBlack() && w.getLeft().isBlack()){
                    w.setBlack(false);
                    x = x.getFather();
                }
                else {
                    if(w.getLeft().isBlack()){
                        w.getRight().setBlack(true);
                        w.setBlack(false);
                        leftRotate(w);
                        w = x.getFather().getLeft();
                    }
                    w.setBlack(x.getFather().isBlack());
                    x.getFather().setBlack(true);
                    w.getLeft().setBlack(true);
                    rightRotate(x.getFather());
                    x = this.root;
                }
            }
        }
        x.setBlack(true);
    }
    public boolean valid(int player, int x, int j, int t, String s1, String s2){

        if(height(root) > 6) return (j > s1.length()) && (t > s2.length());

        boolean ended = true;
        for (int r = 1; r<33; r++){
            if(search(r, root) == RBNode.nill && search(r+32, root) == RBNode.nill) {ended = false; break;}
        }
        if(ended) return (j > s1.length()) && (t > s2.length());

        if ((j > s1.length()) && (t > s2.length())){
            for (int r = 1; r<33; r++){
                if(search(r, root) == RBNode.nill && search(r+32, root) == RBNode.nill) return false;
            }
            return true;
        }
        if(x > 32 || x < 1) return false;

        if(search(x + 32, root) != RBNode.nill) return false;

        RBNode n;
        n = search(x, root);
        if(n == RBNode.nill) return true;
        return n.getPlayer() == player;
    }
}



