package com.gildedrose.rules.factory;

import com.gildedrose.Item;
import com.gildedrose.rules.DoNothingRule;
import com.gildedrose.rules.Rule;
import com.gildedrose.rules.quality.IncreasesWithinBoundsRule;
import com.gildedrose.rules.quality.ZeroQualityRule;

import java.util.List;
import java.util.Map;

import static com.gildedrose.Constants.*;
import static java.util.Arrays.asList;
import static java.util.Map.entry;

public class QualityRulesFactory {

    private QualityRulesFactory() { throw new IllegalStateException("Utility class"); }

    public static Rule ruleFor(Item item) {
        return Map.ofEntries(
                entry(BACKSTAGE_CONCERT, backstageTicketRules(item)),
                entry(AGED_BRIE, agedBrieRules(item)),
                entry(CONJURED_MANA_CAKE, conjuredRules(item)))
                .getOrDefault(item.name, itemThatDecreasesInQuality(item));
    }

    private static Rule conjuredRules(Item item) {
        return findApplicableRule(item, asList(
                new IncreasesWithinBoundsRule(-4, i -> i.sellIn < 0),
                new IncreasesWithinBoundsRule(-2, i -> true)));
    }

    private static Rule backstageTicketRules(Item item) {
        return findApplicableRule(item, asList(
                new ZeroQualityRule(item.quality, i -> i.sellIn < 0),
                new IncreasesWithinBoundsRule(3, i -> i.sellIn < 5),
                new IncreasesWithinBoundsRule(2, i -> i.sellIn < 10),
                new IncreasesWithinBoundsRule(1, i -> true)));
    }

    private static Rule agedBrieRules(Item item) {
        return findApplicableRule(item, asList(
                new IncreasesWithinBoundsRule(2, i -> i.sellIn < 0),
                new IncreasesWithinBoundsRule(1, i -> true)));
    }

    private static Rule itemThatDecreasesInQuality(Item item) {
        return findApplicableRule(item, asList(
                new IncreasesWithinBoundsRule(-2, i -> i.sellIn < 0),
                new IncreasesWithinBoundsRule(-1, i -> true)));
    }

    private static Rule findApplicableRule(Item item, List<Rule> rules) {
        return rules.stream()
                .filter(r -> r.appliesFor(item))
                .findFirst().orElse(new DoNothingRule());
    }
}
