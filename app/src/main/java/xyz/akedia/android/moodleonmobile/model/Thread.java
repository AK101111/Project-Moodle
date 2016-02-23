package xyz.akedia.android.moodleonmobile.model;

/**
 * Created by arnavkansal on 24/02/16.
 */

//        assignments: [ ],
//        registered: {
//        starting_date: "2016-01-01 00:00:00",
//        id: 1,
//        professor: 5,
//        semester: 2,
//        ending_date: "2016-05-10 00:00:00",
//        year_: 2016,
//        course_id: 1
//        },
//        course_threads: [
//        {
//        user_id: 5,
//        description: "aksjfnakjfsnasfjnaskjfn",
//        title: "ABC",
//        created_at: "2016-02-21 18:30:02",
//        registered_course_id: 1,
//        updated_at: "2016-02-21 18:30:02",
//        id: 1
//        }
//        ],
//        course: {
//        code: "cop290",
//        name: "Design Practices in Computer Science",
//        description: "Design Practices in Computer Science.",
//        credits: 3,
//        id: 1,
//        l_t_p: "0-0-6"
//        },
//        grades: [ ],
//        tab: "threads",
//        year: 2016,
//        sem: 2,
//        resources: [ ],
//        previous: [ ]
//        }

public class Thread {
    private int userId;
    private String description;
    private String title;
    private String createdAt;
    private int registeredCourseId;
    private String updatedAt;
    public int id;

    public Thread(int userId, String description, String title, String createdAt, int registeredCourseId, String updatedAt, int id){
        this.userId = userId;
        this.description = description;
        this.title = title;
        this.createdAt = createdAt;
        this.registeredCourseId = registeredCourseId;
        this.updatedAt = updatedAt;
        this.id = id;
    }

    public int userId() {
        return userId;
    }
    public String getDescription(){
        return description;
    }
    public String getTitle(){
        return title;
    }
    public  String getCreatedAt(){
        return createdAt;
    }
    public int getRegisteredCourseId(){
        return registeredCourseId;
    }
    public String getUpdatedAt(){
        return updatedAt;
    }
}

