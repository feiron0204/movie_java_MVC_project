package viewer;

import java.util.ArrayList;
import java.util.Scanner;

import controller.TheaterController;
import model.TheaterDTO;
import model.UserDTO;
import util.ScannerUtil;

public class TheaterViewer {
    // 극장 리스트, 상세 정보 및 극장 등록/수정/삭제가 가능한 메소드들이 담긴 클래스
    // 기본 field
    private Scanner scanner;
    private TheaterController theaterController;
    // 메소드에 필요한 옵션 field
    private UserDTO logIn;
    private ShowInfoViewer showInfoViewer;
    private BoxViewer boxViewer;
    
    // 생성자
    public TheaterViewer() {
        theaterController = new TheaterController();
    }
    
    // setter
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
    
    public void setLogIn(UserDTO logIn) {
        this.logIn = logIn;
    }
        
    public void setShowInfoViewer (ShowInfoViewer showInfoViewer) {
        this.showInfoViewer = showInfoViewer;
    }
    
    public void setBoxViewer(BoxViewer boxViewer) {
        this.boxViewer = boxViewer;
    }
    
 //----------------------------------------------------------------------------------   
    public void showTheater() {
        while(true) {
            String message = new String("\n1. 극장 리스트 2. 극장별 상영작 3. 뒤로 가기");
            int userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);
            
            if(userChoice == 1) {
                // 극장 리스트 출력
                printAll();
                
                message = new String("\n상세 보기할 극장의 번호를 입력해주세요.\n뒤로 가시려면 0을 입력해주세요.");
                int num = ScannerUtil.nextInt(scanner, message, 0, theaterSize());
                if(num != 0) {
                    // 상세 보기할 극장 정보 출력
                    printOne(num);
                    
                    message = new String("\n1. 상영 스케쥴 2. 뒤로 가기");
                    int num2 = ScannerUtil.nextInt(scanner, message, 1, 2);
                    
                    if(num2 == 1) {
                        // 상영정보viewer 연결
                        showInfoViewer.printBytheaterId(num);
                    }
                    
                } else {
                    System.out.println("\n메뉴 화면으로 돌아갑니다.");
                }
                
            } else if(userChoice == 2) {
                // 극장 상영표 전체
                showInfoViewer.printAll();
                                
            } else if(userChoice == 3) {
                System.out.println("\n이전 화면으로 돌아갑니다.");
                break;
            }
        }
        
        
    }
    
    
    
//-------------------------------------------------------------------------------------------
    // 관리자 메뉴
    public void manageTheater() {
        while(true) {
            String message = new String("\n1. 전체 극장 관리 2. 신규 극장 등록 3. 뒤로 가기");
            int userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);
            
            if(userChoice == 1) {
                // 극장 리스트 출력
                printAll();
                
                message = new String("\n상세 보기할 극장의 번호를 입력해주세요.\n뒤로 가시려면 0을 입력해주세요.");
                int num = ScannerUtil.nextInt(scanner, message, 0, theaterSize());
                if(num != 0) {
                    // 상세 보기할 극장 정보 출력
                    printOne(num);
                    
                    message = new String("\n1. 수정 2. 삭제 3. 뒤로가기");
                    int selectNum = ScannerUtil.nextInt(scanner, message, 1, 3);
                    
                    if(selectNum == 1) {
                        // 수정
                        update(num);
                        
                    } else if(selectNum == 2) {
                        // 삭제
                        delete(num);
                        
                    } else if(selectNum == 3) {
                        System.out.println("\n메뉴 화면으로 돌아갑니다.");
                    }
                    
                } else {
                    System.out.println("\n메뉴 화면으로 돌아갑니다.");
                }
                
            } else if(userChoice == 2) {
                // 등록 메소드
                register();
                
            } else if(userChoice == 3) {
                System.out.println("이전 화면으로 돌아갑니다.");
                break;
            }
        }
    }
    
    // 등록 메소드(관리자 전용) - register()
    private void register() {
        String message = new String("\n새로 등록할 극장의 지점명을 입력해주세요.");
        String branch = ScannerUtil.nextLine(scanner, message);
        
        message = new String("\n새로 등록할 극장의 위치를 입력해주세요.");
        String location = ScannerUtil.nextLine(scanner, message);
        
        message = new String("\n새로 등록할 극장의 연락처를 입력해주세요.");
        String tel = ScannerUtil.btelnextLine(scanner, message);
        
        // 극장 정보를 TheaterDTO에 저장
        TheaterDTO temp = new TheaterDTO();
        temp.setBranch(branch);
        temp.setLocation(location);
        temp.setTel(tel);
        
        theaterController.insert(temp);
        
        // 극장 상영관 등록 -> boxViewer에 연결
        // theater가 insert 실행이 끝나야 id가 나오므로 아래 줄에 위치
        boxViewer.insert(temp.getId());
        
        System.out.println("\n성공적으로 극장 등록이 완료되었습니다!");
    }
    
    // 수정 메소드(관리자 전용) - update(id)
    private void update(int id) {
        TheaterDTO t = theaterController.selectOne(id);
        
        String message = new String("\n새로운 지점명을 입력해주세요.");
        t.setBranch(ScannerUtil.nextLine(scanner, message));
        
        message = new String("\n새로운 연락처를 입력해주세요.");
        t.setTel(ScannerUtil.btelnextLine(scanner, message));
        
        message = new String("\n정말로 수정하시겠습니끼? (Y/N)");
        String yesNo = ScannerUtil.yesNonextLine(scanner, message);
        
        if(yesNo.equalsIgnoreCase("Y")) {
            // 수정 확정인 경우 상영관 정보도 수정 -> boxViewer 연결
            boxViewer.update(id);
            
            message = new String("\n수정을 위해 관리자 비밀번호를 입력해주세요.");
            String password = ScannerUtil.nextLine(scanner, message);
            
            if(logIn.getPassword().equals(password)) {
                theaterController.update(t);
                // 극장 수정 시 기존 상영 정보 삭제
                showInfoViewer.deleteBytheaterId(id);
                
                System.out.println("\n성공적으로 수정이 완료되었습니다!");
            } else {
                System.out.println("\n비밀번호가 틀려 수정에 실패하였습니다.");
            }
        }
    }
    
    // 상영관 리스트 출력 - printAll()
    public void printAll() {
        ArrayList<TheaterDTO> list = theaterController.selectAll();
        System.out.println("\n| NO |  극 장 명  | 상영관 수 |");
        for(TheaterDTO t : list) {
            System.out.println("| " + t.getId() + " | " + t.getBranch() + " | "+ boxViewer.size(t.getId()) + " |");
        }
        System.out.println();
    }
    
    // 특정 극장 정보 출력
    private void printOne(int id) {
        TheaterDTO t = theaterController.selectOne(id);
        
        System.out.println("--------------------------------------");
        System.out.println("지점명 " + t.getBranch());
        System.out.println("위치 " + t.getLocation());
        System.out.println("연락처 " + t.getTel());
        // 해당 극장 상영관 정보 출력
        boxViewer.listbyTheaterId(id);
        System.out.println("--------------------------------------");
    }
    
    // 삭제 메소드(관리자 전용) - delete(int)
    private void delete(int id) {
        String message = new String("\n정말 삭제하시겠습니까? (Y/N)");
        String yesNo = ScannerUtil.yesNonextLine(scanner, message);
        
        if(yesNo.equalsIgnoreCase("Y")) {
            message = new String("\n관리자 비밀번호를 입력해주세요.");
            String password = ScannerUtil.nextLine(scanner, message);
            
            // 비밀번호 일치
            if(password.equals(logIn.getPassword())) {
                System.out.println("\n성공적으로 삭제가 완료되었습니다!");
                // 해당 극장 상영 정보 삭제
                showInfoViewer.deleteBytheaterId(id);
                // 해당 극장 상영관 정보 삭제
                boxViewer.delete(id);
                // 해당 극장 삭제
                theaterController.delete(id);
                
            } else {
                System.out.println("비밀번호가 틀려 삭제에 실패하였습니다.");
            }
        }
    }
    
    // 전체 상영관 정보 list return 메소드 - theaterlist()
    public ArrayList<TheaterDTO> theaterlist(){
        ArrayList<TheaterDTO> list = theaterController.selectAll();
        return list;
    }
    
    // 극장 수 메소드 - size()
    public int theaterSize() {
        ArrayList<TheaterDTO> list = theaterController.selectAll();
        return list.size();
    }
    
    // theaterId로 극장명 찾기
    public String theaterName(int theaterId) {
        return theaterController.theaterNamebytheaterId(theaterId);
    }
}
