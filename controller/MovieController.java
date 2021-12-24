package controller;

import java.util.ArrayList;

import model.MovieDTO;

public class MovieController {
    private ArrayList<MovieDTO> list;
    private int nextId;
    
    // 생성자
    public MovieController() {
        list = new ArrayList<>();
        nextId = 1;
        
        // 예시
        ex();
    }
    
    
    // 영화 등록 메소드 - register(MovieDTO)
    public void register(MovieDTO m) {
        m.setId(nextId++);
        list.add(m);
    }
    
    // 영화 전체 ArrayList 전달 메소드 - selectAll()
    public ArrayList<MovieDTO> selectAll() {
        ArrayList<MovieDTO> temp = new ArrayList<>();
        
        for(MovieDTO m : list) {
            temp.add(new MovieDTO(m));
        }
        return temp;
    }
    
    // 전체 영화 수 전달 메소드
    public int sizeAll() {
        return selectAll().size();
    }
    
    // 영화 상세보기
    public MovieDTO selectOne(int id) {
        for(MovieDTO m : list) {
            if(m.getId()==id) {
                return new MovieDTO(m);
            }
        }
        return null;
    }
    
    // 영화 수정
    public void update(MovieDTO m) {
        list.set(list.indexOf(m), m);
    }
    
    // 영화 삭제
    public void delete(int id) {
        list.remove(selectOne(id));
    }
    
    // 고객이 예매한 영화 모두 보기
    public ArrayList<MovieDTO> reserveM(int userId) {
        ArrayList<MovieDTO> temp = new ArrayList<>();
        
        for(MovieDTO m : list) {
            if(m.getUserId() == userId) {
                temp.add(new MovieDTO(m));
            }
        }
        return temp;
    }
    
    // movie title로 movieId 찾기
    public int searchByTitle(String title) {
        for(MovieDTO m : list) {
            if(m.getTitle().equals(title)) {
                return m.getId();
            }
        }
        return -1;
    }
    
    // movieId로 movie Title 찾기
    public String searchBymovieId(int movieId) {
        for(MovieDTO m : list) {
            if(m.getId() == movieId) {
                return m.getTitle();
            }
        }
        return null;
    }
    
    // 특정 영화 러닝타임 전달 메소드
    public int runTime(int movieId) {
        for(MovieDTO m : list) {
            if(m.getId() == movieId) {
                return m.getRunTime();
            }
        }
        return -1;
    }
    
    // 예시
    public void ex() {
        MovieDTO m = new MovieDTO();
        m.setTitle("이터널스");
        m.setGenre("액션");
        m.setSummary("줄거리 1");
        m.setRate("12세관람가");
        m.setDirector("클로이 자오");
        m.setRelease("20211103");
        m.setRunTime(155);
        register(m);
        
        m = new MovieDTO();
        
        m.setTitle("듄");
        m.setGenre("모험, 드라마, SF");
        m.setSummary("줄거리 2");
        m.setRate("12세관람가");
        m.setDirector("드니 빌뇌브");
        m.setRelease("20211020");
        m.setRunTime(155);
        register(m);
        
        m = new MovieDTO();
        
        m.setTitle("강릉");
        m.setGenre("범죄, 액션");
        m.setSummary("줄거리 3");
        m.setRate("청소년관람불가");
        m.setDirector("윤영빈");
        m.setRelease("20211110");
        m.setRunTime(119);
        register(m);
        
        m = new MovieDTO();
        
        m.setTitle("장르만 로맨스");
        m.setGenre("드라마");
        m.setSummary("줄거리 4");
        m.setRate("15세관람가");
        m.setDirector("조은지");
        m.setRelease("20211117");
        m.setRunTime(113);
        register(m);
        
    }
}
