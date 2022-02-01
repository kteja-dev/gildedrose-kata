package com.gildedrose.rules.factory;

import com.gildedrose.Item;
import com.gildedrose.rules.DoNothingRule;
import com.gildedrose.rules.Rule;
import com.gildedrose.rules.stepin.AlwaysDecreasesRule;

import java.util.Map;

import static com.gildedrose.Constants.SULFURAS_HAND_OF_RAGNAROS;
import static java.util.Map.entry;

public class SellInRulesFactory {

    private SellInRulesFactory() { throw new IllegalStateException("Utility class"); }

    public static Rule ruleFor(Item item) {
        return Map.<String, Rule>ofEntries(
                entry(SULFURAS_HAND_OF_RAGNAROS, new DoNothingRule()))
                .getOrDefault(item.name, new AlwaysDecreasesRule());
    }
}
