package com;

import org.junit.Test;

import java.io.IOException;
import java.util.LinkedHashMap;

public class Mytest {
    @Test
    public void test() throws IOException {

        LinkedHashMap<String, Integer> hashMap = new LinkedHashMap<String, Integer>(

        ){
            private static final long serialVersionUID = 3801124242820219131L;

            @Override
            public boolean remove(Object key, Object value) {
                return super.remove(key, value);
            }
        };

    }
}
