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
        Item firstItem = app.items[0];
        assertEquals("Item name", firstItem.name);
        assertEquals(4, firstItem.sellIn);
        assertEquals(3, firstItem.quality);
    }

    @Test
    void qualityAndSellIsDecreasedByOneEveryday() {
        Item updated = updateToNextDay(new Item("Item name", 4, 3));
        assertEquals(3, updated.sellIn);
        assertEquals(2, updated.quality);

        updated = updateToNextDay(updated);
        assertEquals(2, updated.sellIn);
        assertEquals(1, updated.quality);
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

    @Test
    void onceTheSellByDateHasPassedQualityDegradesTwiceAsFast() {
        Item updated = updateToNextDay(new Item("Item name", -3, 10));
        assertEquals(8, updated.quality);
    }

    @Test
    void agedBrieIncreasesInQualityTheOlderItGets() {
        Item updated = updateToNextDay(new Item("Aged Brie", 5, 10));
        assertEquals(11, updated.quality);
    }

    @Test
    void qualityOfAnItemIsNeverMoreThan50() {
        Item updated = updateToNextDay(new Item("Aged Brie", 5, 50));
        assertEquals(50, updated.quality);
    }

    @Test
    void sulfurasNeverDecreasesInQuality() {
        Item updated = updateToNextDay(new Item("Sulfuras, Hand of Ragnaros", 2, 40));
        assertEquals(40, updated.quality);
    }

    @Test
    void sulfurasNeverHasToBeSold() {
        Item updated = updateToNextDay(new Item("Sulfuras, Hand of Ragnaros", 2, 40));
        assertEquals(2, updated.sellIn);
    }

    @Test
    void backstagePassesQualityIncreaseBy1WhenThereAreMoreThen10Days() {
        Item updated = updateToNextDay(new Item("Backstage passes to a TAFKAL80ETC concert", 11, 20));
        assertEquals(10, updated.sellIn);
        assertEquals(21, updated.quality);
    }

    @Test
    void backstagePassesQualityIncreasesBy2WhenThereAre10DaysOrLessUnto6Days() {
        Item backstagePass = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20);

        Item updated = updateToNextDay(backstagePass);
        assertEquals(9, updated.sellIn);
        assertEquals(20 + 2, updated.quality);

        updated = updateToNextDay(updated);
        assertEquals(20 + 2 + 2, updated.quality);

        updated = updateToNextDay(updated);
        updated = updateToNextDay(updated);
        updated = updateToNextDay(updated);
        assertEquals(5, updated.sellIn);
        assertEquals(20 + 2 + 2 + 2 + 2 + 2, updated.quality);
    }

    @Test
    void backstagePassesQualityIncreasesBy3WhenThereAre5DaysOrLessUntoTheConcertDate() {
        Item backstagePass = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 30);

        Item updated = updateToNextDay(backstagePass);
        assertEquals(4, updated.sellIn);
        assertEquals(30 + 3, updated.quality);

        updated = updateToNextDay(updated);
        assertEquals(30 + 3 + 3, updated.quality);

        updated = updateToNextDay(updated);
        updated = updateToNextDay(updated);
        updated = updateToNextDay(updated);
        assertEquals(0, updated.sellIn);
        assertEquals(30 + 3 + 3 + 3 + 3 + 3, updated.quality);
    }

    @Test
    void backstagePassesQualityDropsTo0AfterTheConcert() {
        Item updated = updateToNextDay(new Item("Backstage passes to a TAFKAL80ETC concert", 0, 45));
        assertEquals(-1, updated.sellIn);
        assertEquals(0, updated.quality);
    }

    /*
     * Missing:
     * - "Backstage passes" different from "TAFKAL80ETC" concert should be supported
     * - quality limits: item initialized with 55 quality should be updated to 50 instead of decreased to 54
     * - quality limits: what's happened with negative quality?
     * - also merely "Sulfuras" should be recognized as legendary item ?
     */

    private static Item updateToNextDay(Item item) {
        GildedRose app = new GildedRose(new Item[] {item});
        app.updateQuality();
        return app.items[0];
    }
}
