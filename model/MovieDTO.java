package model;

public class MovieDTO {
    // 영화 기본 정보
    // id, title, genre, summary, rate, director, release, runTime, userId
    
    // userId는 일반 관람객, 전문 평론가, 관리자 등급에 따라 메뉴가 달라지므로 필요
    
    // field
    private int id;
    // 영화제목
    private String title;
    // 장르
    private String genre;
    // 줄거리
    private String summary;
    // 등급
    private String rate;
    // 감독
    private String director;
    // 개봉일
    private String release;
    // 러닝타임
    private int runTime;
    // userId
    private int userId;
    
    // getter/setter
    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // 생성자
    public MovieDTO() {
        id = 0;
        title = new String();
        genre = new String();
        summary = new String();
        rate = new String();
        director = new String();
        userId = 0;
        runTime = 0;
    }
    
    public MovieDTO(MovieDTO m) {
        id = m.id;
        title = new String(m.title);
        genre = new String(m.genre);
        summary = new String(m.summary);
        rate = new String(m.rate);
        director = new String(m.director);
        release = new String(m.release);
        userId = m.userId;
        runTime = m.runTime;
        
    }
    
    public boolean equals(Object o) {
        if(o instanceof MovieDTO) {
            MovieDTO m = (MovieDTO)o;
            return id == m.id;
        }
        return false;
    }

}
