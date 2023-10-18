import java.sql.Time;
import java.util.Date;

/**
 * The type Exercise.
 */
public class Exercise {
    private Date date;
    private Time duration;
    private String type;
    private int intensity; //1,2,3,4,5
    private int calBurned;


    /**
     * Gets date.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets duration.
     *
     * @return the duration
     */
    public Time getDuration() {
        return duration;
    }

    /**
     * Sets duration.
     *
     * @param duration the duration
     */
    public void setDuration(Time duration) {
        this.duration = duration;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets intensity.
     *
     * @return the intensity
     */
    public int getIntensity() {
        return intensity;
    }

    /**
     * Sets intensity.
     *
     * @param intensity the intensity
     */
    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    /**
     * Gets cal burned.
     *
     * @return the cal burned
     */
    public int getCalBurned() {
        return calBurned;
    }

    /**
     * Sets cal burned.
     *
     * @param calBurned the cal burned
     */
    public void setCalBurned(int calBurned) {
        this.calBurned = calBurned;
    }
}
