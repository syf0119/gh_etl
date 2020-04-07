package com.wondertek.bigdata.offline.dimension.key.base;

import com.wondertek.bigdata.offline.common.GlobalConstants;
import com.wondertek.bigdata.offline.dimension.key.KeyBaseDimension;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VersionDimensionKey extends KeyBaseDimension {
    private int id;
    private String name = "";

    public VersionDimensionKey(){}

    public VersionDimensionKey(String name) {
        super();
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VersionDimensionKey that = (VersionDimensionKey) o;

        if (id != that.id) {
            return false;
        }
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(KeyBaseDimension o) {
        if (o == this) {
            return 0;
        }
        VersionDimensionKey bd = (VersionDimensionKey) o;
        int result = Integer.compare(this.id, bd.getId());
        if (result == 0) {
            if (result == 0) {
                return this.name.compareTo(bd.getName());
            }
            return result;
        }
        return result;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(id);
        out.writeUTF(name);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.id = in.readInt();
        this.name = in.readUTF();
    }

    public static List<VersionDimensionKey> buildList(String name) {
        List<VersionDimensionKey> result = new ArrayList<>();
        result.add(new VersionDimensionKey(name));
        result.add(new VersionDimensionKey(GlobalConstants.VALUE_OF_ALL));
        return result;
    }
}
