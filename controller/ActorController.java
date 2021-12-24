package controller;

import java.util.ArrayList;

import model.ActorDTO;

public class ActorController {
    
    private ArrayList<ActorDTO> list;
    private int nextId;
    
    public ActorController() {
        list = new ArrayList<>();
        nextId = 1;
        
        // 예시
        ex();
        
    }
    
    // 등록
    public void insert(ActorDTO a) {
        a.setId(nextId++);
        list.add(a);
    }
    
    // 전체 출력
    public ArrayList<ActorDTO> selectAll() {
        ArrayList<ActorDTO> temp = new ArrayList<>();
        for(ActorDTO a : list) {
            temp.add(new ActorDTO(a));
        }
        return temp;
    }
    
    public ActorDTO selectOne(int id) {
        for(ActorDTO a : list) {
            if(a.getId() == id) {
                return new ActorDTO(a);
            }
        }
        return null;
    }
    
    // 수정
    public void update(ActorDTO a) {
        list.set(list.indexOf(a), a);
    }
    
    // 삭제
    public void delete(int id) {
        list.remove(selectOne(id));
    }
    
    // 영화 출연 배우
    public ArrayList<ActorDTO> actorBymovieId(int movieId){
        ArrayList<ActorDTO> temp = new ArrayList<>();
        for(ActorDTO a : list) {
            if(a.getMovieId() == movieId) {
                temp.add(new ActorDTO(a));
            }
        }
        return temp;
    }
    
    // 영화 삭제 시 배우 삭제
    public void deleteM(int movieId) {
        for(int i = 0; i < list.size(); i++) {
            ActorDTO a = list.get(i);
            if(a.getMovieId() == movieId) {
                list.remove(i);
                i--;
            }
        }
    }
    
    // 배우 이름 검색
    public ActorDTO searchName(String name) {
        for(ActorDTO a : list) {
            if(a.getActor().equals(name)) {
                return new ActorDTO(a);
            }
        }
        return null;
    }
    
    
    public void ex() {
        ActorDTO a = new ActorDTO();
        
        a.setMovieId(1);
        a.setActor("안젤리나 졸리");
        a.setRole("테나");
        insert(a);
        
        a = new ActorDTO();
        
        a.setMovieId(1);
        a.setActor("마동석");
        a.setRole("길가메시");
        insert(a);
        
        a = new ActorDTO();
        
        a.setMovieId(1);
        a.setActor("리처드 매든");
        a.setRole("이카리스");
        insert(a);
        
        a = new ActorDTO();
        
        a.setMovieId(1);
        a.setActor("젬마 찬");
        a.setRole("세르시");
        insert(a);
        
        a = new ActorDTO();
        
        a.setMovieId(1);
        a.setActor("리아 맥휴");
        a.setRole("스프라이트");
        insert(a);
        
        a = new ActorDTO();
        
        a.setMovieId(1);
        a.setActor("쿠마일 난지아니");
        a.setRole("킨고");
        insert(a);
        
        a = new ActorDTO();
        
        a.setMovieId(2);
        a.setActor("티모시 샬라메");
        a.setRole("폴 아트레이드");
        insert(a);
        
        a = new ActorDTO();
        
        a.setMovieId(2);
        a.setActor("레베카 퍼거슨");
        a.setRole("레이디 제시카");
        insert(a);
        
        a = new ActorDTO();
        
        a.setMovieId(2);
        a.setActor("오스카 아이삭");
        a.setRole("레토 아트레이드");
        insert(a);
        
        a = new ActorDTO();
        
        a.setMovieId(2);
        a.setActor("젠데이아");
        a.setRole("챠니");
        insert(a);
        
        a = new ActorDTO();
        
        a.setMovieId(2);
        a.setActor("조쉬 브롤린");
        a.setRole("거니 할렉");
        insert(a);
        
        a = new ActorDTO();
        
        a.setMovieId(2);
        a.setActor("제이슨 모모아");
        a.setRole("던컨 아이다호");
        insert(a);
        
        a = new ActorDTO();
        
        a.setMovieId(3);
        a.setActor("유오성");
        a.setRole("김길석");
        insert(a);
        
        a = new ActorDTO();
        
        a.setMovieId(3);
        a.setActor("장혁");
        a.setRole("이민석");
        insert(a);
        
        a = new ActorDTO();
        
        a.setMovieId(4);
        a.setActor("류승룡");
        a.setRole("현");
        insert(a);
        
        a = new ActorDTO();
        
        a.setMovieId(4);
        a.setActor("오나라");
        a.setRole("미애");
        insert(a);
        
        a = new ActorDTO();
        
        a.setMovieId(4);
        a.setActor("김희원");
        a.setRole("순모");
        insert(a);
        
        a = new ActorDTO();
    }
    
}
