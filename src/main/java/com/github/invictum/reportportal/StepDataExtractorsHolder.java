package com.github.invictum.reportportal;

import com.epam.reportportal.service.ReportPortal;
import com.github.invictum.reportportal.extractor.StepDataExtractor;
import net.thucydides.core.model.TestStep;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Hold all registered step extractors and proceed all passed {@link TestStep} objects
 * Entry point to pass {@link TestStep} through sequence of {@link StepDataExtractor}
 */
public class StepDataExtractorsHolder {

    private Set<StepDataExtractor> processors = new HashSet<>();

    public void register(StepDataExtractor... processors) {
        this.processors = new HashSet<>(Arrays.asList(processors));
    }

    public void proceed(TestStep step) {
        processors.forEach(processor -> {
            Collection<EnhancedMessage> logs = processor.extract(step.clone());
            logs.forEach(item -> ReportPortal.emitLog(item, item.getLevel().toString(), item.getDate()));
        });
    }
}
