package lecturekej;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

 @RestController
 public class RoomController {
    @Autowired
    RoomRepository roomRepository;

    @DeleteMapping(value = "/rooms/{id}")
    public boolean deleteRoom(@PathVariable String id) {
        boolean result = false;

        roomRepository.deleteById(Long.parseLong(id));
        result = true;

        return result;
    }

 }
