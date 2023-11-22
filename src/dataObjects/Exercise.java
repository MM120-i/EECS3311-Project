package dataObjects;

import java.time.LocalDate;

/**
 * The type dataObjects.Exercise.
 */
public class Exercise {
    private LocalDate date;
    private int duration;
    private String type;
    private int intensity; //1,2,3,4,5
    private int calBurned;



    /**
     * Gets date.
     *
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Gets duration.
     *
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets duration.
     *
     * @param duration the duration
     */
    public void setDuration(int duration) {
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

    public void calBurned(double bmr) {
        switch (intensity) {
            case 1:
                calBurned = (int) (bmr * 1.375 - bmr);
                break;
            case 2:
                calBurned = (int) (bmr * 1.55 - bmr);
                break;
            case 3:
                calBurned = (int) (bmr * 1.725 - bmr);
                break;
            case 4:
                calBurned = (int) (bmr * 1.9 - bmr);
                break;
            default:
                calBurned = 0;
        }
    }

    public Exercise( LocalDate date, int duration, String type, int intensity) {
        this.date = date;
        this.duration = duration;
        this.type = type;
        this.intensity = intensity;
    }

    public Exercise(LocalDate date, int duration, String type, int intensity, int calBurned) {
        this.date = date;
        this.duration = duration;
        this.type = type;
        this.intensity = intensity;
        this.calBurned = calBurned;
    }

    public Exercise() {
    }
}
