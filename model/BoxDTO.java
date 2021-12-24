package model;

public class BoxDTO {
    // 상영관 정보
    // id, theaterId, movieId, showInfoId, name
    
    // field
    private int id;
    // 극장 id
    private int theaterId;
    
    // 상영관 이름(1관, 2관...)
    private String name;
    
    // getter/setter
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getTheaterId() {
        return theaterId;
    }
    public void setTheaterId(int theaterId) {
        this.theaterId = theaterId;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    // 생성자
    public BoxDTO() {
        id = 0;
        theaterId = 0;
        name = new String();
    }
    
    public BoxDTO(BoxDTO b) {
        id = b.id;
        theaterId = b.theaterId;
        name = new String(b.name);
    }
    
    public boolean equals(Object o) {
        if(o instanceof BoxDTO) {
            BoxDTO b = (BoxDTO) o;
            return id == b.id;
        }
        return false;
    }

}
