package com.example.shoppinglist.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();


    static {
        // Add some sample items
        //Doesnt add anything anymore, now takes from the database

    }

    public static void addItem(DummyItem item) {
        ITEMS.add(item);
        //ITEM_MAP.put(item.id, item);
    }

    public static void deleteItem(DummyItem item) {
        ITEM_MAP.remove(item.id);
        ITEMS.remove(item);
    }



    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String content;
        public final String amount;
        public final String id;

        public DummyItem(String content, String amount) {
            this.content = content;
            this.amount = amount;
            this.id = DummyContent.ITEMS.size() + 1 + "";
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
