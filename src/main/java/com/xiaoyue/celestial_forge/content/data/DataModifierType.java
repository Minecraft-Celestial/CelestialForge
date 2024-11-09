package com.xiaoyue.celestial_forge.content.data;

import com.xiaoyue.celestial_forge.content.registry.UpgradeRecipe;

import java.util.ArrayList;

public record DataModifierType(UpgradeRecipe start, ArrayList<DataModifierSet> pools) {
}
