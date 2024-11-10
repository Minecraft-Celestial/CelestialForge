package com.xiaoyue.celestial_forge.content.modifier;

import com.xiaoyue.celestial_forge.content.data.UpgradeRecipe;

import java.util.TreeMap;

public record LevelingData(int baseCost, double expCost, TreeMap<Integer, UpgradeRecipe> upgrades) {
}
