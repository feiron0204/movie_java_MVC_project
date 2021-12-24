package viewer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import controller.UserController;
import model.UserDTO;
import util.ScannerUtil;

public class UserViewer {
    // 영화 프로그램 이용을 위한 사용자 로그인, 회원가입 및 영화, 극장, 코멘트에 접속할 수 있는 메소드들이 담긴 클래스.
    // 로그인 후 
    // 일반 관람객의 경우 영화, 극장, 코멘트 메뉴 접속 및 MY PAGE 에서 1. 회원 정보 보기 2. 내가 본 영화 확인 및 수정/삭제 가능
    // 전문 평론가는 일반 관람객과 동일하지만 My PAGE 에서 1. 나의 평론 2. 내 정보 및 수정/삭제 가능
    // 관리자는 위 회원과 달리 곧바로 1. 관리자메뉴로 접속해 영화, 극장, 상영정보, 회원 관리 메뉴를 통해 등록/수정/삭제 가 가능하다.
    // 회원가입 시 가입 여부 확인 후
    // 미가입 사용자의 경우 추가 정보를 입력 받은 후 회원가입이 완료되고 로그인 화면으로 이동한다.
    
    // UserViewer 필요 필드
    private Scanner scanner;
    private UserController userController;
    
    // 메뉴에 필요한 옵션 필드
    private UserDTO logIn;
    private MovieViewer movieViewer;
    private TheaterViewer theaterViewer;
    private CommentViewer commentViewer;
    private ShowInfoViewer showInfoViewer;
    
    // 일반 관람객
    private final int RATE_A = 1;
    // 평론가
    private final int RATE_B = 2;
    // 관리자
    private final int RATE_M = 3;
    // 가입일 표시 포맷
    private final String FORMAT = "yyyy/MM/dd";
    
    // 생성자
    public UserViewer() {
        userController = new UserController();
    }
    
    // setter
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
    
    public void setMovieViewer(MovieViewer movieViewer) {
        this.movieViewer = movieViewer;
    }
    
    public void setTheaterViewer(TheaterViewer theaterViewer) {
        this.theaterViewer = theaterViewer;
    }
    
    public void setCommentViewer(CommentViewer commentViewer) {
        this.commentViewer = commentViewer;
    }
    
    public void SetShowInfoViewer(ShowInfoViewer showInforViewer) {
        this.showInfoViewer = showInforViewer;
    }
    
 //-------------------------------------------------------------------------------------------------------------------------
    
    // main에서 컴파일 시 실행되는 메소드 - showIndex()
    public void showIndex() {
        System.out.println("=====================================");
        System.out.println("     ABC 극장에 오신 것을 환영합니다!");
        System.out.println("=====================================\n");
        String message = new String("1. 로그인 2. 회원가입 3. 나가기");
        
        while(true) {
            // 사용자 입력
            int userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);
            
            if(userChoice == 1) {
                // 로그인
                logIn();
                
                if(logIn != null) {
                    // movie, theater, comment, showIn Viewer에 로그인 정보 전달
                    movieViewer.setLogIn(logIn);
                    theaterViewer.setLogIn(logIn);
                    commentViewer.setLogin(logIn);
                    showInfoViewer.setLogIn(logIn);
                    
                    // showMenu
                    showMenu();
                }
            } else if(userChoice == 2) {
                // 회원가입
                join();
                
            } else if(userChoice == 3) {
                // 나가기
                message = new String("\n정말 종료하시겠습니까? (Y/N)");
                String yesNo = ScannerUtil.yesNonextLine(scanner, message);
                
                if(yesNo.equalsIgnoreCase("Y")) {
                    System.out.println("\n영화가 보고 싶을 땐 ABC 극장으로 방문해 주세요!\n");
                    break;
                }
            }
        }
    }
    
    // logIn()
    private void logIn() {
        logIn = null;
        
        while(logIn == null) {
            String message = new String("\n아이디를 입력해주세요.\n뒤로 가시려면 X를 입력해 주세요.");
            String username = ScannerUtil.nextLine(scanner, message);
            
            if(username.equalsIgnoreCase("X")) {
                System.out.println("\n초기 화면으로 돌아갑니다.");
                break;
            }
            
            message = new String("\n비밀번호를 입력해주세요.");
            String password = ScannerUtil.nextLine(scanner, message);
            
            // 입력된 아이디, 비밀번호 검증
            logIn = userController.authUser(username, password);
            
            if(logIn == null) {
                System.out.println("\n아이디 또는 비밀번호가 잘못 입력되었습니다.");
                System.out.println("아이디와 비밀번호를 다시 입력해 주세요.\n");
            } else {
                System.out.println("\n========== * ==========");
                System.out.println("       로그인 성공!");
                System.out.println("========== * ==========");
            }
        }
    }
    
    // 회원가입
    private void join() {
        System.out.println("\n--- 가입여부 확인 ---");
        String message = new String("이름을 입력해주세요.");
        String name = ScannerUtil.nextLine(scanner, message);
        
        message = new String("\n법정생년월일 8자리를 입력해주세요. (yyyyMMdd)");
        String birth = ScannerUtil.birthnextLine(scanner, message);
        
        message = new String("\n휴대전화 번호를 입력해주세요. (000-0000-0000)\n(-를 반드시 포함하여 입력해주세요.)");
        String tel = ScannerUtil.telnextLine(scanner, message);
        
        if(userController.check(name, birth, tel)) {
            System.out.println("\n이미 가입하신 회원입니다.");
            System.out.println("회원 아이디로 로그인 해주세요.");
            System.out.println("회원 아이디: " + userController.usernameByname(name));
            logIn();
            
        } else {
            // 회원가입 진행
            System.out.println("\n가입된 이력이 없으므로 회원 가입을 진행합니다!");
            System.out.println("=====================================\n");
            message = new String("사용하실 아이디를 입력해주세요.");
            String username = ScannerUtil.nextLine(scanner, message);
            
            while(userController.validateUser(username)) {
                System.out.println("\n이미 사용중인 아이디입니다.");
                message = new String("사용하실 아이디 또는 뒤로 가시려면 x를 입력해주세요.");
                username = ScannerUtil.nextLine(scanner, message);
                
                if(username.equalsIgnoreCase("X")) {
                    break;
                }
            }
            
            if(!username.equalsIgnoreCase("X")) {
                message = new String("\n사용하실 비밀번호를 입력해주세요.");
                String password = ScannerUtil.nextLine(scanner, message);
                
                message = new String("\n비밀번호 재확인을 위해 다시 한 번 비밀번호를 입력해주세요.");
                String password2 = ScannerUtil.nextLine(scanner, message);
                
                while(!password.equals(password2)) {
                    System.out.println("\n비밀번호가 잘못 입력되었습니다.");
                    message = new String("비밀번호를 다시 입력해주세요.");
                    password2 = ScannerUtil.nextLine(scanner, message);
                }
             
                message = new String("\n사용하실 닉네임을 입력해주세요.");
                String nickname = ScannerUtil.nextLine(scanner, message);
                
                message = new String("\n본인 확인 이메일을 입력해주세요.");
                String email = ScannerUtil.EnextLine(scanner, message);
                
                int year = Integer.parseInt(birth.substring(0, 4));
                int month = Integer.parseInt(birth.substring(4, 6));
                int day = Integer.parseInt(birth.substring(6));
                
                // 입력된 데이터 UserDTO에 저장
                UserDTO u = new UserDTO();
                u.setUsername(username);
                u.setPassword(password2);
                u.setName(name);
                u.setBirth(birth);
                u.setBirthyear(year);
                u.setBirthmonth(month);
                u.setBirthday(day);
                u.setNickname(nickname);
                u.setEmail(email);
                u.setTel(tel);
                // 신규 회원은 무조건 일반 회원이므로 RATE_A 고정
                u.setUserRate(RATE_A);
                
                userController.join(u);
                
                System.out.println("\n========== * ==========");
                System.out.println("      회원가입 성공!");
                System.out.println("========== * ==========");
                System.out.println("\n초기화면으로 돌아갑니다.");
                System.out.println("로그인 후 영화를 즐겨주세요!\n");
                logIn();
            }
        }
    }
    
    // menu()
    private void showMenu() {
        // 관리자일 경우 일반 관람객 & 전문 평론가와 다른 메뉴 출력
        if(logIn.getUserRate() == RATE_M) {
            
            while(true) {
                String message = new String("\n1. 관리자 메뉴 가기 2. 로그아웃");
                int userChoice = ScannerUtil.nextInt(scanner, message, 1, 2);
                
                if(userChoice == 1) {
                    // 관리자 메뉴
                    myPage();
                    
                } else if(userChoice == 2) {
                    // 로그아웃
                    message = new String("\n정말 로그아웃하시겠습니까? (Y/N)");
                    String yesNo = ScannerUtil.yesNonextLine(scanner, message);
                    
                    if(yesNo.equalsIgnoreCase("Y")) {
                        System.out.println("\n이용해 주셔서 감사합니다!");
                        logIn = null;
                        // movie, comment, theater, showInfo setLogIn에 전달
                        movieViewer.setLogIn(null);
                        theaterViewer.setLogIn(null);
                        commentViewer.setLogin(null);
                        showInfoViewer.setLogIn(null);
                        break;
                        
                    } else {
                        System.out.println("\n메뉴로 돌아갑니다.");
                    }
                }
            }
        } else {
            String message = new String("\n1. 영화 2. 극장 3. 영화 코멘트 4. MY PAGE 5. 로그아웃");
            
            while(true) {
                int userChoice = ScannerUtil.nextInt(scanner, message, 1, 5);
                
                if(userChoice == 1) {
                    // movieViewer에 연결 -> 영화 메뉴 출력
                    movieViewer.showMovie();
                    
                } else if (userChoice == 2) {
                    // theaterViewer에 연결 -> 극장 메뉴 출력
                    theaterViewer.showTheater();
                    
                } else if(userChoice == 3) {
                    // 영화 코멘트 - commentViewer에 연결
                    commentViewer.showComment();
                    
                } else if(userChoice == 4) {
                    // MY PAGE
                    myPage();
                    
                } else if(userChoice == 5) {
                    // 로그아웃
                    message = new String("\n정말 로그아웃하시겠습니까? (Y/N)");
                    String yesNo = ScannerUtil.yesNonextLine(scanner, message);
                    
                    if(yesNo.equalsIgnoreCase("Y")) {
                        System.out.println("\n이용해 주셔서 감사합니다!");
                        logIn = null;
                        // movie, comment, theater, showInfo setLogIn에 전달
                        movieViewer.setLogIn(null);
                        theaterViewer.setLogIn(null);
                        commentViewer.setLogin(null);
                        showInfoViewer.setLogIn(null);
                        break;
                        
                    } else {
                        System.out.println("\n메뉴로 돌아갑니다.");
                        showMenu();
                    }
                }
            }
        }
    }
    
    
    // MY PAGE
    private void myPage() {
        System.out.println("\n-------------------------------------------");
        System.out.println(" " + logIn.getNickname() + " 님 안녕하세요!");
        System.out.println("-------------------------------------------");
        
        if(logIn.getUserRate() == RATE_A) {
            // 일반 관람객 전용 페이지
            String message = new String("\n1. 내가 본 영화 2. 내 정보 보기 3. 뒤로가기");
            int userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);
            
            if(userChoice == 1) {
                // 내가 본 영화 코멘트 -> commentViewer에 연결
                commentViewer.printList();
                
            } else if(userChoice == 2) {
                // 내 정보 -> 수정/탈퇴 가능
                myInfo();
                
            } else if(userChoice == 3) {
                System.out.println("\n메뉴로 돌아갑니다.");
            }
            
            
        } else if(logIn.getUserRate() == RATE_B) {
            // 전문 평론가 전용 페이지
            String message = new String("\n1. 나의 평론 보기 2. 내 정보 보기 3. 뒤로가기");
            int userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);
            
            if(userChoice == 1) {
                // 내 평점 보기 -> commentViewer에 연결
                commentViewer.editComment();
                
            } else if(userChoice == 2) {
                // 내 정보
                myInfo();
                
            } else if(userChoice == 3) {
                System.out.println("\n메뉴로 돌아갑니다.");
            }
            
        } else if(logIn.getUserRate() == RATE_M) {
            // 관리자 전용 페이지
            String message = new String("\n1. 영화 관리 2. 극장 관리 3. 상영 정보 관리 4. 회원 관리 5. 내 정보 보기 6. 뒤로 가기");
            int userChoice = ScannerUtil.nextInt(scanner, message, 1, 6);
            
            if(userChoice == 1) {
                // 영화 관리 -> movieViewer에 연결
                movieViewer.manageMovie();
                
            } else if(userChoice == 2) {
                // 극장 관리 -> theaterViewer에 연결
                theaterViewer.manageTheater();
                
            } else if(userChoice == 3) {
                // 상영 정보 관리 -> ShowInfoViewer에 연결
                showInfoViewer.manageShow();
                
            } else if(userChoice == 4) {
                // 회원 관리
                manageC();
                
            } else if(userChoice == 5) {
                // 내 정보
                myInfo();
                
            } else if(userChoice == 6) {
                System.out.println("\n메뉴로 돌아갑니다.");
            }
        }
    }
    
    // userId 정보 출력 -> 닉네임, 이름, 생년월일, 이메일, 휴대전화 (사용자 기본 정보)
    private void printInfo() {
        System.out.println("\n-------------------------------------------");
        System.out.println("< " + logIn.getNickname() + "님의 회원 정보 >");
        System.out.println("-------------------------------------------");
        System.out.println("이   름 | " + logIn.getName());
        System.out.println("생년월일 | " + logIn.getBirthyear() + "년 " 
                         + logIn.getBirthmonth() + "월 " + logIn.getBirthday() + "일");
        System.out.println("이메일  | " + logIn.getEmail());
        System.out.println("휴대전화 | " + logIn.getTel());
        System.out.println("-------------------------------------------\n");
    }
    
    // 내 정보 수정/탈퇴 메소드 - myInfo()
    private void myInfo() {
        // 먼저 나의 정보를 확인할 수 있도록 정보 출력 메소드 실행
        printInfo();
        
        String message = new String("\n1. 수정 2. 탈퇴 3. 뒤로 가기");
        int userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);
        
        if(userChoice == 1) {
            // 수정
            update();
            
        } else if(userChoice == 2) {
            // 탈퇴
            delete();
            
        } else if(userChoice == 3) {
            System.out.println("\n메뉴 화면으로 돌아갑니다.");
        }
    }
    
    // 나의 정보 수정 메소드 - update()
    private void update() {
        UserDTO temp = userController.selectOne(logIn.getId());
        
        String message = new String("\n새로운 닉네임을 입력해주세요.");
        temp.setName(ScannerUtil.nextLine(scanner, message));
        
        message = new String("\n새로운 이메일을 입력해주세요.");
        temp.setEmail(ScannerUtil.EnextLine(scanner, message));
        
        message = new String("\n새로운 전화번호를 입력해주세요. (000-0000-0000)");
        temp.setTel(ScannerUtil.telnextLine(scanner, message));
        
        message = new String("\n정말 수정하시겠습니까? (Y/N)");
        String yesNo = ScannerUtil.yesNonextLine(scanner, message);
        
        if(yesNo.equalsIgnoreCase("Y")) {
            message = new String("\n기존 비밀번호를 입력해주세요.");
            String password = ScannerUtil.nextLine(scanner, message);
            
            // 비밀번호 일치
            if(logIn.getPassword().equals(password)) {
                // 수정된 정보 UserDTO에 반영
                userController.update(temp);
                logIn = temp;
                // 다른 viewer에 setLogin에 전달
                movieViewer.setLogIn(temp);
                theaterViewer.setLogIn(temp);
                commentViewer.setLogin(temp);
                showInfoViewer.setLogIn(temp);
                
                System.out.println("\n성공적으로 회원 정보가 수정되었습니다!");
                // 변경된 정보 출력
                printInfo();
                
            } else {
                System.out.println("\n비밀번호가 틀려 회원 정보 수정에 실패하였습니다.");
            }
        }
    }
    
    // 회원 탈퇴 메소드 - delete()
    private void delete() {
        String message = new String("\n정말 탈퇴하시겠습니까? (Y/N)");
        String yesNo = ScannerUtil.yesNonextLine(scanner, message);
        
        if(yesNo.equalsIgnoreCase("Y")) {
            message = new String("\n기존 비밀번호를 입력해주세요.");
            String password = ScannerUtil.nextLine(scanner, message);
            // 비밀번호 일치
            if(logIn.getPassword().equals(password)) {
                System.out.println("\n성공적으로 회원 탈퇴가 완료되었습니다!");
                System.out.println("그동안 이용해 주셔서 감사합니다.\n");
                // 관람평 삭제 - commentViewer에 연결
                commentViewer.deleteByUserId(logIn.getId());
                // 해당 사용자 UserDTO 삭제
                userController.delete(logIn.getId());
                
                logIn = null;
                // movie, rate, showInfo, theater에 logIn 연결
                movieViewer.setLogIn(null);
                theaterViewer.setLogIn(null);
                commentViewer.setLogin(null);
                showInfoViewer.setLogIn(null);
                
                // 회원 탈퇴가 됐으므로 초기 화면으로 이동
                showIndex();
                
            } else {
                System.out.println("\n비밀번호 오류로 회원 탈퇴에 실패하였습니다.");
            }
        }
    }
    
    // 관리자 회원관리 메소드 - manageC()
    // 회원가입시 무조건 일반 관람객 등급으로 등록되기 때문에 관리자 메뉴에서 회원 등급을 변경할 수 있어야 한다.
    private void manageC() {
        // 회원 리스트 출력
        printList();
                
        String message = new String("\n1. 회원 등급 수정 2. 뒤로 가기");
        int userChoice = ScannerUtil.nextInt(scanner, message, 1, 2);
        
        if(userChoice == 1) {
            message = new String("\n회원 등급을 수정할 회원 번호를 입력해주세요.");
            int userNum = ScannerUtil.nextInt(scanner, message);
            // 해당 회원 UserDTO 정보 가져오기
            UserDTO u = userController.selectOne(userNum);
            
            message = new String("\n새로운 등급을 입력해주세요.");
            u.setUserRate(ScannerUtil.nextInt(scanner, message, RATE_A, RATE_M));
            
            message = new String("\n정말 수정하시겠습니까? (Y/N)");
            String yesNo = ScannerUtil.yesNonextLine(scanner, message);
            
            if(yesNo.equalsIgnoreCase("Y")) {
                message = new String("\n관리자 비밀번호를 입력해주세요.");
                String password = ScannerUtil.nextLine(scanner, message);
                // 비밀번호 일치
                if(password.equals(logIn.getPassword())) {
                    // 수정된 회원 등급 update() 
                    userController.update(u);
                    System.out.println("수정이 완료되었습니다.\n");
                }
            }
        }   
    }
    
    // 회원 리스트 메소드 - printList()
    // 관리자만 볼 수 있는 메소드이다.
    private void printList() {
        ArrayList<UserDTO> list = userController.selectAll();
        
        if(list.isEmpty()) {
            System.out.println("\n아직 등록된 회원이 없습니다.");
        } else {
            // 가입일 yyyy/MM/dd 로 표시하기 위해 SimpleDateFormat 생성
            SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
            
            System.out.println("\n----------------------------------------------------------------------");
            System.out.println("총 회원 수: " + list.size());
            System.out.println("----------------------------------------------------------------------");
            System.out.println("| NO | 등 급 | 닉네임 | 이 름 |  생년월일  |   이메일   |  전화번호  |  가입일  |");
            for(UserDTO u : list) {
               System.out.println("| " + u.getId() + " | " + u.getUserRate() + " | " + u.getNickname() 
                              + " | " + u.getName() + " | " + u.getBirth() + " | " +  u.getEmail() 
                              + " | " + u.getTel() + " | " + sdf.format(u.getJoinDate().getTime()) + " |");
            }
            System.out.println("----------------------------------------------------------------------\n");
            
        }
    }
    
    // 평론가 리스트 메소드 - listB()
    // CommentViewer에 연결
    // 특정 전문 평론가의 코멘트를 보기 위한 평론가 목록 출력
    public void listB() {
        ArrayList<UserDTO> list = userController.selectAll();
        
        if(list.isEmpty()) {
            System.out.println("\n등록된 평론가가 없습니다.");
        } else {
            System.out.println("< 영 화 평 론 가 >");
            System.out.println("| NO | 평론가 |");
            for(UserDTO u : list) {
                if(u.getUserRate() == RATE_B) {
                    System.out.println("| " + u.getId() + " | " + u.getNickname() + " |");
                }
            }
        }
    }
    
    // 전체 회원 수
    public int size() {
        return userController.selectAll().size();
    }
    
    // 닉네임 전달
    public String userNicknameById(int id) {
        UserDTO u = userController.selectOne(id);
        return u.getNickname();
    }
    
    // 해당 회원의 등급 반환
    public int userRate(int userId) {
        UserDTO u = userController.selectOne(userId);
        return u.getUserRate();
    }

}
