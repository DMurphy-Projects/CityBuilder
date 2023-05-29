public class MathsTest {

    public static void print(int pos, int width, int height)
    {
        printCoords(pos % width, (pos / width) % height, pos / (width * height));
    }

    public static void printCoords(int x, int y, int z)
    {
        System.out.println(String.format("%s %s %s", x, y, z));
    }

    public static void main(String[] args)
    {
        int i = 0, j = 0, k = 1;
        int w = 2, h = 2, l = 2;

        int start =         i + (w * (j + k * h));

        int transposed =    k + (l * (j + i * h));
        int trans_rev =     (l-k-1) + (l * (j + i * h));

        print(start, w, h);

        print(transposed, l, h);
        print(trans_rev, l, h);
    }
}
