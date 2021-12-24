package model;

public class ShowInfoDTO {
    // 상영 정보
    // id, movieId, theaterId, boxId, userId, table, time
    
    // userId 는 관리자가 등록/수정/삭제 할 때 필요
    // movieId 는 상영 정보에 영화 연결할 때 필요
    // theaterId는 상영 정보에 극장 연결할 때 필요
    
    //field
    private int id;
    // userId
    private int userId;
    // movieId
    private int movieId;
    // theaterId
    private int theaterId;
    // 상영관 id
    private int boxId;
    
    // 상영날짜
    private String table;
    // 상영시간
    private String time;

    // getter/setter
    public int getBoxId() {
        return boxId;
    }

    public void setBoxId(int boxId) {
        this.boxId = boxId;
    }
    public int getUserId() {
        return userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public int getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(int theaterId) {
        this.theaterId = theaterId;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }


    // constructor
    public ShowInfoDTO() {
        id = 0;
        movieId = 0;
        theaterId = 0;
        userId = 0;
        boxId = 0;
    }
    
    public ShowInfoDTO(ShowInfoDTO s) {
        id = s.id;
        movieId = s.movieId;
        theaterId = s.theaterId;
        boxId = s.boxId;
        userId = s.userId;
        table = new String(s.table);
        time = new String(s.time);
    }

    public boolean equals(Object o) {
        if(o instanceof ShowInfoDTO) {
            ShowInfoDTO s = (ShowInfoDTO) o;
            return id == s.id;
        }
        return false;
    }
}
