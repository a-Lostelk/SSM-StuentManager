import org.junit.Test;

import java.text.Collator;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: fang
 * @Date: 2019/8/24
 */
public class TestClass {
    @Test
    public void testSort() {
        Person p1 = new Person(1L, "周杰伦");
        Person p2 = new Person(3l, "刘德华");
        Person p3 = new Person(2l, "张学友");
        Person p4 = new Person(4l, "成龙");
        Person p5 = new Person(5l, "胶布虫");

        ArrayList persons = new ArrayList();
        System.out.println(persons);
        persons.add(p1);
        persons.add(p2);
        persons.add(p3);
        persons.add(p4);
        persons.add(p5);

        Collections.sort(persons, (Comparator<Person>) (o1, o2) -> {
            Collator collator = Collator.getInstance(Locale.CHINA);
            return collator.compare(o1.getName(), o2.getName());
        });

        Iterator iterator = persons.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
