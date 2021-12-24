package model;

public class TheaterDTO {
    // 극장 정보
    // id, branch, location, tel, userId, movieId
    
    // userId 는 관리자만 극장 등록/수정/삭제 할 수 있기 때문에 필요
    // movieId 는 상영 극장 정보 연결에 필요
    
    // field
    private int id;
    // 지점명
    private String branch;
    // 위치
    private String location;
    // tel
    private String tel;
    // movieId
    private int movieId;
    // userId
    private int userId;

    // getter/setter
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    // 생성자
    public TheaterDTO() {
        id = 0;
        branch = new String();
        location = new String();
        tel = new String();
        movieId = 0;
        userId = 0;
    }
    
    public TheaterDTO(TheaterDTO t) {
        id = t.id;
        branch = new String(t.branch);
        location = new String(t.location);
        tel = new String(t.tel);
        movieId = t.movieId;
        userId = t.userId;
    }
    
    public boolean equals(Object o) {
        if(o instanceof TheaterDTO) {
            TheaterDTO t = (TheaterDTO) o;
            return id == t.id;
        }
        return false;
    }

}
