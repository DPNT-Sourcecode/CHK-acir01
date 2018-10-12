package befaster.solutions.CHK;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class SKUParserTest {

    private SKUParser skuParser;

    @Before
    public void setUp() {
        skuParser = new SKUParser("G:\\Repos\\accelerate-challenge\\tdl-runner-java\\src\\main\\resources\\skus");
    }

    @Test
    public void parseLineTest() {
        SKU result = skuParser.parseLine("| C    | 20    |                        |");
        assertThat(result.getItemName(), equalTo("C"));
        assertThat(result.getPrice(), equalTo(20));

        SKU with_one_special = skuParser.parseLine("| B    | 30    | 2B for 45              |");
        assertThat(with_one_special.getPrice(), equalTo(30));
        assertThat(with_one_special.getItemName(), equalTo("B"));
        assertThat(with_one_special.getSpecialOffersMap().containsKey(2), equalTo(true));
        assertThat(with_one_special.getSpecialOffersMap().containsValue(45), equalTo(true));

        SKU with_two_special = skuParser.parseLine("| B    | 30    | 2B for 45,5b for 200       |");
        assertThat(with_two_special.getPrice(), equalTo(30));
        assertThat(with_two_special.getItemName(), equalTo("B"));
        assertThat(with_two_special.getSpecialOffersMap().containsKey(2), equalTo(true));
        assertThat(with_two_special.getSpecialOffersMap().containsValue(45), equalTo(true));
        assertThat(with_two_special.getSpecialOffersMap().containsKey(5), equalTo(true));
        assertThat(with_two_special.getSpecialOffersMap().containsValue(200), equalTo(true));

        SKU with_reducer = skuParser.parseLine("| R   | 11  | 3R get one Q free  |");
        assertThat(with_reducer.getPrice(), equalTo(11));
        assertThat(with_reducer.getItemName(), equalTo("R"));
        assertThat(with_reducer.getSpecialReducer(), notNullValue());
        assertThat(with_reducer.getSpecialReducer().getReduceTarget(), equalTo("Q"));
        assertThat(with_reducer.getSpecialReducer().getTriggerAmount(), equalTo(3));

        SKU reducer_treated_as_special = skuParser.parseLine("| F    | 10    | 2F get one F free      |");
        assertThat(reducer_treated_as_special.getPrice(), equalTo(10));
        assertThat(reducer_treated_as_special.getItemName(), equalTo("F"));
        assertThat(reducer_treated_as_special.getSpecialOffersMap().containsKey(3), equalTo(true));
        assertThat(reducer_treated_as_special.getSpecialOffersMap().containsValue(20), equalTo(true));
        assertThat(reducer_treated_as_special.getSpecialReducer(), nullValue());
    }

    @Test
    public void parseFileTest() throws IOException {
        HashMap<String, SKU> result = skuParser.parse();
        assertThat(result.containsKey("V"), equalTo(true));
    }

    @Test
    public void parseGroupDiscount() {
        SKU result = skuParser.parseLine("| X    | 17    | buy any 3 of (S,T,X,Y,Z) for 45 |");
        assertThat(result.getItemName(), equalTo("X"));
        assertThat(result.getPrice(), equalTo(17));
        assertThat(result.getGroupDiscount(), notNullValue());
        assertThat(result.getGroupDiscount().getGroupPrice(), equalTo(45));
        assertThat(result.getGroupDiscount().getTriggerAmount(), equalTo(3));

    }
}
