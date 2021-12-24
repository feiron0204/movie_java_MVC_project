package viewer;

import java.util.ArrayList;
import java.util.Scanner;

import controller.BoxController;
import model.BoxDTO;
import util.ScannerUtil;

public class BoxViewer {
    private BoxController boxController;
    private Scanner scanner;
    
    // 생성자
    public BoxViewer() {
        boxController = new BoxController();
    }
    
    // setter
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
    
    // Theater 관련
    // 등록 메소드 -> TheaterViewer 연결
    public void insert(int theaterId) {
        BoxDTO b = new BoxDTO();
        // 극장 id BoxDTO에 저장
        b.setTheaterId(theaterId);
        
        String message = new String("\n상영관의 이름을 입력해주세요.");
        b.setName(ScannerUtil.nextLine(scanner, message));
        
        boxController.insert(b);
        
        message = new String("\n추가 등록하시겠습니까? (Y/N)");
        String yesNo = ScannerUtil.nextLine(scanner, message);
        
        if(yesNo.equalsIgnoreCase("Y")) {
            insert(theaterId);
        }
    }
    
    // 수정 메소드 -> TheaterViewer 연결
    public void update(int theaterId) {
        // 해당 극장 상영관 삭제
        boxController.delete(theaterId);
        // 새로 등록
        insert(theaterId);
    }
    
    // 삭제 메소드 - 극장 삭제 시 -> TheaterViewer 연결
    public void delete(int theaterId) {
        boxController.delete(theaterId);
    }
        
    // 상영관 수
     public int size(int theaterId) {
         return boxController.sizebyTheaterId(theaterId);
     }
     
     // 특정 극장 상영관 정보 -> showInfoViewer 연결
     public void listbyTheaterId(int theaterId) {
         ArrayList<BoxDTO> list = boxController.selectAllbyTheaterId(theaterId);
         System.out.println("상영관 정보");
         for(int i = 0; i < list.size(); i++) {
             BoxDTO b = list.get(i);
             System.out.println(b.getId() + ". " +b.getName());
         }
     }
     
     public String boxName(int theaterId, int id){
         return boxController.selectOnebyTheaterIdAndId(theaterId, id).getName();
     }

}
