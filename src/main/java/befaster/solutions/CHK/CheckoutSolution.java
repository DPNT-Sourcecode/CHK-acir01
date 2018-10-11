package befaster.solutions.CHK;

import java.util.HashMap;

public class CheckoutSolution {
    //offers table
    private HashMap<String, Skus> priceOffersTable;

    public CheckoutSolution() {
        priceOffersTable = new HashMap<String, Skus>();
        Skus A = new Skus("A", 50, true, 3, 130);
        Skus B = new Skus("B", 30, true, 2, 45);
        Skus C = new Skus("C", 20, false, null, null);
        Skus D = new Skus("D", 15, false, null, null);

        priceOffersTable.put(A.getItem(), A);
        priceOffersTable.put(B.getItem(), B);
        priceOffersTable.put(C.getItem(), C);
        priceOffersTable.put(D.getItem(), D);
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
        //set amount for each Skus

        //calculate total price of each item
        int totalPrice = 0;
        for (Skus item : priceOffersTable.values()) {
            totalPrice += item.getTotalPrice();
        }


        String item = skus.substring(skus.length() - 1);
        String amount_s = skus.substring(0, skus.length() - 1);

        Integer amount;
        if (amount_s.isEmpty())
            amount = 1;
        else {
            try {
                amount = Integer.parseInt(amount_s);
            } catch (NumberFormatException ex) {
                return -1;
            }
        }
        //amount is negative
        if (amount < 1)
            return -1;

        Skus toCalculate = priceOffersTable.get(item);
        //no items exist
        if (toCalculate == null)
            return -1;

        toCalculate.setAmount(amount);
        return toCalculate.getTotalPrice();
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

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getItem() {
            return item;
        }

        public Skus(String item, int price, boolean isSpecial, Integer specialAmount, Integer specialPrice) {
            this.specialAmount = specialAmount;
            this.item = item;
            this.price = price;
            this.isSpecial = isSpecial;
            this.specialPrice = specialPrice;
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
}
