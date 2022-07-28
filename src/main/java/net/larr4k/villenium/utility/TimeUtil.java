package net.larr4k.villenium.utility;

import com.google.common.base.Preconditions;
import net.larr4k.villenium.api.message.DefaultMessages;
import net.larr4k.villenium.api.message.Message;
import net.larr4k.villenium.api.message.MessagesAPI;
import net.larr4k.villenium.utility.type.Type;


import java.util.*;


public interface TimeUtil {

    default Message getDurationMessage(long from, long to) {
        MessagesAPI<?> api = Type.getMESSAGES_API();
        int delta = (int) ((to - from) / 1000);
        Preconditions.checkArgument(delta >= 0);

        if (delta == 0) {
            return api.getExactMessage("0");
        }

        LinkedHashMap<DefaultMessages, Integer> periods = new LinkedHashMap<>();

        periods.put(DefaultMessages.SECOND, delta % 60);
        delta /= 60;

        periods.put(DefaultMessages.MINUTE, delta % 60);
        delta /= 60;

        periods.put(DefaultMessages.HOUR, delta % 24);
        delta /= 24;

        periods.put(DefaultMessages.DAY, delta);

        List<DefaultMessages> revert = new ArrayList<>(periods.keySet());
        Collections.reverse(revert);

        Message result = null;
        for (DefaultMessages period : revert) {
            int value = periods.get(period);
            if (value > 0) {
                if (result == null) {
                    result = api.getPlural(period, value);
                } else {
                    result = api.get(DefaultMessages.SPACED_MESSAGE, result, api.getPlural(period, value));
                }
            }
        }

        return result;
    }

    default long parse(String input) {
        input = input.toLowerCase().replace(" ", "");
        Map<String, Long> periods = new HashMap<String, Long>(){{
            long val;
            put("s", val = 1L);
            put("m", val *= 60);
            put("h", val *= 60);
            put("d", val *= 24);
            put("mo", val * 30);
            put("y", val *= 365);
            put("year", val);
        }};
        long result = 0;
        long value = 0;
        StringBuilder period = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (c >= '0' && c <= '9') {
                if (period.length() > 0) {
                    Long length = periods.get(period.toString());
                    Preconditions.checkArgument(length != null, "Illegal period given: " + period.toString());
                    result += value * length;
                    value = 0;
                    period = new StringBuilder();
                }
                value = value * 10 + (c - '0');
            } else {
                period.append(c);
            }
        }
        if (period.length() > 0) {
            Long length = periods.get(period.toString());
            Preconditions.checkArgument(length != null, "Illegal period given: " + period.toString());
            result += value * length;
        }
        Preconditions.checkArgument(result >= 0, "Input overflow");
        return result;
    }

}
