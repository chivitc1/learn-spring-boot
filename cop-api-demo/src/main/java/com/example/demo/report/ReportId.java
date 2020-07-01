package com.example.demo.report;

import com.example.demo.entitybase.AbstractEntityId;
import com.example.demo.user.ArtifactForFramework;

import java.util.UUID;

public class ReportId extends AbstractEntityId<UUID> {
    @ArtifactForFramework
    public ReportId() { }

    public ReportId(UUID id) {
        super(id);
    }
}
