package com.mtf.kibs.service;

import com.mtf.kibs.dto.*;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;

public interface CommService {

    List<CommCodeDTO> getCommCodeList(CommCodeDTO commCodeDTO);

    SmsResponseDTO smsSend(SmsDTO smsDTO);

    String getSystemicSiteMap();

    void processUpdateFileDeleteUseN(FileDTO fileDTO);

    ResponseDTO processUpdateFileParentSeq(FileDTO fileDTO);

    List<FileDTO> processSelectFileParentSeqList(FileDTO fileDTO);
}