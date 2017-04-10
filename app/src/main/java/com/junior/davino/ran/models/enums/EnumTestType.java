package com.junior.davino.ran.models.enums;

import org.parceler.Parcel;

/**
 * Created by davin on 07/03/2017.
 */

@Parcel
public enum EnumTestType {
    COLORS,
    DIGITS,
    LETTERS,
    OBJECTS;

    @Override
    public String toString() {
        switch(this) {
            case COLORS: return "Cores";
            case DIGITS: return "Dígitos";
            case LETTERS: return "Letras";
            case OBJECTS: return "Objetos";
            default: throw new IllegalArgumentException();
        }
    }
}
