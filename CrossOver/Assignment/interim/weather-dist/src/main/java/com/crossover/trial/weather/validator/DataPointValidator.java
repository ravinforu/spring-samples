package com.crossover.trial.weather.validator;

import com.crossover.trial.weather.model.DataPoint;
import com.crossover.trial.weather.model.DataPointType;

public class DataPointValidator {
    public static boolean isValid(DataPointType pointType, DataPoint dp) {
        Mean mean;
        try {
            mean = pointType.getClass().getField(pointType.name()).getAnnotation(Mean.class);
        } catch (NoSuchFieldException noSuchFieldException) {
            return false;
        }
        if (mean.minMean() <= dp.getMean() && mean.isAvoidMax()) {
            return true;
        } else if (mean.minMean() <= dp.getMean() && mean.maxMean() > dp.getMean()) {
            return true;
        }
        return false;
    }
}
