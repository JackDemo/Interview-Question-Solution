public class ReflectTest {
    public static void main(String[] args) {
        String wholeNameLine = "Parent";
        String wholeNamePoint = "Parent";
        System.out.println("下面是测试Classloader的效果");
        testClassloader(wholeNameLine, wholeNamePoint);

        System.out.println("----------------------------------");
        System.out.println("下面是测试Class.forName的效果");
        testForName(wholeNameLine, wholeNamePoint);
    }

    private static void testClassloader(String wholeNameLine, String wholeNamePoint) {
        Class<?> line;
        Class<?> point;
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try {
            line = classLoader.loadClass(wholeNameLine);
            point = classLoader.loadClass(wholeNamePoint);

            System.out.println("line   " + line.getName());
            System.out.println("point   " + point.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testForName(String wholeNameLine, String wholeNamePoint) {
        try {
            Class<?> line = Class.forName(wholeNameLine);
            Class<?> point = Class.forName(wholeNamePoint);
            System.out.println("line   " + line.getName());
            System.out.println("point   " + point.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}