import java.util.Objects;

public class TimeOfDay implements Comparable<TimeOfDay> {

    //часы (от 0 до 23)
    private int hours;
    //минуты (от 0 до 59)
    private int minutes;

    public TimeOfDay(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    @Override
    public int compareTo(TimeOfDay other) {
        if (this.hours != other.hours) {
            return Integer.compare(this.hours, other.hours);
        } else {
            return Integer.compare(this.minutes, other.minutes);
        }
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;

        TimeOfDay timeOfDay = (TimeOfDay) obj;
        return Objects.equals(timeOfDay.hours, this.hours) && Objects.equals(timeOfDay.minutes,this.minutes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.hours,this.minutes);
    }
}
