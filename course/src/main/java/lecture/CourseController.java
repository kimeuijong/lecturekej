package lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.Optional;

@RestController
public class CourseController {
    @Autowired
    CourseRepository courseRepository;

    @PostMapping(value = "/course")
    public Course registerCourse(@RequestBody Map<String, String> param) {

        Course course = new Course();
        course.setName(param.get("name"));
        course.setTeacher(param.get("teacher"));
        course.setFee(Long.parseLong(param.get("fee")));
        course.setTextBook(param.get("textBook"));

        course = courseRepository.save(course);

        return course;
    }

    @PatchMapping(value = "/course/{id}")
    public Course modifyCourse(@RequestBody Map<String, String> param, @PathVariable String id) {

        Optional<Course> opt = courseRepository.findById(Long.parseLong(id));
        Course course = null;

        if (opt.isPresent()) {
            course = opt.get();

            if (param.get("name") != null)
                course.setName(param.get("name"));
            if (param.get("teacher") != null)
                course.setTeacher(param.get("teacher"));
            if (param.get("fee") != null)
                course.setFee(Long.parseLong(param.get("fee")));
            if (param.get("textBook") != null)
                course.setTextBook(param.get("textBook"));

            course = courseRepository.save(course);
        }

        return course;
    }

    @PutMapping(value = "/course/{id}")
    public Course modifyCoursePut(@RequestBody Map<String, String> param, @PathVariable String id) {
        return this.modifyCourse(param, id);
    }

    @GetMapping(value = "/course/{id}")
    public Course inquiryCourseById(@PathVariable String id) {

        Optional<Course> opt = courseRepository.findById(Long.parseLong(id));
        Course course = null;

        if (opt.isPresent())
            course = opt.get();

        return course;
    }

    @GetMapping(value = "/course")
    public Iterable<Course> inquiryCourse() {

        Iterable<Course> iter = courseRepository.findAll();

        return iter;
    }

    @DeleteMapping(value = "/course/{id}")
    public boolean deleteCourse(@PathVariable String id) {
        boolean result = false;

        courseRepository.deleteById(Long.parseLong(id));
        result = true;

        return result;
    }
}