### 代码结构

`com/zouhu/lock/IntrinsicLock`： 给出自增这个线程不安全问题，并利用 `synchronized` 关键字解决。

`com/zouhu/lock/CAS`：分别利用 Atomic 和 Unsafe 类解决安全问题。

`com/zouhu/lock/ExplicitLock`：利用 ReentrantLock 解决安全问题。





### 并发编程

 [并发编程三大特性.md](并发编程三大特性.md)  ：并发编程的特性主要包括原子性、有序性、可见性。

 [Java 内存模型.md](Java 内存模型.md) ：Java 内存模型解决了可见性和有序性





### 内置锁

 [什么是 Java 内置锁.md](什么是 Java 内置锁.md) ：每个 Java 对象都可以用作一个内置锁。线程进入同步代码块或方法时会自动获得该锁，在退出同步代码块或方法时会释放该锁。

 [synchronized 的使用.md](synchronized 的使用.md) ：synchronized 是 Java 内置锁的实现方式。



### 显示锁

 [什么是 Java 显示锁.md](什么是 Java 显示锁.md) ：介绍 Java 显示锁的 Lock 接口，并给出显示锁和内置锁的区别。

 [ReentranLock 显示锁.md](ReentranLock 显示锁.md) ：简单地介绍 ReentranLock 显示锁。





### CAS 算法

 [CAS 算法.md](CAS 算法.md) ：CAS( Compare And Swap)  是一种保障变量操作原子性的无锁算法，可以在多个线程之间实现对共享数据的安全更新。

 [Unsafe 类.md](Unsafe 类.md) ：UnSafe 类封装了 CPU 的底层原子操作，从而提供 CAS 方法。

 [Atomic 原子类.md](Atomic 原子类.md) ：原子类利用 CAS 算法和 Violate ，保证了变量的原子性和可见性。





