package com.zouhu.deepCopyBySerialization;

import com.zouhu.copy.deepCopyBySerialization.Address;
import com.zouhu.copy.deepCopyBySerialization.Person;
import org.junit.Test;

import java.io.IOException;

public class PersonTest {

    /**
     * 测试利用序列化实现的深拷贝
     */
    @Test
    public void deepCopy() throws IOException, ClassNotFoundException {
        Address address = new Address("City", "Street");
        Person person1 = new Person("John", 30, address);

        Person person2 = (Person) person1.deepCopy();  // 序列化拷贝

        System.out.println(person1 != person2); // true
        System.out.println(person1.getAddress() != person2.getAddress()); // true
    }
}