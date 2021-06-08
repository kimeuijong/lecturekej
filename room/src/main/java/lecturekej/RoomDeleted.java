package lecturekej;

public class RoomDeleted extends AbstractEvent {

    private Long id;
    public RoomDeleted() {
        super();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}