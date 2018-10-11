package befaster.solutions.CHK;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Skus {
    private int amount;

    // item
    private String item;

    private int price;

    private SpecialReducerItem specialReducer;

    private Map<Integer, Integer> specialOffersMap;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getItemName() {
        return item;
    }

    public boolean isReducerOf(String targetName) {
        if (specialReducer == null)
            return false;

        return specialReducer.getReduceTarget().equals(targetName);
    }

    public SpecialReducerItem getSpecialReducer() {
        return specialReducer;
    }

    public Skus(String item, int price, SpecialReducerItem specialReducer, Map<Integer, Integer> specialOffersMap) {
        this.item = item;
        this.price = price;
        this.isSpecial = isSpecial;
        this.specialReducer = specialReducer;
        this.specialOffersMap = specialOffersMap;
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        //is have special price
        if (specialOffersMap != null) {
            int leftAmount = amount;
            Map<Integer, Integer> orderedSpecials = specialOffersMap.entrySet().
                    stream().
                    sorted(Map.Entry.comparingByKey(Comparator
                            .reverseOrder())).collect(Collectors
                    .toMap(Map.Entry::getKey,
                            Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));

            //key - bundle size. value - bundle price
            for (Map.Entry<Integer, Integer> entry : orderedSpecials.entrySet()) {
                int bundleSize = entry.getKey();
                if (leftAmount < bundleSize)
                    continue;
                totalPrice += (leftAmount / bundleSize) * entry.getValue();
                leftAmount = (leftAmount % bundleSize);
            }
            //last items not in available bundles goes by default price
            totalPrice += leftAmount * price;
            return totalPrice;
        }
        //else return amount by price
        return amount * price;
    }
}
