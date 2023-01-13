package technology.mgm.demo.beans;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.chrono.IsoChronology;
import java.util.ArrayList;
import java.util.List;


@Data
@Component
public class Person implements Serializable {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    public enum Sex {
        MALE, FEMALE
    }



    private String address;
    private String city;
    private String cob; // city of birth
    private String comment;
    private String country;
    private String dob; // day of birth
    private String email;
    private String imageName;
    private String lastName;
    private String middleName;
    private String mobile;
    private String phone;
    private String preName;
    private Sex gender;
    private String title;
    private String website;
    private String zip;
    private LocalDate birthday;

    /**
     * constructor of person
     * @param preName
     * @param lastName
     * @param city
     * @param gender from enum
     * @param email mail address
     * @param dob day of birth
     * @param comment any comment
     */
    public Person(String preName, String lastName, String city, Sex gender,
                  String email, LocalDate dob, String comment) {
        this.city = city;
        this.comment = comment;
        this.email = email;
        this.lastName = lastName;
        this.preName = preName;
        this.gender = gender;
        this.setBirthday(dob);
        logger.info("Person instantiated ");
    }

    /**
     * init block that sets demo values to a new person.
     * middlename is not set
     */
    {
        setPreName("Donald");
        setMiddleName("D.");
        setLastName("Duck");
        setAddress("Gänseweg 33");
        setZip("12345");
        setCity("Entenhausen");
        setCountry("demo_country");
        setEmail("demo_email");
        setWebsite("demo_website");
        setPhone("demo_phone");
        setMobile("demo_mobile");
        setDob("demo_dob");
        setTitle("demo_title");
        setComment("demo_comment");
        setImageName("demo_imageName");
        setBirthday( IsoChronology.INSTANCE.date(1988, 8, 8));
    }


    public Person() {
    }

    /**
     * returns the age in years of this person
     * @return
     */
    public int getAge() {
        return birthday
                .until(IsoChronology.INSTANCE.dateNow())
                .getYears();
    }

    /**
     * returns a String saying hello
     * @return
     */
    public String identify() {
        String sb = "Hello, my name is " +
                this.getPreName() + " " +
                (this.getMiddleName().length() > 0 ?  this.getMiddleName() + " " : " ") +
                this.getLastName() +
                " and I live in " + this.getCity() + ". " +
                "My age is " + getAge() + " years.";
        return sb;
    }

    /**
     * returns a single demo Person
     * @return
     */
    public static Person getSinglePerson() {
        Person p = new Person("Birgit","Meier","Hattingen",Sex.FEMALE,"birgit@demo.net", IsoChronology.INSTANCE.date(1980, 6, 11),"Person 1");
        return p;
    }

    /**
     * creates a single demo Person and returns the String value of its identify method
     * @return
     */
    public static String getPersonIdentification() {
        Person p = new Person("Birgit","Meier","Hattingen",Sex.FEMALE,"birgit@demo.net", IsoChronology.INSTANCE.date(1980, 6, 11),"Person 1");
        return p.identify();
    }
    /**
     * returns a list of 4 example Persons
     * @return
     */
    public static List getListOfPersons() {
        List<Person> demo = new ArrayList<>();
        demo.add(new Person("Birgit","Meier","Hattingen",Sex.FEMALE,"birgit@demo.net", IsoChronology.INSTANCE.date(1980, 6, 11),"Person 1"));
        demo.add(new Person("Hans","Dampf","Bonn",Sex.MALE,"hans@demo.net", IsoChronology.INSTANCE.date(1966, 7, 12),"Person 2"));
        demo.add(new Person("Peter","Pan","Rostock",Sex.MALE,"peter@demo.net", IsoChronology.INSTANCE.date(1950, 5, 14),"Person 3"));
        demo.add(new Person("Sandra","Sauer","Wuppertal",Sex.FEMALE,"sandra@demo.net", IsoChronology.INSTANCE.date(1940, 6, 23),"Person 4"));
        demo.add(new Person("Kornelia","Pticar","Wuppertal",Sex.FEMALE,"cp@demo.net", IsoChronology.INSTANCE.date(1990, 6, 13),"Person 5"));
        demo.add(new Person("Kuno","Karpfen","Karlsruhe",Sex.MALE,"kk@demo.net", IsoChronology.INSTANCE.date(1996, 7, 12),"Person 6"));
        demo.add(new Person("Veronika","Wolf", "Wien", Sex.FEMALE, "ww@demo.net", IsoChronology.INSTANCE.date(2001, 6, 3), "Person 7 - a twin"));
        demo.add(new Person("Stefanie", "Wolf","Wien",Sex.FEMALE,"sw@demo.net", IsoChronology.INSTANCE.date(2001, 6, 3),"Person 8 - second twin"));
        return demo;
    }

    public boolean isFemale() {
        return (this.gender == Sex.FEMALE);
    }

    public boolean isMale() {
        return (this.gender == Sex.MALE);
    }

    public String toString() {
        return  (this.preName + " " + this.middleName + " " + this.lastName + " from " + this.city + ", " + this.gender).replaceAll("\\s+" ," ");
    }
}