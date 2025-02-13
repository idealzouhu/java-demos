package com.zouhu.deepCopyByClonable;

import com.zouhu.copy.deepCopyByClonable.Address;
import com.zouhu.copy.deepCopyByClonable.Person;
import org.junit.Test;

public class PersonTest {

    @Test
    public void testClone() throws CloneNotSupportedException {
        Address address = new Address("City", "Street");
        Person person1 = new Person("John", 30, address);

        Person person2 = (Person) person1.clone();  // 深拷贝

        System.out.println(person1 != person2); // true
        System.out.println(person1.getAddress() != person2.getAddress()); // true
    }
}