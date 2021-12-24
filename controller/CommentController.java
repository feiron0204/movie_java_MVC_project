package controller;

import java.util.ArrayList;
import java.util.Calendar;

import model.CommentDTO;

public class CommentController {
    private ArrayList<CommentDTO> list;
    private int nextId;
    
    public CommentController() {
        list = new ArrayList<>();
        nextId = 1;
        
        // 평점 예시
        ex();
    }
    
    // 평점 등록
    public void write(CommentDTO r) {
        r.setId(nextId++);
        r.setDate(Calendar.getInstance());
        r.setEditDate(Calendar.getInstance());
        list.add(r);
    }
    
    // 평점 전체 보기
    public ArrayList<CommentDTO> selectAll() {
        ArrayList<CommentDTO> temp = new ArrayList<>();
        for(CommentDTO r : list) {
            temp.add(new CommentDTO(r));
        }
        return temp;
    }
    
    // logIn id별 코멘트 보기
    public ArrayList<CommentDTO> selectBywriterId(int userId) {
        ArrayList<CommentDTO> temp = new ArrayList<>();
        for(CommentDTO c : list) {
            if(c.getUserId() == userId) {
                temp.add(new CommentDTO(c));
            }
        }
        return temp;
    }
    
    // 회원별 코멘트 -> userId 반환
    public int selectByWriterIdByMovie(int userId, int movieId) {
        ArrayList<CommentDTO> list = selectBywriterId(userId);
        
        for(CommentDTO c : list) {
            if(c.getMovieId() == movieId) {
                return c.getUserId();
            }
        }
        return -1;
    }
    
    // 회원별 해당 영화 코멘트 유무
    public boolean selectByUserRateByMovie(int userRate, int movieId) {
        for(CommentDTO c : list) {
            if(c.getUserId() == userRate && c.getMovieId() == movieId) {
                return true;
            }
        }
        return false;
        
    }
    
    // 회원별 코멘트 -> CommentDTO 반환
    public CommentDTO selectByWriterMovieComment(int userId, int movieId) {
        ArrayList<CommentDTO> list = selectBywriterId(userId);
        
        for(CommentDTO c : list) {
            if(c.getMovieId() == movieId) {
                return new CommentDTO(c);
            }
        }
        return null;
    }
    
    
    
    // 등급별 평점 출력
    // 일반
    public ArrayList<CommentDTO> commentA(int rateId){
        ArrayList<CommentDTO> temp = new ArrayList<>();
        for(CommentDTO r : list) {
            if(r.getRateId() == rateId) {
                temp.add(new CommentDTO(r));
            }
        }
        return temp;
    }
    
    // 평론가
    public ArrayList<CommentDTO> commentB(int rateId){
        ArrayList<CommentDTO> temp = new ArrayList<>();
        for(CommentDTO r : list) {
            if(r.getRateId() == rateId) {
                temp.add(new CommentDTO(r));
            }
        }
        return temp;
    }
    
    
    // 영화별 평점
    public ArrayList<CommentDTO> selectMovie(int movieId) {
        ArrayList<CommentDTO> temp = new ArrayList<>();
        for(CommentDTO r : list) {
            if(r.getMovieId() == movieId) {
                temp.add(new CommentDTO(r));
            }
        }
        return temp;
    }
    
    // 영화별 평균 평점 (5점 만점)
    public double movieAverage(int movieId) {
        double sum = 0;
        int count = 0;
        for(CommentDTO r : list) {
            if(r.getMovieId() == movieId) {
                sum += r.getRate();
                count++;
            }
        }
        return sum / count;
    }
    
    // 평론가별 평균 평점
    public double BAverage(int rateB) {
        double sum = 0;
        int count = 0;
        for(CommentDTO r : list) {
            if(r.getRateId() == rateB) {
                sum += r.getRate();
                count++;
            }
        }
        return sum / count;
    }
    
    // 평점 상세 보기
    public CommentDTO selectOne(int id) {
        for(CommentDTO r : list) {
            if(r.getId() == id) {
                return new CommentDTO(r);
            }
        }
        return null;
    }
    
    // 수정
    public void update(CommentDTO r) {
        r.setEditDate(Calendar.getInstance());
        list.set(list.indexOf(r), r);
    }
    
    // 삭제
    public void delete(int id) {
        list.remove(selectOne(id));
    }
    
    
    // 회원 탈퇴 시 삭제
    public void deleteByUser(int userId) {
        for(int i = 0; i < list.size(); i++) {
            CommentDTO r = list.get(i);
            if(r.getUserId() == userId) {
                list.remove(i);
                i--;
            }
        }
    }
    
    // 영화 삭제 시 삭제
    public void deleteByMovie(int movieId) {
        for(int i = 0; i < list.size(); i++) {
            CommentDTO r = list.get(i);
            if(r.getMovieId() == movieId) {
                list.remove(i);
                i--;
            }
        }
    }
    
    public void ex() {
        CommentDTO c = new CommentDTO();
        
        c.setMovieId(1);
        c.setRate(4.0);
        c.setReview("코멘트 1-1");
        c.setUserId(1);
        c.setRateId(1);
        write(c);
        
        c = new CommentDTO();
        
        c.setMovieId(1);
        c.setRate(3.0);
        c.setReview("코멘트 1-2");
        c.setUserId(1);
        c.setRateId(1);
        write(c);
        
        c = new CommentDTO();
        
        c.setMovieId(2);
        c.setRate(4.4);
        c.setReview("코멘트 2-1");
        c.setUserId(1);
        c.setRateId(1);
        write(c);
        
        c = new CommentDTO();
        
        c.setMovieId(2);
        c.setRate(2.3);
        c.setReview("코멘트 2-2");
        c.setUserId(1);
        c.setRateId(1);
        write(c);
        
        c = new CommentDTO();
        
        c.setMovieId(3);
        c.setRate(4.8);
        c.setReview("코멘트 3");
        c.setUserId(2);
        c.setRateId(2);
        write(c);
        
        c = new CommentDTO();
        
        c.setMovieId(4);
        c.setExpReview("기대평 1");
        c.setUserId(2);
        c.setRateId(2);
        write(c);
    }

}
