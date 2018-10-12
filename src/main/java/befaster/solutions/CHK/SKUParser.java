package befaster.solutions.CHK;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Stream;

public class SKUParser {
    private HashMap<String, SKU> units;
    private String filePath;
    private GroupDiscount groupDiscount;

    public SKUParser(String filePath) {
        this.filePath = filePath;
        this.units = new HashMap<>();
    }

    public HashMap<String, SKU> parse() throws IOException {

        //read line of file

        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream.forEach(line -> {
                SKU sku = parseLine(line);
                units.put(sku.getItemName(), sku);
            });
        }
        return units;
    }

    public SKU parseLine(String line) {
        //split on columns
        //remove first and last '|'
        String parsed = line.substring(1, line.length() - 1);
        String[] columns = parsed.split("\\|");
        //get item name
        String itemName = columns[0].trim();
        //get item price
        String itemPriceStr = columns[1].trim();
        Integer itemPrice = Integer.parseInt(itemPriceStr);
        //get special offers
        return buildSkuWithSpecialOffers(columns[2], itemName, itemPrice);
    }

    private SKU buildSkuWithSpecialOffers(String offersString, String itemName, int itemPrice) {


        //split offers
        String[] offers = offersString.trim().split(",");
        //foreach offer
        HashMap<Integer, Integer> specials = new HashMap();
        //for now can be only one
        SpecialReducerItem reducerItem = null;
        for (String offer : offers) {
            if (offer.isEmpty())
                continue;

            //offer type
            OfferType of = getOfferType(offer);

            //if reducer
            if (of == OfferType.Reducer) {
                String cleanData = offer.replace("get one", "").
                        replace("free", "").trim();
                String[] granular = cleanData.split("\\s+");
                String target = granular[1].trim();

                String trimTarget = granular[0].trim();
                String amountStr = trimTarget.substring(0, 1);
                String initiator = trimTarget.substring(1, 2);
                Integer amountTriger = Integer.parseInt(amountStr);
                //treat same free as special
                if (initiator.equals(target)) {
                    specials.put(amountTriger + 1, itemPrice * amountTriger);
                } else {
                    //TODO: reduce amount can be possibly changed in special offers column
                    reducerItem = new SpecialReducerItem(target, 1, amountTriger);
                }
            } else if (of == OfferType.GroupDiscount) {
                if(groupDiscount == null)
                    groupDiscount = new GroupDiscount();
            } else {
                String[] parts = offer.split("for");
                String bundleDef = parts[0].trim();
                Integer amount = Integer.parseInt(bundleDef.substring(0, bundleDef.length() - 1));
                Integer bundlePrice = Integer.parseInt(parts[1].trim());
                specials.put(amount, bundlePrice);
            }
        }
        SKU s = new SKU(itemName, itemPrice, reducerItem, specials);


        return s;
    }

    private OfferType getOfferType(String offer) {
        OfferType of = null;
        if (offer.startsWith("buy any"))
            return OfferType.GroupDiscount;
        if (offer.contains("for"))
            of = OfferType.Special;
        else
            of = OfferType.Reducer;
        return of;
    }

    private enum OfferType {
        Reducer,
        GroupDiscount, Special
    }
}
