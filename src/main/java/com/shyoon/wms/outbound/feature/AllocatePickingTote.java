package com.shyoon.wms.outbound.feature;

import com.shyoon.wms.location.domain.Location;
import com.shyoon.wms.location.domain.LocationRepository;
import com.shyoon.wms.outbound.domain.Outbound;
import com.shyoon.wms.outbound.domain.OutboundRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AllocatePickingTote {

    private final OutboundRepository outboundRepository;
    private final LocationRepository locationRepository;

    @PostMapping("/outbounds/allocate-picking-tote")
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public void request(@RequestBody @Valid final Request request) {
        final Outbound outbound = outboundRepository.getBy(request.outboundNo);
        final Location tote = locationRepository.getByLocationBarcode(request.toteBarcode);
        outbound.allocatePickingTote(tote);
    }

    public record Request(
            @NotNull(message = "출고 번호는 필수입니다.")
            Long outboundNo,
            @NotBlank(message = "토트 바코드는 필수입니다.")
            String toteBarcode) {
    }
}
