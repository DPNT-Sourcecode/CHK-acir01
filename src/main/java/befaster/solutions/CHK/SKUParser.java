package befaster.solutions.CHK;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Stream;

public class SKUParser {
    private HashMap<String, SKU> units;
    private String filePath;

    public SKUParser(String filePath) {
        this.filePath = filePath;
        this.units = new HashMap<>();
    }

    public HashMap<String, SKU> parse() throws IOException {

        //read line of file

        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream.forEach(line -> parseLine(line));
        }
        return units;
    }

    public void parseLine(String line) {
        //split on columns
        //remove first and last '|'
        String parsed = line.substring(1, line.length() - 1);
        String[] columns = parsed.split("|");
        //get item name
        String itemName = columns[0].trim();
        //get item price
        String itemPriceStr = columns[1].trim();
        Integer itemPrice = Integer.parseInt(itemPriceStr);
        //get special offers
        getSpecialOffers(columns[2]);

    }

    private void getSpecialOffers(String offersString) {
        //split offers
        String[] offers = offersString.trim().split(",");
        //foreach offer


        for(String offer : offers){
            //offer type
            //if reducer

            //add reducer
            //else
            //add special
        }
    }

    private enum OfferType{
        Reducer,
        Special
    }
}
