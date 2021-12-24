package model;

import java.util.Calendar;

public class CommentDTO {
    // 영화 평점, 기대평, 코멘트 정보
    // id, userId, movieId, date, editDate, rate, review, expReview, rateId
    
    // userId 는 회원 등급에 따라 메뉴가 달라지므로 필요
    // movieId 는 특정 영화에 대한 평점, 기대평, 코멘트가 작성되야 하므로 필요
    // rateId 는 전문 평론가, 일반 관람객 별 코멘트를 출력하기 위해 필요
    
    // field
    private int id;
    // 작성 회원 번호
    private int userId;
    // 영화 번호
    private int movieId;
    // 작성일자
    private Calendar date;
    // 수정일자
    private Calendar editDate;
    // 평점
    private double rate;
    // 관람평
    private String review;
    // 기대평
    private String expReview;
    // 작성 회원 등급
    private int rateId;
    
    // getter/setter
    public int getRateId() {
        return rateId;
    }

    public void setRateId(int rateId) {
        this.rateId = rateId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Calendar getEditDate() {
        return editDate;
    }

    public void setEditDate(Calendar editDate) {
        this.editDate = editDate;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getExpReview() {
        return expReview;
    }

    public void setExpReview(String expReview) {
        this.expReview = expReview;
    }

    // 생성자
    public CommentDTO() {
        id = 0;
        userId = 0;
        movieId = 0;
        rate = 0.0;
        review = new String("");
        expReview = new String("");
        rateId = 0;
    }
    
    public CommentDTO(CommentDTO r) {
        id = r.id;
        userId = r.userId;
        movieId = r.movieId;
        rate = r.rate;
        review = new String(r.review);
        expReview = new String(r.expReview);
        date = Calendar.getInstance();
        date.setTime(r.date.getTime());
        editDate = Calendar.getInstance();
        editDate.setTime(r.editDate.getTime());
        rateId = r.rateId;
    }

    public boolean equals(Object o) {
        if(o instanceof CommentDTO) {
            CommentDTO r = (CommentDTO)o;
            return id == r.id;
        }
        return false;
    }
}
