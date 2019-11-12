package cn.thens.kdroid.sample.moc;

import com.google.gson.Gson;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public final class Moc {
    private static <T> T construct(Class<? extends T> type) throws Exception {
        return new Gson().fromJson("{}", type);
    }

    public static <T> T create(final Class<T> clazz) {
        T instance = null;
        try {
            instance = construct(clazz);
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isSynthetic()) continue;
                Object value = getMockValue(field, field.getGenericType());
                if (value != null) setFieldValue(instance, field, value);
            }
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
            return instance;
        }
    }

    public static <T> java.util.List<T> createList(final Class<T> clazz, int length) {
        final java.util.List<T> list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            list.add(create(clazz));
        }
        return list;
    }

    private static Object getMockValue(final Field field, final Type type) throws NoSuchMethodException {
        if (type == field.getDeclaringClass()) return null;
        if (type instanceof ParameterizedType) return getTypeValue(field, (ParameterizedType) type);
        if (type == boolean.class || type == Boolean.class) return getBoolValue(field);
        if (type == int.class || type == Integer.class) return getIntValue(field);
        if (type == long.class || type == java.lang.Long.class) return getLongValue(field);
        if (type == float.class || type == java.lang.Float.class) return getFloatValue(field);
        if (type == double.class || type == java.lang.Double.class) return getDoubleValue(field);
        if (type == java.lang.String.class) return getStringValue(field);
        return create((Class<?>) type);
    }

    private static Object getTypeValue(final Field field, final ParameterizedType type) throws NoSuchMethodException {
        final Type rawType = type.getRawType();
        final Type[] typeArgs = type.getActualTypeArguments();
        if (rawType == java.util.List.class || rawType == ArrayList.class) {
            return getListValue(field, typeArgs[0]);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static Object getListValue(final Field field, final Type type) throws NoSuchMethodException {
        final java.util.List list = new ArrayList<>();
        final Size annotation = field.getAnnotation(Size.class);
        final int[] value = annotation != null ? annotation.value() : (int[]) Size.class.getMethod("value").getDefaultValue();
        final int length = value[(int) Math.floor(Math.random() * value.length)];
        for (int i = 0; i < length; i++) {
            list.add(getMockValue(field, type));
        }
        return list;
    }

    private static Object getBoolValue(final Field field) throws NoSuchMethodException {
        final Bool annotation = field.getAnnotation(Bool.class);
        final boolean[] value = annotation != null ? annotation.value() : (boolean[]) Bool.class.getMethod("value").getDefaultValue();
        return value[(int) Math.floor(Math.random() * value.length)];
    }

    private static Object getIntValue(final Field field) throws NoSuchMethodException {
        final Int annotation = field.getAnnotation(Int.class);
        final int[] value = annotation != null ? annotation.value() : (int[]) Int.class.getMethod("value").getDefaultValue();
        return value[(int) Math.floor(Math.random() * value.length)];
    }

    private static Object getLongValue(final Field field) throws NoSuchMethodException {
        final Long annotation = field.getAnnotation(Long.class);
        final long[] value = annotation != null ? annotation.value() : (long[]) Long.class.getMethod("value").getDefaultValue();
        return value[(int) Math.floor(Math.random() * value.length)];
    }

    private static Object getFloatValue(final Field field) throws NoSuchMethodException {
        final Float annotation = field.getAnnotation(Float.class);
        final float[] value = annotation != null ? annotation.value() : (float[]) Float.class.getMethod("value").getDefaultValue();
        return value[(int) Math.floor(Math.random() * value.length)];
    }

    private static Object getDoubleValue(final Field field) throws NoSuchMethodException {
        final Double annotation = field.getAnnotation(Double.class);
        final double[] value = annotation != null ? annotation.value() : (double[]) Double.class.getMethod("value").getDefaultValue();
        return value[(int) Math.floor(Math.random() * value.length)];
    }

    private static Object getStringValue(final Field field) throws NoSuchMethodException {
        final String annotation = field.getAnnotation(String.class);
        final java.lang.String[] value = annotation != null ? annotation.value() : (java.lang.String[]) String.class.getMethod("value").getDefaultValue();
        return value[(int) Math.floor(Math.random() * value.length)];
    }

    private static void setFieldValue(Object obj, Field field, Object value) throws IllegalAccessException {
        int modifiers = field.getModifiers();
        if (Modifier.isStatic(modifiers)) return;
        field.setAccessible(true);
        field.set(obj, value);
    }

    public interface ValueProvider<T, A extends Annotation> {
        List<Class<?>> matchTypes();

        T getValue(A annotation) throws NoSuchMethodException;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public static @interface Bool {
        boolean[] value() default {false, true};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public static @interface Double {
        double[] value() default {0, -10.1, 3.141592654, -200.3, 200.4, -3000.5, 3000.6, -40000.7};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public static @interface Float {
        float[] value() default {0f, -0.1f, 0.2f, -0.3f, 0.4f, -1.5f, 2.6f, -3.7f, 4.8f, -5.9f};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public static @interface Int {
        int[] value() default {0, -1, 2, -3, 4, -5, 6, -7, 8, -9};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public static @interface Size {
        int[] value() default {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public static @interface Long {
        long[] value() default {995616913, 1595232913, 1279613713, 1563610513, 1500538513};
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public static @interface String {
        java.lang.String[] value() default {"OEUROWUOROW", "q9jasldfll--", "015496789", "ddfee", "Thinkpad", "q&(}.:942U#U@"};
    }
}
