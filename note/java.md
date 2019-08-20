### 静态类  
当在一个类中创建类时才能使用静态内部类，其他情况无法创建静态类  
静态内部类和非静态内部类的区别：  
* 1.静态内部类可以创建静态属性和方法，费静态内部类则不可以  
* 2.静态内部类只能访问外部类的静态成员，非静态内部类则可以访问外部类的静态和费静态成员、属性  
* 3.创建内部类的区别  
```
        //非静态内部类的创建
        InnerClass outter = new InnerClass();
        AA aa = outter.new AA();
        AA aa2 = outter.new AA();
        //静态内部类的创建
        BB bb = new InnerClass.BB();
        BB bb2 = new InnerClass.BB();
``` 

### log4j.properties  
[请看这里](https://blog.csdn.net/cen_s/article/details/78426682)
  
