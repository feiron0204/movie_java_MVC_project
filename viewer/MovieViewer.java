package viewer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import controller.MovieController;
import model.MovieDTO;
import model.UserDTO;
import util.ScannerUtil;

public class MovieViewer {
    // 영화 정보 확인 및 등록/수정/삭제를 할 수 있는 메소드들이 담긴 클래스
    // 기본 field
    private Scanner scanner;
    private MovieController movieController;
    // 메소드에 필요한 옵션 field
    private UserDTO logIn;
    private CommentViewer commentViewer;
    private ShowInfoViewer showInfoViewer;
    private ActorViewer actorViewer;
    
    private final String FORMAT = "yyyyMMdd";
    
    // 생성자
    public MovieViewer() {
        movieController = new MovieController();
    }
    
    // setter
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
    
    public void setLogIn(UserDTO logIn) {
        this.logIn = logIn;
    }
    
    public void setCommentViewer(CommentViewer commentViewer) {
        this.commentViewer = commentViewer;
    }
    
    public void setShowInfoViewer(ShowInfoViewer showInfoViewer) {
        this.showInfoViewer = showInfoViewer;
    }
    
    public void setActorViewer(ActorViewer actorViewer) {
        this.actorViewer = actorViewer;
    }
    
//------------------------------------------------------------------------------------------
    
    // userViewer에 연결된 showMovie 메소드
    public void showMovie() {
        while(true) {
            String message = new String("\n1. 영화차트 2. 개봉예정 3. 코멘트 4. 뒤로 가기");
            int userChoice = ScannerUtil.nextInt(scanner, message, 1, 4);
            
            if(userChoice == 1) {
                // 현재 상영 중인 영화 리스트
                nowMovieList();
                // 현재 상영작 중 상세 보기할 영화를 볼 수 있는 메뉴 메소드
                selectNowMovie();
                
            } else if(userChoice == 2) {
                // 개봉 예정 영화 리스트
                preMovieList();
                // 개봉 예정작 중 상세 보기할 영화를 볼 수 있는 메뉴 메소드
                selectPreMovie();
                
            } else if(userChoice == 3) {
                // 코멘트 -> commentViewer에 연결
                commentViewer.showComment();
            
            }else if(userChoice == 4) {
                System.out.println("\n메인 화면으로 돌아갑니다.");
                break;
            }
        }
    }
    
    // string to calendar -> String으로 저장된 날짜를 calendar 데이터타입으로 변경하는 메소드
    private Calendar StringtoCal(String str) {
        // yyyyMMdd 형식으로 변환
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
        Calendar cal = Calendar.getInstance();
        try {
            Date date = sdf.parse(str);
            cal.setTime(date);
        } catch(ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }
    
    // 영화 리스트(번호, 제목만 출력) - moiveList()
    public void movieList() {
        ArrayList<MovieDTO> list = movieController.selectAll();
        System.out.println("\n| NO |  영   화  |");
        for(MovieDTO m : list) {
            System.out.println("| " + m.getId() + " | " + m.getTitle() + " |");
        }
        System.out.println();
    }
    
    // 영화 리스트(ArrayList 전달) - movieList2()
    public ArrayList<MovieDTO> movieList2() {
        return movieController.selectAll();
    }
    
    // 현재 상영작 리스트 메소드 - nowMovieList()
    // 현재 날짜 이후 개봉작은 상영예정작에 포함
    // 현재 날짜부터 1달 전 개봉작은 현재상영작에 포함
    // CommentViewer, ShowInforViewer에 연결되므로 public 접근제한자 사용
    public void nowMovieList() {
        // 현재 날짜
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        // 한달 전 날짜        
        Calendar bef = Calendar.getInstance();
        bef.getTime();
        bef.add(Calendar.MONTH, -1);
        
        ArrayList<MovieDTO> list = movieController.selectAll();
        
        System.out.println("\n < 현재 상영 중인 영화 리스트 > ");
        System.out.println("--------------------------------------------------------");
        System.out.println("| NO |  영  화  |  개  봉  일  |");
        
        for(MovieDTO m : list) {
            // 개봉일 년도, 월, 일로 나눠 
            String year = m.getRelease().substring(0, 4);
            String month = m.getRelease().substring(4, 6);
            String day = m.getRelease().substring(6);
            
            // 개봉일이 현재 날짜보다는 이전 && 개봉일이 한달 전 날짜보다는 이후 OR 개봉일이 현재 날짜인 경우에만 영화 리스트 출력
            if(StringtoCal(m.getRelease()).before(cal) && StringtoCal(m.getRelease()).after(bef)
                    || StringtoCal(m.getRelease()).equals(cal) ) {
                System.out.println("| " + m.getId() + " | " + m.getTitle() + " | " + year + "년 " + month + "월 " + day + "일 |");
            }
        }
        
        System.out.println("--------------------------------------------------------\n");
    }
    
    // 현재 상영작 중 상세 보기할 영화를 선택할 수 있는 메소드 - selectNorMovie()
    private void selectNowMovie() {
        String message = new String("\n1. 상세보기 2. 뒤로 가기");
        int userChoice = ScannerUtil.nextInt(scanner, message, 1, 2);
        
        if(userChoice == 1) {
            // 상세보기
            message = new String("\n상세 보기 할 영화 번호를 입력해 주세요.");
            int movieNum = ScannerUtil.nextInt(scanner, message, 1, movieController.sizeAll());
            
            // 현재 상영작이 맞는지 check
            // 현재 날짜
            Calendar cal = Calendar.getInstance();
            cal.getTime();
            // 한달 전 날짜        
            Calendar bef = Calendar.getInstance();
            bef.getTime();
            bef.add(Calendar.MONTH, -1);
            
            MovieDTO m = movieController.selectOne(movieNum);
            
            if(StringtoCal(m.getRelease()).before(cal) && StringtoCal(m.getRelease()).after(bef)
                    || StringtoCal(m.getRelease()).equals(cal) ) {
                // 선택한 영화 정보 출력
                printMovie(movieNum);
                // 코멘트 Viewer 연결 -> 해당 영화에 작성된 코멘트 출력
                commentViewer.printMovieScoreOne(movieNum);
                
                message = new String("\n1. 상영 극장 2. 상영 스케쥴 3. 뒤로 가기");
                int num2 = ScannerUtil.nextInt(scanner, message, 1, 3);
                
                if(num2 == 1) {
                    // 극장 리스트
                    showInfoViewer.printBymovieIdByTheater(movieNum);
                    
                } else if(num2 == 2) {
                    // 상영 리스트
                    showInfoViewer.printBymovieId(movieNum);
                    
                } else if(num2 == 3) {
                    System.out.println("\n이전 화면으로 돌아갑니다.");
                }
            } else {
                // 영화 번호 잘못 입력
                System.out.println("\n영화 번호를 잘못 입력하셨습니다.\n 이전 화면으로 돌아갑니다.");
            }
            
        } else if(userChoice == 2) {
            // 뒤로 가기
            System.out.println("\n메뉴 화면으로 돌아갑니다.");
        }
    }
    
    // 상영예정작 리스트 메소드 - preMovieList()
    // CommentViewer, ShowInforViewer에 연결되므로 public 접근제한자 사용
    public void preMovieList() {
        // 현재 날짜
        Calendar cal = Calendar.getInstance();
        cal.getTime();
            
        ArrayList<MovieDTO> list = movieController.selectAll();
        
        System.out.println("\n < 상영 예정 영화 리스트 > ");
        System.out.println("----------------------------");
        System.out.println("| NO |  영   화  | 개 봉 예 정 일 |");
        
        for(MovieDTO m : list) {
            String year = m.getRelease().substring(0, 4);
            String month = m.getRelease().substring(4, 6);
            String day = m.getRelease().substring(6);
            
            if(StringtoCal(m.getRelease()).after(cal)) {
                System.out.println("| " + m.getId() + " | " + m.getTitle() + " | " + year + "년 " + month + "월 " + day + "일 |");
            }
        }
        System.out.println("----------------------------\n");
    }
    
    // 상영 예정작 상세보기 메뉴 메소드 - selectPreMovie()
    // 상영 예정작이기 때문에 코멘트가 존재할 수 없기 때문에 CommentViewer 연결 메소드가 상이함
    private void selectPreMovie() {
        String message = new String("\n1. 상세보기 2. 뒤로가기");
        int movieNum = ScannerUtil.nextInt(scanner, message, 1, 2);
        
        if(movieNum == 1) {
            // 상세 보기
            message = new String("\n상세 보기 할 영화의 번호를 입력해주세요.");
            int num2 = ScannerUtil.nextInt(scanner, message, 1, movieController.sizeAll());
            
            // 현재 날짜
            Calendar cal = Calendar.getInstance();
            cal.getTime();
            
            MovieDTO m = movieController.selectOne(num2);
            // 상영 예정작 영화인지 check
            if(StringtoCal(m.getRelease()).after(cal)) {
                // 선택 상영 예정작 정보 출력
                printMovie(num2);
                
                // 코멘트 Viewer 연결
                message = new String("\n1. 기대평 2. 뒤로가기");
                int num3 = ScannerUtil.nextInt(scanner, message, 1, 2);
                if(num3 == 1) {
                    // commentViewer 상영 예정작 기대평 연결
                    commentViewer.AeditE(num2);
                }
            } else {
                // 영화 번호 잘못 입력
                System.out.println("영화 번호를 잘못 입력하셨습니다.\n이전 화면으로 돌아갑니다.");
            }
            
        } else if(movieNum == 2) {
            // 뒤로 가기
            System.out.println("\n메뉴 화면으로 돌아갑니다.");
        }
    }
    
    // printMovie()
    // commentViewer에 연결되므로 public 접근제한자 사용
    public void printMovie(int id) {
        // 특정 영화 MovieDTO 가져오기
        MovieDTO m = movieController.selectOne(id);
        
        // 현재 날짜
        Calendar cur = Calendar.getInstance();
        cur.getTime();
        
        // 개봉일 수 계산
        // 현재 날짜 - 개봉일 = 개봉일 수
        long diffSec = (cur.getTimeInMillis() - StringtoCal(m.getRelease()).getTimeInMillis())/1000;
        long diffDays = diffSec / (24*60*60) + 1;
        
        String year = m.getRelease().substring(0, 4);
        String month = m.getRelease().substring(4, 6);
        String day = m.getRelease().substring(6);
        
        System.out.println("\n-------------------------");
        System.out.println(m.getTitle());
        System.out.println("개 봉 일 | " + year + "년 " + month + "월 " + day + "일");
        System.out.println("개   봉 | " + diffDays + "일 째");
        System.out.println("\n기본정보");
        System.out.println(m.getSummary());
        System.out.println("등    급 | " + m.getRate());
        System.out.println("장    르 | " + m.getGenre());
        System.out.println("상영 시간 | " + m.getRunTime()+"분");
        System.out.println("\n출연/제작");
        System.out.println("감    독 |" + m.getDirector());
        // 출연진
        actorViewer.showActor(id);
        
        System.out.println();
        System.out.println("-------------------------\n");
    }
         
    
//-----------------------------------------------------------------------------------------------    
    // 관리자 메뉴
    public void manageMovie() {
        // 영화 리스트
        nowMovieList();
        preMovieList();
        
        while(true) {
            String message = new String("\n1. 영화 등록 2. 영화 수정 3. 영화 삭제 4. 뒤로 가기");
            int userChoice = ScannerUtil.nextInt(scanner, message, 1, 4);
            
            if(userChoice == 1) {
                // 등록
                registerMovie();
                
                // 등록된 영화가 리스트에 반영됐는지 확인하기 위해 영화 리스트 출력
                System.out.println();
                nowMovieList();
                preMovieList();
                
            } else if(userChoice == 2) {
                // 수정
                message = new String("\n수정할 영화의 번호를 입력하세요.\n뒤로 가려면 0번을 입력하세요.");
                int movieNum = ScannerUtil.nextInt(scanner, message, 0, movieController.sizeAll());
                
                if(movieNum != 0) {
                    // 수정 메소드
                    updateMovie(movieNum);
                }
                
            } else if(userChoice == 3) {
                // 삭제
                message = new String("\n삭제할 영화의 번호를 입력하세요.\n뒤로 가려면 0번을 입력하세요.");
                int movieNum = ScannerUtil.nextInt(scanner, message, 0, movieController.sizeAll());
                
                if(movieNum != 0) {
                    // 삭제 메소드
                    deleteMovie(movieNum);
                }
                
            } else if(userChoice == 4) {
                System.out.println("\n메인 화면으로 돌아갑니다.");
                break;
            }
        }
    }
    
    // 영화 상영 등급 리스트 메소드 - rateList()
    private void rateList() {
        System.out.println("\n------------------------------");
        System.out.println("전체관람가 - 모든 연령의 관람객 관람");
        System.out.println("12세관람가 - 12세 미만의 관람객 관람 불가");
        System.out.println("15세관람가 - 15세 미만의 관람객 관람 불가 (단, 부모 등 보호자 동반 시 관람 가능");
        System.out.println("18세관람가 - 18세 미만의 관람객 관람 불가");
        System.out.println("------------------------------\n");
    }
    
    // 영화 등록
    private void registerMovie() {
        MovieDTO m = new MovieDTO();
        System.out.println("======= 영화 등록 =======\n");
        
        String message = new String("\n영화의 제목을 입력해주세요.");
        String title = ScannerUtil.nextLine(scanner, message);
        
        message = new String("\n영화의 장르를 입력해주세요.");
        String genre = ScannerUtil.nextLine(scanner, message);
        
        // 상영 등급 리스트 출력
        rateList();
        message = new String("\n영화의 등급을 입력해주세요.");
        String rate = ScannerUtil.nextLine(scanner, message);
        
        message = new String("\n영화의 개봉일을 입력해주세요. (yyyyMMdd)");
        String release = ScannerUtil.birthnextLine(scanner, message);
        
        message = new String("\n영화의 줄거리를 입력해주세요.");
        String summary = ScannerUtil.nextLine(scanner, message);
        
        message = new String("\n영화의 러닝타임을 입력해주세요. (분 단위)");
        int runtime = ScannerUtil.nextInt(scanner, message);
        
        message = new String("\n감독의 이름을 입력해주세요.");
        String director = ScannerUtil.nextLine(scanner, message);
        
        m.setTitle(title);
        m.setGenre(genre);
        m.setRate(rate);
        m.setSummary(summary);
        m.setRelease(release);
        m.setDirector(director);
        m.setRunTime(runtime);
        
        movieController.register(m);
        
        // 배우 등록 시 영화 id가 필요하므로 영화 등록 후 actorViewer에 연결
        message = new String("\n출연진을 입력해주세요.");
        // actorViewer 연결
        actorViewer.insert(m.getId());
        
        System.out.println("성공적으로 영화 등록이 완료되었습니다.\n");
    }
    
    // 영화 수정 메소드 - updateMovie(int)
    private void updateMovie(int id) {
        MovieDTO m = movieController.selectOne(id);
        
        String message = new String("\n영화의 변경된 제목을 입력해주세요.");
        m.setTitle(ScannerUtil.nextLine(scanner, message));
        
        System.out.println();
        
        rateList();
        message = new String("\n영화의 변경된 등급을 입력해주세요.");
        m.setRate(ScannerUtil.nextLine(scanner, message));
        
        message = new String("\n영화의 변경된 개봉일을 입력해주세요. (yyyyMMdd)");
        m.setRelease(ScannerUtil.birthnextLine(scanner, message));
        
        message = new String("\n정말 수정하시겠습니까? (Y/N)");
        String yesNo = ScannerUtil.yesNonextLine(scanner, message);
        
        if(yesNo.equalsIgnoreCase("Y")) {
            message = new String("\n관리자 비밀번호를 입력해주세요.");
            String password = ScannerUtil.nextLine(scanner, message);
            
            if(password.equals(logIn.getPassword())) {
                System.out.println("\n성공적으로 수정이 완료되었습니다!");
                movieController.update(m);
                printMovie(id);
                // 영화 정보가 필요한 viewer에 바뀐 정보 전달하기
                
            } else {
                System.out.println("\n비밀번호가 틀려 수정에 실패하였습니다.");
            }
        } else {
            System.out.println("\n이전 화면으로 돌아갑니다.");
        }
    }
    
    // 영화 삭제
    private void deleteMovie(int id) {
        String message = new String("\n정말 삭제하시겠습니까? (Y/N)");
        String yesNo = ScannerUtil.yesNonextLine(scanner, message);
        
        if(yesNo.equalsIgnoreCase("Y")) {
            message = new String("\n관리자 비밀번호를 입력해주세요.");
            String password = ScannerUtil.nextLine(scanner, message);
            
            if(password.equals(logIn.getPassword())) {
                System.out.println("\n성공적으로 삭제가 완료되었습니다!");
                // 영화 정보가 필요한 viewer에 바뀐 정보 전달하기
                commentViewer.deleteByMovieId(id);
                showInfoViewer.deleteByMovieId(id);
                movieController.delete(id);
                actorViewer.deleteBymovieId(id);
                
            } else {
                System.out.println("\n비밀번호가 틀려 삭제에 실패하였습니다.");
            }
            
        } else {
            System.out.println("\n이전 화면으로 돌아갑니다.");
        }
    }
    
    // 영화 제목 전달
    public String movieTitle(int movieId) {
        return movieController.searchBymovieId(movieId);
    }
    
    // 영화 러닝타임 전달
    public int movieRuntime(int movieId) {
        return movieController.runTime(movieId);
    }
    
    // 영화 개수 -> CommentViewer 연결
    public int movieSize() {
        ArrayList<MovieDTO> list = movieController.selectAll();
        return list.size();
    }
}
