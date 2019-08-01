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
    ConcurrentHashMap:<br>  计算key的hashcode并与之右移16位做异或，在与0x7fffffff做&运算<br>
    Hashtable: <br> 直接计算key.hascode()& 0x7FFFFFFF<br>
    
    >存储方式不同：<br> 
    Hashtable:链表+数组，数组里面放的是当前hash的第一个数据，链表里面放的是hash冲突的数据<br>
     ConcurrentHashMap是数组+链表+红黑树
---

- 4.1.6  JAVA8的ConcurrentHashMap为什么放弃了分段锁，有什么问题吗，如果你来设计，你如何设计。<br>
jdk8 放弃了分段锁二是用了node锁，减低锁的粒度，提高性能，并使用CAS操作确保node一些操作的原子性，取代了锁
但是ConcurrentHashMap的一些操作使用了synchronized锁，而不是ReentrantLock，虽说jdk8
的synchronized锁性能进行了优化，但是我觉得还是使用ReentrantLock锁能进行更多的性能优化。<br>
---
- 4.1.7 有没有有顺序的Map实现类，如果有，他们是怎么保证有序的。<br>
LinkedHashMap是基于元素进入集合的顺序或者被访问的先后顺序排序，TreeMap则是基于元素固有顺序（由Comparator或者Comparable确定）

---
- 4.1.8 抽象类和接口的区别，类可以继承多个类么，接口可以继承多个接口么,类可以实现多个接口么。<br>

|类别|抽象类|接口|最终类（final）修饰|
|:----:|----:|----:|----:|
|能否有成员变量|是|否|是|
|能否被继承|是|是|否|
|能否被多重继承|否|是|否|
|能否有成员方法|是|否|是|

1.抽象类可以有抽象方法，也可以没有抽象方法。<br>
2.抽象类可以有实例方法，也可以没有实例方法。<br>
3.继承抽象类，子类必须Override所有抽象方法。<br>
4.抽象类中的非抽象方法可以被Overload。<br>
5.抽象类可以有成员变量，抽象方法可被default、protected、public修饰<br>

6.final类可以有final方法，也可以没有<br>
7.final类不能被继承，final方法不能被Overload，final变量只能被赋值一次，不可改变<br>

8.在jdk8.0之前，接口不能有实例方法，只能有抽象方法和定义常量（被pubic static final修饰的变量<br>
9.在jdk8.0之前，所有的抽象方法默认为public abstract关键字修饰，只能存在抽象方法。<br>
10.在jdk8.0时，引入default关键字以及static关键字，可以有实例方法和
成员变量，如果一个类同时实现了两个或以上带有default方法和static修饰
方法的接口，则static方法不影响，default方法必须在实现类中重写，
否则编译失败。<br>

---

- 4.1.9 继承和聚合的区别在哪<br>
继承是is a的关系，子类继承父类的属性。比如我是一个人。聚合是整体和部分的弱关系，
可以脱离存在，部分的生命周期可以超越整体，例如，电脑包含显示器和主机、鼠标等等。而组合表示的是整体和部分的
强关系，可以不可相互脱离存在，例如人has a 眼睛，嘴，鼻子，耳朵等等。



---
- 4.2.0 IO模型有哪些，讲讲你理解的nio ，他和bio，aio的区别是啥，谈谈reactor模型。<br>
IO全称其实是Input/Output的缩写。BIO即Java开始之初推出的IO操作模块，BlockingIO
的缩写，即阻塞IO。
  - BIO、NIO、AIO的区别<br>
   1. BIO就是传统的java.io包，基于流模型实现，交互方式是同步、阻塞方式。也就是说，
   在读入输入流或者输出流时，在读写动作完成之前，线程会一直阻塞在哪里，它们之间的调用时可靠地线性顺序
   。优点：代码简单、直观；缺点：IO效率和扩展性很低，容易成为应用性能瓶颈。
   2. NIO是Java1.4引入的java.nio包，提供了Channel、Selector、Buffer等新的抽象，可以构建多路复用的
   、同步非阻塞IO程序，同时提供更接近操作系统底层高性能的数据操作方式。
   3. AIO是java1.7之后引入的包，是NIO升级版本，提供了一部异步非阻塞IO操作方式，所以人们叫它AIO
   (Asynchronous IO),异步IO是基于事件和回调机制实现的，也就是应用操作之后会直接返回，不阻塞在那
   里，当后台处理完成，操作系统会通知相应的进程
   进行后续操作。
   <br>
   传统的IO大致分为4种类型：
   1. InputStream、OutputStream基于字节操作的IO
   2. Writer、Reader基于字符操作的IO
   3. File 基于磁盘操作的IO
   4. Socket基于网络操作的IO
   <br>
   NIO,jdk1.4引入，为同步非阻塞IO，需要用户进程在建立一个连接，并注册在多路复用器
   上。多路复用器会对多个用户提出的多个连接进行轮询，发现有IO请求，即创建一个工作Thread
   来操作。用户进程需要不断的询问IO操作是否就绪。<br>
   
   BIO,一个连接一个线程，在并发数很大的情况下容易发生OOM，当然可以通过线程池技术缓解，是同步阻塞IO。
   

首先：nio主要有几个事件，包括读就绪，写就绪， 新连接到来， 当有
新事件操作时，首先把事件注册到对应的处理器；
其次：并由一个线程不断循环等待，调用操作系统底层的函数select() 或者 
epoll（Linux 2.6之前是select、poll，2.6之后是epoll，Windows是iocp），
并负责向操作系统查询IO是否就绪(标记：从网卡已经拷贝到内核缓存区，
准备就绪)，如果就绪执行事件处理器（从内核缓存区到用户内存）；
这个过程就是利用了Reactor事件驱动的模式；<br>
 
 所有的IO模式都分为两个阶段，
    一是等待就绪（准备数据）也就是从网卡copy到内核缓存区
    （从内核缓存区copy到网卡）， 二是真正的操作（读，写） 也就是
    从内核缓存区copy到用户地址空间；前者（等待就绪）对于BIO模式
    是阻塞的， 对于NIO，AIO都是非阻塞的；后者（读写处理）对于BIO，
    NIO都是阻塞的， 但是AIO不是阻塞的，完全是异步的， 在这个处理阶段，
    一般都是多核处理器，如果能够利用多核心进行I/O，
    无疑对效率会有更大的提高，我们可以采用线程池的模式，
    多个线程去处理 ，比如tomcat 的 nio就是采用此模式， 
    但是redis是单线程处理的，因为redis完全是内存操作，不会出现超时
    的现象；

|IO模式|网卡 copy 内核缓冲区|内核缓冲区 copy 用户地址空间|
|:----:|----:|----:|
|BIO|阻塞|阻塞|
|NIO|非阻塞|阻塞|
|AIO|非阻塞|非阻塞|
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
- 4.2.2 反射中，Class.forName和ClassLoader区别 。<br>
类的加载包括（装载、连接、初始化）<br>
1) class.forname()除了将类.class文件加载到jvm中之外，还会对类进行解释，
执行类中的static{}代码块。完成装载、连接任务，可以通过参数控制是否初始化，
默认初始化。<br>
2) classloader只是将.class文件加载到jvm中，不执行static中的内容。完成装载任务，
可以通过参数控制是否连接。默认连接。<br>
---
4.2.3 动态代理的几种实现方式及优缺点<br>
一种是JDK原生动态代理，基于统一的接口，完全原生，无外部依赖。<br>
一种是CGLIB，基于asm字节码生成库，允许在运行时，对字节码进行修改和动态生成，
是基于继承的方式实现，但无法动态增强类中被final方法修饰的类。CGLib采用了非常
底层的字节码技术，其原理是通过字节码技术为一个类创建子类，并在子类中采用方法
拦截的技术拦截所有父类方法的调用，顺势织入横切逻辑。<br>
---
4.2.4 谈谈final的作用<br>
被final修饰的类不能被继承，被final修饰的方法不能被覆盖，被final修饰的变量不能被改变。（引用）
“引用”是Java中非常重要的一个概念，对于引用的理解不深，很容易犯
一些自己都没有意识到的错误。被final修饰的变量，不管变量是在是哪种变量，
切记不可变的是变量的引用而非引用指向对象的内容。<br>
1、被final修饰的方法，JVM会尝试为之寻求内联，这对于提升Java的效率
是非常重要的。因此，假如能确定方法不会被继承，那么尽量将方法定义为final的，
具体参见运行期优化技术的方法内联部分<br>
2、被final修饰的常量，在编译阶段会存入调用类的常量池中，
具体参见类加载机制最后部分和Java内存区域<br>
---
4.2.5 写出单例模式的三种写法：
```/**
    * 饿汉式单例模式（不管你用没用 ，我先建立此对象）
    * 优点：没有加锁，效率提升
    * 缺点：类加载时初始化，浪费内存
    */
   public class Singleton1 {
       private Singleton1 singleton = new Singleton1();
       private Singleton1(){
   
       }
       public Singleton1 getSingleton(){
           return singleton;
       }
   }
```
``` /**
     * 懒汉式单例模式（只有当使用时才建立此对象）
     * 优点：加锁，当多线程调用时效率降低
     * 缺点：在需要使用对象时，建立，节省内存。
     */
    public class Singleton2 {
        private static Singleton2 singleton;
        private Singleton2(){
    
        }
        public synchronized static Singleton2 getSingleton(){
            if(singleton==null){
                singleton = new Singleton2();
            }
            return singleton;
        }
    
        public static void main(String[] args) {
            Singleton2 singletontest1 = Singleton2.getSingleton();
            Singleton2 singletontest2 = Singleton2.getSingleton();
            Singleton2 singletontest3 = Singleton2.getSingleton();
            System.out.println(singletontest1==singletontest2&&singletontest2==singletontest3);
        }
    }
 ```  
```/**
    * 登记式模式（holder）
    * 使用私有静态内部类的方法，避免实例在类加载时创建，
    * 并达到使用时创建的需求，没有加锁
    */
   public class Singleton3 {
   
       private Singleton3(){}
       public static Singleton3 getSingleton3(){
           return Holder.singleton;
       }
       private static class Holder{
           private static final Singleton3 singleton = new Singleton3();
       }
   }
```
