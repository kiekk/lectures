package com.inflearn.toby.order;

import java.math.BigDecimal;

public record OrderRequest(String no, BigDecimal total) {

}
