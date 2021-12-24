package util;

public class ArrayUtil {
    
    // size()
    public static int size (int[] arr) {
        return arr.length;
    }
    
    // get()
    public static int get (int[] arr, int index) {
        return arr[index];
    }
    
    // isEmpty()
    public static boolean isEmpty(int[] arr) {
        return size(arr) == 0;
    }
    
    // contains()
    public static boolean contains(int[] arr, int e) {
        for(int i = 0; i < size(arr); i++) {
            if(get(arr, i) == e) {
                return true;
            } 
        }
        return false;
    }
    
    // indexOf()
    public static int indexOf(int[] arr, int e) {
        for(int i = 0; i < size(arr); i++) {
            if(get(arr, i) == e) {
                return i;
            }
        }
        return -1;
    }
    
    // lastIndexOf()
    public static int lastIndexOf(int[] arr, int e) {
        for(int i = size(arr)-1; i >= 0; i--) {
            if(get(arr, i) == e) {
                return i;
            }
        }
        return -1;
    }
    
    // add()
    public static int[] add(int[] arr, int e) {
        int[] temp = new int[size(arr) + 1];
        
        for(int i = 0; i < size(arr); i++) {
            temp[i] = get(arr, i);
        }
        
        temp[size(temp) - 1] = e;
        
        return temp;
    }
    
    // H. add()
    public static int[] add(int[] arr, int index, int e) {
        
        int[] temp = new int[0];
        for(int i = 0; i < index; i++) {
            temp = add(temp, get(arr, i));
        }
        
        temp = add(temp, e);
        
        for(int i = index; i < size(arr); i++) {
            temp = add(temp, get(arr, i));
        }
        
        return temp;
    }
    
    
    // set()
    public static int set (int[] arr, int index, int e) {
        int temp = get(arr, index);
        arr[index] = e;
        return temp;    
        
    }
    
    
    // J.removeByIndex()
    public static int[] removeByIndex(int[] arr, int index) {
        
        if(!(index >= 0 && index < size(arr))) {
            return arr;
        }
        
        int temp[] = new int[0];
        
        for(int i = 0; i < size(arr); i ++) {
            if(i != index) {
                temp = add(temp, get(arr, i));
            }
        }
        
        return temp;
    }
    
    
    // removeByElement()
    public static int[] removeByElement(int[] arr, int e) {
        return removeByIndex(arr, indexOf(arr, e)); 
    }
}
