package com.xiaoyue.celestial_forge.content.data;

import com.xiaoyue.celestial_forge.CelestialForge;
import com.xiaoyue.celestial_forge.register.CFFlags;

public class ReinforceDataHolder {

    public static void rebuild() {
        CelestialForge.REINFORCE.getAll().forEach(attrData -> CFFlags.DATA_MAP.put(attrData.flag(), attrData));
    }
}
