package hamed_gh.ir.divaaremehrabani.app;

/**
 * Created by 6 on 02/22/2016.
 */
public class URIs {
	public static final String DOMAIN = "http://www.abredanesh.ir/"; //"http://192.168.2.5:1020/";
	public static final String API = DOMAIN + "api/v0/";

	public static final String GET_VERSION = API + "app/version";
	public static final String LOGIN = API + "account/login";
	public static final String UPDATE = API + "account/update";
	public static final String UPLOAD_IMAGE = API + "account/FileUpload";
	public static final String UPLOAD_FILE = API + "video/upload";
	public static final String GET_NOTIFS = API + "event/GetNotifs";
	public static final String GET_CLASS_HOLD = API + "course/GetCourseSessions";
	public static final String GET_EVENTS = API + "event/GetEvents";
	public static final String GET_WEEK_SCHEDULE = API + "course/GetWeekSchedule";
	public static final String GET_DAY_SCHEDULE = API + "course/GetDaySchedule";
	public static final String GET_COURSES = API + "course/GetMyCourses";
	public static final String GET_EXAMS = API + "exam/GetCourseExams";
	public static final String GET_LIBRARY = API + "Library/GetLibrary";
	public static final String GET_PHOTO_GALLERY = API + "Gallery/GetPhotoGallery";
	public static final String GET_VIDEO_GALLERY = API + "Gallery/GetVideoGallery";
	public static final String GET_CHILDREN = API + "account/getParentStudents";
	public static final String GET_SCHOOL = API + "school/GetSchool";
	public static final String GET_STUDENTPARENTS = API + "account/GetStudentParents";
	public static final String GET_CLASSROOMSTUDENTS = API + "account/GetClassRoomStudents";
	public static final String GET_SCHOOLLIST = API + "school/GetTeacherSchoolsList";
	public static final String GET_TEACHER_ClASSROOMSLIST = API + "course/GetTeacherClassRoomsList";
	public static final String GET_TEACHER_COURSESLIST = API + "course/GetTeacherCoursesList";
	public static final String GET_TEACHER_FULL_COURSESLIST = API + "course/getTeacherFullCourses";
	public static final String GET_TEACHER_DAYSCHEDULE = API + "course/GetTeacherDaySchedule";
	public static final String GET_TEACHER_WEEKSCHEDULE = API + "course/GetTeacherWeekSchedule";
}