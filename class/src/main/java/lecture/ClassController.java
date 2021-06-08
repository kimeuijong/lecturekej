package lecture;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClassController {
    @Autowired
    ClassRepository classRepository;

    @PostMapping(value = "/class")
    public Class registerClass(@RequestBody Map<String, String> param) {

        Class cla = new Class();
        cla.setCourseId(Long.parseLong(param.get("courseId")));
        cla.setFee(Long.parseLong(param.get("fee")));
        cla.setStudent(param.get("student"));

        cla = classRepository.save(cla);

        return cla;
    }

    @PatchMapping(value = "/class/{id}")
    public Class modifyClass(@RequestBody Map<String, String> param, @PathVariable String id) {

        Optional<Class> opt = classRepository.findById(Long.parseLong(id));
        Class cla = null;

        if (opt.isPresent()) {
            cla = opt.get();

            if (param.get("courseId") != null)
                cla.setCourseId(Long.parseLong(param.get("courseId")));
            if (param.get("fee") != null)
                cla.setFee(Long.parseLong(param.get("fee")));
            if (param.get("student") != null)
                cla.setStudent(param.get("student"));

            cla = classRepository.save(cla);
        }

        return cla;
    }

    @PutMapping(value = "/class/{id}")
    public Class modifyClassPut(@RequestBody Map<String, String> param, @PathVariable String id) {
        return this.modifyClass(param, id);
    }

    @GetMapping(value = "/class/{id}")
    public Class inquiryClassById(@PathVariable String id) {

        Optional<Class> opt = classRepository.findById(Long.parseLong(id));
        Class cla = null;

        if (opt.isPresent())
            cla = opt.get();

        return cla;
    }

    @GetMapping(value = "/class")
    public Iterable<Class> inquiryClass() {

        Iterable<Class> iter = classRepository.findAll();

        return iter;
    }

    @DeleteMapping(value = "/class/{id}")
    public boolean deleteClass(@PathVariable String id) {
        boolean result = false;

        classRepository.deleteById(Long.parseLong(id));
        result = true;

        return result;
    }
}
