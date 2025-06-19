package io.batch.apiservice.apiservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiInfo {

    private String url;

    @Builder.Default
    private List<? extends ApiRequestVO> apiRequestList = new ArrayList<>();

}
