package com.crossover.techtrial.service;

import java.util.List;
import java.util.LongSummaryStatistics;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.crossover.techtrial.model.HourlyElectricity;

/**
 * HourlyElectricityService interface for all services realted to HourlyElectricity.
 * @author Crossover
 *
 */
public interface HourlyElectricityService {
  HourlyElectricity save(HourlyElectricity hourlyElectricity);
  
  Page<HourlyElectricity> getAllHourlyElectricityByPanelId(Long panelId, Pageable pageable);
  
  public List<LongSummaryStatistics> getDaylyElectricityByPanelId(Long panelId, Pageable pageable, int dayFrom,
			int dayTo, int year);
}
