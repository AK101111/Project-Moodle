package xyz.akedia.android.moodleonmobile.config;

/**
 * Contains urls of apis
 */
public class ApiUrls {
    public static final String LOGIN = "/default/login.json";
    public final static String LOGOUT = "/default/logout.json";
    public final static String COURSELIST = "/courses/list.json";

    public final static String NOTIFICATIONS = "/default/notifications.json";
    public final static String GRADES = "/default/grades.json"; // /courses/course.json/<coursecode>/grades
    public final static String COURSE_BASE = "/courses/course.json/";        //<coursecode>/assignments";

    public final static String ASSIGNMENT_BASE = "/courses/assignment.json/";
    //final static String info of a particular assignment:​/courses/assignment.json/<assignment‐number> Course grades:​/courses/course.json/<coursecode>/grades
    public final static String COURSE_THREADS_BASE = "/courses/course.json/";       //<coursecode>/threads
    public final static String THREAD_INFO_BASE = "/threads/thread.json/";         //<thread‐number>
    public final static String THREAD_NEW = "/threads/new.json?";         //<thread‐number>
    //Create new thread: /threads/new.json?title=<title>&description=<desc>&course_code=<coursecode> Add comment to a thread: /threads/post_comment.json?thread_id=<thread_id>&description=<desc>

    public final static String ADD_COMMENT = "/threads/post_comment.json";
}
