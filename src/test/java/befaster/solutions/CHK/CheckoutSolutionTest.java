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
        assertThat(price, equalTo(0));
    }

    @Test
    public void incorrect_input(){
        Integer price = checkoutSolution.checkout("FA1rf1f2");
        assertThat(price, equalTo(-1));
    }

    @Test
    public void different_items(){
        Integer price = checkoutSolution.checkout("DDAA");
        assertThat(price, equalTo(130));

        Integer price_with_special = checkoutSolution.checkout("DDAAA");
        assertThat(price_with_special, equalTo(160));

        Integer price_all_four = checkoutSolution.checkout("DBAC");
        assertThat(price_all_four, equalTo(115));

        Integer all_specials = checkoutSolution.checkout("ABABA");
        assertThat(all_specials, equalTo(175));
    }

    @Test
    public void test_reducer(){
        Integer price = checkoutSolution.checkout("BEE");
        assertThat(price, equalTo(80));

        Integer price_with_one_b = checkoutSolution.checkout("BBEE");
        assertThat(price_with_one_b, equalTo(110));

        Integer only_ee = checkoutSolution.checkout("EE");
        assertThat(only_ee, equalTo(80));

        Integer no_reduction = checkoutSolution.checkout("BE");
        assertThat(no_reduction, equalTo(70));
    }

    @Test
    public void no_specials_check(){
        Integer price = checkoutSolution.checkout("CC");
        assertThat(price, equalTo(40));
    }


    @Test
    public void get_price_a(){

        Integer price = checkoutSolution.checkout("AA");
        assertThat(price, equalTo(100));

        Integer price_special = checkoutSolution.checkout("AAA");
        assertThat(price_special, equalTo(130));

        Integer price_over_special = checkoutSolution.checkout("AAAAA");
        assertThat(price_over_special, equalTo(200));

        Integer single_item = checkoutSolution.checkout("A");
        assertThat(single_item, equalTo(50));
    }
}
