package kr.co.kmarket.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagingDTO {
    private int groupStart;     // 페이지 그룹 시작 값
    private int groupEnd;       // 페이지 그룹 끝 값
    private int currentPage;    // 현재 페이지 번호
    private int lastPage;       // 마지막 페이지 번호
    private int start;
}
