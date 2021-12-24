package viewer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import controller.CommentController;
import model.CommentDTO;
import model.MovieDTO;
import model.UserDTO;
import util.ScannerUtil;

public class CommentViewer {
    private Scanner scanner;
    private CommentController commentController;
    private UserDTO logIn;
    
    private UserViewer userViewer;
    private MovieViewer movieViewer;
    
    // 일반 관람객
    private final int RATE_A = 1;
    // 평론가
    private final int RATE_B = 2;
    
    private final String FORMAT = "yyyy/MM/dd";

    // 생성자
    public CommentViewer() {
        commentController = new CommentController();
    }
    
    // setter
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
    
    public void setLogin(UserDTO logIn) {
        this.logIn = logIn;
    }
    
    public void setUserViewer(UserViewer userViewer) {
        this.userViewer = userViewer;
    }
    
    public void setMovieViewer(MovieViewer movieViewer) {
        this.movieViewer = movieViewer;
    }
    
 //----------------------------------------------------------------------------------------------------------------------   
    // 메뉴 코멘트
    public void showComment() {
        while(true) {
            String message = new String("\n1. 전체 영화 평점 2. 영화 코멘트 3. 평론가 코멘트 4. 실관람객 코멘트  5. 평가하기 6. 뒤로 가기");
            int userChoice = ScannerUtil.nextInt(scanner, message, 1, 6);
            
            if(userChoice == 1) {
                // 전체 영화 평점
                allMovie();
                
            } else if(userChoice == 2) {
                // 영화 코멘트
                // 영화 리스트 출력
                movieViewer.movieList();
                
                message = new String("\n코멘트를 조회하시려면 영화 번호를 입력해주세요.\n뒤로 가시려면 0번을 입력해주세요.");
                int num = ScannerUtil.nextInt(scanner, message, 0, movieViewer.movieSize());
                
                if(num != 0) {
                    // 특정 영화 코멘트 출력
                    printMovieScoreOne(num);
                }
                
            } else if(userChoice == 3) {
                // 전문 평론가 코멘트
                commentB();
                
            } else if(userChoice == 4) { 
                // 실관람객 코멘트
                commentA();
            
            } else if(userChoice == 5) {
                // 코멘트 작성하기
                edit();
                
            } else if(userChoice == 6) {
                System.out.println("이전 화면으로 돌아갑니다.");
                break;
            }
        }
    }
    
    // 영화 전체 평균
    private void allMovie() {
        
        ArrayList<MovieDTO> list = movieViewer.movieList2();
        System.out.println("\n-----------------------------------------------------------------------------------------");
        System.out.println("\n| NO | 영화 | 평점 |");
        System.out.println("-----------------------------------------------------------------------------------------");
        for(MovieDTO m : list) {
            System.out.print("| " + m.getId() + " | " +m.getTitle() +" | ");
            System.out.printf("%.1f |\n", commentController.movieAverage(m.getId()));
        }
        System.out.println("-----------------------------------------------------------------------------------------\n");
    }
    
      
    // 특정 영화 전체 평점 -> movieViewer에 연결
    public void printMovieScoreOne(int movieId) {
        
        ArrayList<CommentDTO> list = commentController.selectMovie(movieId);
        System.out.println("< "+ movieViewer.movieTitle(movieId) + " >");
        printMovieC(list);
    }
    
    // 특정 영화 코멘트 출력
    private void printMovieC(ArrayList<CommentDTO> list) {
        // 작성일자/수정일자 출력 형식 - yyyy/MM/dd
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
        
        System.out.println("\n-----------------------------------------------------------------------------------------");
        System.out.println("전체 " + list.size());
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("| NO | 작성자 | 평 점 |   기 대 평   |   코 멘 트   |  작 성 일 자  |  수 정 일 자  |");
        System.out.println("-----------------------------------------------------------------------------------------");
        
        for(CommentDTO c : list) {
            if(c.getExpReview().equals("")) {
                // 기대평이 없을 경우 없음 표시
                String exp = "없 음";
                System.out.println("| " + c.getId() +" | " + userViewer.userNicknameById(c.getUserId()) +" | " 
                        + c.getRate() + " | " + exp + " | " + c.getReview() 
                + " | " + sdf.format(c.getDate().getTime()) + " | " + sdf.format(c.getEditDate().getTime()) + " |");
            
            } else if (c.getReview().equals("")) {
                // 코멘트가 없을 경우 없음 표시
                String rev = "없 음";
                System.out.println("| " + c.getId() +" | " + userViewer.userNicknameById(c.getUserId()) +" | " 
                        + c.getRate() + " | " + c.getExpReview() + " | " + rev 
                + " | " + sdf.format(c.getDate().getTime()) + " | " + sdf.format(c.getEditDate().getTime()) + " |");
            } else {
                // 둘 다 존재할 경우 정상 표시
                System.out.println("| " + c.getId() + " | "+ movieViewer.movieTitle(c.getMovieId()) +" | " 
                        + c.getRate() + " | " + c.getExpReview() + " | " + c.getReview() 
                + " | " + sdf.format(c.getDate().getTime()) + " | " + sdf.format(c.getEditDate().getTime()) + " |");
            }
        }
        System.out.println("-----------------------------------------------------------------------------------------");
    }
    
    // 전체 영화 코멘트 출력
    private void printAllMovieC(ArrayList<CommentDTO> list) {
        // 작성일자/수정일자 출력 형식 - yyyy/MM/dd
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
        
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("| NO | 작성자 | 영  화 |  평  점  |    기  대  평    |    코  멘  트    |  작 성 일 자  |  수 정 일 자  |");
        for(CommentDTO c : list) {
            
            if(c.getExpReview().equals("")) {
                // 기대평이 없을 경우 없음 표시
                String exp = "없 음";
                System.out.println("| " + c.getId() +" | " + userViewer.userNicknameById(c.getUserId()) + " | "+ movieViewer.movieTitle(c.getMovieId()) +" | " 
                        + c.getRate() + " | " + exp + " | " + c.getReview() 
                + " | " + sdf.format(c.getDate().getTime()) + " | " + sdf.format(c.getEditDate().getTime()) + " |");
            
            } else if(c.getReview().equals("")) {
                // 코멘트가 없을 경우 없음 표시
                String rev = "없 음";
                System.out.println("| " + c.getId() +" | " + userViewer.userNicknameById(c.getUserId()) + " | "+ movieViewer.movieTitle(c.getMovieId()) +" | " 
                        + c.getRate() + " | " + c.getExpReview() + " | " + rev 
                + " | " + sdf.format(c.getDate().getTime()) + " | " + sdf.format(c.getEditDate().getTime()) + " |");
            
            } else {
                // 둘 다 존재할 경우 정상 표시
                System.out.println("| " + c.getId() + " | "+ movieViewer.movieTitle(c.getMovieId()) +" | " 
                        + c.getRate() + " | " + c.getExpReview() + " | " + c.getReview() 
                + " | " + sdf.format(c.getDate().getTime()) + " | " + sdf.format(c.getEditDate().getTime()) + " |");
            }
        }
        System.out.println("-----------------------------------------------------------------------------------------\n");
    }
    
    // 특정인 코멘트 출력
    private void printOneC(ArrayList<CommentDTO> list) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
        
        System.out.println("| NO | 영  화 |  평  점  |    기  대  평    |    코  멘  트    |  작 성 일 자  |  수 정 일 자  |");
        for(CommentDTO c : list) {
            if(c.getExpReview().equals("")) {
                // 기대평 없을 경우
                String exp = "없 음";
                System.out.println("| " + c.getId() + " | "+ movieViewer.movieTitle(c.getMovieId()) +" | " 
                        + c.getRate() + " | " + exp + " | " + c.getReview() 
                + " | " + sdf.format(c.getDate().getTime()) + " | " + sdf.format(c.getEditDate().getTime()) + " |");
            
            } else if(c.getReview().equals("")) {
                // 코멘트 없을 경우
                String rev = "없 음";
                System.out.println("| " + c.getId() + " | "+ movieViewer.movieTitle(c.getMovieId()) +" | " 
                        + c.getRate() + " | " + c.getExpReview() + " | " + rev 
                + " | " + sdf.format(c.getDate().getTime()) + " | " + sdf.format(c.getEditDate().getTime()) + " |");
            
            } else {
                // 둘 다 있을 경우
                System.out.println("| " + c.getId() + " | "+ movieViewer.movieTitle(c.getMovieId()) +" | " 
                        + c.getRate() + " | " + c.getExpReview() + " | " + c.getReview() 
                + " | " + sdf.format(c.getDate().getTime()) + " | " + sdf.format(c.getEditDate().getTime()) + " |");
            }
        }
        System.out.println();
    }
    
    // 평론가 코멘트 출력
    private void commentB() {
        ArrayList<CommentDTO> list = commentController.commentB(RATE_B);
                
        String message = new String("\n1. 전체 평론가 코멘트 보기 2. 원하는 평론가 코멘트 보기 3. 뒤로 가기");
        int userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);
        if(userChoice == 1) {
            
            System.out.println("\n-----------------------------------------------------------------------------------------");
            System.out.println("전    체 " + list.size());
            System.out.println("평균 평점" + commentController.BAverage(RATE_B));
            printAllMovieC(list);
            
            
        } else if(userChoice == 2) {
            
            // 평론가 리스트
            userViewer.listB();
            
            message = new String("\n코멘트가 보고싶은 평론가의 번호를 입력해주세요.\n뒤로 가시려면 0번을 입력해주세요.");
            int choice = ScannerUtil.nextInt(scanner, message, 0, userViewer.size());
            
            while(RATE_B != userViewer.userRate(choice)) {
                System.out.println("\n평론가 번호를 잘못 입력하셨습니다.");
                message = new String("\n코멘트가 보고싶은 평론가의 번호를 입력해주세요.\n뒤로 가시려면 0번을 입력해주세요.");
                choice = ScannerUtil.nextInt(scanner, message, 0, userViewer.size());
                
                // 0 입력한 경우 while문 나가기
                if(choice == 0) {
                    break;
                }
            }
            
            if(choice != 0) {
                ArrayList<CommentDTO> rateB = commentController.selectBywriterId(choice);
                System.out.println("\n< 평론가 " + userViewer.userNicknameById(choice) + "의 코멘트 >");
                printOneC(rateB);
                
            } else {
                System.out.println("\n이전 화면으로 돌아갑니다.");
            }
            
        } else if(userChoice == 3) {
            System.out.println("\n이전 화면으로 돌아갑니다.");
        }
    }
    
    // 일반 관람객 코멘트 출력
    private void commentA() {
        ArrayList<CommentDTO> list = commentController.commentA(RATE_A);
        
        String message = new String("\n1. 전체 영화 코멘트 2. 영화별 코멘트 3. 뒤로 가기");
        int userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);
        
        if(userChoice == 1) {
            // 전체 영화 코멘트
            System.out.println("\n-----------------------------------------------------------------------------------------");
            System.out.println("전체 " + list.size());
            
            printAllMovieC(list);
            
        } else if(userChoice == 2) {
            // 영화별 코멘트
            // 영화 리스트
            movieViewer.movieList();
            message = new String("\n코멘트가 보고 싶은 영화의 번호를 입력해주세요.\n뒤로 가시려면 0번을 입력해주세요.");
            int num = ScannerUtil.nextInt(scanner, message, 0, movieViewer.movieSize());
            
            // 특정 영화 코멘트
            if(num != 0) {
                // 해당 영화의 실관람객 코멘트가 없을 경우를 따져야 한다.
                if(commentController.selectByUserRateByMovie(RATE_A, num)) {
                    // 해당 영화 코멘트 존재
                    System.out.println("< "+ movieViewer.movieTitle(num) + " >");
                    
                    printMovieC(list);
                    
                } else {
                    // 해당 영화 코멘트 미존재
                    System.out.println("\n아직 등록된 코멘트가 없습니다.");
                }
            }            
            
        } else if(userChoice == 3) {
            System.out.println("\n이전 화면으로 돌아갑니다.");
        }
    }
    
    
    // 상영예정작 코멘트 작성 - movieViewer 연결
    public void AeditE(int movieId) {
        while(true) {
            String message = new String("\n1. 기대평 보기 2. 기대평 작성 3. 뒤로 가기");
            int userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);
            
            if(userChoice == 1) {
                // 기대평 보기
                expComment(movieId);
                
            } else if(userChoice == 2) {
                // 기대평 작성
                writeExpComment(movieId);
                
            } else if(userChoice == 3) {
                System.out.println("\n이전 화면으로 돌아갑니다.");
                break;
            }
        }
    }
    
    // 기대평만 출력
    private void expComment(int movieId) {
        
        String message = new String("\n1. 전체 기대평 2. 평론가 기대평 3. 뒤로 가기");
        int userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);
        
        if(userChoice == 1) {
            // 전체 기대평
            allexpComment(movieId);
            
        } else if(userChoice == 2) {
            // 평론가 기대평
            BexpComment(movieId);
            
        } else if(userChoice == 3) {
            System.out.println("\n이전 화면으로 돌아갑니다.");
        }
    }
    
    // 특정 영화 평론가 기대평
    private void BexpComment(int movieId) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
        ArrayList<CommentDTO> list = commentController.commentB(RATE_B);
        System.out.println("\n-----------------------------------------------------------------------------------------");
        System.out.println(movieViewer.movieTitle(movieId) + " 평론가 기대평");
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("| NO | 작성자 |    기  대  평    |  작 성 일 자  |  수 정 일 자  |");
        for(CommentDTO c : list) {
            if(c.getMovieId() == movieId && c.getRateId() == RATE_B) {
                // 기대평 없을 경우 출력되지 않도록 조건식
                if(!c.getExpReview().equals("")) {
                    System.out.println("| " + c.getId() + " | "+ userViewer.userNicknameById(c.getUserId()) +" | " 
                            +  c.getExpReview() + " | " + sdf.format(c.getDate().getTime()) + " | " + sdf.format(c.getEditDate().getTime()) + " |");
                }
            }
        }
        System.out.println("-----------------------------------------------------------------------------------------\n");
    }
    
    // 특정 영화 전체 기대평
    private void allexpComment(int movieId) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
        ArrayList<CommentDTO> list = commentController.selectMovie(movieId);
        System.out.println("\n-----------------------------------------------------------------------------------------");
        System.out.println(movieViewer.movieTitle(movieId) + " 기대평");
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("| NO | 작성자 |    기  대  평    |  작 성 일 자  |  수 정 일 자  |");
        for(CommentDTO c : list) {
            if(!c.getExpReview().equals("")) {
             // 기대평 없을 경우 출력되지 않도록 조건식
                System.out.println("| " + c.getId() + " | "+ userViewer.userNicknameById(c.getUserId()) +" | " 
                        +  c.getExpReview() + " | " + sdf.format(c.getDate().getTime()) + " | " + sdf.format(c.getEditDate().getTime()) + " |");
            }
        }
        System.out.println("-----------------------------------------------------------------------------------------\n");
    }
    
    
//----------------------------------------------------------------------------------------------------    
    // 전문 평론가용 메뉴
    public void editComment() {
        
        while(true) {
            String message = new String("\n1. 내가 쓴 코멘트 보기 2. 새로운 코멘트 등록 3. 뒤로 가기");
            int userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);
            
            if(userChoice == 1) {
                // 코멘트 리스트
                printList();
                
            } else if(userChoice == 2) {
                // 등록
                edit();
                
            } else if(userChoice == 3) {
                // 뒤로 가기
                System.out.println("이전 화면으로 돌아갑니다.");
                break;
            }
        }
    }
    
    // 코멘트 등록
    private void edit() {
        while(true) {
            String message = new String("\n1. 현재 상영작 코멘트 작성 2. 상영 예정작 기대평 작성 3. 뒤로 가기");
            int userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);
            
            if(userChoice == 1) {
                // 현재 상영작 출력
                movieViewer.nowMovieList();
                
                message = new String("\n코멘트를 작성할 영화의 번호 또는 뒤로 가시려면 0번을 입력해주세요.");
                int num = ScannerUtil.nextInt(scanner, message, 0, movieViewer.movieList2().size());
                
                if(num != 0) {
                    // 특정 영화 코멘트 작성
                    writeComment(num);
                    
                } else {
                    System.out.println("\n이전 화면으로 돌아갑니다.");
                }
                
            } else if(userChoice == 2) {
                // 상영 예정작 출력
                movieViewer.preMovieList();
                
                message = new String("\n기대평을 작성할 영화의 번호 또는 뒤로 가시려면 0번을 입력해주세요.");
                int num = ScannerUtil.nextInt(scanner, message, 0, movieViewer.movieList2().size());
                
                if(num != 0) {
                    // 특정 영화 기대평 작성
                    writeExpComment(num);
                    
                } else {
                    System.out.println("\n이전 화면으로 돌아갑니다.");
                }
            } else if(userChoice == 3) {
                System.out.println("\n이전 화면으로 돌아갑니다.");
                break;
            }
        }
    }
    
    // 코멘트 작성
    // 현재 상영작은 기대평이 존재할 수도 있음!
    // logIn.getId가 movieId에 작성한 코멘트가 있는지 check
    // 있으면 기존 코멘트 불러와서 저장
    // 없다면 새로 코멘트 객체 만들어서 저장
    private void writeComment(int id) {
        // 기대평 존재 영화
        if(logIn.getId() == commentController.selectByWriterIdByMovie(logIn.getId(), id)) {
            // 기대평 존재 영화
            CommentDTO c = commentController.selectByWriterMovieComment(logIn.getId(), id);
            // 코멘트 작성
            writeC(c);
        
        // 기대평 미존재    
        } else {
            // 첫 코멘트 작성
            firstWrtie("코멘트", id);
        }
    }
    
    // 기대평 존재하는 userId 코멘트 작성
    private void writeC(CommentDTO c) {
        if(c.getReview().equals("")) {
            // 코멘트 없을 경우
            String message = new String("\n평점을 입력해주세요.(5점 만점 / 소수점 1자리까지)");
            c.setRate(ScannerUtil.nextDouble(scanner, message, 0, 5));
            
            message = new String("\n관람평을 입력해주세요.");
            c.setReview(ScannerUtil.nextLine(scanner, message));
            
            message = new String("\n정말 등록하시겠습니까? (Y/N)");
            String yesNo = ScannerUtil.yesNonextLine(scanner, message);
            
            if(yesNo.equalsIgnoreCase("Y")) {
                message = new String("\n비밀번호를 입력해주세요.");
                String password = ScannerUtil.nextLine(scanner, message);
                
                // 비밀번호 일치
                if(password.equals(logIn.getPassword())) {
                    commentController.update(c);
                    System.out.println("\n성공적으로 코멘트가 등록되었습니다!");
                    
                    // 작성한 코멘트 출력
                    printOne(c.getId());
                    
                } else {
                    System.out.println("\n비밀번호가 틀려 등록에 실패하였습니다.");
                }
            }
        } else {
            // 코멘트 있을 경우
            System.out.println("\n이미 코멘트를 작성한 영화입니다.");
        }
    }
    
    // 첫작성
    private void firstWrtie(String s, int movieId) {
        CommentDTO c = new CommentDTO();
        if(s.equals("코멘트")) {
            // 코멘트 등록
            if(c.getReview().equals("")) {
                // 코멘트 없을 경우
                c.setMovieId(movieId);
                c.setUserId(logIn.getId());
                c.setRateId(logIn.getUserRate());
                
                String message = new String("\n평점을 입력해주세요.(5점 만점 / 소수점 1자리까지)");
                c.setRate(ScannerUtil.nextDouble(scanner, message, 0, 5));
                
                message = new String("\n관람평을 입력해주세요.");
                c.setReview(ScannerUtil.nextLine(scanner, message));
                
                message = new String("\n정말 등록하시겠습니까? (Y/N)");
                String yesNo = ScannerUtil.yesNonextLine(scanner, message);
                
                if(yesNo.equalsIgnoreCase("Y")) {
                    message = new String("\n비밀번호를 입력해주세요.");
                    String password = ScannerUtil.nextLine(scanner, message);
                    
                    // 비밀번호 일치
                    if(password.equals(logIn.getPassword())) {
                        commentController.write(c);
                        System.out.println("\n성공적으로 코멘트가 등록되었습니다!");
                        
                        // 작성한 코멘트 출력
                        printOne(c.getId());
                        
                    } else {
                        System.out.println("\n비밀번호가 틀려 등록에 실패하였습니다.");
                    }
                }
                
            } else {
                // 코멘트 있을 경우
                System.out.println("\n이미 코멘트를 작성한 영화입니다.");
            }
            
        } else if(s.equals("기대평")) {
            // 기대평 등록
            if(c.getReview().equals("")) {
                c.setMovieId(movieId);
                c.setUserId(logIn.getId());
                c.setRateId(logIn.getUserRate());
                
                String message = new String("\n기대평을 입력해주세요.");
                c.setExpReview(ScannerUtil.nextLine(scanner, message));
                
                message = new String("\n정말 등록하시겠습니까? (Y/N)");
                String yesNo = ScannerUtil.yesNonextLine(scanner, message);
                
                if(yesNo.equalsIgnoreCase("Y")) {
                    message = new String("\n비밀번호를 입력해주세요.");
                    String password = ScannerUtil.nextLine(scanner, message);
                    
                    if(password.equals(logIn.getPassword())) {
                        commentController.write(c);
                        System.out.println("\n성공적으로 기대평이 등록되었습니다!");
                        printOne(c.getId());
                        
                    } else {
                        System.out.println("\n비밀번호가 틀려 등록에 실패하였습니다.");
                    }
                }
            } else {
                System.out.println("\n이미 관람하신 영화입니다.");
            }
        }
    }
    
    
    // 기대평 작성
    private void writeExpComment(int id) {
        // 이미 해당 영화에 기대평 작성했음
        if(logIn.getId() == commentController.selectByWriterIdByMovie(logIn.getId(),id)) {
            System.out.println("\n이미 기대평을 작성하셨습니다.");
            
            String message = new String("\n1. 수정 2. 삭제 3. 뒤로 가기");
            int userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);
            
            if(userChoice == 1) {
                // 수정
                updateComment(id);
                
            } else if(userChoice == 2) {
                // 삭제
                deleteComment(id);
            }
            
        } else {
            // 작성한 기대평 없음
            firstWrtie("기대평", id);
            
        }
    }
    
//-----------------------------------------------------------------------------------------------------------    
    // MYPAGE -> 회원 코멘트
    // userViewer 연결
    public void printList() {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
        
        // 해당 회원이 작성한 코멘트 리스트 불러오기
        ArrayList<CommentDTO> list = commentController.selectBywriterId(logIn.getId());
        
        System.out.println(" " + logIn.getNickname() + "님의 작성 코멘트");
        System.out.println("\n내가 본 영화 총 " + list.size() + " 편");
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("| NO |   영  화   |  평 점  |  작 성 일 자  |");
        for(CommentDTO c : list) {
            System.out.println("| " + c.getId() + " | " + movieViewer.movieTitle(c.getMovieId()) + " | " + c.getRate() 
                            + " | "+ sdf.format(c.getDate().getTime()) +" |");
        }
        System.out.println("------------------------------------------------------------------------------\n");
        
        String message = new String("\n상세보기할 코멘트의 번호 또는 뒤로 가시려면 0번을 입력해주세요.");
        int userChoice = ScannerUtil.nextInt(scanner, message, 0, list.size());
        
        if(userChoice != 0) {
            // 상세보기 메소드
            printOne(userChoice);
            
        } else {
            System.out.println("\n이전 화면으로 돌아갑니다.");
        }
    }
    
    // 코멘트 상세보기
    private void printOne(int id) { // id = comment id
        CommentDTO c = commentController.selectOne(id);
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
        
        System.out.println("------------------------------------------------------------------------------");
        // 내가 작성한 코멘트의 영화 정보 출력
        System.out.println("영화 정보");
        movieViewer.printMovie(c.getMovieId());
        System.out.println("------------------------------------------------------------------------------");
        // 코멘트 출력
        if(c.getExpReview().equals("")) {
            // 기대평 없을 경우
            System.out.println("|  평 점  |    코  멘  트    |  작 성 일 자  |  수 정 일 자  |");
            System.out.println("| " + + c.getRate() + " | " + c.getReview() 
            + " | " + sdf.format(c.getDate().getTime()) + " | " + sdf.format(c.getEditDate().getTime()) + " |");
        
        } else if(c.getReview().equals("")) {
            // 코멘트 없을 경우
            System.out.println("|  평 점  |    기  대  평    |  작 성 일 자  |  수 정 일 자  |");
            System.out.println("| " + + c.getRate() + " | " + c.getExpReview() + " | "  
            + " | " + sdf.format(c.getDate().getTime()) + " | " + sdf.format(c.getEditDate().getTime()) + " |");
        } else {
            // 둘 다 있을 경우
            System.out.println("|  평 점  |    기  대  평    |    코  멘  트    |  작 성 일 자  |  수 정 일 자  |");
            System.out.println("| " + + c.getRate() + " | " + c.getExpReview() + " | " + c.getReview() 
            + " | " + sdf.format(c.getDate().getTime()) + " | " + sdf.format(c.getEditDate().getTime()) + " |");
        }
        System.out.println("------------------------------------------------------------------------------\n");
        
        String message = new String("\n1. 수정 2. 삭제 3. 뒤로 가기");
        int userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);
        
        if(userChoice == 1) {
            // 수정
            updateComment(id);
            
        } else if(userChoice == 2) {
            // 삭제
            deleteComment(id);
            
        }
    }
    
    // 수정 메소드
    private void updateComment(int id) {        
        CommentDTO c = commentController.selectOne(id);
        
        String message = new String("\n1. 기대평 수정 2. 코멘트 수정 3. 뒤로 가기");
        int userChoice = ScannerUtil.nextInt(scanner, message, 1, 3);
        
        if(userChoice == 1) {
            // 기대평 수정
            message = new String("\n새로운 기대평을 입력해주세요.");
            c.setExpReview(ScannerUtil.nextLine(scanner, message));
            
            message = new String("\n정말 수정하시겠습니까? (Y/N)");
            String yesNo = ScannerUtil.yesNonextLine(scanner, message);
            
            if(yesNo.equalsIgnoreCase("Y")) {
                message = new String("\n비밀번호를 입력해주세요.");
                String password = ScannerUtil.nextLine(scanner, message);
                
                if(password.equals(logIn.getPassword())) {
                    commentController.update(c);
                    System.out.println("\n성공적으로 수정이 완료되었습니다!");
                    printOne(id);
                } else {
                    System.out.println("\n비밀번호가 틀려 수정에 실패하였습니다.");
                }
            }
            
        } else if(userChoice == 2) {
            // 코멘트 수정
            message = new String("\n새로운 평점을 입력해주세요. (5점 만점 / 소수점 1자리까지)");
            c.setRate(ScannerUtil.nextDouble(scanner, message, 0, 5));
            
            message = new String("\n새로운 코멘트를 입력해주세요.");
            c.setReview(ScannerUtil.nextLine(scanner, message));
            
            message = new String("\n정말 수정하시겠습니까? (Y/N)");
            String yesNo = ScannerUtil.yesNonextLine(scanner, message);
            
            if(yesNo.equalsIgnoreCase("Y")) {
                message = new String("\n비밀번호를 입력해주세요.");
                String password = ScannerUtil.nextLine(scanner, message);
                
                if(password.equals(logIn.getPassword())) {
                    commentController.update(c);
                    System.out.println("\n성공적으로 수정이 완료되었습니다!");
                    printOne(id);
                } else {
                    System.out.println("\n비밀번호가 틀려 수정에 실패하였습니다.");
                }
            }
        }
    }
    
    // 삭제
    private void deleteComment(int id) {
        String message = new String("\n정말 삭제하시겠습니까? (Y/N)");
        String yesNo = ScannerUtil.yesNonextLine(scanner, message);
        
        if(yesNo.equalsIgnoreCase("Y")) {
            message = new String("\n비밀번호를 입력해주세요.");
            String password = ScannerUtil.nextLine(scanner, message);
            
            if(password.equals(logIn.getPassword())) {
                commentController.delete(id);
                System.out.println("\n성공적으로 삭제를 완료되었습니다!");
            } else {
                System.out.println("\n비밀번호가 틀려 삭제에 실패하였습니다.");
            }
        }
    }
    
    // 영화 삭제 시 코멘트 삭제
    public void deleteByMovieId(int movieId) {
        System.out.println("해당 영화의 코멘트가 모두 삭제되었습니다.");
        commentController.deleteByMovie(movieId);
    }
    
    // 회원 탈퇴 시 코멘트 삭제
    public void deleteByUserId(int userId) {
        System.out.println("해당 회원의 영화 코멘트가 모두 삭제되었습니다.");
        commentController.deleteByUser(userId);
    }
    
    

}
