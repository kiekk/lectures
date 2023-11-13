package com.shyoon.wms.location.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
