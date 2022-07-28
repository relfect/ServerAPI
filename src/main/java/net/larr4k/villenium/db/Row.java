package net.larr4k.villenium.db;

import java.util.List;


public class Row {

    private final QueryResult parent;
    private final List<Object> values;

    public Row(QueryResult parent, List<Object> values) {
        this.parent = parent;
        this.values = values;
    }

    public int getInt(int index) {
        return getInt(index, 0);
    }

    public int getInt(int index, int fallback) {
        Object val = values.get(index - 1);
        if (val == null) {
            return fallback;
        }
        if (val instanceof Number) {
            return ((Number) val).intValue();
        }
        return (int) val;
    }

    public int getInt(String column) {
        return getInt(column, 0);
    }

    public int getInt(String column, int fallback) {
        return getInt(parent.getColumnIndex(column), fallback);
    }

    public long getLong(int index, long fallback) {
        Object val = values.get(index - 1);
        if (val == null) {
            return fallback;
        }
        if (val instanceof Number) {
            return ((Number) val).longValue();
        }
        return (long) val;
    }

    public long getLong(int index) {
        return getLong(index, 0L);
    }

    public long getLong(String column, long fallback) {
        return getLong(parent.getColumnIndex(column), fallback);
    }

    public long getLong(String column) {
        return getLong(column, 0L);
    }

    public float getFloat(int index, float fallback) {
        Object val = this.values.get(index - 1);
        if (val == null) {
            return fallback;
        }
        if (val instanceof Number) {
            return ((Number) val).floatValue();
        }
        return (float) val;
    }

    public float getFloat(int index) {
        return getFloat(index, 0F);
    }

    public float getFloat(String column, float fallback) {
        return getFloat(parent.getColumnIndex(column), fallback);
    }

    public float getFloat(String column) {
        return getFloat(column, 0F);
    }

    public double getDouble(int index, double fallback) {
        Object val = this.values.get(index - 1);
        if (val == null) {
            return fallback;
        }
        if (val instanceof Number) {
            return ((Number) val).doubleValue();
        }
        return (double) val;
    }

    public double getDouble(int index) {
        return getDouble(index, 0D);
    }

    public double getDouble(String column, double fallback) {
        return getDouble(parent.getColumnIndex(column), fallback);
    }

    public double getDouble(String column) {
        return getDouble(column, 0D);
    }

    public boolean getBoolean(int index, boolean fallback) {
        Object val = values.get(index - 1);
        if (val == null) {
            return fallback;
        }
        if (val instanceof Number) {
            return ((Number) val).intValue() != 0;
        }
        return (boolean) val;
    }

    public boolean getBoolean(int index) {
        return getBoolean(index, false);
    }

    public boolean getBoolean(String column, boolean fallback) {
        return getBoolean(parent.getColumnIndex(column), fallback);
    }

    public boolean getBoolean(String column) {
        return getBoolean(column, false);
    }

    public String getString(int index, String fallback) {
        Object val = this.values.get(index - 1);
        if (val == null) {
            return fallback;
        }
        if (val instanceof String) {
            return (String) val;
        }
        return val.toString();
    }

    public String getString(int index) {
        return getString(index, "null");
    }

    public String getString(String column, String fallback) {
        return getString(parent.getColumnIndex(column), fallback);
    }

    public String getString(String column) {
        return getString(column, "null");
    }

    public byte[] getBytes(int index) {
        return (byte[]) values.get(index - 1);
    }

    public byte[] getBytes(String column) {
        return getBytes(parent.getColumnIndex(column));
    }

    public Object getObject(int index) {
        return values.get(index - 1);
    }

    public Object getObject(String column) {
        return getObject(parent.getColumnIndex(column));
    }

}
