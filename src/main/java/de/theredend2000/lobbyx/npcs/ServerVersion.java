package de.theredend2000.lobbyx.npcs;

public enum ServerVersion {
    v1_19_R0,
    REFLECTED,
    UNKNOWN;

    @Override
    public String toString() {
        return name();
    }
}
