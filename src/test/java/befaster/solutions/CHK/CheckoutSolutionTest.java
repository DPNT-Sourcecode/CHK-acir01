package befaster.solutions.CHK;

import org.junit.Before;
import org.junit.Test;

public class CheckoutSolutionTest {

    private CheckoutSolution checkoutSolution;

    @Before
    public void setUp(){
        checkoutSolution = new CheckoutSolution();
    }

    @Test
    public void get_price_a(){
        Integer price = checkoutSolution.checkout("2A");
    }
}
