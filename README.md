# CelestialForge

## File Structure

Path: `data/<namespace>/celestial_forge_config/modifier/<filename>.json`

### Config JSON file Structure

- `map`: Map of modifier type to data
    - Map key: ModifierType (`ALL`/`ARMOR`/`TOOL`/`WEAPON`/`RANGED`/`CURIO`)
    - Map value: Modifier type data
        - `start`: data of type `UpgradeRecipe`
        - `pools`: List of modifier pools
            - List element: ModifierPool
                - `gate`: struct
                    - `list`: List
                        - List element: data of type `UpgradeRecipe`
                - `entries`: Map of modifier ID to modifier
                    - Map key: ID of the modifier, usually `<namespace>:<name>`
                    - Map value: Modifier
                        - `weight`: integer representing relative likelyhood to roll this modifier
                        - `modifiers`: List of modifier attribute entries
                            - List element: ModifierEntry
                                - `attr`: ID of the attribute to modify
                                - `op`: Operation of the modifier (`ADDITION`/`MULTIPLY_BASE`/`MULTIPLY_TOTAL`)
                                - `base`: Base value of the modifier
                                - `perLevel`: Per-level increment of the modifier, as percentage of base value

### UpgradeRecipe Structure

- `exp`: Integer representing levels to cost
- `list`: List if ingredients
    - List element: ingredient data format, such as `{"item":"minecraft:diamond"}`