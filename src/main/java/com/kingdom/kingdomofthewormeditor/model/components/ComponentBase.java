package com.kingdom.kingdomofthewormeditor.model.components;

public abstract class ComponentBase {
    public abstract ComponentType getType();

    @Override
    public String toString() {
        return String.format("%s [%s]", getType().name(), this.hashCode());
    }
}
