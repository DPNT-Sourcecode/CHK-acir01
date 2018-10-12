package befaster.solutions.CHK;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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
    }
}
