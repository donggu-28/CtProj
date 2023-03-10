package com.campus.CtProj.controller;

import com.campus.CtProj.domain.EnterDto;
import com.campus.CtProj.service.EnterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

//@Controller
//@ResponseBody
@RestController
public class EnterController {
    @Autowired
    EnterService service;

    // 방 입장하는 메서드
//    @ResponseBody
    @PostMapping("/enter/{bno}")   // /ch4/comments?bno=1085 POST
    // CommentDto 그대로 하면 안들어간다! 그래서 앞에 @RequestBody 를 붙여줘야한다
    public ResponseEntity<String> write(@RequestBody EnterDto dto, @PathVariable Integer bno, String user_id, HttpSession session) {    // 입력한 내용을 받아와야하니깐 CommentDto dto 해줘야한다.
        user_id = (String)session.getAttribute("id");
        dto.setRoom_bno(bno);
        dto.setUser_id(user_id);
        System.out.println(dto);

        try {
            if(service.enter(dto) != 1)
                throw new Exception("Enter failed. ");
            return new ResponseEntity<>("ENT_OK", HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("ENT_ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    // 방을 나가기
    // {cno} 이거는 밑에 그냥 쿼리스트링으로 한게 아니고 rest 방식으로 한 url의 일부이므로 @PathVariable 을 붙여준다.
    @DeleteMapping("/enter/{bno}")       // /comments/1 <-- 삭제할 방 번호
//    @ResponseBody
    // /comments/1?bno=1085         // 이 bno 는 그냥 쿼리스트링
    public ResponseEntity<String> remove(@PathVariable Integer bno, HttpSession session) throws Exception {
        String user_id = (String) session.getAttribute("id");


        try {
            System.out.println(bno);
            System.out.println(user_id);
            int rowCnt = service.remove(bno, user_id);
            System.out.println(rowCnt);

            if(rowCnt != 1)
                throw new Exception("Delete Failed");
            return new ResponseEntity<>("DEL_OK",HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("DEL_ERR",HttpStatus.BAD_REQUEST);
        }
    }

//   //  방안에 내용들을 수정하는 메서드
//    @ResponseBody
//    @PatchMapping("/rooms/{bno}")   // /ch4/comments/bno PATCH
//    // CommentDto 그대로 하면 안들어간다! 그래서 앞에 @RequestBody 를 붙여줘야한다
//    public ResponseEntity<String> modify(@PathVariable Integer bno,@RequestBody RoomDto dto)  {    // 입력한 내용을 받아와야하니깐 CommentDto dto 해줘야한다.
//        //        String writer = (String)session.getAttribute("id");
//
//        String writer = "17100725";
//        dto.setWriter(writer);
//        dto.setBno(bno);
//        System.out.println("dto = " + dto);
//
//        try {
//            if(service.modify(dto) != 1)
//                throw new Exception("Write failed. ");
//            return new ResponseEntity<>("MOD_OK", HttpStatus.OK);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>("MOD_ERR", HttpStatus.BAD_REQUEST);
//        }
//    }
//
//
//
//    // 모든 방을 가져온다
//    @RequestMapping("/rooms")            // comments?bno=1080 GET
////      @ResponseBody
//        public ResponseEntity<List<RoomDto>> list()  {
//        List<RoomDto> list = null;
//        try {
//            list =  service.getList();
//            // return 으로 그냥 list 를 보내는 것이 아니라 ResponseEntity<List<CommentDto>>(list, HttpStatus.OK) 를 쓴 이유는
//            // 그냥 list 로 보내면 오류가 나도 응답은 200번대로 나온다 그래서 responseEntity를 사용해서 list 에다가 + 상태코드도 같이 보내주게 한다.
//            return new ResponseEntity<List<RoomDto>>(list, HttpStatus.OK);   // 200
//        } catch (Exception e) {
//                    e.printStackTrace();
//            return new ResponseEntity<List<RoomDto>>(list, HttpStatus.BAD_REQUEST);      //400
//        }
//
//    }
}