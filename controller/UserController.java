package controller;

import java.util.ArrayList;
import java.util.Calendar;

import model.UserDTO;

public class UserController {
    // 사용자 운영에 필요한 메소드 모음집
    // 추가, 수정, 삭제, 출력 메소드 
    
    private ArrayList<UserDTO> list;
    private int nextId;

    // 일반 관람객 - 회원가입 시 무조건 일반 관람객으로 등록되므로 필드 필요
    private final int RATE_A = 1;
    
    // UserController 생성자
    public UserController() {
        list = new ArrayList<>();
        nextId = 4; // ex()에 3명이 이미 입력되어 있기 때문에 4로 초기화
        
        // 예제       
        ex();
    }
    
 // ------------------------------------------------------------
    
    // 회원가입 메소드 - join(UserDTO)
    public void join(UserDTO u) {
        u.setId(nextId++);
        // 회원가입 시 회원 등급은 무조건 일반 관람객
        u.setUserRate(RATE_A);
        // 회원가입 시 날짜 생성(추후 관리자가 가입일 순으로 정렬할 수 있도록) 
        u.setJoinDate(Calendar.getInstance());
        list.add(u);
    }
    
    // 특정 회원 정보 메소드(공통) - selectOne(int)
    public UserDTO selectOne(int id) {
        for(UserDTO u : list) {
            if(u.getId() == id) {
                return new UserDTO(u);
            }
        }
        return null;
    }
    
    // 전체 회원 list 메소드(관리자 전용) - selectAll()
    public ArrayList<UserDTO> selectAll(){
        ArrayList<UserDTO> temp = new ArrayList<>();
        for(UserDTO u : list) {
            temp.add(new UserDTO(u));
        }
        return temp;
    }
    
    // 수정 메소드 - update(UserDTO)
    public void update(UserDTO u) {
        list.set(list.indexOf(u), u);
    }
    
    // 탈퇴 메소드 - delete(int)
    public void delete(int id) {
        list.remove(selectOne(id));
    }
    
    // 검증 메소드(로그인 아이디 & 패스워드 기존 list에 있는지 확인) - authUser(String, String)
    public UserDTO authUser(String username, String password) {
        for(UserDTO u : list) {
            if(u.getUsername().equalsIgnoreCase(username) && u.getPassword().equals(password)) {
                return new UserDTO(u);
            }
        }
        return null;
    }
        
    // 회원 아이디 유무 확인 메소드 - validateUser(String)
    public boolean validateUser(String username) {
        if(username.equalsIgnoreCase("X")) {
            return true;
        }
        for(UserDTO u : list) {
            if(u.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }
    
    // 회원가입 여부 확인 메소드(매개변수: 이름, 생년월일, 휴대전화 번호) - check(String, String, String)
    public boolean check(String name, String birth, String tel) {
        for(UserDTO u : list) {
            if(u.getName().equals(name) &&
                    u.getBirth().equals(birth) && u.getTel().equals(tel)) {
                return true;
            }
        }
        return false;
    }
    
    // 회원 이름으로 아이디 전달 메소드 - usernameByname(String)
    public String usernameByname(String name) {
        for(UserDTO u : list) {
            if(u.getName().equals(name)) {
                return u.getUsername();
            }
        }
        return null;
    }
    
    
    // 예시
    public void ex() {
        UserDTO u = new UserDTO();
        u.setId(1);
        u.setUserRate(1);
        u.setUsername("A");
        u.setPassword("1");
        u.setNickname("일반A");
        u.setName("A");
        u.setBirth("20020208");
        u.setEmail("a@aaa.com");
        u.setTel("010-1111-1111");
        u.setJoinDate(Calendar.getInstance());
        list.add(u);
        
        u = new UserDTO();
        
        u.setId(2);
        u.setUserRate(2);
        u.setUsername("B");
        u.setPassword("1");
        u.setNickname("평론가B");
        u.setName("B");
        u.setBirth("19880505");
        u.setEmail("a@aaa.com");
        u.setTel("010-1111-1111");
        u.setJoinDate(Calendar.getInstance());
        list.add(u);
        
        u = new UserDTO();
        
        u.setId(3);
        u.setUserRate(3);
        u.setUsername("C");
        u.setPassword("1");
        u.setNickname("관리자C");
        u.setName("C");
        u.setBirth("19951015");
        u.setEmail("a@aaa.com");
        u.setTel("010-1111-1111");
        u.setJoinDate(Calendar.getInstance());
        list.add(u);
    }
    
    

}
