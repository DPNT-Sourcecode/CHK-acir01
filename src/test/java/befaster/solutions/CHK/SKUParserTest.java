package befaster.solutions.CHK;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class SKUParserTest {

    private SKUParser skuParser;

    @Before
    public void setUp() {
        skuParser = new SKUParser("NoMatterNow");
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

    }
}
