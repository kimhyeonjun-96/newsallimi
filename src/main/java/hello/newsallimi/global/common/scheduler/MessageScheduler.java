package hello.newsallimi.global.common.scheduler;

import hello.newsallimi.domain.auth.repository.AuthRepositoryImpl;
import hello.newsallimi.domain.auth.service.AuthService;
import hello.newsallimi.domain.member.repository.MemberRepository;
import hello.newsallimi.domain.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageScheduler {

    private final MessageSource messageSource;
    private final MessageService messageService;
    private final AuthService authService;
    private final AuthRepositoryImpl authRepositoryImpl;
    private final MemberRepository memberRepository;

    public void sendTodatNews(){

        log.info("====== sendTodatNews 메서드 실행 ======");

//        String authToken;
//        ListMessageDto msgDto = new ListMessageDto();
//        msgDto.setHeaderTitle("자세히 보기");
//        msgDto.setWebUrl("");
//        msgDto.setMobileUrl("");
//
//        List<ListMessageDto> msgDtoItemList = new ArrayList<>();
//        int idx = 1;
//        List<Member> users = memberRepository.findAll();
//        for (Member user : users) {
//            System.out.println("user.toString() = " + user.toString());
//
//            authToken = user.responseAuthToken();
//            System.out.println("authToken = " + authToken);
//            if (StringUtils.hasText(authToken)){
//                DefaultMessageDto myMsg = new DefaultMessageDto();
//                myMsg.setBtnTitle("자세히보기");
//                myMsg.setMobileUrl("");
//                myMsg.setObjType("text");
//                myMsg.setWebUrl("");
//                myMsg.setText("메시지 테스트입니다.");
//                messageService.sendMessage(AuthService.authCode, myMsg);
//            }else{
//                System.out.println("발급된 토큰이 없습니다.");
//            }
//        }

//        String naverNewsUrl = "https://news.naver.com/main/main.naver";
//        List<NewsInfoDto> newsInfoList = newsService.getNewsInfo();
//        String authToken = authRepository.getAuthToken();
//
//        ListMessageDto msgDto = new ListMessageDto();
//        msgDto.setHeaderTitle("오늘의 주요 뉴스");
//        msgDto.setWebUrl(naverNewsUrl);
//        msgDto.setMobileUrl(naverNewsUrl);
//
//        List<ListMessageDto> msgDtoItemList = new ArrayList<>();
//        int idx = 1;
//        for (NewsInfoDto newsInfo : newsInfoList) {
//            ListMessageDto msgDtoItem = new ListMessageDto();
//            String title = newsInfo.getTitle();
//
//            msgDtoItem.setTitle(title);
//            msgDtoItem.setDescription(newsInfo.getDescription());
//            msgDtoItem.setImageUrl("");
//            msgDtoItem.setImageWidth("50");
//            msgDtoItem.setImageHeight("50");
//            msgDtoItem.setWebUrl(newsInfo.getUrl());	// 클릭시 뉴스로 연결
//            msgDtoItem.setMobileUrl(newsInfo.getUrl());
//            msgDtoItemList.add(msgDtoItem);
//            if(idx % 3 == 0) {
//                msgDto.setDtoList(msgDtoItemList);
//                msgService.sendListMessage(authToken, msgDto);
//                msgDtoItemList = new ArrayList<>();
//            }
//            idx ++;
//        }
        log.info("====== sendTodatNews 메서드 종료 ======");
    }
}
