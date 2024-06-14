package ai.inno.clever.service.events.database;

import ai.inno.clever.domain.FactoringRequest;
import ai.inno.clever.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;



@Component
public class FactoringRequestModelListener extends AbstractMongoEventListener<FactoringRequest> {

    private SequenceGeneratorService sequenceGenerator;

    @Autowired
    public FactoringRequestModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<FactoringRequest> event) {
        if (event.getSource().getSeqId() < 1) {
            event.getSource().setSeqId(sequenceGenerator.generateSequence(FactoringRequest.SEQUENCE_NAME));
        }
    }


}
