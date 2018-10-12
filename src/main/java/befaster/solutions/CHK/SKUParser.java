package befaster.solutions.CHK;

import java.util.HashMap;

public class SKUParser {
    private HashMap<String, SKU> units;
    private String filePath;

    public SKUParser(String filePath) {
        this.filePath = filePath;
        this.units = new HashMap<>();
    }

    public HashMap<String, SKU> parse() {
        //read line of file

        //split on columns

        //get item name

        //get item price

        //get special offers


        return units;
    }

    private void getSpecialOffers(String offersString) {
        //offer type

        //if reducer
            //add reducer
        //else
            //add special
    }


}
