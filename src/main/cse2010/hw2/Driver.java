package cse2010.hw2;

public class Driver {
    /**
     * You can modify this method to test your code.
     */
    public static void main(String... args) {
        Polynomial poly1 = new Poly(4);
        poly1.addTerm(1,3);
        poly1.addTerm(2,2);
        poly1.addTerm(3,1);
        poly1.addTerm(4,0);
        System.out.println("polynomial 1 = " + poly1);

        Poly poly2 = new Poly(4);
        poly2.addTerm(2,1);
        poly2.addTerm(1,0);
        poly2.addTerm(3,2);
        poly2.addTerm(4,3);
        System.out.println("polynomial 2 = " + poly2);

        Polynomial poly3 = poly1.add(poly2);
        System.out.println("polynomial add = " + poly3);

        Polynomial poly4 = poly1.mult(poly2);
        System.out.println("polynomial mult = " + poly4);

        System.out.println("Eval at 5 = " + poly4.eval(5));

        
    }
}

