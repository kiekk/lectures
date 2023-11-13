package com.shyoon.wms.inbound.feature;

public class LPNBarcodeAlreadyExistsException extends RuntimeException {

    private static final String MESSAGE = "LPN 바코드 %s는 이미 존재합니다.";

    public LPNBarcodeAlreadyExistsException(String lpnBarcode) {
        super(String.format(MESSAGE, lpnBarcode));
    }
}
