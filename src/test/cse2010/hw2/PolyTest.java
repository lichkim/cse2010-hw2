package cse2010.hw2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PolyTest {

    static Stream<Arguments> should_generate_polynomials_with_single_term() {
        return Stream.of(
                Arguments.of(1, 0, "1x^0"),
                Arguments.of(2, 1, "2x^1"),
                Arguments.of(3, 2, "3x^2"));
    }

    @ParameterizedTest
    @MethodSource
    void should_generate_polynomials_with_single_term(int coef, int exponent, String expected) {

        Polynomial poly = new Poly(1);
        poly.addTerm(coef, exponent);

        assertEquals(expected, poly.toString());
        assertEquals(exponent, poly.degree());
        assertEquals(1, poly.getTermCount());
    }

    @Test
    void should_generate_polynomials_by_adding_terms_in_ascending_order() {

        Polynomial poly = new Poly(3);
        poly.addTerm(1, 0);
         poly.addTerm(2, 1);
         poly.addTerm(3, 2);

         assertEquals("3x^2 + 2x^1 + 1x^0", poly.toString());
         assertEquals(2, poly.degree());
         assertEquals(3, poly.getTermCount());
    }

    @Test
    void should_generate_polynomials_by_adding_terms_in_descending_order() {

        Polynomial poly = new Poly(3);
        poly.addTerm(3, 2);
        poly.addTerm(2, 1);
        poly.addTerm(1, 0);

        assertEquals("3x^2 + 2x^1 + 1x^0", poly.toString());
        assertEquals(2, poly.degree());
        assertEquals(3, poly.getTermCount());
    }

    @Test
    void should_generate_polynomials_by_adding_terms_in_random_order() {

        Polynomial poly = new Poly(3);
        poly.addTerm(2, 1);
        poly.addTerm(3, 2);
        poly.addTerm(1, 0);

        assertEquals("3x^2 + 2x^1 + 1x^0", poly.toString());
        assertEquals(2, poly.degree());
        assertEquals(3, poly.getTermCount());
    }

    @Test
    void should_create_polynomials_with_vararg_terms() {

        Polynomial poly = new Poly(new Term(2, 1), new Term(3, 2), new Term(1, 0));

        assertEquals("3x^2 + 2x^1 + 1x^0", poly.toString());
        assertEquals(2, poly.degree());
        assertEquals(3, poly.getTermCount());
    }

    @Test
    void should_generate_identical_polynomial_when_added_by_unit_polynomial() {

        Polynomial poly = new Poly(3);
        poly.addTerm(3, 2);
        poly.addTerm(2, 1);
        poly.addTerm(1, 0);

        Poly unitPoly = new Poly(0);

        assertEquals("3x^2 + 2x^1 + 1x^0", poly.add(unitPoly).toString());
        assertEquals(2, poly.degree());
        assertEquals(3, poly.getTermCount());
    }

    @Test
    void should_add_two_polynomials() {

        Polynomial poly1 = new Poly(4);
        poly1.addTerm(1, 3);
        poly1.addTerm(2, 2);
        poly1.addTerm(3, 1);
        poly1.addTerm(4, 0);

        Polynomial poly2 = new Poly(new Term(2, 1), new Term(1, 0), new Term(4, 3));

        Polynomial poly3 = poly1.add(poly2);

        assertEquals("5x^3 + 2x^2 + 5x^1 + 5x^0", poly3.toString());
        assertEquals(3, poly3.degree());
        assertEquals(4, poly3.getTermCount());
    }

    @Test
    void should_multiply_two_polynomials() {
        Polynomial poly1 = new Poly(4);
        poly1.addTerm(1, 3);
        poly1.addTerm(2, 2);
        poly1.addTerm(3, 1);
        poly1.addTerm(4, 0);

        Polynomial poly2 = new Poly(4);
        poly2.addTerm(2, 1);
        poly2.addTerm(1, 0);
        poly2.addTerm(4, 3);

        Polynomial poly3 = poly1.mult(poly2);

        assertEquals("4x^6 + 8x^5 + 14x^4 + 21x^3 + 8x^2 + 11x^1 + 4x^0", poly3.toString());
        assertEquals(6, poly3.degree());
        assertEquals(7, poly3.getTermCount());
    }

    @Test
    void should_evaluate_polynomial_at_a_given_point() {

        Polynomial poly = new Poly(new Term(2, 1), new Term(3, 2), new Term(1, 0));

        assertEquals("3x^2 + 2x^1 + 1x^0", poly.toString());
        assertEquals(40.26, poly.eval(3.3), 0.01);
    }

}
