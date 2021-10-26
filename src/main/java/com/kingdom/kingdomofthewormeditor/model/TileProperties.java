package com.kingdom.kingdomofthewormeditor.model;

import com.kingdom.kingdomofthewormeditor.model.components.ComponentBase;

import java.util.HashSet;
import java.util.Set;

public class TileProperties {
    private Set<ComponentBase> components;

    public Set<ComponentBase> getComponents() {
        if (components == null) {
            components = new HashSet<>();
        }
        return components;
    }
}
