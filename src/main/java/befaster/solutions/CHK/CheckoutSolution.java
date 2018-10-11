package befaster.solutions.CHK;

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
        priceOffersTable.put(A.getItem(), A);
        priceOffersTable.put(B.getItem(), B);
        priceOffersTable.put(C.getItem(), C);
        priceOffersTable.put(D.getItem(), D);
        priceOffersTable.put(E.getItem(), E);
    }

    // input 2B, 3A, need to check out which kind of input
    public Integer checkout(String skus) {
        if (skus == null)
            return -1;

        if (skus.isEmpty())
            return 0;

        //match amount of letters
        char[] items = skus.toCharArray();

        //count each item and check for correct items
        HashMap<String, Integer> itemsAmountMap = new HashMap<>();
        for (char c : items) {
            //incorrect product in table
            String item_name = Character.toString(c);
            if (!priceOffersTable.containsKey(item_name)) {
                return -1;
            }
            itemsAmountMap.computeIfPresent(item_name, (k, v) -> v + 1);
            itemsAmountMap.computeIfAbsent(item_name, key -> 1);
        }
        //remove items if there special match
        for()
        //set amount for each Skus
        for (Skus item : priceOffersTable.values()) {
            Integer itemAmount = itemsAmountMap.get(item.getItem());
            if (itemAmount == null)
                item.setAmount(0);
            else
                item.setAmount(itemAmount);
        }
        //calculate total price of each item
        int totalPrice = 0;
        for (Skus item : priceOffersTable.values()) {
            totalPrice += item.getTotalPrice();
        }
        return totalPrice;
    }

    private class Skus {
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


        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getItem() {
            return item;
        }

        public SpecialReducerItem getSpecialReducer() {
            return specialReducer;
        }

        public Skus(String item, int price, boolean isSpecial, Integer specialAmount, Integer specialPrice,
                    SpecialReducerItem specialReducer) {
            this.specialAmount = specialAmount;
            this.item = item;
            this.price = price;
            this.isSpecial = isSpecial;
            this.specialPrice = specialPrice;
            this.specialReducer = specialReducer;
        }

        public int getTotalPrice() {
            int totalPrice = 0;
            //is have special price
            if (isSpecial) {
                //calculate amount of special bundles
                totalPrice = (amount / specialAmount) * specialPrice;
                //add left item with default price
                totalPrice += (amount % specialAmount) * price;
                return totalPrice;
            }
            //else return amount by price
            return amount * price;
        }
    }

    private class SpecialReducerItem {
        private String reduceTarger;
        private int reduceAmount;
        private int triggerAmount;

        public SpecialReducerItem(String reduceTarger, int reduceAmount, int triggerAmount) {
            this.reduceTarger = reduceTarger;
            this.reduceAmount = reduceAmount;
            this.triggerAmount = triggerAmount;
        }
    }
}
