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
    public void empty_input_check(){
        Integer price = checkoutSolution.checkout("");
        assertThat(price, equalTo(-1));
    }

    @Test
    public void negative_amount(){
        Integer price = checkoutSolution.checkout("-20B");
        assertThat(price, equalTo(-1));
    }

    @Test
    public void incorrect_input(){
        Integer price = checkoutSolution.checkout("FA1rf1f2");
        assertThat(price, equalTo(-1));
    }

    @Test
    public void incorrect_amount(){
        Integer price = checkoutSolution.checkout("fasA");
        assertThat(price, equalTo(-1));
    }

    @Test
    public void no_specials_check(){
        Integer price = checkoutSolution.checkout("2C");
        assertThat(price, equalTo(40));
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

        Integer single_item = checkoutSolution.checkout("A");
        assertThat(single_item, equalTo(50));
    }
}
