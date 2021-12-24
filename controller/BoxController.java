package controller;

import java.util.ArrayList;

import model.BoxDTO;

public class BoxController {
    private ArrayList<BoxDTO> list;
    private int nextId;
    
    public BoxController() {
        list = new ArrayList<>();
        nextId = 1;
        // 예시
        ex();
    }
    
    // 상영관 등록/수정 메소드 - insert(BoxDTO)
    public void insert(BoxDTO b) {
        b.setId(nextId++);
        list.add(b);
    }
    
    // 상영관 전체 출력 메소드 - selectAll()
    public ArrayList<BoxDTO> selectAll(){
        ArrayList<BoxDTO> temp = new ArrayList<>();
        
        for(BoxDTO b : list) {
            temp.add(new BoxDTO(b));
        }
        return temp;
    }
    
    // 특정 영화관 상영관 전체 출력 메소드 - selectAllbyTheaterId(int)
    public ArrayList<BoxDTO> selectAllbyTheaterId(int theaterId){
        ArrayList<BoxDTO> temp = new ArrayList<>();
        for(BoxDTO b : list) {
            if(b.getTheaterId() == theaterId) {
                temp.add(new BoxDTO(b));
            }
        }
        return temp;
    }
    
    // 특정 영화관의 특정 상영관 출력
    public BoxDTO selectOnebyTheaterIdAndId(int theaterId, int id) {
        for(BoxDTO b : list) {
            if(b.getId() == id && b.getTheaterId() == theaterId) {
                return new BoxDTO(b);
            }
        }
        return null;
    }
    
    
    // 특정 극장 상영관 삭제 메소드 - delete(int)
    public void delete(int theaterId) {
        for(int i = 0; i < list.size(); i++) {
            BoxDTO b = list.get(i);
            if(b.getTheaterId() == theaterId) {
                list.remove(i);
                i--;
            }
        }
    }
        
    // 특정 극장 상영관 수 return 메소드 - sizebyTheaterId(int)
    public int sizebyTheaterId(int theaterId) {
        ArrayList<BoxDTO> list = selectAllbyTheaterId(theaterId);
        return list.size();
    }
    
    // 특정 극장 특정 상영관 이름 return 메소드
    public String namebyTheaterIdWithId(int theaterId, int id) {
        BoxDTO b = selectOnebyTheaterIdAndId(theaterId, id);
        return b.getName();
    }
    
    
    
    public void ex() {
        BoxDTO b = new BoxDTO();
        //1
        b.setTheaterId(1);
        b.setName("1관");
        insert(b);
        
        b = new BoxDTO();
        //2
        b.setTheaterId(1);
        b.setName("2관");
        insert(b);
        
        b = new BoxDTO();
        //3
        b.setTheaterId(1);
        b.setName("3관");
        insert(b);
        
        b = new BoxDTO();
        //4
        b.setTheaterId(1);
        b.setName("4관");
        insert(b);
        
        b = new BoxDTO();
        
        b.setTheaterId(1);
        b.setName("5관");
        insert(b);
        
        b = new BoxDTO();
        
        b.setTheaterId(2);
        b.setName("1관");
        insert(b);
        
        b = new BoxDTO();
        
        b.setTheaterId(2);
        b.setName("2관");
        insert(b);
        
        b = new BoxDTO();
        
        b.setTheaterId(2);
        b.setName("3관");
        insert(b);
        
        b = new BoxDTO();
        
        b.setTheaterId(2);
        b.setName("4관");
        insert(b);
        
        b = new BoxDTO();
        
        b.setTheaterId(2);
        b.setName("5관");
        insert(b);
        
        b = new BoxDTO();
        
        b.setTheaterId(3);
        b.setName("1관");
        insert(b);
        
        b = new BoxDTO();
        
        b.setTheaterId(3);
        b.setName("2관");
        insert(b);
        
        b = new BoxDTO();
        
        b.setTheaterId(3);
        b.setName("3관");
        insert(b);
        
        b = new BoxDTO();
        
        b.setTheaterId(4);
        b.setName("1관");
        insert(b);
        
        b = new BoxDTO();
        
        b.setTheaterId(4);
        b.setName("2관");
        insert(b);
        
        b = new BoxDTO();
        
        b.setTheaterId(4);
        b.setName("3관");
        insert(b);
        
        b = new BoxDTO();
        
        b.setTheaterId(4);
        b.setName("4관");
        insert(b);
    }

}
