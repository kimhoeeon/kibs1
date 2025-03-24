package com.mtf.kibs.service.impl;

import com.google.gson.Gson;
import com.mtf.kibs.constants.CommConstants;
import com.mtf.kibs.dto.*;
import com.mtf.kibs.mapper.CommMapper;
import com.mtf.kibs.service.CommService;
import com.mtf.kibs.util.StringUtil;
import lombok.Setter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;

@Service
public class CommServiceImpl implements CommService {

    @Setter(onMethod_ = {@Autowired})
    private CommMapper commMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    @Override
    public List<CommCodeDTO> getCommCodeList(CommCodeDTO commCodeDTO) {
        System.out.println("CommServiceImpl > getCommCodeList : ======");
        return commMapper.getCommCodeList(commCodeDTO);
    }

    @Override
    public String getSystemicSiteMap() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n" +
                "  <!-- kibs.com 페이지들 -->\n" +
                "  <url>\n" +
                "    <loc>https://kibs.com/main.do</loc>\n" +
                "    <changefreq>daily</changefreq>\n" +
                "    <priority>0.9</priority>\n" +
                "  </url>\n" +
                "  <url>\n" +
                "    <loc>https://kibs.com/guide/notice.do</loc>\n" +
                "    <changefreq>monthly</changefreq>\n" +
                "    <priority>0.8</priority>\n" +
                "  </url>\n" +
                "  <url>\n" +
                "    <loc>https://kibs.com/exhibitor/guide.do</loc>\n" +
                "    <changefreq>monthly</changefreq>\n" +
                "    <priority>0.8</priority>\n" +
                "  </url>\n" +
                "  <url>\n" +
                "    <loc>https://kibs.com/visitor/guide_off.do</loc>\n" +
                "    <changefreq>monthly</changefreq>\n" +
                "    <priority>0.8</priority>\n" +
                "  </url>\n" +
                "  <url>\n" +
                "    <loc>https://kibs.com/board/gallery.do</loc>\n" +
                "    <changefreq>monthly</changefreq>\n" +
                "    <priority>0.8</priority>\n" +
                "  </url>\n" +
                "  <url>\n" +
                "    <loc>https://kibs.com/online/kibstv.do</loc>\n" +
                "    <changefreq>monthly</changefreq>\n" +
                "    <priority>0.8</priority>\n" +
                "  </url>\n" +
                "</urlset>";
    }

    /**
     * 단건 전송용
     * */
    @Override
    public SmsResponseDTO smsSend(SmsDTO smsDTO) {
        String senderParam = smsDTO.getSender();
        if(senderParam.contains("-")){
            senderParam = senderParam.replaceAll("-","");
        }
        String receiverParam = smsDTO.getReceiver();
        if(receiverParam.contains("-")){
            receiverParam = receiverParam.replaceAll("-","");
        }

        String result = "";
        try {
            final String encodingType = "UTF8";
            final String boundary = "____boundary____";

            /**************** 문자전송하기 예제 ******************/
            /* "result_code":결과코드,"message":결과문구, */
            /* "msg_id":메세지ID,"error_cnt":에러갯수,"success_cnt":성공갯수 */
            /* 동일내용 > 전송용 입니다.
            /******************** 인증정보 ********************/
            String sms_url = "https://apis.aligo.in/send/"; // 전송요청 URL

            Map<String, String> sms = new HashMap<String, String>();

            sms.put("user_id", "meetingfan"); // SMS 아이디
            sms.put("key", "ddefu9nx1etgljr1p1z1n9h7ri5u8mf0"); //인증키

            /******************** 인증정보 ********************/

            /******************** 전송정보 ********************/
            sms.put("msg", smsDTO.getMessage()); // 메세지 내용
            sms.put("receiver", receiverParam); // 수신번호
            sms.put("destination", ""/*smsDTO.getReceiver()+"|"+smsDTO.getCustomerName()*/); // 수신인 %고객명% 치환
            sms.put("sender", senderParam); // 발신번호
            sms.put("rdate", ""); // 예약일자 - 20161004 : 2016-10-04일기준
            sms.put("rtime", ""); // 예약시간 - 1930 : 오후 7시30분
            sms.put("testmode_yn", ""); // Y 인경우 실제문자 전송X , 자동취소(환불) 처리
            sms.put("title", "KIBS 경기국제보트쇼 온라인전시관 문의"); //  LMS, MMS 제목 (미입력시 본문중 44Byte 또는 엔터 구분자 첫라인)

            String image = "";
            //image = "/tmp/pic_57f358af08cf7_sms_.jpg"; // MMS 이미지 파일 위치

            /******************** 전송정보 ********************/

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();

            builder.setBoundary(boundary);
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName(encodingType));

            for (Iterator<String> i = sms.keySet().iterator(); i.hasNext(); ) {
                String key = i.next();
                builder.addTextBody(key, sms.get(key)
                        , ContentType.create("Multipart/related", encodingType));
            }

            File imageFile = new File(image);
            if (image != null && image.length() > 0 && imageFile.exists()) {

                builder.addPart("image",
                        new FileBody(imageFile, ContentType.create("application/octet-stream"),
                                URLEncoder.encode(imageFile.getName(), encodingType)));
            }

            HttpEntity entity = builder.build();

            HttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(sms_url);
            post.setEntity(entity);

            HttpResponse res = client.execute(post);

            if(res != null){
                BufferedReader in = new BufferedReader(new InputStreamReader(res.getEntity().getContent(), encodingType));
                String buffer = null;
                while((buffer = in.readLine())!=null){
                    result += buffer;
                }
                in.close();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Gson gson = new Gson();
        SmsResponseDTO responseDTO = gson.fromJson(uniToKor(result), SmsResponseDTO.class);
        System.out.println("Msg Send Response : " + responseDTO.toString());
        //{"result_code":"1","message":"success","msg_id":"583009869","success_cnt":1,"error_cnt":0,"msg_type":"SMS"}

        return responseDTO;
    }

    public String uniToKor(String uni){
        StringBuilder result = new StringBuilder();

        for(int i=0; i<uni.length(); i++){
            if(uni.charAt(i) == '\\' &&  uni.charAt(i+1) == 'u'){
                Character c = (char)Integer.parseInt(uni.substring(i+2, i+6), 16);
                result.append(c);
                i+=5;
            }else{
                result.append(uni.charAt(i));
            }
        }
        return result.toString();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public void processUpdateFileDeleteUseN(FileDTO fileDTO) {
        System.out.println("CommServiceImpl > processUpdateFileDeleteUseN");
        try {
            if(!StringUtil.isEmpty(fileDTO.getUserId())){
                commMapper.updateFileDeleteUseN(fileDTO);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateFileParentSeq(FileDTO fileDTO) {
        System.out.println("CommServiceImpl > processUpdateFileParentSeq");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(!StringUtil.isEmpty(fileDTO.getId())){

                result = commMapper.updateFileParentSeq(fileDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Update Fail] Id : " + fileDTO.getId();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Id Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateFileParentSeq ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<FileDTO> processSelectFileParentSeqList(FileDTO fileDTO) {
        System.out.println("CommServiceImpl > processSelectFileParentSeqList");
        return commMapper.selectFileParentSeqList(fileDTO);
    }

}