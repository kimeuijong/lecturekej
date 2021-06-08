package lecturekej;

import lecturekej.config.kafka.KafkaProcessor;

import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @Autowired RoomRepository roomRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverCourseRegistered_RegisterRoomWithCourse(@Payload CourseRegistered courseRegistered){

        if(!courseRegistered.validate()) return;

        System.out.println("\n\n##### listener RegisterRoomWithCourse : " + courseRegistered.toJson() + "\n\n");

        // Sample Logic //
        Room room = new Room();
        room.setLecturename(courseRegistered.getName());
        room.setTeacher(courseRegistered.getTeacher());

        List<Room> rm = roomRepository.findByLecturenameIsNull(); 
        if(!rm.isEmpty()){
            room.setId(rm.get(0).getId());
            room.setRoomnumber(rm.get(0).getRoomnumber());
        }else{
            System.out.println("No empty classrooms");
        }
        roomRepository.save(room);
            
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}
