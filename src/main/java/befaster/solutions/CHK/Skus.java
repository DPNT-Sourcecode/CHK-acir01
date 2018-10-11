package befaster.solutions.CHK;

import java.util.Map;

public class Skus {
    private int amount;
    // special amount
    private Integer specialAmount;

    // item
    private String item;

    private int price;
    // is special
    private boolean isSpecial;

    // special price
    private Integer specialPrice;

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

    public Skus(String item, int price, boolean isSpecial, Integer specialAmount, Integer specialPrice,
                SpecialReducerItem specialReducer, Map<Integer, Integer> specialOffersMap) {
        this.specialAmount = specialAmount;
        this.item = item;
        this.price = price;
        this.isSpecial = isSpecial;
        this.specialPrice = specialPrice;
        this.specialReducer = specialReducer;
        this.specialOffersMap = specialOffersMap;
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        //is have special price
        if (isSpecial) {
            int leftAmount = 0;
            //key - bundle size. value - bundle price
            for (Map.Entry<Integer, Integer> entry : specialOffersMap.entrySet()) {
                totalPrice += (amount / entry.getKey()) * entry.getValue();
                leftAmount = (amount % entry.getKey());
            }
            //last items not in available bundles goes by default price
            totalPrice += leftAmount * price;

//            //calculate amount of special bundles
//            totalPrice = (amount / specialAmount) * specialPrice;
//            //add left item with default price
//            totalPrice += (amount % specialAmount) * price;
            return totalPrice;
        }
        //else return amount by price
        return amount * price;
    }
}
