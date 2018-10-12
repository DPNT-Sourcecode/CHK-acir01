package befaster.solutions.CHK;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.*;
import java.util.stream.Collectors;

public class CheckoutSolution {
    //offers table
    private HashMap<String, SKU> priceOffersTable;

    public CheckoutSolution() {
    }

    public Integer checkout(String skus) {

        priceOffersTable = new HashMap<String, SKU>();
        try {
            priceOffersTable =
                    new SKUParser("G:\\Repos\\accelerate-challenge\\" +
                            "tdl-runner-java\\src\\main\\resources\\skus").parse();
        } catch (IOException e) {
            return -1;
        }

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
        int totalPrice = priceOffersTable.values().stream().
                filter(f -> !f.isInGroupDiscount())
                .map(d -> d.getTotalPrice()).
                        reduce(0, (n, o) -> n + o);
        totalPrice += calculateGroupDiscountItemsPrice();
        return totalPrice;
    }

    private int calculateGroupDiscountItemsPrice() {
        //get group price
        //order group price SKU in reverse order
        List<SKU> sorted = priceOffersTable.values().stream().
                filter(f -> f.isInGroupDiscount()).
                sorted(Comparator.comparing(SKU::getPrice).reversed()).
                collect(Collectors.toList());
        //get items for group
        if (sorted.isEmpty())
            return 0;

        SKU first = sorted.get(0);
        GroupDiscount gd = first.getGroupDiscount();

        Integer price = gd.getGroupPrice();
        Integer triggerAmount = gd.getTriggerAmount();

        //take pairs of the most expensive and match them as discount
        int totalPrice = 0;
        List<SKU> leftSku = new ArrayList<>();
        for (SKU sku : sorted) {
            for (int i = 0; i < sku.getAmount(); i++) {
                leftSku.add(sku);
                if (leftSku.size() == triggerAmount) {
                    totalPrice += price;
                    leftSku.em;
                }
            }
        }

        //take least priced not grouped sku and append to total price
        for (int i = 0; i < skuLeft; i++) {
            totalPrice += sorted.get(sorted.size() - 1 - i).getPrice();
        }
        //fill by 3 each group till the end of group items
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
