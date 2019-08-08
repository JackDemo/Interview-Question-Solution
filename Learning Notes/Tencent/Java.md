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
被继承，被修饰的方法无法被Overload,被修饰的变量只能被赋值一次，后无法修改。被final修饰的变量引用不可变。
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
   
同步与异步：相对于操作结果来说是否需要等待结果返回；
阻塞与非阻塞：在等待期间如果不能干别的事情，就是阻塞；在等待期间可以干别的事情，就是非阻塞；

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
- 4.2.3 动态代理的几种实现方式及优缺点<br>
一种是JDK原生动态代理，基于统一的接口，完全原生，无外部依赖。<br>
一种是CGLIB，基于asm字节码生成库，允许在运行时，对字节码进行修改和动态生成，
是基于继承的方式实现，但无法动态增强类中被final方法修饰的类。CGLib采用了非常
底层的字节码技术，其原理是通过字节码技术为一个类创建子类，并在子类中采用方法
拦截的技术拦截所有父类方法的调用，顺势织入横切逻辑。<br>
---
- 4.2.4 谈谈final的作用<br>
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
- 4.2.5 写出单例模式的三种写法：
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
---
- 4.2.8 如何在父类中为子类自动完成所有的hashcode和equals实现？这么做有何优劣。<br>
  结合以上要求，得出了以下实现高质量equals方法的诀窍：<br>
1.使用==符号检查“参数是否为这个对象的引用”。如果是，则返回true。这只不过是一种性能优化，如果比较操作有可能很昂贵，就值得这么做。<br>
2.使用instanceof操作符检查“参数是否为正确的类型”。如果不是，则返回false。一般来说，所谓“正确的类型”是指equals方法所在的那个类。<br>
3.把参数转换成正确的类型。因为转换之前进行过instanceof测试，所以确保会成功。<br>
4.对于该类中的每个“关键”域，检查参数中的域是否与该对象中对应的域相匹配。如果这些测试全部成功，则返回true;否则返回false。<br>
5.当编写完成了equals方法之后，检查“对称性”、“传递性”、“一致性”。<br>
注意：<br>
覆盖equals时总要覆盖hashCode 《Effective Java》作者说的<br>
不要企图让equals方法过于只能。<br>
不要将equals声明中的Object对象替换为其他的类型（因为这样我们并没有覆盖Object中的equals方法哦）<br>
---
- 4.2.9 请结合 OO 设计理念，谈谈访问修饰符 public、private、protected、default 在应用设计中的作用。
访问控制符，主要用于表示修饰块的作用域，方便隔离防护。<br>

|关键字|同一个类|同一个包 |不同包的子类|不同包的非子类|
|:----:|----:|----:|----:|----:|
|private|Yes|No|No|No|
|default|Yes|Yes|No|No|
|protected|Yes|Yes|Yes|No|
|public|Yes|Yes|Yes|Yes|

---
- 4.3.0 深拷贝和浅拷贝区别。<br>
  复制引用和复制值的区别。将对象序列化为字节序列后，默认会将该对象的整个对象图进行序列化，再通过反序列即可完美地实现深拷贝。
---
- 4.3.1 数组和链表数据结构的描述以及时间复杂度<br>
数组是将元素在内存中连续存放，由于每个元素在内存中占用内存空间相同，
可以通过下标迅速的访问数组中的元素。每次增加或删除一个元素，都需要向后或者
向前移动大量的元素。所以应用环境应为访问次数较多，增加或删除操作较少的情况。<br>
链表恰好相反，是通过存放在元素中的指针联系在一起的，在内存中不是顺序存储的。
在访问元素时需要进行遍历，但在插入及删除元素时，只需找到需要插入或删除元素的前后位置
，即可插入，不需要移动元素。<br>

|类型|插入或删除|访问 |
|:----:|----:|----:|
|数组|O(n)|O(1)|
|链表|O(1)|O(n)|
---
- 4.3.2 error和exception的区别，CheckedException，RuntimeException的区别。<br>
error:通常是由JVM生成并抛出，大多数错误与代码编写者无关。这些错误不可查，
因为他们在应用程序的控制和处理能力之外，而且大多数程序运行时不允许出现的
状况。对于合理的应用程序来说，即使确实发生了错误，本质上也不应该师徒去处理它所引起的异常状况。
Exception：在Exception分支中有一个重要的子类RuntimeException（运行时异常），这类异常
是不检查异常，程序中可以选择捕获处理，也可以选择不处理。这类错误一般是由于
程序逻辑错误引起的，程序应该从逻辑角度尽可能避免这类异常；而RuntimeException之外的异常统称为非运行时异常
，如果不处理，程序就不能编译通过。如IOException，SQLException等及用户自己定义的异常，一般情况下不自定义检查异常。
---
- 4.3.3 请列出5个运行时异常<br>
ArrayIndexOutException、NullPointerException、ClassCastException、ArithmeticException、ClassNotFoundException、MissingResourceException
IllegalArgumentException
---
- 4.3.4 自定义一个java.lang.String类，这个类是否可以被类加载器加载？为什么。<br>
不能，因为JVM在类加载时实现了双亲委派模型。类加载器具体可以分为四类，第一类是
Bootstrap ClassLoader（启动类加载器），处于顶层；
第二类，Extension ClassLoader（扩展类加载器），处于第二层；
第三类，Application ClassLoader（应用程序加载器），处于第三层；
第四类，Customer ClassLoader（用户自定义加载器），处于底层。
自底向上检查类是否已经被加载，自顶向下尝试加载类。
<br>
再简单说下双亲委托机制：如果一个类加载器收到了类加载的请求，
它首先不会自己尝试去加载这个类，而是把这个请求委派给父类加载器，
每一个层次的类加载器都是加此，因此所有的加载请求最终到达顶层的
启动类加载器，只有当父类加载器反馈自己无法完成加载请求时
（指它的搜索范围没有找到所需的类），子类加载器才会尝试自己去加载。
<br>
双亲委派模型可以确保安全性，可以保证所有的Java类库都是由启动类加
载器加载。如用户编写的java.lang.Object，加载请求传递到启动类加载器，
启动类加载的是系统中的Object对象，而用户编写的java.lang.Object不会被
加载。如用户编写的java.lang.virus类，加载请求传递到启动类加载器，启
动类加载器发现virus类并不是核心Java类，无法进行加载，将会由具体的
子类加载器进行加载，而经过不同加载器进行加载的类是无法访问彼此的。
由不同加载器加载的类处于不同的运行时包。所有的访问权限都是基于同
一个运行时包而言的。
<br>
---
- 4.3.5 说一说你对java.lang.Object对象中hashCode和equals方法的理解。
在什么场景下需要重新实现这两个方法。<br>
hashCode方法是native方法，而哈希码本身是提高查找效率的算法，在容器插入
方面，如果不能插入容器中已经存在的对象，可以先使用hashcode方法进行比较，
如果相等，在使用equals方法进行判断是否插入，目的是为了提高插入效率。
在编写equals方法时，需要满足自反性、对称性、传递性、一致性、非空性。
同样在容器中，如果存入自定义类，如果重新实现了equals方法，那么需要重写hashCode方法，
因为在get方法中需要比较hashcode方法，如果没有重写，则get为空，但实际上是equals的，
所以在此场景下需要重写两个方法。
---
- 4.3.6 在jdk1.5中，引入了泛型，泛型的存在是用来解决什么问题。<br>
泛型的本质是参数化类型，也就是说所操作的数据类型被指定为一个参数，泛型的好处
是在编译的时候检查类型安全，并且所有的强制转换都是自动的和隐式的，以提高代码的重用率。
---
- 4.3.7 这样的 a.hashcode() 有什么用，与 a.equals(b)有什么关系。<br>
hashCode不唯一，equals方法最终确定两个对象是否相等。
---
- 4.3.8 有没有可能2个不相等的对象有相同的hashcode。<br>
有
---
- 4.3.9 Java 中的 HashSet 内部是如何工作的。<br>
底层使用hashmap实现，将存入HashSet中的元素用作hashmap的key，value部分使用
了一个PRESENT常量代替。
---
- 4.4.0  什么是序列化，怎么序列化，为什么序列化，反序列化会遇到什么问题，如何解决。<br>
序列化：把对象转换为字节序列的过程称为对象的序列化。<br>
反序列化：把字节序列恢复为对象的过程称为对象的反序列化。<br>
实现Serializable接口即可序列化。<br>
当你想把的内存中的对象状态保存到一个文件中或者数据库中时候；<br>
当你想用套接字在网络上传送对象的时候；<br>
当你想通过RMI传输对象的时候；<br>
ObjectOutputStream代表对象输出流：<br>
它的writeObject(Object obj)方法可对参数指定的obj对象进行序列化，把得到的字节序列写到一个目标输出流中。<br>
ObjectInputStream代表对象输入流：<br>
它的readObject()方法从一个源输入流中读取字节序列，再把它们反序列化为一个对象，并将其返回。<br>
简单来说，Java的序列化机制是通过在运行时判断类的serialVersionUID来验证版本一致性的。在进行反序列化时，JVM会把传来的字节流中的serialVersionUID与本地相应实体（类）的serialVersionUID进行比较，如果相同就认为是一致的，可以进行反序列化，否则就会出现序列化版本不一致的异常。(InvalidCastException)
强烈建议在一个可序列化类中显示的定义serialVersionUID，为它赋予明确的值。
---
- 4.4.1 java8的新特性。<br>

 >1.Lambda 表达式<br>
 
 java8 四大核心函数式接口Function、Consumer、Supplier、Predicate<br>
```
        Arrays.asList("a","b","c").forEach((String e) ->{
            System.out.println(e);
        });
        Arrays.asList("a","b","c").sort((e1,e2)->{
            int result = e1.compareTo(e2);
            return result;
        });
```
 >2.接口的默认方法和静态方法

```
默认方法可重写，可不必重写。
static方法在多接口继承时需要注意调用方式。
```
>3.方法引用
```
final Car car = Car.create( Car::new );
final List< Car > cars = Arrays.asList( car );
```
> 4.重复注解<br>

> 5.更好的类型推断

> 6.Optional
```
Optional< String > fullName = Optional.ofNullable( null );
System.out.println( "Full Name is set? " + fullName.isPresent() );        
System.out.println( "Full Name: " + fullName.orElseGet( () -> "[none]" ) ); 
System.out.println( fullName.map( s -> "Hey " + s + "!" ).orElse( "Hey Stranger!" ) );
输出：
Full Name is set? false
Full Name: [none]
Hey Stranger!
```
> 7.steam
```
final Collection< Task > tasks = Arrays.asList(
    new Task( Status.OPEN, 5 ),
    new Task( Status.OPEN, 13 ),
    new Task( Status.CLOSED, 8 ) 
);
final long totalPointsOfOpenTasks = tasks
    .stream()
    .filter( task -> task.getStatus() == Status.OPEN )
    .mapToInt( Task::getPoints )
    .sum();

System.out.println( "Total points: " + totalPointsOfOpenTasks );
输出：
Total points: 18
```
>8.Date/Time API(JSR 310)

>9.Base64

>10.使用Metaspace（JEP 122）代替持久代（PermGen space）。
在JVM参数方面，使用-XX:MetaSpaceSize和-XX:MaxMetaspaceSize代替原来的-XX:PermSize和-XX:MaxPermSize。


<br>

#### JVM

- 4.4.2 什么时候会发生栈内存溢出（StackOverflowError）？堆内存溢出(OutOfMemoryError:java heap space)呢？<br>
栈是线程私有的，他的生命周期与线程相同，每个方法在执行的时候
都会创建一个栈帧，用来存储局部变量表，操作数栈，动态链接，方法
出口灯信息。局部变量表又包含基本数据类型，对象引用类型（局部变
量表编译器完成，运行期间不会变化）
所以我们可以理解为栈溢出就是方法执行是创建的栈帧超过了栈的深度。
那么最有可能的就是方法递归调用产生这种结果。
可使用-Xss 参数进行改进。还有（-Xmx  最大堆大小 -Xms 初始堆大小  -Xmn 年轻代大小 -XXSurvivorRatio 年轻代中的Eden区与Survivor区的大小比值

堆内存溢出是因为创建对象使用内存过多且没有被有效回收。
- 4.4.3 JVM的内存结构，Eden和Survivor比例。<br>
Eden:Survivor:Survivor = 8:1:1
年轻代Young：年老代Old=1:2
- 4.4.4 JVM内存为什么要分成新生代，老年代，持久代。新生代中为什么要分为Eden和Survivor。<br>
考虑分代机制的基础，是提升内存回收效率。对很大一部分创建之后就销毁的对象即使进行内存回收，如果不分代，
所有的对象都挤在一起，无法辨别那些对象是新创建的，这些对象有很大几率立即被回收。分代机制可以将这些新生对象
放在一个固定的地方，提升内存回收的效率。同样，一些常量、字符串等存在于整个生命周期中的对象类型只有很小的几率被回收，
所以分为持久带进行存储，由于FullGC的时间较长，所以尽量减少Full GC的频率。<br>
当没有Survivor时，每次需要对所有新生代的对象进行minor GC，不管是不是很快被释放，对象均进入
年老代，这样很快年老代被塞满，也会提高FullGC触发的频率。<br>
当只有1个Survivor时，每次Eden填满之后，进行minorGC，将幸存对象放入Survivor中，然后下一轮，
当Eden填满后，这时对Eden及survivor区的对象进行minorGC，将剩下的对象继续存放在Survivor中，
最后Eden幸存对象放置在Survivor幸存对象之后，一些Survivor对象被释放，形成内存碎片，变成非连续的
空间，严重影响JVM性能。（转载：https://blog.csdn.net/towads/article/details/79784249）
至于为什么没有3或者更多Survivor可能是权衡过之后的最佳答案是2个Survivor。
---
- 4.4.5 JVM中一次完整的GC流程是怎样的，对象如何晋升到老年代，说说你知道的几种主要的JVM参数。<br>
-Xmx  最大堆大小 -Xms 初始堆大小  -Xmn 年轻代大小 -XXSurvivorRatio 年轻代中的Eden区与Survivor区的大小比值
-Xss 栈容量(stack size)
-XX:PermSize=size 永生代最小容量
-XX:MaxPermSize=size 永生代最大容量
---
- 4.4.6 你知道哪几种垃圾收集器，各自的优缺点，重点讲下cms和G1，包括原理，流程，优缺点。<br>
首先回答这个问题，请允许我介绍四种垃圾回收算法：<br>
1. 标记-清除算法（Mark-Sweep）<br>
首先标记处所有需要的对象，然后标记完成后统一回收被标记对象。
缺点：过程效率问题。空间问题，产生大量不连续的内存碎片，当有大对象需要分配时，极有可能没有足够连续的内存而提前出发下一次垃圾收集操作<br>
2. 复制算法（Copying）（针对新生代）<br>
可用内存按大小分为两块，每次使用完其中的一块，进行垃圾回收操作，将还存活的对象复制到另一块上，原来的那块内存一次清理掉。
优点：每一次都是对其中一块内存进行回收，内存分配时就不用考虑内存碎片的问题，移动堆顶指针，按顺序分配内存即可，实现简单，运行高效。
缺点：可使用内存缩小为原来的一半
商业虚拟机Hotspot结合新生代对象98%朝生夕死，将新生代内存划分为Eden:S0:S1=8:1:1,这样每次只有10%内存浪费。当GC后Survivor不够用时，年老代进行
“分配担保”进入老年代。<br>
3. 标记整理算法（Mark-Compact）（针对老年代）<br>
复制算法在存活对象生存率高时，执行更过复制操作效率低，空间浪费，遇到对象存活过多的情况时需要格外的内存进行分配担保。
根据老年代的特点提出标记-整理算法，标记过程仍与标记-清除算法一样，但清除步骤不是直接对可回收对象进行整理，而是让所有存活的对象向
一端移动，然后直接清理掉端边界以外的内存，然后更新所有存活对象中所有只想被移动对象的指针。
整理的顺序：
1-任意顺序 优点：速度快，实现简单。 缺点：降低赋值器的局限性。
2-线性顺序 将具有关联关系的对象排列在一起
3- 滑动顺序 将对象“滑动”到对的一端，从而挤出垃圾，可以保持对象在堆找那个原有的顺序
4. 分代收集算法<br>

垃圾收集器：<br>
1. Serial收集器（用于新生代）<br>
优点：简单高效，没有线程交互的开销，在Client模式下为默认新生代收集器
缺点：在进行GC的时候必须暂停所有工作线程（Stop the World）<br>
2. ParNew收集器（新生代）<br>
SerialGC的多线程版本，是Server模式下的虚拟机中的首选的新生代收集器，因为
除了Serial收集器外，目前只有它能与CMS收集器配合工作。<br>
3. Parallel Scavenge收集器（“吞吐量优先”收集器）（新生代）<br>
使用复制算法，并行多线程，独特住处在于它的关注点与其他收集器不同，CMS
等收集器关注点在于尽可能缩短垃圾收集时用户线程的停顿时间，而Parallel Scavenge
收集器目的在于打到一个可控制的吞吐量（Throughput）。
停顿时间越短需要与用户交互的程序来说越好，良好的响应速度能提升用户的体验。
高吞吐量可以最高效率的利用CPU时间，尽快地完成程序的运算任务，主要适合在后台运算
而不太需要太多交互任务。<br>


    >参数设置：
    -XX:MaxGCPauseMillis 控制最大垃圾收集停顿时间。（大于0的毫秒数）停顿时间缩短是以牺牲吞吐量和新生代空间换取的。（新生代调的小，吞吐量跟着小，垃圾收集时间就短，停顿就小）。
    -XX:GCTimeRatio 直接设置吞吐量大小，0<x<100 的整数，允许的最大GC时间=1/(1+x)。
    -XX:+UseAdaptiveSizePolicy  一个开关参数，开启GC自适应调节策略（GC Ergonomics）,
    将内存管理的调优任务（新生代大小-Xmn、Eden与Survivor区的比例-XX:SurvivorRatio、晋升老年代对象年龄-XX: PretenureSizeThreshold 、等细节参数）交给虚拟机完成。这是Parallel Scavenge收集器与ParNew收集器的一个重要区别，另一个是吞吐量。
    原文链接：https://blog.csdn.net/clover_lily/article/details/80160726


4. Serial Old收集器（老年代）<br>
Serial收集器老年版本，单线程，使用“标记-整理”，主要在Client模式下的<br>
虚拟机使用。
5. Parallel Old收集器（老年代）<br>
Parallel Scavenge老年版本，多线程，使用“标记-整理”，在注重吞吐量及CPU敏感的场合，
优先考虑Parallel Scavenge + Parallel Old收集器。<br>

6. CMS收集器（Concurrent Mark Sweep）
以获取最短回收停顿时间为目标的收集器。针对老年代，能与Serial 及ParNew关联。<br>
    步骤：<br>
    1. 初始标记：需要“Stop the world”，标记GC Roots能直接关联到的对象，速度快。
    2. 并发标记：进行GC Roots Tracing过程，在此过程中，不需要“Stop the world”
    3. 重新标记（CMS remark）：需要“Stop the world”，修正标记期间，因用户程序继续运行而导致标记
    产生变动的一部分对象的标记记录。
    4. 并发清除：此阶段不需要““Stop the world”
停顿时间比较：并发标记>重新标记>初始标记
优点：并发收集，低停顿，基于“标记-清除”算法。
缺点：1-对CPU资源非常敏感。CMS默认回收线程数：（CPU数量+3）/4
2-无法处理浮动垃圾。并发清理阶段用户线程运行产生的垃圾过了标记
阶段无法在本次收集中清理掉，成为浮动垃圾。默认在老年代被使用了68%的空间后被激活。
3-产生大量空间碎片

7. G1收集器<br>
相对于CMS来说优点：1.整体上看基于“标记-整理”算法实现收集器，局部（2个Region之间）使用复制算法2.非常精确地控制停顿
原理：极力避免全区域垃圾收集，之前的收集进行的范围都是整个新生代或老年代，
而G1将整个Java堆（包括新生代。老年代）划分为多个大小固定的独立区域（Region），
并且跟中这些区域里面的垃圾堆积程度，在后台维护一个优先列表，每次根据允许的收集时间，有限回收垃圾最多的区域。
（区域划分（物理方面）。有优先级的区域回收，保证了G1收集器在有限的时间内可以获得最高的收集效率）
可预测的停顿
这是G1相对于CMS的另一个大优势，降低停顿时间是G1和CMS共同的
关注点，但G1除了追求低停顿外，还能建立可预测的停顿时间模型。
可以明确指定M毫秒时间片内，垃圾收集消耗的时间不超过N毫秒。
在低停顿的同时实现高吞吐量
---
- 4.4.8  当出现了内存溢出，你怎么排错。<br>
 首先控制台查看错误日志，然后使用jdk自带的jvisualvm工具查看系统的堆栈日志，定位出内存溢出的空间：堆，栈还是永久代（jdk8以后不会出现永久代的内存溢出），如果是堆内存溢出，看是否创建了超大的对象
如果是栈内存溢出，看是否创建了超大的对象，或者产生了死循环。
---
- 4.4.9 JVM内存模型的相关知识了解多少，比如重排序，内存屏障，happen-before，主内存，工作内存等。<br>
重排序：jvm虚拟机允许在不影响代码最终结果的情况下，可以乱序执行。
内存屏障：可以阻挡编译器的优化，也可以阻挡处理器的优化
happens-before原则：
1：一个线程的A操作总是在B之前，那多线程的A操作肯定实在B之前。
2：monitor 再加锁的情况下，持有锁的肯定先执行。
3：volatile修饰的情况下，写先于读发生
4：线程启动在一起之前 start
5：线程死亡在一切之后 end
6：线程操作在一切线程中断之前
7：一个对象构造函数的结束都该对象的finalizer的开始之前
8：传递性，如果A肯定在B之前，B肯定在C之前，那A肯定是在C之前。
主内存：所有线程共享的内存空间
工作内存：每个线程特有的内存空间
---
- 4.5.0 单说说你了解的类加载器，可以打破双亲委派么，怎么打破。
自定义用户类加载器中重写loadClass方法。
---
- 4.5.1 Java反射机制
Reflection（反射）是动态语言的关键，反射机制允许程序在执行期间借助于Reflection API
  取得任何类的内部信息，并能直接操作任意对象的内部属性和方法。
---
- 4.5.2 线上应用的JVM参数有哪
---
- 4.5.3 g1和cms区别，吞吐量有限和相应优先的垃圾收集器选择
---
- 4.5.4 怎么打出线程栈信息

