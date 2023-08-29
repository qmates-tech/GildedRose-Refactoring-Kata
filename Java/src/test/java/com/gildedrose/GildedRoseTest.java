package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    void initAppWithAnEmptyItemsArray() {
        GildedRose app = new GildedRose(new Item[] {});
        assertEquals(0, app.items.length);

        app.updateQuality();
        assertEquals(0, app.items.length);
    }

    @Test
    void initAppWithASingleItem() {
        Item[] items = new Item[]{new Item("Item name", 4, 3)};
        GildedRose app = new GildedRose(items);

        assertEquals(1, app.items.length);
        Item appItem = app.items[0];
        assertEquals("Item name", appItem.name);
        assertEquals(3, appItem.quality);
        assertEquals(4, appItem.sellIn);
    }

    @Test
    void qualityAndSellInDecreaseByOneEveryday() {
        Item updated = updateToNextDay(new Item("Item name", 4, 3));
        assertEquals(2, updated.quality);
        assertEquals(3, updated.sellIn);

        updated = updateToNextDay(updated);
        assertEquals(1, updated.quality);
        assertEquals(2, updated.sellIn);
    }

    @Test
    void qualityOfAnItemIsNeverNegative() {
        Item updated = updateToNextDay(new Item("Item name", 0, 0));
        assertEquals(0, updated.quality);
    }

    @Test
    void sellInValueCanBeNegative() {
        Item updated = updateToNextDay(new Item("Item name", 0, 0));
        assertEquals(-1, updated.sellIn);
    }

    private static Item updateToNextDay(Item item) {
        GildedRose app = new GildedRose(new Item[] {item});
        app.updateQuality();
        return app.items[0];
    }
}
