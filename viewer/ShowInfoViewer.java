package viewer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import controller.ShowInfoController;
import model.ShowInfoDTO;
import model.TheaterDTO;
import model.UserDTO;
import util.ScannerUtil;

public class ShowInfoViewer {
    private Scanner scanner;
    private ShowInfoController showInfoController;

    private UserDTO logIn;
    private TheaterViewer theaterViewer;
    private BoxViewer boxViewer;
    private MovieViewer movieViewer;

    private final String FORMAT = "yyyyMMdd";

    public ShowInfoViewer() {
        showInfoController = new ShowInfoController();
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public void setLogIn(UserDTO logIn) {
        this.logIn = logIn;
    }

    public void setTheaterViewer(TheaterViewer theaterViewer) {
        this.theaterViewer = theaterViewer;
    }
    
    public void setBoxViewer(BoxViewer boxViewer) {
        this.boxViewer = boxViewer;
    }

    public void setMovieViewer(MovieViewer movieViewer) {
        this.movieViewer = movieViewer;
    }

    // 관리자 메뉴-----------------------------------------------------------------------------------------------------------
    public void manageShow() {
        
        while (true) {
            String message = new String("\n1. 전체 상영정보 관리 2. 신규 상영정보 등록 3. 뒤로 가기 ");
            int userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);

            if (userChoice == 1) {
                // 상영정보 리스트
                printAll();
                
                message = new String("\n1. 수정 2. 삭제 3. 뒤로 가기");
                int num = ScannerUtil.nextInt(scanner, message, 1, 3);
                
                if(num == 1) {
                    // 수정
                    message = new String("\n수정하실 상영 정보의 번호를 입력해주세요.");
                    int editNum = ScannerUtil.nextInt(scanner, message, 1, size());
                    
                    update(editNum);
                    
                } else if(num == 2) {
                    // 삭제
                    message = new String("\n삭제하실 상영 정보의 번호를 입력해주세요.");
                    int deleteNum = ScannerUtil.nextInt(scanner, message, 1, size());
                    
                    delete(deleteNum);
                }

            } else if (userChoice == 2) {
                // 신규 상영정보 등록
                register();

            } else if (userChoice == 3) {
                System.out.println("\n메뉴 화면으로 돌아갑니다.");
                break;
            }
        }
    }

    // String to Cal
    // 상영날짜 String 데이터타입을 Calendar로 변환하는 메소드
    private Calendar StringtoCal(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
        Calendar cal = Calendar.getInstance();
        try {
            Date date = sdf.parse(str);
            cal.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }
    // 상영시간 String 데이터타입을 calendar로 변환하는 메소드
    private Calendar StringtoCalTime(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("hhmm");
        Calendar cal = Calendar.getInstance();
        try {
            Date date = sdf.parse(str);
            cal.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }
    
    // 전체 상영 정보
    public void printAll() {

        ArrayList<ShowInfoDTO> list = showInfoController.selectAll();

        System.out.println("\n| NO | 상영극장 | 상영관 | 상영작 |  상 영 날 짜  |    상  영  시  간    |");
        for (ShowInfoDTO s : list) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
            SimpleDateFormat ert = new SimpleDateFormat("HH시 mm분");

            Calendar table = StringtoCal(s.getTable());
            Calendar time = StringtoCalTime(s.getTime());

            int runTime = movieViewer.movieRuntime(s.getMovieId());
            
            // 끝나는 시간
            Calendar endTime = Calendar.getInstance();
            endTime.setTime(time.getTime());
            endTime.add(Calendar.MINUTE, runTime);

            String ta = sdf.format(table.getTime());
            String ti = ert.format(time.getTime());
            String en = ert.format(endTime.getTime());
            
            System.out.println("| " + s.getId() + " | " + theaterViewer.theaterName(s.getTheaterId()) + " | " +
                   boxViewer.boxName(s.getTheaterId(), s.getBoxId()) + " | " + movieViewer.movieTitle(s.getMovieId()) 
                   + " | " + ta + " | " + ti + " - " + en + " |");
        }
        System.out.println();
    }
    // 코멘트 개수
    private int size() {
        return showInfoController.selectAll().size();
    }

    // 특정 극장 상영 정보
    public void printBytheaterId(int theaterId) {
        ArrayList<ShowInfoDTO> list = showInfoController.theaterBy(theaterId);

        System.out.println("\n"+ theaterViewer.theaterName(theaterId) + "상영 리스트");
        System.out.println("\n| NO | 상 영 관 | 상 영 작 |  상 영 날 짜  |   상 영 시 간   |");

        for (ShowInfoDTO s : list) {
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
            SimpleDateFormat ert = new SimpleDateFormat("HH시 mm분");

            Calendar table = StringtoCal(s.getTable());
            Calendar time = StringtoCalTime(s.getTime());

            int runTime = movieViewer.movieRuntime(s.getMovieId());

            Calendar endTime = Calendar.getInstance();
            endTime.setTime(time.getTime());
            endTime.add(Calendar.MINUTE, runTime);

            String ta = sdf.format(table.getTime());
            String ti = ert.format(time.getTime());
            String en = ert.format(endTime.getTime());

            System.out.println("| " + s.getId() + " | " + boxViewer.boxName(theaterId, s.getBoxId()) + " | " 
                        + movieViewer.movieTitle(s.getMovieId()) + " | " + ta + " | " + ti + " - " + en + " |");
        }
        System.out.println();

    }
    
    // 특정 극장 특정 영화 상영 정보
    public void printBytheaterId(int theaterId, int movieId) {
        ArrayList<ShowInfoDTO> list = showInfoController.theaterBy(theaterId);

        System.out.println("\n"+ theaterViewer.theaterName(theaterId) +"의 "+movieViewer.movieTitle(movieId)+ "상영 리스트");
        System.out.println("\n| NO | 상 영 관 |  상 영 날 짜  |   상 영 시 간   |");

        for (ShowInfoDTO s : list) {
            if(s.getMovieId() == movieId) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
                SimpleDateFormat ert = new SimpleDateFormat("HH시 mm분");

                Calendar table = StringtoCal(s.getTable());
                Calendar time = StringtoCalTime(s.getTime());

                int runTime = movieViewer.movieRuntime(s.getMovieId());

                Calendar endTime = Calendar.getInstance();
                endTime.setTime(time.getTime());
                endTime.add(Calendar.MINUTE, runTime);

                String ta = sdf.format(table.getTime());
                String ti = ert.format(time.getTime());
                String en = ert.format(endTime.getTime());

                System.out.println("| " + s.getId() +" | " + boxViewer.boxName(theaterId, s.getBoxId()) +" | "
                           + ta + " | " + ti + " - " + en + " |");
            }
        }
        System.out.println();
    }

    // 특정 영화 상영 정보
    public void printBymovieId(int movieId) {
        ArrayList<ShowInfoDTO> list = showInfoController.movieBy(movieId);

        System.out.println("\n"+ movieViewer.movieTitle(movieId) + "상영 정보");
        System.out.println("\n| NO | 상 영 극 장 | 상 영 관 |  상 영 날 짜  |   상 영 시 간   |");

        for (ShowInfoDTO s : list) {
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
            SimpleDateFormat ert = new SimpleDateFormat("HH시 mm분");

            Calendar table = StringtoCal(s.getTable());
            Calendar time = StringtoCalTime(s.getTime());

            int runTime = movieViewer.movieRuntime(s.getMovieId());

            Calendar endTime = Calendar.getInstance();
            endTime.setTime(time.getTime());
            endTime.add(Calendar.MINUTE, runTime);

            String ta = sdf.format(table.getTime());
            String ti = ert.format(time.getTime());
            String en = ert.format(endTime.getTime());

            System.out.println("| " + s.getId() + " | " + theaterViewer.theaterName(s.getTheaterId()) + " | " +
                    boxViewer.boxName(s.getTheaterId(), s.getBoxId()) + " | " + ta + " | " + ti + " - " + en + " |");
        }
        System.out.println();
    }
    
    // 특정 영화 상영 극장 정보 -> movieViewer 연결
    public void printBymovieIdByTheater(int movieId) {
        ArrayList<TheaterDTO> list2 = theaterViewer.theaterlist();

        System.out.println("\n"+ movieViewer.movieTitle(movieId) + " 상영 극장");
        System.out.println("\n| NO | 상 영 극 장 |");
        for(TheaterDTO t : list2) {
            if(showInfoController.showOK(t.getId(), movieId)) {
                System.out.println("| " + t.getId() + " | " + t.getBranch() + " |");
            }
        }
        
        String message = new String("\n상영 스케쥴이 궁금한 극장의 번호를 입력해주세요.\n뒤로가시려면 0번을 입력해주세요.");
        int tid = ScannerUtil.nextInt(scanner, message, 0, theaterViewer.theaterSize());
        
        if(tid != 0) {
            printBytheaterId(tid, movieId);
        }
    }
    
    // 상영 정보 등록 메소드 - register()
    private void register() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
        
        // 오늘부터 2주치 날짜 상영 시간표만 등록 가능
        Calendar cal = Calendar.getInstance();
        cal.getTime();

        Calendar aft = Calendar.getInstance();
        aft.getTime();
        aft.add(Calendar.DATE, +14);

        ShowInfoDTO s = new ShowInfoDTO();

        // 영화 리스트
        movieViewer.nowMovieList();
        movieViewer.preMovieList();
        String message = new String("\n등록할 상영 영화의 번호를 입력해주세요.");
        s.setMovieId(ScannerUtil.nextInt(scanner, message, 1, movieViewer.movieSize()));

        // 극장 리스트
        System.out.println();
        theaterViewer.printAll();
        message = new String("\n상영할 극장의 번호를 입력해주세요.");
        int theater = ScannerUtil.nextInt(scanner, message, 1, theaterViewer.theaterSize());
        s.setTheaterId(theater);
        
        // 상영관 리스트
        boxViewer.listbyTheaterId(s.getTheaterId());
        message = new String("\n상영할 상영관의 번호를 입력해주세요.");
        s.setBoxId(ScannerUtil.nextInt(scanner, message));
        
        message = new String("\n상영일을 입력해주세요. (yyyyMMdd)\n(" + sdf.format(aft.getTime()) + "까지 등록 가능)");
        String showDay = ScannerUtil.birthnextLine(scanner, message);
        
        while(StringtoCal(showDay).after(aft)) {
            System.out.println("\n등록 가능한 상영일이 아닙니다.");
            message = new String("\n상영일을 다시 입력해주세요. (yyyyMMdd)");
            showDay = ScannerUtil.birthnextLine(scanner, message);
        }

        message = new String("\n상영 시간을 입력해주세요. (HHmm)");
        String showTime = ScannerUtil.timenextLine(scanner, message);

        message = new String("\n정말 등록하시겠습니까? (Y/N)");
        String yesNo = ScannerUtil.yesNonextLine(scanner, message);

        if (yesNo.equalsIgnoreCase("Y")) {
            message = new String("\n관리자 비밀번호를 입력해주세요.");
            String password = ScannerUtil.nextLine(scanner, message);

            if (password.equals(logIn.getPassword())) {
                s.setTable(showDay);
                s.setTime(showTime);
                
                showInfoController.insert(s);
                
                System.out.println("\n상영 정보가 등록되었습니다.");

            } else {
                System.out.println("\n비밀번호가 틀려 등록에 실패하였습니다.");
            }
        }

    }

    // 상영 정보 수정
    private void update(int id) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
        // 오늘부터 2주치 날짜 상영 시간표만 등록 가능
        Calendar cal = Calendar.getInstance();
        cal.getTime();

        Calendar aft = Calendar.getInstance();
        aft.getTime();
        aft.add(Calendar.DATE, +14);
        
        // 기존 상영정보 불러오기
        ShowInfoDTO s = showInfoController.selectOne(id);

        String message = new String("\n새로운 상영 날짜를 입력해주세요. (yyyyMMdd)\n(" + sdf.format(aft.getTime()) + "까지 등록 가능)");
        String tabel = ScannerUtil.birthnextLine(scanner, message);
        
        if(StringtoCal(tabel).after(aft)) {
            System.out.println("등록 가능한 상영일이 아닙니다.");
            message = new String("\n상영일을 다시 입력해주세요. (yyyyMMdd)");
            tabel = ScannerUtil.birthnextLine(scanner, message);
        }

        message = new String("\n새로운 시간을 입력해주세요. (military time: HHmm)");
        String time = ScannerUtil.timenextLine(scanner, message);

        message = new String("\n정말 수정하시겠습니까? (Y/N)");
        String yesNo = ScannerUtil.yesNonextLine(scanner, message);

        if (yesNo.equalsIgnoreCase("Y")) {
            message = new String("\n관리자 비밀번호를 입력해주세요.");
            String password = ScannerUtil.nextLine(scanner, message);

            if (password.equals(logIn.getPassword())) {
                s.setTable(tabel);
                s.setTime(time);
                
                showInfoController.update(s);
                System.out.println("\n상영 정보가 수정되었습니다.");

            } else {
                System.out.println("\n비밀번호가 틀려 수정에 실패하였습니다");
            }
        }
    }

    // 상영 정보 삭제
    private void delete(int id) {
        String message = new String("\n정말 삭제하시겠습니까? (Y/N)");
        String yesNo = ScannerUtil.yesNonextLine(scanner, message);

        if (yesNo.equalsIgnoreCase("Y")) {
            message = new String("\n관리자 비밀번호를 입력해주세요.");
            String password = ScannerUtil.nextLine(scanner, message);

            if (password.equals(logIn.getPassword())) {
                showInfoController.delete(id);
                System.out.println("\n상영 정보가 삭제되었습니다.");

            } else {
                System.out.println("\n비밀번호가 틀려 삭제에 실패하였습니다.");
            }
        }
    }

    // 극장 삭제 시 해당 극장 상영 정보 삭제
    public void deleteBytheaterId(int theaterId) {
        System.out.println("\n해당 극장의 상영 정보가 삭제되었습니다.");
        showInfoController.deleteBytheater(theaterId);
    }

    // 영화 삭제 시 해당 영화 상영 정보 삭제
    public void deleteByMovieId(int movieId) {
        System.out.println("\n해당 영화의 상영 정보가 삭제되었습니다.");
        showInfoController.deleteBymovie(movieId);
    }

}
