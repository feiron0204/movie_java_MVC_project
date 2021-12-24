package controller;

import java.util.ArrayList;

import model.ShowInfoDTO;

public class ShowInfoController {
    private ArrayList<ShowInfoDTO> list;
    private int nextId;
    
    public ShowInfoController() {
        list = new ArrayList<>();
        nextId = 1;
        // 예시
        ex();
    }
    
    // 등록
    public void insert(ShowInfoDTO s) {
        s.setId(nextId++);
        list.add(s);
    }
    
    // 상영 정보 전체 조회
    public ArrayList<ShowInfoDTO> selectAll() {
        ArrayList<ShowInfoDTO> temp = new ArrayList<>();
        for(ShowInfoDTO s : list) {
            temp.add(new ShowInfoDTO(s));
        }
        return temp;
    }
    
    // 영화별 상영 정보 조회
    public ArrayList<ShowInfoDTO> movieBy(int movieId) {
        ArrayList<ShowInfoDTO> temp = new ArrayList<>();
        for(ShowInfoDTO s : list) {
            if(s.getMovieId() == movieId) {
                temp.add(new ShowInfoDTO(s));
            }
        }
        return temp;
    }
    
    // 극장별 상영 정보 조회
    public ArrayList<ShowInfoDTO> theaterBy(int theaterId) {
        ArrayList<ShowInfoDTO> temp = new ArrayList<>();
        for(ShowInfoDTO s : list) {
            if(s.getTheaterId() == theaterId) {
                temp.add(new ShowInfoDTO(s));
            }
        }
        return temp;
    }
    
    // id 상영 정보 조회
    public ShowInfoDTO selectOne(int id) {
        for(ShowInfoDTO s : list) {
            if(s.getId() == id) {
                return new ShowInfoDTO(s);
            }
        }
        return null;
    }
    
    // 극장id별 영화 상영 유무
    public boolean showOK(int theaterId, int movieId) {
        for(ShowInfoDTO s : list) {
            if(s.getTheaterId() == theaterId && s.getMovieId() == movieId) {
                return true;
            }
        }
        return false;
    }
    
    // 수정
    public void update(ShowInfoDTO s) {
        list.set(list.indexOf(s), s);
    }
    
    // 삭제
    public void delete(int id) {
        list.remove(selectOne(id));
    }
    
    // 영화 삭제 시 상영 정보 삭제
    public void deleteBymovie(int movieId) {
        for(int i = 0; i < list.size(); i++) {
            ShowInfoDTO s = list.get(i);
            if(s.getMovieId() == movieId) {
                list.remove(i);
                i--;
            }
        }
    }
    
    // 극장 폐쇄 시 상영 정보 삭제
    public void deleteBytheater(int theaterId) {
        for(int i = 0; i < list.size(); i++) {
            ShowInfoDTO s = list.get(i);
            if(s.getTheaterId() == theaterId) {
                list.remove(i);
                i--;
            }
        }
    }

    public void ex() {
        ShowInfoDTO s = new ShowInfoDTO();
        // 강남점
        s.setMovieId(1);
        s.setTheaterId(1);
        s.setBoxId(1);
        s.setTable("20211115");
        s.setTime("1410");
        insert(s);
        
        s = new ShowInfoDTO();
        
        s.setMovieId(2);
        s.setTheaterId(1);
        s.setBoxId(2);
        s.setTable("20211115");
        s.setTime("2030");
        insert(s);
        
        s = new ShowInfoDTO();
        
        s.setMovieId(2);
        s.setTheaterId(1);
        s.setBoxId(2);
        s.setTable("20211115");
        s.setTime("1410");
        insert(s);
        
        s = new ShowInfoDTO();
        
        s.setMovieId(3);
        s.setTheaterId(1);
        s.setBoxId(3);
        s.setTable("20211115");
        s.setTime("1815");
        insert(s);
        
        s = new ShowInfoDTO();
        
        s.setMovieId(1);
        s.setTheaterId(1);
        s.setBoxId(4);
        s.setTable("20211117");
        s.setTime("0900");
        insert(s);
        
        s = new ShowInfoDTO();
        
        s.setMovieId(1);
        s.setTheaterId(1);
        s.setBoxId(5);
        s.setTable("20211116");
        s.setTime("1140");
        insert(s);
        
        s = new ShowInfoDTO();
        
        // 압구정점
        s.setMovieId(1);
        s.setTheaterId(2);
        s.setBoxId(6);
        s.setTable("20211113");
        s.setTime("1030");
        insert(s);
        
        s = new ShowInfoDTO();
        
        s.setMovieId(3);
        s.setTheaterId(2);
        s.setBoxId(7);
        s.setTable("20211114");
        s.setTime("1615");
        insert(s);
        
        s = new ShowInfoDTO();
        
        // 춘천점
        s.setMovieId(1);
        s.setTheaterId(3);
        s.setBoxId(11);
        s.setTable("2021112");
        s.setTime("0955");
        insert(s);
        
        s = new ShowInfoDTO();
        
        s.setMovieId(3);
        s.setTheaterId(3);
        s.setBoxId(11);
        s.setTable("20211115");
        s.setTime("1320");
        insert(s);
        
        s = new ShowInfoDTO();
        
        s.setMovieId(3);
        s.setTheaterId(3);
        s.setBoxId(12);
        s.setTable("20211115");
        s.setTime("1410");
        insert(s);
        
        s = new ShowInfoDTO();
        
        // 남포점
        s.setMovieId(1);
        s.setTheaterId(4);
        s.setBoxId(14);
        s.setTable("20211115");
        s.setTime("1410");
        insert(s);
        
        s = new ShowInfoDTO();
        
        s.setMovieId(1);
        s.setTheaterId(4);
        s.setBoxId(15);
        s.setTable("2021112");
        s.setTime("0955");
        insert(s);
        
    }
}
