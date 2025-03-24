package com.mtf.kibs.mapper;

import com.mtf.kibs.dto.CommCodeDTO;
import com.mtf.kibs.dto.FileDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommMapper {
    List<CommCodeDTO> getCommCodeList(CommCodeDTO commCodeDTO);

    void updateFileDeleteUseN(FileDTO fileDTO);

    Integer updateFileParentSeq(FileDTO fileDTO);

    List<FileDTO> selectFileParentSeqList(FileDTO fileDTO);
}