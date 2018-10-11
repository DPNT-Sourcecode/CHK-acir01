package befaster.solutions.CHK;

import java.security.InvalidKeyException;
import java.util.HashMap;

public class CheckoutSolution {
    //offers table
    private HashMap<String, Skus> priceOffersTable;

    public CheckoutSolution() {
        priceOffersTable = new HashMap<String, Skus>();
        Skus A = new Skus("A", 50, true, 3, 130, null);
        Skus B = new Skus("B", 30, true, 2, 45, null);
        Skus C = new Skus("C", 20, false, null, null, null);
        Skus D = new Skus("D", 15, false, null, null, null);
        Skus E = new Skus("E", 40, false, null, null,
                new SpecialReducerItem("B", 1, 2));
        priceOffersTable.put(A.getItemName(), A);
        priceOffersTable.put(B.getItemName(), B);
        priceOffersTable.put(C.getItemName(), C);
        priceOffersTable.put(D.getItemName(), D);
        priceOffersTable.put(E.getItemName(), E);
    }

    // input 2B, 3A, need to check out which kind of input
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

        //set amount for each Skus
        setAmountOfItems(itemsAmountMap);

        //reduce items amount if reduction is applicable
        applyFreeItemsSpecials();

        //calculate total price of each item
        int totalPrice = calculateTotalPrice();
        return totalPrice;
    }

    private int calculateTotalPrice() {
        int totalPrice = 0;
        for (Skus item : priceOffersTable.values()) {
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
        for (Skus item : priceOffersTable.values()) {
            Integer itemAmount = itemsAmountMap.get(item.getItemName());
            if (itemAmount == null)
                item.setAmount(0);
            else {
                item.setAmount(itemAmount);
            }
        }
    }

    private void applyFreeItemsSpecials() {
        for (Skus item : priceOffersTable.values()) {
            Skus withReducer = priceOffersTable.values().stream().
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

    private class Skus {

    }
}
