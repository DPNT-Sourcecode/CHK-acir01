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
            stream.forEach(System.out::println);
        }
        return units;
    }

    public HashMap<String, SKU> parseLine() {


        //split on columns

        //get item name

        //get item price

        //get special offers


        return units;
    }

    private void getSpecialOffers(String offersString) {
        //split offers

        //foreach offer

        //offer type

        //if reducer
        //add reducer
        //else
        //add special
    }


}
