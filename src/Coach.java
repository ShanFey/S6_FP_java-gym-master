import java.util.Objects;

public class Coach {

    //фамилия
    private String surname;
    //имя
    private String name;
    //отчество
    private String middleName;

    public Coach(String surname, String name, String middleName) {
        this.surname = surname;
        this.name = name;
        this.middleName = middleName;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getMiddleName() {
        return middleName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Coach coach = (Coach) obj;
        return Objects.equals(this.surname,coach.surname) && Objects.equals(this.name,coach.name)
            && Objects.equals(this.middleName,coach.middleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.surname,this.name,this.middleName);
    }

    @Override
    public String toString() {
        return this.surname + " " + this.name + " " + this.middleName;
    }
}
