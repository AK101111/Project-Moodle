package xyz.akedia.android.moodleonmobile.model;

/**
 * Created by akedia on 23/02/16.
 */
public class CourseScheme {
    private int lecture;
    private int tutorial;
    private int practical;

    public CourseScheme(int l, int t, int p) {
        lecture = l;
        tutorial = t;
        practical = p;
    }

    public static CourseScheme parseLtp(String ltp) {
        String[] components =  ltp.split("-");
        int lecture = Integer.parseInt(components[0]);
        int tutorial = Integer.parseInt(components[1]);
        int practical = Integer.parseInt(components[2]);
        return new CourseScheme(lecture,tutorial,practical);
    }

    public String toString() {
        return String.format("%d-%d-%d",lecture,tutorial,practical);
    }
}
