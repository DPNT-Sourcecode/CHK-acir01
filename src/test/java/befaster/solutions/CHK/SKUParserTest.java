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
    }
}
