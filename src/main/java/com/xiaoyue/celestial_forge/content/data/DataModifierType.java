package com.xiaoyue.celestial_forge.content.data;

import java.util.ArrayList;

public record DataModifierType(UpgradeRecipe start, ArrayList<DataModifierSet> pools) {
}