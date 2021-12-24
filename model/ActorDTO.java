package model;

public class ActorDTO {
    // 배우 정보
    // 영화 정보에 들어간다.
    
    // 1개 영화에 여러 명의 배우가 등장하므로 별도의 ActorDTO 생성
    
    // field
    private int id;
    // movieId
    private int movieId;
    // 배우 이름
    private String actor;
    // 배역
    private String role;
    
    // getter/setter
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getMovieId() {
        return movieId;
    }
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
    public String getActor() {
        return actor;
    }
    public void setActor(String actor) {
        this.actor = actor;
    }
    
    // 생성자
    public ActorDTO() {
        id = 0;
        movieId = 0;
        actor = new String();
        role = new String();
    }
    
    public ActorDTO(ActorDTO a) {
        id = a.id;
        movieId = a.movieId;
        actor = new String(a.actor);
        role = new String(a.role);
    }
    
    public boolean equals(Object o) {
        if(o instanceof ActorDTO) {
            ActorDTO a = (ActorDTO) o;
            return id == a.id;
        }
        return false;
    }
    

}
