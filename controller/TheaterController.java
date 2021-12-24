package controller;

import java.util.ArrayList;

import model.TheaterDTO;

public class TheaterController {
    // 극장 출력 및 등록/수정/삭제에 필요한 메소드 모음집
    
    private ArrayList<TheaterDTO> list;
    private int nextId;
    
    // 생성자
    public TheaterController() {
        list = new ArrayList<>();
        nextId = 1;
        
        // 극장 예시
        ex();
    }
//---------------------------------------------------------------
    
    // 등록 메소드 - insert(TheaterDTO)
    public void insert(TheaterDTO t) {
        t.setId(nextId++);
        list.add(t);
    }
    
    // 극장 전체 ArrayList 전달 - selectAll()
    public ArrayList<TheaterDTO> selectAll(){
        ArrayList<TheaterDTO> temp = new ArrayList<>();
        for(TheaterDTO t : list) {
            temp.add(new TheaterDTO(t));
        }
        return temp;
    }
    
    // 특정 극장 상세 보기 메소드 - selectOne(int)
    public TheaterDTO selectOne(int id) {
        for(TheaterDTO t : list) {
            if(t.getId() == id) {
                return new TheaterDTO(t);
            }
        }
        return null;
    }
    
    // 영화별 극장 ArrayList 전달 - movieBy(int)
    public ArrayList<TheaterDTO> movieBy(int movieId){
        ArrayList<TheaterDTO> temp = new ArrayList<>();
        for(TheaterDTO t : list) {
            if(t.getMovieId() == movieId) {
                temp.add(new TheaterDTO(t));
            }
        }
        return temp;
    }
    
    // 수정 메소드 - update(TheaterDTO)
    public void update(TheaterDTO t) {
        list.set(list.indexOf(t), t);
    }
    
    // 삭제 메소드 - delete(int)
    public void delete(int id) {
        list.remove(selectOne(id));
    }
    
    // search - 이름 비교
    public TheaterDTO search(String branch) {
        for(TheaterDTO t : list) {
            if(t.getBranch().equals(branch)) {
                return new TheaterDTO(t);
            }
        }
        return null;
    }
    
    // 극장 id 매칭해서 극장 이름 반환
    public String theaterNamebytheaterId(int theaterId) {
        TheaterDTO t = selectOne(theaterId);
        return t.getBranch();
    }
    
    
    public void ex() {
        TheaterDTO t = new TheaterDTO();
        t.setBranch("강남점");
        t.setLocation("서울특별시 강남구 강남대로 438(역삼동)");
        t.setTel("02-111-1111");
        
        insert(t);
        
        t = new TheaterDTO();
        
        t.setBranch("압구정점");
        t.setLocation("서울특별시 강남구 압구정로30길 45(신사동)");
        t.setTel("02-2222-2222");
        
        insert(t);
        
        t = new TheaterDTO();
        
        t.setBranch("춘천점");
        t.setLocation("강원도 춘천시 지석로 80, 3층~8층(퇴계동)");
        t.setTel("064-1234-4567");
        
        insert(t);
        
        t = new TheaterDTO();
        
        t.setBranch("남포점");
        t.setLocation("부산관역시 중구 남포동 6가 92-1");
        t.setTel("051-1234-5678");
        
        insert(t);
    }

}
