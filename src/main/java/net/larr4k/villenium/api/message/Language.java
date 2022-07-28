package net.larr4k.villenium.api.message;

import net.larr4k.villenium.utility.type.Type;

public enum Language {
    RUSSIAN {
        @Override
        public int getPlural(int value) {
            int tenth = value % 10;
            int hundredth = value % 100;
            if (hundredth > 10 && hundredth < 20) {
                return 3;
            }
            if (tenth == 1) {
                return 1;
            }
            if (tenth > 1 && tenth < 5) {
                return 2;
            }
            return 3;
        }
    },
    ENGLISH {
        @Override
        public int getPlural(int value) {
            return value == 1 ? 1 : 2;
        }
    };

    public final static Language[] VALUES = values();

    public abstract int getPlural(int value);

    public String getLocalization(Enum messageKey) {
        return Type.getMESSAGES_API().get(messageKey).toString();
    }

    public String getLocalization(Enum messageKey, Object... args) {
        return Type.getMESSAGES_API().get(messageKey, args).toString();
    }

}
