package befaster.solutions.CHK;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CheckoutSolutionTest {

    private CheckoutSolution checkoutSolution;

    @Before
    public void setUp(){
        checkoutSolution = new CheckoutSolution();
    }

    @Test
    public void get_price_a(){
        Integer price = checkoutSolution.checkout("2A");
        assertThat(price, equalTo(100));

        Integer price_special = checkoutSolution.checkout("3A");
        assertThat(price_special, equalTo(130));

        Integer price_over_special = checkoutSolution.checkout("5A");
        //2 by default price and 3 for special
        assertThat(price_over_special, equalTo(230));
    }
}
