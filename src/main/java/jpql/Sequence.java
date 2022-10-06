package jpql;

public class Sequence {

    private static Long sequence = 1L;

    public static String getString(){
        return "" + sequence++;
    }

    public static int getInt(){
        int i = sequence.intValue();
        sequence++;
        return i;
    }

}
