package viewer;

import java.util.ArrayList;
import java.util.Scanner;

import controller.ActorController;
import model.ActorDTO;
import util.ScannerUtil;

public class ActorViewer {
    private Scanner scanner;
    private ActorController actorController;
    
    
    public ActorViewer() {
        actorController = new ActorController();
    }
    
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    
    // 배우 등록
    public void insert(int movieId) {
        ActorDTO a = new ActorDTO();
        
        a.setMovieId(movieId);
        
        String message = new String("\n출연진의 이름을 입력해주세요.");
        a.setActor(ScannerUtil.nextLine(scanner, message));
        
        message = new String("\n출연진의 배역을 입력해주세요.");
        a.setRole(ScannerUtil.nextLine(scanner, message));
        
        actorController.insert(a);
        
        message = new String("\n출연진을 추가 등록하겠습니까? Y/N");
        String yesNo = ScannerUtil.nextLine(scanner, message);
        
        if(yesNo.equalsIgnoreCase("Y")) {
            insert(movieId);
        } else {
            System.out.println("출연진 등록을 마칩니다.\n");
        }
    }
    
    // 배우 출력
    public void showActor(int movieId) {
        ArrayList<ActorDTO> list = actorController.actorBymovieId(movieId);
        for(ActorDTO a : list) {
            System.out.println(a.getRole() + " 역 | " + a.getActor());
        }
    }
    
    
    // 영화 삭제 시 배우 삭제
    public void deleteBymovieId(int movieId) {
        actorController.deleteM(movieId);
    }

}
