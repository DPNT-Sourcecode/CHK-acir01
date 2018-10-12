package befaster.solutions.CHK;

import java.util.ArrayList;
import java.util.List;

public class GroupDiscount {

    public List<SKU> getGroupItems() {
        return groupItems;
    }

    public int getGroupPrice() {
        return groupPrice;
    }

    public void setGroupPrice(int groupPrice) {
        this.groupPrice = groupPrice;
    }

    public int getTriggerAmount() {
        return triggerAmount;
    }

    public void setTriggerAmount(int triggerAmount) {
        this.triggerAmount = triggerAmount;
    }

    private List<SKU> groupItems = new ArrayList<>();

    private int groupPrice;

    private int triggerAmount;

    public void addGroupItem(SKU s) {
        groupItems.add(s);
    }
}
