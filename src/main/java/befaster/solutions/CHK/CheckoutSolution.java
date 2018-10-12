package befaster.solutions.CHK;

import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.Map;

public class CheckoutSolution {
    //offers table
    private HashMap<String, SKU> priceOffersTable;

    public CheckoutSolution() {
        priceOffersTable = new HashMap<String, SKU>();

        Map<Integer, Integer> a_specials = new HashMap<>();
        a_specials.put(3, 130);
        a_specials.put(5, 200);
        SKU A = new SKU("A", 50, null, a_specials);
        priceOffersTable.put(A.getItemName(), A);

        Map<Integer, Integer> b_specials = new HashMap<>();
        b_specials.put(2, 45);

        SKU B = new SKU("B", 30, null, b_specials);
        SKU C = new SKU("C", 20, null, null);
        SKU D = new SKU("D", 15, null, null);
        SKU E = new SKU("E", 40, new SpecialReducerItem("B", 1, 2),
                null);
        SpecialReducerItem reducerF = new SpecialReducerItem("F", 1, 2);

        //treat reducer of f as special offer?
        Map<Integer, Integer> f_specials = new HashMap<>();
        f_specials.put(3, 20);

        SKU F = new SKU("F", 10, null, f_specials);

        priceOffersTable.put(B.getItemName(), B);
        priceOffersTable.put(C.getItemName(), C);
        priceOffersTable.put(D.getItemName(), D);
        priceOffersTable.put(E.getItemName(), E);
        priceOffersTable.put(F.getItemName(), F);
    }

    private void appendNewSkuItem(String itemName, int price, SpecialReducerItem specialReducer,
                                  Map<Integer, Integer> specialsMap){
        SKU newSku = new SKU(itemName, price, specialReducer, specialsMap);
        priceOffersTable.put(newSku.getItemName(), newSku);
    }

    public Integer checkout(String skus) {
        if (skus == null)
            return -1;

        if (skus.isEmpty())
            return 0;

        char[] items = skus.toCharArray();

        //count each item and check for non-correct items
        HashMap<String, Integer> itemsAmountMap;
        try {
            itemsAmountMap = getItemsAmountMap(items);
        } catch (InvalidKeyException e) {
            return -1;
        }

        //set amount for each SKU
        setAmountOfItems(itemsAmountMap);

        //reduce items amount if reduction is applicable
        applyFreeItemsSpecials();

        //calculate total price of each item
        int totalPrice = calculateTotalPrice();
        return totalPrice;
    }

    private int calculateTotalPrice() {
        int totalPrice = 0;
        for (SKU item : priceOffersTable.values()) {
            totalPrice += item.getTotalPrice();
        }
        return totalPrice;
    }

    private HashMap<String, Integer> getItemsAmountMap(char[] items) throws InvalidKeyException {
        HashMap<String, Integer> itemsAmountMap = new HashMap<>();
        for (char c : items) {
            //incorrect product in table
            String item_name = Character.toString(c);
            if (!priceOffersTable.containsKey(item_name)) {
                throw new InvalidKeyException("Invalid item");
            }
            itemsAmountMap.computeIfPresent(item_name, (k, v) -> v + 1);
            itemsAmountMap.computeIfAbsent(item_name, key -> 1);
        }
        return itemsAmountMap;
    }

    private void setAmountOfItems(HashMap<String, Integer> itemsAmountMap) {
        for (SKU item : priceOffersTable.values()) {
            Integer itemAmount = itemsAmountMap.get(item.getItemName());
            if (itemAmount == null)
                item.setAmount(0);
            else {
                item.setAmount(itemAmount);
            }
        }
    }

    private void applyFreeItemsSpecials() {
        for (SKU item : priceOffersTable.values()) {
            SKU withReducer = priceOffersTable.values().stream().
                    filter(x -> x.isReducerOf(item.getItemName())).findFirst().orElse(null);
            SpecialReducerItem reducer = withReducer == null ? null : withReducer.getSpecialReducer();
            int itemAmount = item.getAmount();
            if (reducer != null) {
                int amountToReduce = reducer.getReducedAmount(item.getItemName(), withReducer.getAmount());
                int realAmount = itemAmount - amountToReduce;
                if (realAmount < 0)
                    item.setAmount(0);
                else
                    item.setAmount(realAmount);
            }
        }
    }
}
