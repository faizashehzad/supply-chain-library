import java.util.Arrays;

public class Objects {
    public static boolean equals(java.lang.Object a, java.lang.Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

    public static int hash(java.lang.Object... values);
}