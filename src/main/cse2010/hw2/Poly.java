package cse2010.hw2;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * An array implementation of the polynomial API.
 */
public class Poly implements Polynomial {

    private Term[] terms; // array of terms, not sorted
    private int next = 0; // denotes next available slot & (term count)

    /**
     * Creates a new polynomial which can hold up to `maxCount` Term`s.
     *
     * @param maxCount number of terms
     */
    public Poly(int maxCount) {
        // you code goes here
        terms = new Term[maxCount];
        next = 0;
    }

    /**
     * Creates a new polynomial with given terms as parameters.
     *
     * @param terms array of terms to be added.
     */
    public Poly(Term... terms) {
        this(terms.length);

        for (Term term : terms) {
            addTerm(term.coef, term.exp);
        }
    }

    /*
     * 객체의 값만을 복사하는
     * Deep Copy
     */
    public Poly deepCopy(Poly poly) {
        return new Poly(Arrays.stream(poly.terms)
                        .filter(Objects::nonNull)
                        .toArray(Term[]::new));
    }

    /**
     * Returns the degree of this polynomial.
     *
     * @return degree of polynomial
     */
    @Override
    public int degree() {
        // your code goes here
        Arrays.sort(terms, 0, next, (a, b) -> b.exp - a.exp);
        return this.terms[0] == null ? 0 : this.terms[0].exp;
    }

    /**
     * Returns the number of terms in this polynomial.
     *
     * @return the number of terms
     */
    @Override
    public int getTermCount() {
        return next;
    }

    /**
     * Returns the coefficient of the term with the given exponent.
     *
     * @param exponent exponent
     * @return coefficient of the term with the given exponent
     */
    @Override
    public int getCoefficient(int exponent) {
        for (int i = 0; i < terms.length; i++) {
            if (terms[i] == null) break;
            if (terms[i].exp == exponent) return terms[i].coef;
        }
        return 0;   //해당하는 차수가 없으면 당연히 그 coef는 0이다.
    }

    /**
     * Insert a new term into a given polynomial.
     *
     * @param coef     coefficient
     * @param exponent exponent
     */
    @Override
    public void addTerm(int coef, int exponent) {

        // you code goes here
        try {
            terms[next] = new Term(coef, exponent);
            next++;
        } catch (Exception e) { //OutOfBoundaryError가 발생할 것으로 예상됨
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Adds the target polynomial object with the one given as a parameter.
     * As a result, the returned polynomial object will eventually represent
     * the sum of two polynomials (C = A.add(B). Note that A should not be
     * modified as a result of this operation.
     *
     * @param rhs a polynomial
     * @return a new polynomial (`rhs` + `this`)
     */
    @Override
    public Polynomial add(Polynomial rhs) {

        // you code goes here
        try{
            Poly prhs = (Poly) rhs;
            Poly addedPoly = new Poly(prhs.next + this.next);   //더하기의 항 수는 최대가 두 term들의 항들의 개수의 합이다.
            Poly copiedPrhs = deepCopy(prhs);
            Poly selfCopied = deepCopy(this);
            for (int i = 0; i < copiedPrhs.next; i++) {
                Term toaddTerm = copiedPrhs.terms[i];
                if (this.getCoefficient(toaddTerm.exp) != 0 && toaddTerm.coef + this.getCoefficient(toaddTerm.exp) != 0) {  //this 객체에 겹치는 exp가 존재하면서 그 coef의 합이 0이 아닌 경우
                    addedPoly.addTerm(toaddTerm.coef + selfCopied.getCoefficient(toaddTerm.exp), toaddTerm.exp);
                } else {
                    addedPoly.addTerm(toaddTerm.coef, toaddTerm.exp);
                }
                copiedPrhs.terms[i] = new Term(0, toaddTerm.exp);
            }
            
            for (Term toaddTerm : selfCopied.terms) {
                if (addedPoly.getCoefficient(toaddTerm.exp) == 0) {  //이미 계산된 것들은 제외하자. addedPoly에 없는 차수가 있다면 계산되지 않은 것이다.
                    addedPoly.addTerm(toaddTerm.coef, toaddTerm.exp);
                }
            }

            int totalLen = 0;
            for (Term checkTerm : addedPoly.terms) {
                if (checkTerm == null) break;
                totalLen++;
            }

            if (totalLen == 0) {    //0만 남은 경우
                return new Poly(new Term(0, 0));
            }

            Polynomial answer = new Poly(totalLen);
            for (Term ansTerm : addedPoly.terms) {
                if (ansTerm == null) break;
                answer.addTerm(ansTerm.coef, ansTerm.exp);
            }

            return answer;

        } catch (Exception e) {
            System.out.println("Your Polynomial Object is not an instance of Poly... Try again");
            e.printStackTrace();
            throw e;
        }
    }


    /**
     * Multiply the target polynomial object with the one given as a parameter.
     * As a result, the returned polynomial object will eventually represent
     * the product of two polynomials (C = A.mutiply(B). Note that A should not be
     * modified as a result of this operation.
     *
     * @param rhs a polynomial
     * @return a new polynomial (`rhs` * `this`)
     */
    @Override
    public Polynomial mult(Polynomial rhs) {

        // you code goes here
        try{
            Poly prhs = (Poly) rhs;
            Poly[] mulPoly = new Poly[prhs.next];   //각각의 원소가 copiedPrhs.terms[i] * selfCopied.terms인 배열
            Poly copiedPrhs = deepCopy(prhs);
            Poly selfCopied = deepCopy(this);

            for (int i = 0; i < copiedPrhs.getTermCount(); i++) {
                Poly multipliedPoly = new Poly(selfCopied.getTermCount());
                for (int j = 0; j < selfCopied.getTermCount(); j++) {
                    multipliedPoly.addTerm(copiedPrhs.terms[i].coef * selfCopied.terms[j].coef, copiedPrhs.terms[i].exp + selfCopied.terms[j].exp);
                }
                mulPoly[i] = multipliedPoly;
            }

            Poly ansPoly = new Poly(new Term(0, 0));
            for (int i = 0; i < mulPoly.length; i++) {
                Poly temp = (Poly)ansPoly.add(mulPoly[i]);
                ansPoly = temp;
            }

            return ansPoly;
            
        } catch (Exception e) {
            System.out.println("Your Polynomial Object is not an instance of Poly... Try again");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Evaluates the polynomial for a given value of x.
     *
     * @param x a value of x
     * @return the value of the polynomial for the given value of x
     */
    @Override
    public double eval(double x) {
        double answer = 0;

        for (Term ele : terms) {
            if (ele == null) break;
            answer += ele.coef * Math.pow(x, ele.exp);    
        }

        return answer;
    }

    /**
     * Returns a string representation of this polynomial.
     *
     * @return a string representation of this polynomial
     */
    @Override
    public String toString() {
        // Sample code ... you can freely modify this code if necessary
        Arrays.sort(terms, 0, next, (a, b) -> b.exp - a.exp);
        return Arrays.stream(terms)
                .filter(Objects::nonNull)
                .map(Term::toString)
                .collect(Collectors.joining(" + "));
    }

}
