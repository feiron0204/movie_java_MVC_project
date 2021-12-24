package util;

public class StringUtil {
    // size()
    public static int size(String[] str) {
        return str.length;
    }

    // get()
    public static String get(String[] str, int index) {
        return str[index];
    }

    // isEmpty()
    public static boolean isEmpty(String[] str) {
        return size(str) == 0;
    }

    // contains()
    public static boolean contains(String[] str, String e) {
        for (int i = 0; i < size(str); i++) {
            if (get(str, i).equals(e)) {
                return true;
            }
        }
        return false;
    }

    // indexOf()
    public static int indexOf(String[] str, String e) {
        for (int i = 0; i < size(str); i++) {
            if (get(str, i).equals(e)) {
                return i;
            }
        }
        return -1;
    }

    // lastIndexOf()
    public static int lastIndexOf(String[] str, String e) {
        for (int i = size(str) - 1; i >= 0; i--) {
            if (get(str, i).equals(e)) {
                return i;
            }
        }
        return -1;
    }

    // add()
    public static String[] add(String[] name, String e) {
        String[] temp = new String[size(name) + 1];

        for (int i = 0; i < size(name); i++) {
            temp[i] = get(name, i);
        }

        temp[size(temp) - 1] = e;

        return temp;
    }

    // add()
    public static String[] add(String[] str, int index, String e) {

        String[] temp = new String[0];

        for (int i = 0; i < index; i++) {
            temp = add(temp, get(str, i));
        }

        temp = add(temp, e);

        for (int i = index; i < size(str); i++) {
            temp = add(temp, get(str, i));
        }

        return temp;
    }

    // set()
    public static String set(String[] str, int index, String e) {
        String temp = get(str, index);
        str[index] = e;
        return temp;

    }

    // remove()
    public static String[] remove(String[] str, int index) {

        if (!(index >= 0 && index < size(str))) {
            return str;
        }

        String temp[] = new String[0];

        for (int i = 0; i < size(str); i++) {
            if (i != index) {
                temp = add(temp, get(str, i));
            }
        }

        return temp;
    }

    // removeByElement()
    public static String[] remove(String[] str, String e) {
        return remove(str, indexOf(str, e));

    }

}
