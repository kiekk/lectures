package com.shyoon.wms.location.feature;

import com.shyoon.wms.inbound.domain.LPN;
import com.shyoon.wms.inbound.domain.LPNRepository;
import com.shyoon.wms.location.domain.Location;
import com.shyoon.wms.location.domain.LocationRepository;
import org.springframework.util.Assert;

class AssignLocationLPN {

    private final LocationRepository locationRepository;
    private final LPNRepository lpnRepository;

    AssignLocationLPN(LocationRepository locationRepository, LPNRepository lpnRepository) {
        this.locationRepository = locationRepository;
        this.lpnRepository = lpnRepository;
    }

    public void request(final Request request) {
        final Location location = locationRepository.getByLocationBarcode(request.locationBarcode);
        final LPN lpn = lpnRepository.getByLPNBarcode(request.lpnBarcode);

        location.assignLPN(lpn);
    }


    public record Request(
            String locationBarcode,
            String lpnBarcode) {

        public Request {
            Assert.hasText(locationBarcode, "로케이션 바코드는 필수입니다.");
            Assert.hasText(lpnBarcode, "LPN 바코드는 필수입니다.");
        }
    }
}
