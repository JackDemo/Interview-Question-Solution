- 4.1.0 JAVA中的几种基本数据类型是什么，各自占用多少字节<br>
 
    | 基本数据类型  | 字节数 | 位数 |
    |:-------|---:|-------:|
    | byte  | 1 | 8 |
    | char  | 2 | 16 |
    | short  | 2 | 16 |
    | int  | 4 | 32 |
    | float  | 4 | 32 |
    | long  | 8 | 64 |
    | double  | 8 | 64 |
----
- 4.1.1 String类能被继承吗？为什么？<br>
String类不能被继承，被final关键字修饰。被final关键字修饰的类无法
被继承，被修饰的方法无法被Overload，被修饰的变量只能被赋值一次，后无法修改。
----
- 4.1.2 String，StringBuffer，StringBuilder的区别<br>
String被声明为final class，在使用String进行大量字符串拼接工作时，
会产生很多无用的中间对象，如果频繁进行，可能会影响性能。
StringBuffer是线程安全的，StringBuilder是线程不安全的。StringBuffer
就是为了解决大量拼接字符串时产生很多中间对象问题而提供的一个类，提供append和add
方法。在使用时要注意设置好capacity。避免较大开销。<br>
StringBuilder capacity变化规则：<br>（内容总长度 = 原始字符串长度+新增字符串长度）

    | 初始值  | 原capacity<内容总长度<=capacity*2+2 | 内容总长度>capacity*2+2 |
    |:-------|---:|-------:|
    | 16  | capacity*2+2 | 内容总长度 |
----
- 4.1.3 ArrayList 和 LinkedList有什么区别？<br>
ArrayList底层由数组实现， 在一般情况下，在查询方面较快，可以直接通过下标进行
 访问，时间复杂度O(1)。增删较慢，例如插入元素，需要复制插入元素后面的元素，
 然后在插入完成后依次向后挪动赋值，并可能因为容量不够而进行扩容。
 LinkedList与ArrayList相反，插入B节点操作只需找到插入前一位置的节点A1，后一节点位置A2，
 然后A1指向B，B指向A2。查询较慢，需要遍历链表。总结一下，对于随机查询，ArrayList较快，随机增删，
 LinkedList较快。如果考虑特殊情况，则不一定。例如插入时在ArrayList首尾插入。
 ---
 - 4.1.4 讲讲类的实例化顺序，比如父类静态数据，构造函数，字段，子类静态数据，构造函数，字段，当new时候，
 他们的执行顺序。<br>
 首先，当类第一次加载，并new一个对象时：此时类信息第一次加载到内存中，
 static方法块会进行加载，具体顺序为：<br>
 父类静态变量初始化->父类静态代码块->子类静态变量初始化->子类静态代码块->父类成员变量初始化->父类普通代码块->父类构造函数
 ->子类成员变量初始化->子类普通代码块->子类构造函数<br>
 当在此new对象时，static变量及代码块不执行，执行顺序为：<br>
 父类成员变量初始化->父类普通代码块->父类构造函数
  ->子类成员变量初始化->子类普通代码块->子类构造函数<br>
  ---
  - 4.1.5 用过哪些Map类，都有什么区别，HashMap是线程安全的吗,
  并发下使用的Map是什么，他们内部原理分别是什么，比如存储方式
  ，hashcode，扩容，默认容量等。<br>
HashMap是非线程安全的，只是用于单线程环境下，多线程环境下可以
采用concurrent并发包下的concurrentHashMap(Segment可重入锁)，初始容量为16，装填因子0.75。
HashMap存数据的过程是：<br>
HashMap内部维护了一个存储数据的Entry数组，HashMap采用链表
解决冲突，每一个Entry本质上是一个单向链表。当准备添加一个key-value对时，
首先通过hash(key)方法计算hash值，然后通过indexFor(hash,length)求
该key-value对的存储位置，计算方法是先用hash&0x7FFFFFFF后，再对length取模，
这就保证每一个key-value对都能存入HashMap中，当计算出的位置相同时，
由于存入位置是一个链表，则把这个key-value对插入链表头。
HashMap中key和value都允许为null。key为null的键值对永远都放在以
table[0]为头结点的链表中。
存储方式底层均为Entry数组，使用单链表来解决地址冲突问题

    | 比较项  | HashMap | HashTable |
    |:-------|---:|-------:|
    | 是否支持序列化  | 是 | 是 |
    | 是否实现Cloneable接口  | 是 | 是 |
    | 是否线程安全  | 否 | 是 |
    | 继承的父类  | AbstractMap | Dictionary（已被废弃） |
    | 是否包含contains()  | 否 | 是 |
    | 是否允许空值（k,v）  | 是 | 否 |
    | 扩容方式  | 原容量的2倍 | 原容量2倍+1 |
    | 初始容量  | 16 | 11 |

    >计算得到的hashcode不同：<br>
    ConcurrentHashMap:<br>  计算key的hashcode并与之右移16位做异或，在与0x7fffffff做&运算
    Hashtable: <br> 直接计算key.hascode()& 0x7FFFFFFF<br>
    >存储方式不同：<br> 
    Hashtable:链表+数组，数组里面放的是当前hash的第一个数据，链表里面放的是hash冲突的数据
     ConcurrentHashMap是数组+链表+红黑树

---
- 4.1.7 有没有有顺序的Map实现类，如果有，他们是怎么保证有序的。<br>
LinkedHashMap是基于元素进入集合的顺序或者被访问的先后顺序排序，TreeMap则是基于元素固有顺序（由Comparator或者Comparable确定）
---

- 4.1.6  JAVA8的ConcurrentHashMap为什么放弃了分段锁，有什么问题吗，如果你来设计，你如何设计。<br>
jdk8 放弃了分段锁二是用了node锁，减低锁的粒度，提高性能，并使用CAS操作确保node一些操作的原子性，取代了锁
但是ConcurrentHashMap的一些操作使用了synchronized锁，而不是ReentrantLock，虽说jdk8
的synchronized锁性能进行了优化，但是我觉得还是使用ReentrantLock锁能进行更多的性能优化。<br>
---
- 4.2.1 反射的原理，反射创建类实例的三种方式是什么。<br>
   - 类加载的三个步骤<br>
加载：由类加载器完成，找到对应的字节码，**创建一个Class对象**<br>
链接：验证类中的字节码，为静态域分配空间<br>
初始化(JVM)：如果该类有超类，则对其初始化，执行静态初始化器和静态初始化块<br>
   - Class<br>
      - Class本身是一个类
      - Class对象只能由系统创建对象
      - 一个类在JVM中只会有一个Class实例
      - 一个Calss对象对应的是一个加载在JVM中.class文件
      -  每个类的实例都会记得由哪个Class实例生成,通过Class能够得到一个类中的完整结构
  - Reflection（反射）是动态语言的关键，反射机制允许程序在执行期间借助于Reflection API
  取得任何类的内部信息，并能直接操作任意对象的内部属性和方法。
  ```public class ClassDemo1 {
     public static void main(String[] args) {
         Foo foo1 = new Foo();
        //第一种：通过类名.class来创建
         Class class1 = Foo.class;
         
         //第二种：通过对象名.getClass()来创建
         Class class2 = foo1.getClass();
         
         //第三种：通过Class.forName()来创建
         Class class3 = null;
         try {
             class3 = Class.forName("com.imooc.reflect.Foo");
         } catch (ClassNotFoundException e) {
             e.printStackTrace();
         }
         System.out.println(class2==class3);//true
         
         //创建Foo类的实例对象
         try {
             //需要有无参数的构造方法
             Foo foo = (Foo) class1.newInstance();//需要强转
             foo.print();
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
     }
     class Foo{
         public void print(){
             System.out.println("foo");
         }
     }

---
- 4.2.2 反射中，Class.forName和ClassLoader区别 。