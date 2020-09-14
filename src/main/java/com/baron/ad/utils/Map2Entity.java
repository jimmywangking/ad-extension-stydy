package com.baron.ad.utils;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/***
 @package com.baron.ad
 @author Baron
 @create 2020-09-14-7:27 PM
 */
public class Map2Entity {

    public static <T> T map2Entity(Map<String, Object> map, Class<T> targetClass) throws IllegalAccessException, InstantiationException{
        Class superClass;
        Field[] fields;
        T target = targetClass.newInstance();

        List<Field> targetFields = new LinkedList<>();

        superClass = targetClass;

        while (superClass != null && superClass != Object.class) {

            fields = superClass.getDeclaredFields();
            targetFields.addAll(Arrays.asList(fields));
            superClass = superClass.getSuperclass();
        }

        for (Field targetField: targetFields) {
            for (Map.Entry<String, Object> mapEntry: map.entrySet()){
                if (targetField.getName().equals(mapEntry.getKey())){

                    boolean targetFlag = targetField.isAccessible();

                    targetField.setAccessible(true);
                    targetField.set(target, mapEntry.getValue() instanceof BigInteger ? ((BigInteger) mapEntry.getValue()).longValue(): mapEntry.getValue());
                    targetField.setAccessible(targetFlag);

                    break;
                }
            }
        }
        return target;
    }
}
