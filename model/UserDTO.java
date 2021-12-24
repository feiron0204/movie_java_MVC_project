package model;

import java.util.Calendar;

public class UserDTO {
    // 영화 프로그램을 이용에 필요한 사용자 정보
    // id, username, password, nickname, name, birth(int: birthyear, birthmonth, birthday), 
    // email, tel, userRate(상수: 일반 관람객 RATE_A, 전문 평론가 RATE_B, 관리자 RATE_M), joinDate

    // UserDTO field
    private int id;
    // id
    private String username;
    // pw
    private String password;
    // 닉네임
    private String nickname;
    // 이름
    private String name;
    // 생년월일
    private String birth;
    private int birthyear;
    private int birthmonth;
    private int birthday;
    // 이메일
    private String email;
    // 전화번호
    private String tel;
    // 가입일
    private Calendar joinDate;
    // 등급
    private int userRate;

    // getter/setter
    public Calendar getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Calendar joinDate) {
        this.joinDate = joinDate;
    }
    
    public int getUserRate() {
        return userRate;
    }

    public void setUserRate(int userRate) {
        this.userRate = userRate;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthyear() {
        return birthyear;
    }

    public void setBirthyear(int birthyear) {
        this.birthyear = birthyear;
    }

    public int getBirthmonth() {
        return birthmonth;
    }

    public void setBirthmonth(int birthmonth) {
        this.birthmonth = birthmonth;
    }

    public int getBirthday() {
        return birthday;
    }

    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    // 생성자
    public UserDTO() {
        id = 0;
        username = new String();
        password = new String();
        nickname = new String();
        name = new String();
        email = new String();
        tel = new String();
        birthyear = 0;
        birthmonth = 0;
        birthday = 0;
        userRate = 0;
    }

    public UserDTO(UserDTO u) {
        id = u.id;
        username = new String(u.username);
        password = new String(u.password);
        nickname = new String(u.nickname);
        name = new String(u.name);
        email = new String(u.email);
        tel = new String(u.tel);
        birth = new String(u.birth);
        birthyear = u.birthyear;
        birthmonth = u.birthmonth;
        birthday = u.birthday;
        userRate = u.userRate;
        joinDate = Calendar.getInstance();
        joinDate.setTime(u.getJoinDate().getTime());
    }

    public boolean equals(Object o) {
        if (o instanceof UserDTO) {
            UserDTO u = (UserDTO) o;
            return id == u.id;
        }
        return false;
    }

}
