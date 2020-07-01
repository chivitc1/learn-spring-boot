package com.example.demo.report;

import com.example.demo.entitybase.UniqueIdGenerator;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class ReportRepositoryImpl implements ReportRepositoryCustom {

    private final UniqueIdGenerator<UUID> generator;

    @Override
    public ReportId nextId() {
        return new ReportId(generator.nextUniqueId());
    }
}
