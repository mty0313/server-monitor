package top.mty.utils;

public class Assert {
    public static void notNull (Object o) throws Exception {
        if (null == o) {
            throw new Exception();
        }
    }
}
